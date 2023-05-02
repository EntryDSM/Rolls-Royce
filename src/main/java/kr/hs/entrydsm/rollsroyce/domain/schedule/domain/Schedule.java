package kr.hs.entrydsm.rollsroyce.domain.schedule.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
