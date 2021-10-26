package kr.hs.entrydsm.rollsroyce.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@AllArgsConstructor
@RedisHash
public class AuthCode {

	@Id
	private final String email;

	private String code;

	private boolean isVerified;

	@TimeToLive
	private long ttl;

	public AuthCode updateAuthCode(String code, long ttl) {
		this.code = code;
		this.ttl = ttl;
		this.isVerified = false;
		return this;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public AuthCode verify() {
		this.isVerified = true;
		return this;
	}

}
