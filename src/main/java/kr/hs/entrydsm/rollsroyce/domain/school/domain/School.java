package kr.hs.entrydsm.rollsroyce.domain.school.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_school")
public class School {

	@Id
	@Column(length = 7, nullable = false)
	private String code;

	@Column(length = 30, nullable = false)
	private String name;

	@Column(length = 50, nullable = false)
	private String information;

	@Column(length = 150, nullable = false)
	private String address;

}
