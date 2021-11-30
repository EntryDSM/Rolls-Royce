package kr.hs.entrydsm.rollsroyce.domain.application.domain;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Application extends BaseTimeEntity {

	@Id
	private Long receiptCode;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receipt_code")
	private User user;

	public abstract boolean isGraduation();

	public abstract String getDate();

	public abstract boolean hasEmptyInfo();

	public abstract String getSchoolName();

	protected boolean isExists(String target) {
		return target != null && !target.isBlank();
	}

	protected Application(Long receiptCode) {
		this.receiptCode = receiptCode;
	}

}
