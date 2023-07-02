package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.exception.NoticeNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class UploadNoticeImageService {

    private final S3Util s3Util;
    private final NoticeRepository noticeRepository;

    @Transactional
    public String execute(Long noticeId, MultipartFile file) {
        String filename;
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> NoticeNotFoundException.EXCEPTION);

        if (file != null) {
            filename = s3Util.putObject(file, "/post");
        } else {
            filename = s3Util.upload(file, "/post");
        }

        notice.updateImage(filename);

        return s3Util.generateObjectUrl(filename);
    }
}
