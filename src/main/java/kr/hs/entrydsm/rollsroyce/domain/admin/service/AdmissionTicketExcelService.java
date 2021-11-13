package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationPeriodNotOverException;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.InvalidFileException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.ApplicationCountFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel.AdmissionTicket;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.facade.ScoreFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.exception.RequestFailToOtherServerException;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.client.TmapApi;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.request.RouteRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.CoordinateResponse;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.RouteResponse;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.ss.usermodel.ClientAnchor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.apache.poi.hssf.usermodel.HSSFPicture.PICTURE_TYPE_PNG;

@RequiredArgsConstructor
@Service
public class AdmissionTicketExcelService {

	@Value("${tmap.app.key}")
	private String appKey;

	private final TmapApi tmapApi;
	private final S3Util s3Util;
	private final StatusFacade statusFacade;
	private final ScheduleFacade scheduleFacade;
	private final AdminFacade adminFacade;
	private final ScoreFacade scoreFacade;
	private final UserFacade userFacade;
	private final ApplicationCountFacade applicationCountFacade;

	public void execute(HttpServletResponse response) {
		adminFacade.getRootAdmin();

		if (!scheduleFacade.getScheduleByType(Type.END_DATE)
				.isAfter(LocalDateTime.now())) {
			throw ApplicationPeriodNotOverException.EXCEPTION;
		}

		List<Long> result = new ArrayList<>();

		int lessCount = 0;
		List<Score> spareApplicationQueue = new ArrayList<>();

		for(ApplicationType type : ApplicationType.values()) {
			for(int i = 0; i < 2; i++) {
				List<Score> applicants =
						scoreFacade.queryScoreByApplicationTypeAndIsDaejeon(type, i != 0);
				int limitCount = applicationCountFacade.countOfApplicationTypeAndIsDaejeon(type, i != 0);

				if(applicants.size() > limitCount) {
					for(int j = 0; j < limitCount; j++) {
						result.add(applicants.remove(0).getReceiptCode());
					}
					spareApplicationQueue.addAll(applicants);
				} else {
					result.addAll(applicants.parallelStream()
							.map(Score::getReceiptCode)
							.collect(Collectors.toList()));
					lessCount += limitCount - applicants.size();
				}
			}
		}

		scoreFacade.listSort(spareApplicationQueue);

		if(spareApplicationQueue.size() < lessCount)
			result.addAll(spareApplicationQueue.parallelStream()
					.map(Score::getReceiptCode)
					.collect(Collectors.toList()));
		else {
			for(int i = 0; i < lessCount; i++) {
				result.add(spareApplicationQueue.remove(0).getReceiptCode());
			}
		}
		saveAllApplicantsExamCode();
		getAdmissionTicket(response, result);
	}

	private void getAdmissionTicket(HttpServletResponse response, List<Long> applicantReceiptCodes) {
		AdmissionTicket admissionTicket = new AdmissionTicket();
		int x = 0;
		int y = 0;
		int count = 1;

		for(Long receiptCode : applicantReceiptCodes) {
			User user = userFacade.getUserByCode(receiptCode);
			Application application;
			if(user.getEducationalStatus().equals(EducationalStatus.QUALIFICATION_EXAM))
				application = user.getQualification();
			else application = user.getGraduation();

			String examCode = user.getExamCode();
			String name = user.getName();
			String middleSchool = application.getSchoolName();
			String area = user.getIsDaejeon().equals(Boolean.TRUE) ? "대전" : "전국";
			String applicationType = user.getApplicationType().toString();

			byte[] imageBytes = s3Util.getObject("images/" + user.getPhotoFileName());
			admissionTicket.format(x * 17,y * 7, examCode, name, middleSchool, area, applicationType, String.valueOf(receiptCode));

			int index = admissionTicket.getWorkbook().addPicture(imageBytes, PICTURE_TYPE_PNG);
			HSSFPatriarch patriarch = admissionTicket.getSheet().createDrawingPatriarch();
			HSSFClientAnchor anchor;
			anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) (y * 7),2 + (x * 17), (short) (2 + (y * 7)),14 + (x * 17));
			anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
			patriarch.createPicture(anchor, index);

			count++;
			if(count % 3 ==0) {
				x++;
				y = 0;
			} else {
				y++;
			}
		}

		try{
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			String formatFilename = "attachment;filename=\"";
			String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"));
			String fileName = new String((formatFilename + time + "수험표.xlsx\"").getBytes("KSC5601"), "8859_1");
			response.setHeader("Content-Disposition", fileName);

			admissionTicket.getWorkbook().write(response.getOutputStream());
		} catch (IOException e) {
			throw InvalidFileException.EXCEPTION;
		}
	}

	private void saveAllApplicantsExamCode() {
		List<User> users = statusFacade.findAllPassStatusTrue();
		List<User> userSort = new ArrayList<>(users);

		int commonDaejeon = 1;
		int commonNationwide = 1;
		int meisterDaejeon = 1;
		int meisterNationwide = 1;
		int socialDaejeon = 1;
		int socialNationwide = 1;

		for (User user : users) {
			StringBuilder examCode = new StringBuilder();
			switch (user.getApplicationType()) {
				case COMMON:
					examCode.append(1);
					break;
				case MEISTER:
					examCode.append(2);
					break;
				default:
					examCode.append(3);
			}
			if (user.getIsDaejeon().equals(Boolean.TRUE)) examCode.append(1);
			else examCode.append(2);

			user.updateExamCode(examCode.toString());
		}

		for (User user : users) {
			CoordinateResponse coordinate =
					tmapApi.getCoordinate(appKey, URLEncoder.encode(user.getAddress(), StandardCharsets.UTF_8));
			RouteResponse distance = tmapApi.routeGuidance(appKey,
					RouteRequest.builder()
							.startX(Double.parseDouble(coordinate.getLat()))
							.startY(Double.parseDouble(coordinate.getLon()))
							.build()
			);
			if(distance.getFeatureList().size() < 1)
				throw RequestFailToOtherServerException.EXCEPTION;
			user.updateDistance(distance.getTotalDistance());
		}

		userSort.sort((o1, o2) -> Double.compare(o2.getDistance(), o1.getDistance()));

		for(User user : userSort) {
			int examOrder = 0;
			String examCode = user.getExamCode();
			if(examCode.startsWith("11")) {
				examOrder = commonDaejeon++;
			} else if(examCode.startsWith("12")) {
				examOrder = commonNationwide++;
			} else if(examCode.startsWith("21")) {
				examOrder = meisterDaejeon++;
			} else if(examCode.startsWith("22")) {
				examOrder = meisterNationwide++;
			} else if(examCode.startsWith("31")) {
				examOrder = socialDaejeon++;
			} else if(examCode.startsWith("32")) {
				examOrder = socialNationwide++;
			}

			user.updateExamCode(user.getExamCode() + String.format("%03d", examOrder));
			statusFacade.saveStatus(user.getStatus());
		}
	}

}
