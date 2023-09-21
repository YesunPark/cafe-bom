package com.zerobase.cafebom.common;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3UploaderService {

    private final AmazonS3Client amazonS3Client;
    private final String bucketName;

    // @Value("${cloud.aws.s3.bucket}") 어노테이션을 사용할려면 생성자를 따로 만들어야 함
    @Autowired
    public S3UploaderService(AmazonS3Client amazonS3Client,
        @Value("${cloud.aws.s3.bucket}") String bucketName) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
    }

    // multipartFile = 업로드할 파일, dirName = 업로드할 디렉토리 이름
    // 이미지 파일 업로드 jiyeon-23.08.25
    public String uploadFileToS3(MultipartFile multipartFile, String dirName) throws IOException {
        String fileName = dirName + "/" + multipartFile.getOriginalFilename();

        // 업로드할 파일의 메타데이터 객체를 생성
        ObjectMetadata metadata = new ObjectMetadata();
        // 파일의 크기를 메타데이터에 설정
        metadata.setContentLength(multipartFile.getSize());
        // 파일의 콘텐츠 타입(형식)을 메타데이터에 설정
        metadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 파일을 AmazonS3 버킷에 업로드
            amazonS3Client.putObject(
                new PutObjectRequest(bucketName, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            );

        }
        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    // 이미지 파일 삭제 jiyeon-23.08.25
    // 메서드는 구현했지만 S3확인했을때 이미지가 삭제되고있진 않습니다(추후 다시 구현예정)
    public void deleteFile(String picture) throws IOException {
        try {
            // picture 값에서 파일의 경로와 이름 부분을 추출하여 키로 사용
            String key = picture.substring(picture.lastIndexOf("/") + 1);
            amazonS3Client.deleteObject(bucketName, key);
        } catch (SdkClientException e) {
            throw new IOException("Error deleting file from S3", e);
        }
    }
}
