package kr.hs.entrydsm.rollsroyce.domain.admin.domain;


import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "tbl_admin")
public class Admin {

    @Id
    @Column(length = 8)
    private String id;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 24, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

}
