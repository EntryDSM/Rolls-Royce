package kr.hs.entrydsm.rollsroyce.domain.status.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.querydsl.core.annotations.QueryEntity;
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
@QueryEntity
public class Status {

	@Id
	private Long receiptCode;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receipt_code")
	private User user;

	@ColumnDefault("0")
	@Column(nullable = false)
	private Boolean isPrintsArrived;

	@ColumnDefault("0")
	@Column(nullable = false)
	private Boolean isSubmitted;

	@ColumnDefault("0")
	@Column(nullable = false)
	private Boolean isPaid;

	private LocalDateTime submittedAt;

	@Column(columnDefinition = "char(5)")
	private String examCode;

	@ColumnDefault("0")
	@Column(nullable = false)
	private boolean isFirstRoundPass;

	public void isSubmitToTrue() {
		this.isSubmitted = true;
  }
  
	public void cancelIsSubmitted() {
		this.isSubmitted = false;
		if (this.isPrintsArrived)
			this.isPrintsArrived = false;
	}

	public void updateIsPrintsArrived() {
		this.isPrintsArrived = !this.isPrintsArrived;
	}

	public void updateIsPaid() {
		this.isPaid = !this.isPaid;
	}

}
