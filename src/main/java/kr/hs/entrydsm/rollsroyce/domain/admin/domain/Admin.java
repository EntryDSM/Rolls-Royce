package kr.hs.entrydsm.rollsroyce.domain.admin.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_admin")
public class Admin {

	@Id
	@Column(length = 8)
	private String id;

	@Column(length = 60, nullable = false)
	private String password;

	@Column(length = 5, nullable = false)
	private String name;

	@Column(length = 24)
	@Enumerated(EnumType.STRING)
	private Role role;

}
