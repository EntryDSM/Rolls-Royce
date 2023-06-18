package kr.hs.entrydsm.rollsroyce.domain.auth.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kcb.org.json.JSONObject;

import kr.hs.entrydsm.rollsroyce.domain.auth.domain.PassInfo;
import kr.hs.entrydsm.rollsroyce.domain.auth.domain.repository.PassInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.auth.exception.InvalidPassException;
import kr.hs.entrydsm.rollsroyce.domain.auth.presentation.dto.response.QueryPassInfoResponse;
import kr.hs.entrydsm.rollsroyce.global.utils.pass.PassUtil;

@RequiredArgsConstructor
@Service
public class QueryPassInfoService {
    private final PassInfoRepository passInfoRepository;
    private final PassUtil passUtil;

    @Value("${pass.exp}") private Long EXP;

    private static final String RESULT_CODE = "RSLT_CD";
    private static final String RESULT_NAME = "RSLT_NAME";
    private static final String TEL_NO = "TEL_NO";
    private static final String RESULT_CODE_OK = "B000";

    @Transactional
    public QueryPassInfoResponse execute(String token) {
        JSONObject resJson = passUtil.getResponseJson(token);

        String resultCode = resJson.getString(RESULT_CODE);

        if (!RESULT_CODE_OK.equals(resultCode)) {
            throw InvalidPassException.EXCEPTION;
        }

        String name = resJson.getString(RESULT_NAME);
        String phoneNumber = resJson.getString(TEL_NO);

        PassInfo passInfo =
                PassInfo.builder().phoneNumber(phoneNumber).name(name).ttl(EXP).build();

        passInfoRepository.save(passInfo);

        return new QueryPassInfoResponse(phoneNumber, name);
    }
}
