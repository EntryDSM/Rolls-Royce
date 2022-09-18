package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleDto {

    @NotBlank
    private final Type type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NotBlank
    private final LocalDateTime date;

}
