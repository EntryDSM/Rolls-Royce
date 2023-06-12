package kr.hs.entrydsm.rollsroyce.domain.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kcb.module.v3.OkCert;
import kcb.org.json.JSONObject;

import kr.hs.entrydsm.rollsroyce.global.exception.InternalServerErrorException;

@Slf4j
@Service
public class PassPopupService {

    private static final String TARGET = "PROD";

    @Value("pass.site-name") String SITE_NAME;

    @Value("pass.site-url") private String SITE_URL;

    @Value("${pass.popup-url}") private String POPUP_URL;

    @Value("${pass.return-url}") private String RETURN_URL;

    @Value("${pass.cp-cd}") private String CP_CD;

    @Value("${pass.license}") private String LICENSE;

    String SVC_NAME = "IDS_HS_POPUP_START";

    String RQST_CAUS_CD = "00";

    @Transactional
    public String execute() {
        try {
            JSONObject reqJson = new JSONObject();
            reqJson.put("RETURN_URL", RETURN_URL);
            reqJson.put("SITE_NAME", SITE_NAME);
            reqJson.put("SITE_URL", SITE_URL);
            reqJson.put("RQST_CAUS_CD", RQST_CAUS_CD);

            String reqStr = reqJson.toString();

            OkCert okcert = new OkCert();

            String resultStr = okcert.callOkCert(TARGET, CP_CD, SVC_NAME, LICENSE, reqStr);

            JSONObject resJson = new JSONObject(resultStr);

            String RSLT_CD = resJson.getString("RSLT_CD");
            String RSLT_MSG = resJson.getString("RSLT_MSG");
            String MDL_TKN = "";

            boolean succ = false;

            if ("B000".equals(RSLT_CD) && resJson.has("MDL_TKN")) {
                MDL_TKN = resJson.getString("MDL_TKN");
                succ = true;
            }

            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<html>");
            htmlBuilder.append("<title>KCB 휴대폰 본인확인 서비스 샘플 2</title>");
            htmlBuilder.append("<head>");
            htmlBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">");
            htmlBuilder.append("<script type=\"text/javascript\">");
            htmlBuilder.append("function request(){");
            htmlBuilder.append("document.form1.action = \"" + POPUP_URL + "\";");
            htmlBuilder.append("document.form1.method = \"get\";");
            htmlBuilder.append("document.form1.submit();");
            htmlBuilder.append("}");
            htmlBuilder.append("</script>");
            htmlBuilder.append("</head>");
            htmlBuilder.append("<body>");
            htmlBuilder.append("<form name=\"form1\">");
            htmlBuilder.append(
                    "<input type=\"hidden\" name=\"tc\" value=\"kcb.oknm.online.safehscert.popup.cmd.P931_CertChoiceCmd\"/>");
            htmlBuilder.append("<input type=\"hidden\" name=\"cp_cd\" value=\"" + CP_CD + "\"/>");
            htmlBuilder.append("<input type=\"hidden\" name=\"mdl_tkn\" value=\"" + MDL_TKN + "\"/>");
            htmlBuilder.append("<input type=\"hidden\" name=\"target_id\" value=\"\"/>");
            htmlBuilder.append("</form>");
            htmlBuilder.append("</body>");
            htmlBuilder.append("<script>");
            if (succ) {
                htmlBuilder.append("request();");
            } else {
                htmlBuilder.append("alert('" + RSLT_CD + " : " + RSLT_MSG + "'); self.close();");
            }
            htmlBuilder.append("</script>");
            htmlBuilder.append("</html>");

            return htmlBuilder.toString();

        } catch (Exception e) {
            log.error("error", e);
            throw InternalServerErrorException.EXCEPTION;
        }
    }
}
