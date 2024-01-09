package com.projectlib.librarian.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Component
public class FilesHelper {
    @Value("${upload.dir}")
    private String uploadDir;

    public String saveImageToServer(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();

        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithUuid = uuid + fileExtension;

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileNameWithUuid);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileNameWithUuid;
    }

    private void deleteImagesFromServer(List<String> imagePaths) {
        for (String imagePath : imagePaths) {
            try {
                Path path = Paths.get(imagePath);
                Files.deleteIfExists(path);
                System.out.println("File deleted from: " + path.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
