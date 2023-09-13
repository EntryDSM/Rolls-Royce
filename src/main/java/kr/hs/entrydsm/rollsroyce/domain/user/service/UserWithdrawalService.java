package kr.hs.entrydsm.rollsroyce.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class UserWithdrawalService {
    private final UserFacade userFacade;
    private final UserRepository userRepository;
    private final EntryInfoRepository entryInfoRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public void execute() {
        User user = userFacade.getCurrentUser();
        if (user.getEntryInfo() != null) {
            statusRepository.delete(user.getStatus());
            entryInfoRepository.delete(user.getEntryInfo());
        }
        userRepository.delete(user);
    }
}
