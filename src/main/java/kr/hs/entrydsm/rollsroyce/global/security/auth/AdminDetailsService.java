package kr.hs.entrydsm.rollsroyce.global.security.auth;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminFacade adminFacade;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Admin admin = adminFacade.getAdminById(id);
        return new AdminDetails(admin.getId(), admin.getRole());
    }

}
