package kr.hs.entrydsm.rollsroyce.domain.admin.facade;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.AdminRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminFacade {

	private final AdminRepository adminRepository;

	public Admin getAdminById(String id) {
		return adminRepository.findById(id)
				.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
	}

	public void findAdmin() {
		adminRepository.findById(getEmail())
				.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
	}

	public Admin getAdmin() {
		return adminRepository.findById(getEmail())
				.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
	}

	public Admin getRootAdmin() {
		return adminRepository.findById(getEmail())
				.filter(admin -> Role.ROOT.equals(admin.getRole()))
				.orElseThrow(() -> AdminNotAccessibleException.EXCEPTION);
	}

	public Role getAdminRole() {
		return adminRepository.findById(getEmail())
				.map(Admin::getRole)
				.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
	}

	private String getEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw AdminNotFoundException.EXCEPTION;
		}
		return authentication.getName();
	}

}
