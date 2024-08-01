package com.odfin.core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MultipleEmojisExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Multiple Emojis Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 200);
            frame.setLayout(new FlowLayout());

            // Pfad zu den Emojis
            String emojiPath = "C:\\Users\\a.asfour\\IdeaProjects\\ODDiscordServer\\emoji.png";

            // Erstelle ein ImageIcon aus der Bilddatei
            ImageIcon emojiIcon = createImageIcon(emojiPath, 24, 24);

            // Erstelle JLabel für Emojis
            JLabel emojiLabel1 = new JLabel(emojiIcon);
            JLabel emojiLabel2 = new JLabel(emojiIcon);
            JLabel emojiLabel3 = new JLabel(emojiIcon);

            // Erstelle JLabel für Text
            JLabel textLabel = new JLabel("Here are some emojis: ");
            JLabel additionalTextLabel = new JLabel(" and some text.");

            // Füge alle JLabel-Elemente zum JFrame hinzu
            frame.getContentPane().add(textLabel);
            frame.getContentPane().add(emojiLabel1);
            frame.getContentPane().add(emojiLabel2);
            frame.getContentPane().add(emojiLabel3);
            frame.getContentPane().add(additionalTextLabel);

            frame.setVisible(true);
        });
    }

    private static ImageIcon createImageIcon(String path, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            BufferedImage scaledImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledImg.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(img, 0, 0, width, height, null);
            g2d.dispose();
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
