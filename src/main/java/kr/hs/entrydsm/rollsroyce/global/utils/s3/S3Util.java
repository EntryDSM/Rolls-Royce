package kr.hs.entrydsm.rollsroyce.global.utils.s3;

import lombok.RequiredArgsConstructor;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import kr.hs.entrydsm.rollsroyce.global.exception.BadFileExtensionException;
import kr.hs.entrydsm.rollsroyce.global.exception.FileIsEmptyException;
import kr.hs.entrydsm.rollsroyce.global.exception.ImageNotFoundException;

@RequiredArgsConstructor
@Component
public class S3Util {

    private static final int EXP_TIME = 1000 * 60 * 2;

    private final AmazonS3Client amazonS3Client;

    @Value("${aws.s3.bucket}") private String bucketName;

    @Value("${aws.s3.base-image-url}") private String baseImageUrl;

    @Value("${aws.s3.prefix}") private String prefix;

    public String upload(MultipartFile file) {
        String ext = verificationFile(file);

        String randomName = UUID.randomUUID().toString();
        String filename = randomName + "." + ext;

        BufferedImage outputImage = makeThumbnail(file);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ImageIO.write(outputImage, "png", os);
        } catch (IOException e) {
            throw ImageNotFoundException.EXCEPTION;
        }

        InputStream is = new ByteArrayInputStream(os.toByteArray());

        amazonS3Client.putObject(new PutObjectRequest(bucketName, prefix + filename, is, null)
                .withCannedAcl(CannedAccessControlList.AuthenticatedRead));

        return filename;
    }

    public String generateObjectUrl(String fileName) {
        if (fileName == null) return null;
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + EXP_TIME);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
                        baseImageUrl, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

    public byte[] getObject(String fileName) {
        try {
            S3Object object = amazonS3Client.getObject(bucketName, fileName);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (RuntimeException | IOException e) {
            throw ImageNotFoundException.EXCEPTION;
        }
    }

    public void delete(String objectName) {
        amazonS3Client.deleteObject(bucketName, "images/" + objectName);
    }

    private BufferedImage makeThumbnail(MultipartFile file) {
        BufferedImage srcImg;

        try {
            srcImg = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw FileIsEmptyException.EXCEPTION;
        }

        int dw = 300;
        int dh = 400;

        int ow = srcImg.getWidth();
        int oh = srcImg.getHeight();

        int nw = ow;
        int nh = (ow * dh) / dw;

        if (nh > oh) {
            nw = (oh * dw) / dh;
            nh = oh;
        }

        BufferedImage cropImg = Scalr.crop(srcImg, (ow - nw) / 2, (oh - nh) / 2, nw, nh);

        return Scalr.resize(cropImg, dw, dh);
    }

    private String verificationFile(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) throw FileIsEmptyException.EXCEPTION;
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        if (!(ext.equals("jpg") || ext.equals("HEIC") || ext.equals("jpeg") || ext.equals("png") || ext.equals("heic")))
            throw BadFileExtensionException.EXCEPTION;
        return ext;
    }
}
