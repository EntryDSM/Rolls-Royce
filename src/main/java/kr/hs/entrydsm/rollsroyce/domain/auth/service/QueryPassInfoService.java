package kr.hs.entrydsm.rollsroyce.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kcb.module.v3.OkCert;
import kcb.module.v3.exception.OkCertException;
import kcb.org.json.JSONObject;

import kr.hs.entrydsm.rollsroyce.domain.auth.domain.PassInfo;
import kr.hs.entrydsm.rollsroyce.domain.auth.domain.repository.PassInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.auth.exception.InvalidPassException;
import kr.hs.entrydsm.rollsroyce.domain.auth.presentation.dto.response.QueryPassInfoResponse;

@RequiredArgsConstructor
@Service
@Slf4j
public class QueryPassInfoService {
    private final PassInfoRepository passInfoRepository;

    @Value("${pass.cp-cd}") private String CP_CD;

    @Value("${pass.license}") private String LICENSE;

    @Value("${pass.exp}") private Long EXP;

    private static final String TARGET = "PROD";
    private static final String SVC_NAME = "IDS_HS_POPUP_RESULT";
    private static final String RESULT_CODE = "RSLT_CD";
    private static final String RESULT_NAME = "RSLT_NAME";
    private static final String TEL_NO = "TEL_NO";
    private static final String MODEL_TOKEN = "MDL_TKN";
    private static final String RESULT_CODE_OK = "B000";

    @Transactional
    public QueryPassInfoResponse execute(String token) {
        JSONObject resJson = getResponseJson(token);

        String resultCode = resJson.getString(RESULT_CODE);

        if (!RESULT_CODE_OK.equals(resultCode)) {
            throw InvalidPassException.EXCEPTION;
        }

        String resultName = resJson.getString(RESULT_NAME);
        String telNo = resJson.getString(TEL_NO);

        PassInfo passInfo =
                PassInfo.builder().name(resultName).phoneNumber(telNo).ttl(EXP).build();

        passInfoRepository.save(passInfo);

        return QueryPassInfoResponse.builder().phoneNumber(telNo).build();
    }

    private JSONObject getResponseJson(String token) {
        JSONObject reqJson = new JSONObject();
        reqJson.put(MODEL_TOKEN, token);

        String reqStr = reqJson.toString();

        OkCert okCert = new OkCert();
        String resultStr = null;
        try {
            resultStr = okCert.callOkCert(TARGET, CP_CD, SVC_NAME, LICENSE, reqStr);
        } catch (OkCertException e) {
            log.error(e.toString());
        }

        assert resultStr != null;
        return new JSONObject(resultStr);
    }
}
