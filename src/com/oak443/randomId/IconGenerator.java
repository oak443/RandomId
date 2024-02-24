package com.oak443.randomId;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IconGenerator {
    private final Integer length;
    private final Integer outputWidth, outputBorderWidth;
    private final Color outputBackground;

    public IconGenerator(Integer length, Integer outputWidth, Integer outputBorderWidth, Color outputBackground) {
        this.length = length;
        this.outputWidth = outputWidth;
        this.outputBorderWidth = outputBorderWidth;
        this.outputBackground = outputBackground;
    }

    public BufferedImage fromHash(Integer hash) {
        if (hash < 0) hash = -hash;
        BufferedImage image = new BufferedImage(length, length, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(new Color(hash % 0x1000000));
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < (length + 1) / 2; j++) {
                if (hash % 2 == 1) {
                    g2d.fillRect(j, i, 1, 1);
                    g2d.fillRect(length - 1 - j, i, 1, 1);
                }
                hash >>= 1;
            }
        }
        return image;
    }

    public BufferedImage fromString(String s) {
        return fromHash(s.hashCode());
    }

    public BufferedImage toOutput(BufferedImage icon, Integer rate) {
        int width = outputWidth * rate, borderWidth = rate * outputBorderWidth;
        BufferedImage image = new BufferedImage(width, (width), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(outputBackground);
        g2d.fillRect(0, 0, width, (width));
        int contentWidth = width - borderWidth, contentX = borderWidth / 2;
        g2d.drawImage(icon, contentX, (contentX), contentWidth, (contentWidth), null);
        return image;
    }

    public BufferedImage toOutput(String s, Integer rate) {
        return toOutput(fromString(s), rate);
    }
}
