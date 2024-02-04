package com.projectlib.librarian.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

    private int processedImageCount = 0;

    public byte[] resizeAndCompressImage(byte[] imageData) throws IOException {
        // Check if image size exceeds the maximum limit
        if (imageData.length > MAX_IMAGE_SIZE) {
            throw new IOException("Image size exceeds the maximum limit.");
        }

        // Read the original image
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));

        // Get original dimensions
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Check if image dimensions exceed the maximum limit
        if (originalWidth > MAX_IMAGE_WIDTH || originalHeight > MAX_IMAGE_HEIGHT) {
            // Calculate the new dimensions
            double scale = Math.min((double) MAX_IMAGE_WIDTH / originalWidth, (double) MAX_IMAGE_HEIGHT / originalHeight);
            int newWidth = (int) (originalWidth * scale);
            int newHeight = (int) (originalHeight * scale);

            // Create a scaled instance of the image
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage bufferedResizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            // Draw the scaled image on to the buffered image
            Graphics2D g = bufferedResizedImage.createGraphics();
            g.drawImage(resizedImage, 0, 0, null);
            g.dispose();

            // Write the buffered image to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedResizedImage, "jpg", outputStream);

            // Increment the processed image count
            processedImageCount++;

            // Set the resized image data
            imageData = outputStream.toByteArray();

            // Check if the maximum number of images has been reached after processing
            if (!isValidImageCount(processedImageCount)) {
                throw new IOException("Maximum number of images allowed has been reached.");
            }
        }

        return imageData;
    }

    public boolean isValidImageSize(byte[] imageData) {
        return imageData.length <= MAX_IMAGE_SIZE;
    }

    public boolean isValidImageCount(int numberOfImages) {
        return numberOfImages <= MAX_IMAGES_ALLOWED;
    }
}
