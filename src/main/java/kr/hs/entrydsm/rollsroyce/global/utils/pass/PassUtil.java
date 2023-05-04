package kr.hs.entrydsm.rollsroyce.global.utils.pass;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kcb.module.v3.OkCert;
import kcb.module.v3.exception.OkCertException;
import kcb.org.json.JSONObject;

import kr.hs.entrydsm.rollsroyce.domain.auth.exception.InvalidOkCertConnectException;

@Component
@Slf4j
public class PassUtil {
    private static final OkCert okCert = new OkCert();

    private static final String TARGET = "PROD";

    private static final String SVC_NAME = "IDS_HS_POPUP_RESULT";

    private static final String MODEL_TOKEN = "MDL_TKN";

    @Value("${pass.cp-cd}") private String CP_CD;

    @Value("${pass.license}") private String LICENSE;

    public JSONObject getResponseJson(String token) {
        JSONObject reqJson = new JSONObject();
        reqJson.put(MODEL_TOKEN, token);

        String reqStr = reqJson.toString();

        String resultStr = null;
        try {
            resultStr = okCert.callOkCert(TARGET, CP_CD, SVC_NAME, LICENSE, reqStr);
        } catch (OkCertException e) {
            throw InvalidOkCertConnectException.EXCEPTION;
        }

        assert resultStr != null;
        return new JSONObject(resultStr);
    }
}
