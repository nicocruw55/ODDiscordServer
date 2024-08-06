package com.odfin.screenshare;

import java.awt.image.BufferedImage;

public class ImageComparer {

    public BufferedImage getDifferenceImage(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();
        BufferedImage diff = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel1 = img1.getRGB(x, y);
                int pixel2 = img2.getRGB(x, y);
                if (pixel1 != pixel2) {
                    diff.setRGB(x, y, pixel1);
                }
            }
        }
        return diff;
    }
}
