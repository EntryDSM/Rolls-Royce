package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
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

	public User updateUserApplication(EducationalStatus educationalStatus, ApplicationType applicationType,
			boolean isDaejeon, ApplicationRemark applicationRemark, HeadCount headcount) {
		this.educationalStatus = educationalStatus;
		this.applicationType = applicationType;
		this.isDaejeon = isDaejeon;
		this.applicationRemark = applicationRemark;
		this.headcount = headcount;
		return this;
	}

	public QueryTypeResponse queryUserApplication() {
		return QueryTypeResponse.builder()
				.applicationRemark(applicationRemark.name())
				.applicationType(applicationType.name())
				.educationalStatus(educationalStatus.name())
				.graduatedAt(queryUserApplication().getGraduatedAt())
				.isDaejeon(isDaejeon)
				.isGraduated(queryUserApplication().isGraduated())
				.build();
	}

}