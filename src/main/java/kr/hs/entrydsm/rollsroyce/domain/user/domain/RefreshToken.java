package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash
public class RefreshToken {

	@Id
	private final Long receiptCode;

	@Indexed
	private String token;

	@TimeToLive
	private long ttl;

	public RefreshToken update(String token, Long ttl) {
		this.token = token;
		this.ttl = ttl;
		return this;
	}

}
