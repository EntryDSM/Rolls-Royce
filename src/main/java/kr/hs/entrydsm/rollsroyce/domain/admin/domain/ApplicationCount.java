package kr.hs.entrydsm.rollsroyce.domain.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_application_count")
public class ApplicationCount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ApplicationType applicationType;

	@Column(columnDefinition = "BIT(1)")
	private boolean isDaejeon;

	private int count;

	public ApplicationCount(ApplicationType applicationType, boolean isDaejeon, int count) {
		this.applicationType = applicationType;
		this.isDaejeon = isDaejeon;
		this.count = count;
	}

	public ApplicationCount update(int count) {
		this.count = count;
		return this;
	}

}
