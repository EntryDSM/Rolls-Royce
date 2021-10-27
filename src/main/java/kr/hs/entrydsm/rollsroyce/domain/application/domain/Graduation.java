package kr.hs.entrydsm.rollsroyce.domain.application.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Graduation extends Application {

	private Boolean isGraduated;

	@Column(length = 5)
	private String studentNumber;

	@Getter(AccessLevel.NONE)
	@ManyToOne
	@JoinColumn(name = "school_code")
	private School school;

	@Column(length = 11)
	private String schoolTel;

	private LocalDate graduatedAt;

	@Builder
	public Graduation(boolean isGraduated, String studentNumber, School school,
			String schoolTel, LocalDate graduatedAt) {
		this.isGraduated = isGraduated;
		this.studentNumber = studentNumber;
		this.school = school;
		this.schoolTel = schoolTel;
		this.graduatedAt = graduatedAt;
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

}
