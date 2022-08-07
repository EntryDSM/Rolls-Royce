package kr.hs.entrydsm.rollsroyce.global.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String receiptCode) throws UsernameNotFoundException {
        return new AuthDetails(receiptCode);
    }

}
