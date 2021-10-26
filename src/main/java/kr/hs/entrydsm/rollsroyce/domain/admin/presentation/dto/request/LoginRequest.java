package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    private final String id;

    private final String password;

}
