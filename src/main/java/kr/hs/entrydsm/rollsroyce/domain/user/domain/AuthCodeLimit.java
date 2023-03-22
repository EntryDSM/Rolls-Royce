package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import lombok.Builder;
import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@RedisHash
public class AuthCodeLimit {

    @Id
    private final String email;

    private int count;

    @TimeToLive
    private long ttl;

    public AuthCodeLimit addCount(Long ttl) {
        this.count++;
        this.ttl = ttl;
        return this;
    }
}
