package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class ScheduleDto {

    @NotBlank
    private final String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NotBlank
    private final LocalDateTime date;
}
