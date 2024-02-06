package com.projectlib.librarian.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AwsS3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${aws.s3.bucket.name}")
    private String awsS3BucketName;

    @Value("${aws.s3.endpoint}")
    private String awsS3Endpoint;

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
        List<String> uniqueFileNames = generateUUIDFileNames(files.size()); // Genera nombres de archivo únicos para la cantidad de archivos

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = uniqueFileNames.get(i) + extension; // Genera un nombre único para el archivo con su extensión
            try {
                amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), null);
                fileNamesWithExtension.add(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNamesWithExtension;
    }

    public List<String> generatePresignedUrlsForImages(List<String> imageUrls) {
        List<String> presignedUrls = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            String fileName = getFileNameFromUrl(imageUrl);
            URL presignedUrl = generatePresignedUrl(awsS3BucketName, fileName);
            presignedUrls.add(presignedUrl.toString());
        }
        return presignedUrls;
    }

    public URL generatePresignedUrl(String bucketName, String fileName) {
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1 hour
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withExpiration(expiration);
        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public String getAwsS3BucketName() {
        return awsS3BucketName;
    }

    public String deleteFiles(List<String> fileUrls) {
        for (String fileUrl : fileUrls) {
            String fileName = getFileNameFromUrl(fileUrl);
            amazonS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName, fileName));
        }
        return "Files deleted successfully";
    }

    private String getFileNameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }

    public List<String> extractImageUrlsFromDeleteResult(String deleteResult) {
        return Arrays.asList(deleteResult.split(","));
    }
}
