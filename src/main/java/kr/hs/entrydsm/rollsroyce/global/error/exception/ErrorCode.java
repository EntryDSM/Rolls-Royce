package kr.hs.entrydsm.rollsroyce.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = Shape.OBJECT)
public enum ErrorCode {

	INVALID_DATE(400, "COMMON400-0", "Date was invalid"),
	REQUEST_BODY_IS_NULL(400, "COMMON400-1", "request body is null."),
	INVALID_TOKEN(401, "COMMON401-0", "Invalid Token"),
	EXPIRED_TOKEN(401, "COMMON401-1", "Expired Token"),
	UNAUTHENTICATED(401, "COMMON401-2", "UnAuthenticated"),
	NOT_FOUND(404, "COMMON404-0", "Not Found"),

	INVALID_AUTH_CODE(401, "USER401-0", "Invalid Auth Code"),
	UNVERIFIED_AUTH_CODE(401, "USER401-1", "UnVerified Auth Code"),
	CREDENTIALS_NOT_FOUND(401, "USER401-2", "User credentials not found"),
	USER_NOT_FOUND(404, "USER404-0", "User Not Found"),
	STATUS_NOT_FOUND(404, "USER404-1", "Status Not Found"),
	USER_ALREADY_EXISTS(409, "USER409-0", "User Already Exists"),
	AUTH_CODE_ALREADY_VERIFIED(409, "USER409-1", "Auth Code Already Verified"),
	AUTH_CODE_REQUEST_OVER_LIMIT(429, "USER429-0", "Auth Code Request Over Limit"),

	APPLICATION_TYPE_UNMATCHED(403, "SCORE403-0", "Application Type is unmatched"),
	GRADE_OR_SCORE_NOT_FOUND(404, "SCORE404-0", "The score does not exist"),

	INVALID_KEYWORD(400, "ADMIN400-0", "It's a wrong keyword."),
	INVALID_FILE(400, "ADMIN400-1", "It's a invalid file."),
	APPLICATION_PERIOD_NOT_OVER(400, "ADMIN400-2","The application period is not over"),
	EXCEL_IO_EXCEPTION(400, "ADMIN400-3","The Excel file cannot be downloaded."),
	REQUEST_FAIL_TO_OTHER_SERVER(400, "ADMIN400-4", "Request fail to other server."),
	INVALID_ADMIN_TOKEN(401, "ADMIN401-0","This token is invalid"),
	INVALID_ADMIN_PASSWORD(401, "ADMIN401-1", "The password is not valid"),
	ADMIN_NOT_ACCESSIBLE(403, "ADMIN403-0", "Don't have permission"),
	ADMIN_NOT_FOUND(404, "ADMIN404-0","The account does not exist"),
	IMAGE_PATH_NOT_FOUND(404, "ADMIN404-1", "The user's image file could not be found."),
	APPLICATION_COUNT_NOT_FOUND(404, "ADMIN404-2", "Application count not found."),
	ALREADY_EXIST_ADMIN_ID(409, "ADMIN409-0", "Already admin id exist"),

	INVALID_SCHEDULE(400, "SCHEDULE400-0", "Schedule object is null."),
	SCHEDULE_NOT_FOUND(404, "SCHEDULE404-0","The schedule does not exist"),

	FILE_IS_EMPTY(400, "APPLICATION400-0", "The file could not be found"),
	INVALID_ENUM_CONSTANT(400, "APPLICATION400-1", "The enum constant is invalid"),
	EDUCATIONAL_STATUS_UNMATCHED(400, "APPLICATION400-2", "Education Status is unmatched"),
	BAD_FILE_EXTENSION(400, "APPLICATION400-3", "File Extension is invalid"),
	EMPTY_CONTENT(400, "APPLICATION400-4", "Content is empty."),
	SCORE_NOT_FOUND(400, "APPLICATION400-5", "Score not found"),
	EDUCATIONAL_STATUS_NULL(400, "APPLICATION400-6", "Educational status is null"),
	REQUEST_FAIL_TO_TMAP_SERVER(400, "APPLICATION400-7", "Request fail to tmap server."),
	INVALID_GRADUATE_AT(400, "APPLICATION400-8", "Invalid graduate at"),
	APPLICATION_NOT_FOUND(404, "APPLICATION404-0","The application could not be found"),
	SCHOOL_NOT_FOUND(404, "APPLICATION404-1", "The school could not be found"),
	EDUCATIONAL_STATUS_NOT_FOUND(404, "APPLICATION404-2", "The educational status could not be found"),
	IMAGE_NOT_FOUND(404, "APPLICATION404-3", "Image not found."),
	FINAL_SUBMIT_REQUIRED(406, "APPLICATION406-0", "Final submit required"),
	PROCESS_NOT_COMPLETED(406, "APPLICATION406-1", "Application process is not completed"),
	ALREADY_SUBMIT(409, "APPLICATION409-0", "Already submit application."),

	FIELD_NOT_FOUND(400, "SCORE400-0", "Field not found"),
	GRADE_NOT_FOUND(404, "SCORE404-0", "User's grade could not be found"),

	MESSAGE_REJECTED(400, "MESSAGE400-0", "Message is rejected")
	;

	private final int status;
	private final String code;
	private final String message;

}
