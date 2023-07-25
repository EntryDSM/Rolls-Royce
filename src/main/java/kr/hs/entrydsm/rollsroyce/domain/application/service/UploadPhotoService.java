package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class UploadPhotoService {

    private final S3Util s3Util;

    private final EntryInfoFacade entryInfoFacade;

    @Transactional
    public String execute(MultipartFile file) {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        String fileName;

        if (entryInfo.getPhotoFileName() == null) {
            fileName = s3Util.upload(file, "entry_photo/");
        } else {
            s3Util.delete(entryInfo.getPhotoFileName(), "entry_photo/");
            fileName = s3Util.upload(file, "entry_photo/");
        }

        entryInfo.updatePhotoFileName(fileName);

        return s3Util.generateObjectUrl(fileName);
    }
}
