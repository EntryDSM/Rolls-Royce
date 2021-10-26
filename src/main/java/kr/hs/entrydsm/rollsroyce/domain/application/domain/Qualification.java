package kr.hs.entrydsm.rollsroyce.domain.application.domain;

import java.time.LocalDate;

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

	public Qualification(long receiptCode) {
		super(receiptCode);
	}

	@Builder
	public Qualification(long receiptCode, LocalDate qualifiedAt) {
		super(receiptCode);
		this.qualifiedAt = qualifiedAt;
	}

	@Override
	public boolean isGraduation() {
		return false;
	}

}
