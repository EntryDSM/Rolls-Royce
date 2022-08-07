package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.service.CancelApplicationSubmitService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.UpdateIsPrintsArrivedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/application")
@RestController("admin.presentation.ApplicationController")
public class ApplicationController {

    private final CancelApplicationSubmitService cancelApplicationSubmitService;
    private final UpdateIsPrintsArrivedService updateIsPrintsArrivedService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/submitted/{receipt-code}")
    public void cancelApplicationSubmitStatus(@PathVariable("receipt-code") long receiptCode) {
        cancelApplicationSubmitService.execute(receiptCode);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/prints-arrived/{receipt-code}")
    public void updateApplicationPrintsArrivedStatus(@PathVariable("receipt-code") long receiptCode, @RequestParam("is_prints_arrived") boolean isArrived) {
        updateIsPrintsArrivedService.execute(receiptCode, isArrived);
    }

}
