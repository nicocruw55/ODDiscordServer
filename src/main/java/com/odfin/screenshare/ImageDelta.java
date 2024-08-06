package com.odfin.screenshare;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ImageDelta {

    public static byte[] getDifferenceBytes(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();

        int[] img1Pixels = new int[width * height];
        int[] img2Pixels = new int[width * height];

        img1.getRGB(0, 0, width, height, img1Pixels, 0, width);
        img2.getRGB(0, 0, width, height, img2Pixels, 0, width);

        // Delta array to hold differences
        byte[] deltaBytes = new byte[width * height * 4]; // 4 bytes per pixel

        for (int i = 0; i < img1Pixels.length; i++) {
            int pixel1 = img1Pixels[i];
            int pixel2 = img2Pixels[i];

            int alpha1 = (pixel1 >> 24) & 0xFF;
            int red1 = (pixel1 >> 16) & 0xFF;
            int green1 = (pixel1 >> 8) & 0xFF;
            int blue1 = (pixel1) & 0xFF;

            int alpha2 = (pixel2 >> 24) & 0xFF;
            int red2 = (pixel2 >> 16) & 0xFF;
            int green2 = (pixel2 >> 8) & 0xFF;
            int blue2 = (pixel2) & 0xFF;

            // Calculate deltas
            deltaBytes[i * 4] = (byte) (alpha2 - alpha1); // alpha
            deltaBytes[i * 4 + 1] = (byte) (red2 - red1); // red
            deltaBytes[i * 4 + 2] = (byte) (green2 - green1); // green
            deltaBytes[i * 4 + 3] = (byte) (blue2 - blue1); // blue
        }

        return deltaBytes;
    }

    public static BufferedImage applyDelta(BufferedImage baseImage, byte[] deltaBytes) {
        int width = baseImage.getWidth();
        int height = baseImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int[] basePixels = new int[width * height];
        baseImage.getRGB(0, 0, width, height, basePixels, 0, width);

        int[] resultPixels = new int[width * height];

        for (int i = 0; i < basePixels.length; i++) {
            int basePixel = basePixels[i];
            int alpha = (basePixel >> 24) & 0xFF;
            int red = (basePixel >> 16) & 0xFF;
            int green = (basePixel >> 8) & 0xFF;
            int blue = (basePixel) & 0xFF;

            alpha = Math.min(255, Math.max(0, (alpha + (deltaBytes[i * 4] & 0xFF))));
            red = Math.min(255, Math.max(0, (red + (deltaBytes[i * 4 + 1] & 0xFF))));
            green = Math.min(255, Math.max(0, (green + (deltaBytes[i * 4 + 2] & 0xFF))));
            blue = Math.min(255, Math.max(0, (blue + (deltaBytes[i * 4 + 3] & 0xFF))));

            resultPixels[i] = (alpha << 24) | (red << 16) | (green << 8) | blue;
        }

        resultImage.setRGB(0, 0, width, height, resultPixels, 0, width);
        return resultImage;
    }
}
