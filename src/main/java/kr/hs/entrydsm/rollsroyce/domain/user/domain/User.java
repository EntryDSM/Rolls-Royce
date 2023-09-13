package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import lombok.AccessLevel;
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
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(columnDefinition = "bit(1) default 1", nullable = false)
    private Boolean isStudent;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private EntryInfo entryInfo;

    @Builder
    public User(String telephoneNumber, String password, String name, Boolean isStudent, EntryInfo entryInfo) {
        this.telephoneNumber = telephoneNumber;
        this.password = password;
        this.name = name;
        this.isStudent = isStudent;
        this.entryInfo = entryInfo;
    }

    public User updatePassword(String password) {
        this.password = password;
        return this;
    }

    public Status getStatus() {
        return this.entryInfo.getStatus();
    }
}
