package com.projectlib.librarian.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private S3FileHandler s3FileHandler;

    public List<String> uploadImagesToS3(List<MultipartFile> images) {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try {
                    String uuid = UUID.randomUUID().toString();
                    String fileName = uuid + "-" + image.getOriginalFilename();
                    s3FileHandler.uploadFile(image);
                    fileNames.add(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileNames;
    }

    public List<String> addPresignedUrlsToBooks(List<String> fileNames) {
        List<String> presignedUrls = new ArrayList<>();
        for (String fileName : fileNames) {
            String presignedUrl = s3FileHandler.generatePresignedUrl(fileName);
            presignedUrls.add(presignedUrl);
        }
        return presignedUrls;
    }

    public void deleteFilesFromS3(List<String> fileNames) {
        for (String fileName : fileNames) {
            s3FileHandler.deleteFile(fileName);
        }
    }


    public void updateImagesInS3(List<String> existingFileNames, List<MultipartFile> newImages) {

        List<String> newFileNames = uploadImagesToS3(newImages);

        deleteFilesFromS3(existingFileNames);
    }
}
