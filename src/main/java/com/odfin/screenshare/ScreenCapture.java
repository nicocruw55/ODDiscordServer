package com.odfin.screenshare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ScreenCapture {

    public BufferedImage captureScreen(Rectangle screenRect) throws AWTException {
        Robot robot = new Robot();
        return robot.createScreenCapture(screenRect);
    }
}
