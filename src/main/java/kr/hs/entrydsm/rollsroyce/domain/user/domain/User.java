package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private long receiptCode;

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

}