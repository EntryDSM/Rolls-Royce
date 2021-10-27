package kr.hs.entrydsm.rollsroyce.domain.status.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_status")
public class Status {

	@Id
	private Long receiptCode;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receipt_code")
	private User user;

	private boolean isPrintedArrived;

	@ColumnDefault("0")
	private Boolean isSubmit;

	private LocalDateTime submittedAt;

	@Column(columnDefinition = "char(5)")
	private String examCode;

	@ColumnDefault("0")
	@Column(nullable = false)
	private boolean isFirstRoundPass;

	public void isSubmittedFalse() {
		this.isSubmit = false;
		if (this.isPrintedArrived)
			this.isPrintedArrived = false;
	}

}
