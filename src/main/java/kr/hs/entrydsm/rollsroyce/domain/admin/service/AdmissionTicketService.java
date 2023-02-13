package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationPeriodNotOverException;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.AdmissionTicketRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.AdmissionTicketResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdmissionTicketService {

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
