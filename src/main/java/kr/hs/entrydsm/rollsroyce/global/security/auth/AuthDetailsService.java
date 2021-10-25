package kr.hs.entrydsm.rollsroyce.global.security.auth;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

	private final UserFacade userFacade;

	@Override
	public UserDetails loadUserByUsername(String receiptCode) throws UsernameNotFoundException {
		User user = userFacade.getUserByCode(Long.valueOf(receiptCode));
		return new AuthDetails(user);
	}

}
