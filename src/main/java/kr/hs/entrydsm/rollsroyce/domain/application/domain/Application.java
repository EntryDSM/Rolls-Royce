package kr.hs.entrydsm.rollsroyce.domain.application.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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

	public abstract boolean isGraduation();

}
