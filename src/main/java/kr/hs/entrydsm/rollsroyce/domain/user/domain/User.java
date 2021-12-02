package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.querydsl.core.annotations.QueryEntity;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.service.dto.UpdateUserInformationDto;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.HeadCount;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_user")
@QueryEntity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long receiptCode;

	@Column(columnDefinition = "char(36)", nullable = false, unique = true)
	private String email;

	@Column(columnDefinition = "char(60)", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(length = 7)
	private ApplicationType applicationType;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ApplicationRemark applicationRemark;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EducationalStatus educationalStatus;

	@Enumerated(EnumType.STRING)
	@Column(length = 16)
	private HeadCount headcount;

	private Boolean isDaejeon;

	@Column(columnDefinition = "char(5)", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(length = 6)
	private Sex sex;

	private LocalDate birthday;

	@Column(columnDefinition = "char(11)")
	private String telephoneNumber;

	@Column(columnDefinition = "char(5)")
	private String parentName;

	@Column(columnDefinition = "char(11)")
	private String parentTel;

	@Column(length = 300)
	private String address;

	@Column(length = 100)
	private String detailAddress;

	@Column(columnDefinition = "char(5)")
	private String postCode;

	@Column(columnDefinition = "char(45)")
	private String photoFileName;

	@Column(length = 1600)
	private String selfIntroduce;

	@Column(length = 1600)
	private String studyPlan;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@Transient
	private int distance;

	public void updateUserApplication(EducationalStatus educationalStatus, ApplicationType applicationType,
			boolean isDaejeon, ApplicationRemark applicationRemark, HeadCount headcount) {
		this.educationalStatus = educationalStatus;
		this.applicationType = applicationType;
		this.isDaejeon = isDaejeon;
		this.applicationRemark = applicationRemark;
		this.headcount = headcount;
	}

	public void updateUserInformation(UpdateUserInformationDto information) {
		this.name = information.getName();
		this.sex = information.getSex();
		this.birthday = information.getBirthday();
		this.parentName = information.getParentName();
		this.parentTel = information.getParentTel();
		this.telephoneNumber = information.getTelephoneNumber();
		this.address = information.getAddress();
		this.postCode = information.getPostCode();
		this.detailAddress = information.getDetailAddress();
	}

	public QueryInformationResponse queryInformation() {
		return QueryInformationResponse.builder()
				.address(address)
				.birthday(DateTimeFormatter.ofPattern("yyyyMMdd")
						.withZone(ZoneId.of("Asia/Seoul"))
						.format(birthday))
				.detailAddress(detailAddress)
				.name(name)
				.parentName(parentName)
				.parentTel(parentTel)
				.postCode(postCode)
				.sex(sex.name())
				.telephoneNumber(telephoneNumber)
				.photoFileName(photoFileName)
				.build();
	}

	public QueryTypeResponse queryUserApplication(Application application) {
		QueryTypeResponse response = QueryTypeResponse.builder()
				.applicationRemark(getValue(applicationRemark))
				.applicationType(getValue(applicationType))
				.educationalStatus(getValue(educationalStatus))
				.isDaejeon(isDaejeon)
				.build();

		changeGraduationInformation(application, response);
		return response;
	}

	public boolean isQualification() {
		return educationalStatus != null &&
				educationalStatus.equals(EducationalStatus.QUALIFICATION_EXAM);
	}

	public void updateSelfIntroduce(String selfIntroduce) {
		this.selfIntroduce = selfIntroduce;
	}

	public boolean hasEmptyInfo() {
		return !(isExists(name) && sex != null && birthday != null && isExists(telephoneNumber) && isExists(parentTel)
				&& isExists(parentName) && isExists(address) && isExists(detailAddress) && isExists(postCode)
				&& photoFileName != null && educationalStatus != null && applicationType != null);
	}

	public boolean isEducationalStatusEmpty() {
		return this.educationalStatus == null;
	}

	public boolean isMale() {
		return sex.equals(Sex.MALE);
	}

	public boolean isFemale() {
		return sex.equals(Sex.FEMALE);
	}

	public void updateStudyPlan(String studyPlan) { this.studyPlan = studyPlan; }

	public User updatePassword(String password) {
		this.password = password;
		return this;
	}

	public void updatePhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public boolean isRecommendationsRequired() {
		return !isEducationalStatusEmpty() && !isCommonApplicationType() && !isProspectiveGraduate();
	}

	public boolean isQualificationExam() {
		return educationalStatus.equals(EducationalStatus.QUALIFICATION_EXAM);
	}

	public boolean isGraduate() {
		return educationalStatus.equals(EducationalStatus.GRADUATE);
	}

	public boolean isProspectiveGraduate() {
		return educationalStatus.equals(EducationalStatus.PROSPECTIVE_GRADUATE);
	}

	public boolean isBasicLiving() {
		return applicationRemark.equals(ApplicationRemark.BASIC_LIVING);
	}

	public boolean isFromNorth() {
		return applicationRemark.equals(ApplicationRemark.FROM_NORTH);
	}

	public boolean isLowestIncome() {
		return applicationRemark.equals(ApplicationRemark.LOWEST_INCOME);
	}

	public boolean isMulticultural() {
		return applicationRemark.equals(ApplicationRemark.MULTICULTURAL);
	}

	public boolean isOneParent() {
		return applicationRemark.equals(ApplicationRemark.ONE_PARENT);
	}

	public boolean isTeenHouseholder() {
		return applicationRemark.equals(ApplicationRemark.TEEN_HOUSEHOLDER);
	}

	public boolean isPrivilegedAdmission() {
		return applicationRemark.equals(ApplicationRemark.PRIVILEGED_ADMISSION);
	}

	public boolean isNationalMerit() {
		return applicationRemark.equals(ApplicationRemark.NATIONAL_MERIT);
	}

	public boolean isCommonApplicationType() {
		return applicationType.equals(ApplicationType.COMMON);
	}

	public boolean isMeisterApplicationType() {
		return applicationType.equals(ApplicationType.MEISTER);
	}

	public boolean isSocialApplicationType() {
		return applicationType.equals(ApplicationType.SOCIAL);
	}

	public void updateDistance(int distance) {
		this.distance = distance;
	}

	public boolean isEducationalStatusEqual(String educationalStatus) {
		return this.educationalStatus != null && this.educationalStatus.name().equals(educationalStatus);
	}

	private String getValue(Object obj) {
		return obj == null ? null : String.valueOf(obj);
	}

	private void changeGraduationInformation(Application application, QueryTypeResponse response) {
		if(application == null)
			return;
		if(application instanceof Graduation)
			response.setGraduated(((Graduation) application).getIsGraduated() != null &&
					((Graduation) application).getIsGraduated());
		response.setGraduatedAt(application.getDate());
	}

	private boolean isExists(String target) {
		return target != null && !target.isBlank();
	}

}