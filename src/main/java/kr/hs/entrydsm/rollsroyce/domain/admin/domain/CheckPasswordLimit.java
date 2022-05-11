package kr.hs.entrydsm.rollsroyce.domain.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@AllArgsConstructor
@RedisHash(timeToLive = 60)
public class CheckPasswordLimit {

    @Id
    private final String id;

}
