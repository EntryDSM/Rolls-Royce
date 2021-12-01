package kr.hs.entrydsm.rollsroyce.domain.application.service;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadPhotoService {

	private final S3Util s3Util;
	private final UserFacade userFacade;

	@Transactional
	public String execute(MultipartFile file) {
		User user = userFacade.getCurrentUser();

		if(user.getPhotoFileName() != null)
			s3Util.delete(user.getPhotoFileName());

		String fileName = s3Util.upload(file);

		user.updatePhotoFileName(fileName);

		return fileName;
	}

}
