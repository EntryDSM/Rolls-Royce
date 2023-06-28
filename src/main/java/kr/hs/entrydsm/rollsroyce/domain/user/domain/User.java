package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.BaseTimeEntity;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "tbl_user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "char(11)", nullable = false, unique = true)
    private String telephoneNumber;

    @Column(columnDefinition = "char(60)", nullable = false)
    private String password;

    @Column(columnDefinition = "char(5)", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private EntryInfo entryInfo;

    public User updatePassword(String password) {
        this.password = password;
        return this;
    }

    public void updateNameAndTelephoneNumber(String name, String telephoneNumber) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }
}
