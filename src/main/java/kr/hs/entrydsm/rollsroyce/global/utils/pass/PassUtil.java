package kr.hs.entrydsm.rollsroyce.global.utils.pass;

import kcb.module.v3.OkCert;
import kcb.module.v3.exception.OkCertException;
import kcb.org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PassUtil {

    private static final String TARGET = "PROD";

    private static final String SVC_NAME = "IDS_HS_POPUP_RESULT";

    private static final String MODEL_TOKEN = "MDL_TKN";

    @Value("${pass.cp-cd}")
    private String CP_CD;

    @Value("${pass.license}")
    private String LICENSE;

    public JSONObject getResponseJson(String token) {
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
