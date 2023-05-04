<%@ page import="kcb.org.json.*" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%

	request.setCharacterEncoding("UTF-8");

	String SITE_NAME = "EntryDSM";
	String SITE_URL = System.getenv("SITE_URL");

	String CP_CD = System.getenv("CP_CD");

	String RETURN_URL = System.getenv("PASS_RETURN_URL");

	String RQST_CAUS_CD = "00";

	String target = "PROD";
	String popupUrl = System.getenv("PASS_POPUP_URL");

	String license = System.getenv("PASS_LICENSE_PATH");

	String svcName = "IDS_HS_POPUP_START";

	JSONObject reqJson = new JSONObject();
	reqJson.put("RETURN_URL", RETURN_URL);
	reqJson.put("SITE_NAME", SITE_NAME);
	reqJson.put("SITE_URL", SITE_URL);
	reqJson.put("RQST_CAUS_CD", RQST_CAUS_CD);

	String reqStr = reqJson.toString();

	kcb.module.v3.OkCert okcert = new kcb.module.v3.OkCert();

	String resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);

	JSONObject resJson = new JSONObject(resultStr);

    String RSLT_CD =  resJson.getString("RSLT_CD");
    String RSLT_MSG = resJson.getString("RSLT_MSG");
    String MDL_TKN = "";
    
	boolean succ = false;
	
    if ("B000".equals(RSLT_CD) && resJson.has("MDL_TKN") ) {
		MDL_TKN = resJson.getString("MDL_TKN");
		succ = true;
    }
	
%>

<html>
<title>KCB 휴대폰 본인확인 서비스 샘플 2</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script type="text/javascript">
	function request(){
		document.form1.action = "<%=popupUrl%>";
		document.form1.method = "post";
	
		document.form1.submit();
	}
</script>
</head>

<body>
	<form name="form1">

	<input type="hidden" name="tc" value="kcb.oknm.online.safehscert.popup.cmd.P931_CertChoiceCmd"/>
	<input type="hidden" name="cp_cd" value="<%=CP_CD%>">
	<input type="hidden" name="mdl_tkn" value="<%=MDL_TKN%>">
	<input type="hidden" name="target_id" value="">
	</form>
</body>
<%
	if (succ) {
		out.println("<script>request();</script>");
	} else {
		out.println("<script>alert('" + RSLT_CD + " : " + RSLT_MSG + "'); self.close(); </script>");
	}
%>
</html>


