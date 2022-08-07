package kr.hs.entrydsm.rollsroyce.domain.schedule.domain;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "tbl_schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 19, unique = true, nullable = false)
    private Type type;

    @Column(nullable = false)
    private LocalDateTime date;

    public Schedule updateDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public boolean isBefore(LocalDateTime localDateTime) {
        return this.date.isBefore(localDateTime);
    }

    public boolean isAfter(LocalDateTime localDateTime) {
        return this.date.isAfter(localDateTime);
    }

}
