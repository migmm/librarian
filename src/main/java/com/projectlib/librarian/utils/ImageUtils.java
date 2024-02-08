package com.projectlib.librarian.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ImageUtils {

    @Value("${image.max.size}")
    private int MAX_IMAGE_SIZE;

    @Value("${image.max.width}")
    private int MAX_IMAGE_WIDTH;

    @Value("${image.max.height}")
    private int MAX_IMAGE_HEIGHT;

    @Value("${image.max.allowed}")
    private int MAX_IMAGES_ALLOWED;

    public boolean isValidImageSize(byte[] imageData) {

        return imageData.length <= MAX_IMAGE_SIZE;
    }

    public boolean isValidImageDimensions(byte[] imageData) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
            if (bufferedImage == null) {
                return false;
            }
            int imageWidth = bufferedImage.getWidth();
            int imageHeight = bufferedImage.getHeight();
            return imageWidth <= MAX_IMAGE_WIDTH && imageHeight <= MAX_IMAGE_HEIGHT;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isValidImageCount(int numberOfImages) {

        return numberOfImages <= MAX_IMAGES_ALLOWED;
    }

    public boolean imageCheck(List<MultipartFile> images) {

        if (images.size() > MAX_IMAGES_ALLOWED) {
            return false;
        }

        for (MultipartFile image : images) {
            if (!isValidImage(image)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidImage(MultipartFile image) {

        try {
            byte[] imageData = image.getBytes();
            if (!isValidImageSize(imageData) || !isValidImageDimensions(imageData)) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
