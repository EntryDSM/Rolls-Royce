package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.querydsl.core.annotations.QueryEntity;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Qualification;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.HeadCount;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.Sex;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.ApplicationNotFoundException;
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
	@Column(length = 6)
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

	@Column(columnDefinition = "char(11)")
	private String schoolTel;

	@Column(length = 300)
	private String address;

	@Column(length = 100)
	private String detailAddress;

	@Column(columnDefinition = "char(5)")
	private String postCode;

	@Column(columnDefinition = "char(45)")
	private String photoFileName;

	@Column(columnDefinition = "char(11)")
	private String homeTel;

	@Column(length = 1600)
	private String selfIntroduce;

	@Column(length = 1600)
	private String studyPlan;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Status status;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Graduation graduation;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Score score;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Qualification qualification;

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
		this.homeTel = information.getHomeTel();
		this.address = information.getAddress();
		this.postCode = information.getPostCode();
		this.detailAddress = information.getDetailAddress();
	}

	public QueryInformationResponse queryInformation() {
		return QueryInformationResponse.builder()
				.address(address)
				.birthday(String.valueOf(birthday))
				.detailAddress(detailAddress)
				.homeTel(homeTel)
				.name(name)
				.parentName(parentName)
				.parentTel(parentTel)
				.postCode(postCode)
				.sex(sex.name())
				.telephoneNumber(telephoneNumber)
				.photoFileName(photoFileName)
				.build();
	}

	public QueryTypeResponse queryUserApplication() {
		QueryTypeResponse response = QueryTypeResponse.builder()
				.applicationRemark(getValue(applicationRemark))
				.applicationType(getValue(applicationType))
				.educationalStatus(getValue(educationalStatus))
				.isDaejeon(isDaejeon)
				.build();

		if(hasApplication()) {
			changeGraduationInformation(Objects.requireNonNullElse(graduation, qualification),
					response);
		}
		return response;
	}

	public boolean hasApplication() {
		return graduation != null || qualification != null;
	}

	public boolean isQualification() {
		return educationalStatus != null &&
				educationalStatus.equals(EducationalStatus.QUALIFICATION_EXAM);
	}

	public Graduation queryGraduation() {
		if(graduation == null)
			throw ApplicationNotFoundException.EXCEPTION;
		return graduation;
	}

	public void changeGraduationInformation(School school, String studentNumber) {
		queryGraduation()
				.changeGraduationInformation(school, studentNumber);
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

	public void isSubmitToTrue() {
		status.isSubmitToTrue();
	}

	private String getValue(Object obj) {
		return obj == null ? null : String.valueOf(obj);
	}

	private void changeGraduationInformation(Application application, QueryTypeResponse response) {
		if(application instanceof Graduation)
			response.setGraduated(((Graduation) application).getIsGraduated() != null &&
					((Graduation) application).getIsGraduated());
		response.setGraduatedAt(application.getDate());
	}

	private boolean isExists(String target) {
		return target != null && !target.isBlank();
	}
	
}