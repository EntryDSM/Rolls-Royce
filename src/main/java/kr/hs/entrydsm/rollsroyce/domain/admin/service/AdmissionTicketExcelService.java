package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationPeriodNotOverException;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.InvalidFileException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.ApplicationCountFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel.AdmissionTicket;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.facade.ScoreFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class AdmissionTicketExcelService {

    private final S3Util s3Util;

    private final ApplicationFacade applicationFacade;
    private final ApplicationCountFacade applicationCountFacade;

    private final ScheduleFacade scheduleFacade;

    private final ScoreFacade scoreFacade;

    private final StatusFacade statusFacade;

    private final EntryInfoFacade entryInfoFacade;

    private final EntryInfoRepository entryInfoRepository;

    public void execute(HttpServletResponse response) {
        if (scheduleFacade.getScheduleByType(Type.END_DATE).isAfter(LocalDateTime.now())) {
            throw ApplicationPeriodNotOverException.EXCEPTION;
        }

        List<Long> result = new ArrayList<>();

        int lessCount = 0;
        List<Score> spareApplicationQueue = new ArrayList<>();

        for (ApplicationType type : ApplicationType.values()) {
            for (int i = 0; i < 2; i++) {
                List<Score> applicants = scoreFacade.queryScoreByApplicationTypeAndIsDaejeon(type, i != 0);
                int limitCount = applicationCountFacade.countOfApplicationTypeAndIsDaejeon(type, i != 0);
                System.out.println(type.name() + (i != 0) + " " + LocalDateTime.now());
                if (applicants.size() > limitCount) {
                    for (int j = 0; j < limitCount; j++) {
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

        System.out.println("전형별 점수" + " " + LocalDateTime.now());

        scoreFacade.listSort(spareApplicationQueue);

        System.out.println("정렬 후" + " " + LocalDateTime.now());

        if (spareApplicationQueue.size() < lessCount)
            result.addAll(spareApplicationQueue.parallelStream()
                    .map(Score::getReceiptCode)
                    .collect(Collectors.toList()));
        else {
            for (int i = 0; i < lessCount; i++) {
                result.add(spareApplicationQueue.remove(0).getReceiptCode());
            }
        }
        statusFacade.updateIsFirstRoundPass(result);
        System.out.println("저장 전" + " " + LocalDateTime.now());
        saveAllApplicantsExamCode();
        System.out.println("저장 후" + " " + LocalDateTime.now());
        getAdmissionTicket(response, result);
    }

    private void getAdmissionTicket(HttpServletResponse response, List<Long> applicantReceiptCodes) {
        AdmissionTicket admissionTicket = new AdmissionTicket();
        int x = 0;
        int y = 0;
        int count = 1;

        for (Long receiptCode : applicantReceiptCodes) {
            EntryInfo entryInfo = entryInfoFacade.getEntryInfoByCode(receiptCode);
            Application application;
            if (EducationalStatus.QUALIFICATION_EXAM.equals(entryInfo.getEducationalStatus()))
                application = applicationFacade.getQualification(receiptCode);
            else application = applicationFacade.getGraduation(receiptCode);

            String examCode = getExamCode(receiptCode);
            String name = entryInfo.getUserName();
            String middleSchool = application.getSchoolName();
            String area = Boolean.TRUE.equals(entryInfo.getIsDaejeon()) ? "대전" : "전국";
            String applicationType = entryInfo.getApplicationType().toString();

            byte[] imageBytes = s3Util.getObject(entryInfo.getPhotoFileName(), "entry_photo/");
            admissionTicket.format(
                    x * 17, y * 7, examCode, name, middleSchool, area, applicationType, String.valueOf(receiptCode));

            int index = admissionTicket.getWorkbook().addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            Drawing drawing = admissionTicket.getSheet().createDrawingPatriarch();
            ClientAnchor anchor;
            anchor = new XSSFClientAnchor(
                    0, 0, 0, 0, (short) (y * 7), 2 + (x * 17), (short) (2 + (y * 7)), 14 + (x * 17));
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            drawing.createPicture(anchor, index);

            count++;
            if (count % 3 == 0) {
                x++;
                y = 0;
            } else {
                y++;
            }
        }

        try {
            response.setCharacterEncoding("utf-8");
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
        for (ApplicationType type : ApplicationType.values()) {
            for (int i = 0; i < 2; i++) {
                List<EntryInfo> entryInfoList = entryInfoRepository.findAllDistanceByTypeAndDaejeon(type, i != 0);
                int entryInfoCount = entryInfoList.size();

                Stream.iterate(0, j -> j + 1).limit(entryInfoCount).forEach(j -> {
                    EntryInfo entryInfo = entryInfoList.get(j);
                    int index = j % 3 * (entryInfoCount / 3) + Math.min((entryInfoCount % 3), j % 3) + j / 3 + 1;
                    String examCode = createExamCode(entryInfo) + String.format("%03d", index);
                    statusFacade.saveStatus(statusFacade.getStatusByReceiptCode(entryInfo.getReceiptCode()));
                });
            }
        }
    }

    private StringBuilder createExamCode(EntryInfo entryInfo) {
        StringBuilder examCode = new StringBuilder();
        switch (entryInfo.getApplicationType()) {
            case COMMON:
                examCode.append(1);
                break;
            case MEISTER:
                examCode.append(2);
                break;
            default:
                examCode.append(3);
        }

        if (Boolean.TRUE.equals(entryInfo.getIsDaejeon())) examCode.append(1);
        else examCode.append(2);

        return examCode;
    }

    private String getExamCode(Long receiptCode) {
        return statusFacade.getStatusByReceiptCode(receiptCode).getExamCode();
    }
}
