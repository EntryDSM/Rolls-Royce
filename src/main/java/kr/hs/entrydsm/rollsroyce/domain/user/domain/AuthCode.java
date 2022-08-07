package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@RedisHash
public class AuthCode {

    @Id
    private final String email;

    private String code;

    private boolean isVerified;

    @TimeToLive
    private long ttl;

    public void updateAuthCode(String code, long ttl) {
        this.code = code;
        this.ttl = ttl;
        this.isVerified = false;
    }

    public AuthCode verify() {
        this.isVerified = true;
        return this;
    }

}
