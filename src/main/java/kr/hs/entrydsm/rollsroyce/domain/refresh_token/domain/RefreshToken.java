package kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@RedisHash
public class RefreshToken {

	@Id
	private final String id;

	@Indexed
	private String token;

	@TimeToLive
	private long ttl;

	public RefreshToken update(String token, long ttl) {
		this.token = token;
		this.ttl = ttl;
		return this;
	}

}
