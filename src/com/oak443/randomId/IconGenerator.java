package com.oak443.randomId;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IconGenerator {
    private final Integer length, outputLength, borderLength;
    private final Color backgroundColor;

    public IconGenerator(Integer length, Integer outputLength, Integer borderLength, Color backgroundColor) {
        this.length = length;
        this.outputLength = outputLength;
        this.borderLength = borderLength;
        this.backgroundColor = backgroundColor;
    }

    public BufferedImage fromHash(int hash) {
        if (hash < 0) hash = -hash;
        BufferedImage image = new BufferedImage(outputLength, outputLength, BufferedImage.TYPE_INT_ARGB);
        Graphics2D ig2d = image.createGraphics();
        ig2d.setColor(backgroundColor);
        ig2d.fillRect(0, 0, outputLength, outputLength);
        int cLength = outputLength - borderLength * 2;
        BufferedImage c = new BufferedImage(length, length, BufferedImage.TYPE_INT_ARGB);
        Graphics2D cg2d = c.createGraphics();
        cg2d.setColor(new Color(hash % 0x1000000));
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < (length + 1) / 2; j++) {
                if (hash % 2 == 1) {
                    cg2d.fillRect(j, i, 1, 1);
                    cg2d.fillRect(length - 1 - j, i, 1, 1);
                }
                hash >>= 1;
            }
        }
        ig2d.drawImage(c, borderLength, borderLength, cLength, cLength, null);
        return image;
    }

    public BufferedImage fromString(String s) {
        return fromHash(s.hashCode());
    }
}
