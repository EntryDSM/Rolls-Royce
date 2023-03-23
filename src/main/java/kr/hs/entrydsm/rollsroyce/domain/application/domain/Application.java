package kr.hs.entrydsm.rollsroyce.domain.application.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@MappedSuperclass
public abstract class Application extends BaseTimeEntity {

    @Id
    private Long receiptCode;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_code")
    private EntryInfo entryInfo;

    public abstract boolean isGraduation();

    public abstract String getDate();

    public abstract boolean hasEmptyInfo();

    public abstract String getSchoolName();

    protected boolean isExists(String target) {
        return target != null && !target.isBlank();
    }

    protected Application(EntryInfo entryInfo) {
        this.entryInfo = entryInfo;
    }
}
