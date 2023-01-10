package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.entryInfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryInfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UploadPhotoService {

    private final S3Util s3Util;

    private final EntryInfoFacade entryInfoFacade;

    @Transactional
    public String execute(MultipartFile file) {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();

        if (entryInfo.getPhotoFileName() != null)
            s3Util.delete(entryInfo.getPhotoFileName());

        String fileName = s3Util.upload(file);

        entryInfo.updatePhotoFileName(fileName);

        return s3Util.generateObjectUrl(fileName);
    }

}
