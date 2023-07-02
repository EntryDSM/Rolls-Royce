package kr.hs.entrydsm.rollsroyce.domain.admin.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Admin(String id, String password, Role role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }
}
