package com.projectlib.librarian.aws;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class AwsS3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${aws.s3.bucket.name}")
    private String awsS3BucketName;

    @Value("${aws.s3.endpoint}")
    private String awsS3Endpoint;

    public AwsS3Service(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public List<String> generateUUIDFileNames(int count) {
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String fileName = UUID.randomUUID().toString();
            fileNames.add(fileName);
        }
        return fileNames;
    }

    public List<String> uploadFiles(List<MultipartFile> files, String bucketName) {
        List<String> fileNamesWithExtension = new ArrayList<>();
        List<String> uniqueFileNames = generateUUIDFileNames(files.size());

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = uniqueFileNames.get(i) + extension;
            try {
                amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), null);
                fileNamesWithExtension.add(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNamesWithExtension;
    }

    public List<String> generatePresignedUrlsForImages(List<String> imageKeys) {
        List<String> presignedUrls = new ArrayList<>();
        for (String imageKey : imageKeys) {
            Date expiration = new Date(System.currentTimeMillis() + 3600000);
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(awsS3BucketName, imageKey)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiration);
            URL presignedUrl = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
            presignedUrls.add(presignedUrl.toString());
        }
        return presignedUrls;
    }

    public String getAwsS3BucketName() {
        return awsS3BucketName;
    }

    private String getFileNameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }

    public List<String> extractImageUrlsFromDeleteResult(String deleteResult) {
        return Arrays.asList(deleteResult.split(","));
    }

    public void deleteFiles(List<String> keys) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(awsS3BucketName)
                .withKeys(keys.toArray(new String[0]));
        DeleteObjectsResult deleteObjectsResult = amazonS3Client.deleteObjects(deleteObjectsRequest);
    }
}
