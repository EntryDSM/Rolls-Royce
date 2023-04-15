package kr.hs.entrydsm.rollsroyce.domain.auth.domain;

import lombok.Builder;
import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@RedisHash
public class PassInfo {

    @Id
    private String name;

    @Indexed
    private String phoneNumber;

    @TimeToLive
    private Long ttl;
}
