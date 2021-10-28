package kr.hs.entrydsm.rollsroyce.domain.application.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.querydsl.core.annotations.QueryEntity;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tbl_graduation_application")
@QueryEntity
public class Graduation extends Application {

	private Boolean isGraduated;

	@Column(length = 5)
	private String studentNumber;

	@Getter(AccessLevel.NONE)
	@ManyToOne
	@JoinColumn(name = "school_code")
	private School school;

	private LocalDate graduatedAt;

	@Builder
	public Graduation(boolean isGraduated, String studentNumber, School school,
			String schoolTel, LocalDate graduatedAt) {
		this.isGraduated = isGraduated;
		this.studentNumber = studentNumber;
		this.school = school;
		this.graduatedAt = graduatedAt;
	}

	public void changeGraduationInformation(School school, String studentNumber) {
		this.school = school;
		this.studentNumber = studentNumber;
	}

	@Override
	public boolean isGraduation() {
		return true;
	}

	@Override
	public String getDate() {
		return graduatedAt == null ? null :
				DateTimeFormatter.ofPattern("yyyyMM")
						.withZone(ZoneId.of("Asia/Seoul"))
						.format(graduatedAt);
	}
  
	@Override
	public boolean hasEmptyInfo() {
		return !(isExists(studentNumber) && isGraduated != null &&
				school != null && graduatedAt != null);
	}
  
  public String getSchoolName() {
		return this.school.getName();
  }

}
