package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@AllArgsConstructor
@RedisHash
public class AuthCodeLimit {

	@Id
	private final String email;

	private int count;

	@TimeToLive
	private final long ttl;

	public AuthCodeLimit addCount() {
		this.count++;
		return this;
	}

}
