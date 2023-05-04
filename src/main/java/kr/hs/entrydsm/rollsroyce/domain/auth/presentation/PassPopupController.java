package kr.hs.entrydsm.rollsroyce.domain.auth.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user/verify")
public class PassPopupController {

    @PostMapping("/popup")
    public String getPassPopup() {
        return "phone_popup2";
    }
}
