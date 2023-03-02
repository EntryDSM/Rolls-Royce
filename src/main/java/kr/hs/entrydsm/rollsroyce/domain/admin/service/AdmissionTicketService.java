package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationPeriodNotOverException;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.InvalidFormatException;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.AdmissionTicketRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.AdmissionTicketResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.AdmissionTicketVo;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdmissionTicketService {

    private final ScheduleFacade scheduleFacade;
    private final EntryInfoRepository entryInfoRepository;

    @Transactional(readOnly = true)
    public AdmissionTicketResponse execute(AdmissionTicketRequest request) {
        if (scheduleFacade.getScheduleByType(Type.END_DATE)
                .isAfter(LocalDateTime.now())) {
            throw ApplicationPeriodNotOverException.EXCEPTION;
        }

        return getAdmissionTicket(request);
    }

    private AdmissionTicketResponse getAdmissionTicket(AdmissionTicketRequest request) {

        List<AdmissionTicketVo> admissionTicket = getAdmissionTicketList(request);

        return AdmissionTicketResponse.builder()
                .admissionTickets(
                        admissionTicket.stream().map(
                                        admissionTickets -> AdmissionTicketResponse.AdmissionTicket.builder()
                                                .photoFileName(admissionTickets.getPhotoFileName())
                                                .receiptCode(admissionTickets.getReceiptCode())
                                                .name(admissionTickets.getName())
                                                .applicationType(admissionTickets.getApplicationType())
                                                .isDaejeon(admissionTickets.getIsDaejeon())
                                                .schoolName(admissionTickets.getSchoolName())
                                                .examCode(admissionTickets.getExamCode())
                                                .build()
                                )
                                .collect(Collectors.toList()))
                .build();
    }

    private List<AdmissionTicketVo> getAdmissionTicketList(AdmissionTicketRequest request) {
        List<AdmissionTicketVo> admissionTicket = entryInfoRepository.findByAdmissionTicket(
                request.getPhotoFileName(),
                request.getReceiptCode(),
                request.getName(),
                request.getSchoolName(),
                request.getApplicationType(),
                request.getIsDaejeon(),
                request.getExamCode()
        );

        if (admissionTicket.isEmpty()) {
            throw InvalidFormatException.EXCEPTION;
        }

        return admissionTicket;
    }
}
