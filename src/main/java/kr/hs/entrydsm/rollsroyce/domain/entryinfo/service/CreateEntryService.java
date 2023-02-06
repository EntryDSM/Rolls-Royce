package kr.hs.entrydsm.rollsroyce.domain.entryinfo.service;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.exception.EntryInfoAlreadyExistsException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateEntryService {
    private final UserFacade userFacade;
    private final EntryInfoRepository entryInfoRepository;

    public void execute() {
       User user = userFacade.getCurrentUser();
       if(entryInfoRepository.findByUser(user).isPresent()){
           throw EntryInfoAlreadyExistsException.EXCEPTION;
       }
        entryInfoRepository.save(EntryInfo.builder().user(user).build());
    }
}
