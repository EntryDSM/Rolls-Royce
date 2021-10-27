package kr.hs.entrydsm.rollsroyce.domain.application.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tbl_qualification_exam_application")
public class Qualification extends Application {

	private LocalDate qualifiedAt;

	@Builder
	public Qualification(LocalDate qualifiedAt) {
		this.qualifiedAt = qualifiedAt;
	}

	@Override
	public boolean isGraduation() {
		return false;
	}

	@Override
	public String getDate() {
		return qualifiedAt == null ? null :
				DateTimeFormatter.ofPattern("yyyyMM")
						.withZone(ZoneId.of("Asia/Seoul"))
						.format(qualifiedAt);
	}

	@Override
	public boolean hasEmptyInfo() {
		return qualifiedAt == null;
	}

}
