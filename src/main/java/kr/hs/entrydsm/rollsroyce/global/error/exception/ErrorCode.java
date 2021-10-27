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
	UNPROVEN_AUTH_CODE(401, "USER401-1", "Unproven Auth Code"),
	CREDENTIALS_NOT_FOUND(401, "USER401-2", "User credentials not found"),
	USER_NOT_FOUND(404, "USER404-0", "User Not Found"),
	USER_ALREADY_EXISTS(409, "USER409-0", "User Already Exists"),
	AUTH_CODE_ALREADY_VERIFIED(409, "USER409-1", "Auth Code Already Verified"),
	AUTH_CODE_REQUEST_OVER_LIMIT(429, "USER429-0", "Auth Code Request Over Limit"),
	STATUS_NOT_FOUND(404, "USER404-1", "Status Not Found"),

	APPLICATION_TYPE_UNMATCHED(403, "SCORE403-0", "Application Type is unmatched"),
	GRADE_OR_SCORE_NOT_FOUND(404, "SCORE404-0", "The score does not exist"),

	INVALID_KEYWORD(400, "ADMIN400-0", "It's a wrong keyword."),
	INVALID_ADMIN_TOKEN(401, "ADMIN401-0","This token is invalid"),
	INVALID_ADMIN_PASSWORD(401, "ADMIN401-1", "The password is not valid"),
	APPLICATION_PERIOD_NOT_OVER(400, "ADMIN400-0","The application period is not over"),
	ADMIN_NOT_FOUND(404, "ADMIN404-0","The account does not exist"),
	SCHEDULE_NOT_FOUND(404, "ADMIN404-2","The schedule does not exist"),
	IMAGE_PATH_NOT_FOUND(404, "ADMIN404-3", "The user's image file could not be found."),
	ALREADY_EXIST_ADMIN_ID(409, "ADMIN409-0", "Already admin id exist"),
	ADMIN_NOT_ACCESSIBLE(403, "ADMIN403-0", "Don't have permission"),

	FILE_IS_EMPTY(400, "APPLICATION400-0", "The file could not be found"),
	INVALID_ENUM_CONSTANT(400, "APPLICATION400-1", "The enum constant is invalid"),
	EDUCATIONAL_STATUS_UNMATCHED(400, "APPLICATION400-2", "Education Status is unmatched"),
	BAD_FILE_EXTENSION(400, "APPLICATION400-3", "File Extension is invalid"),
	EMPTY_CONTENT(400, "APPLICATION400-4", "Content is empty."),
	APPLICATION_NOT_FOUND(404, "APPLICATION404-0","The application could not be found"),
	SCHOOL_NOT_FOUND(404, "APPLICATION404-1", "The school could not be found"),
	EDUCATIONAL_STATUS_NOT_FOUND(404, "APPLICATION404-2", "The educational status could not be found"),
	IMAGE_NOT_FOUND(404, "APPLICATION404-3", "Image not found."),
	FINAL_SUBMIT_REQUIRED(406, "APPLICATION406-0", "Final submit required"),
	SCORE_NOT_FOUND(400, "APPLICATION400-3", "Score not found"),
	EDUCATIONAL_STATUS_NULL(400, "APPLICATION400-4", "Educational status is null"),
	PROCESS_NOT_COMPLETED(406, "APPLICATION406-1", "Application process is not completed"),

	FIELD_NOT_FOUND(400, "SCORE-400-0", "Field not found"),
	GRADE_NOT_FOUND(404, "SCORE-404-0", "User's grade could not be found")
	;

	private final int status;
	private final String code;
	private final String message;

}
