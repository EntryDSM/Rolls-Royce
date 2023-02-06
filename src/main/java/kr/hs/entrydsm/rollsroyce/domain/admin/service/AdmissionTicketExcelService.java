package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationPeriodNotOverException;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.InvalidFileException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.ApplicationCountFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.AdmissionTicketRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.AdmissionTicketResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel.AdmissionTicket;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.facade.ScoreFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AdmissionTicketExcelService {

    private final ScheduleFacade scheduleFacade;
    private final EntryInfoRepository entryInfoRepository;

    public AdmissionTicketResponse execute(AdmissionTicketRequest request) {
        if (scheduleFacade.getScheduleByType(Type.END_DATE)
                .isAfter(LocalDateTime.now())) {
            throw ApplicationPeriodNotOverException.EXCEPTION;
        }

        List<EntryInfo> admissionTicket = entryInfoRepository.findByAdmissionTicket(
                request.getPhotoFileName(),
                request.getReceiptCode(),
                request.getName(),
                request.getSchoolName(),
                request.getApplicationType(),
                request.getIsDaejeon(),
                request.getExamCode()
        );

        return AdmissionTicketResponse.builder()
                .admissionTickets(
                        admissionTicket.stream().map(
                                        admissionTickets -> AdmissionTicketResponse.AdmissionTicket.builder()
                                                .photoFileName(admissionTickets.getPhotoFileName())
                                                .receiptCode(admissionTickets.getReceiptCode())
                                                .name(admissionTickets.getUserName())
                                                .applicationType(admissionTickets.getApplicationType().toString())
                                                .isDaejeon(admissionTickets.getIsDaejeon())
                                                .build()
                                )
                                .collect(Collectors.toList()))
                .schoolName(request.getSchoolName())
                .examCode(request.getExamCode())
                .build();
    }

}
