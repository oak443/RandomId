package com.oak443.randomId;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.List;

public enum Root {
    INSTANCE;

    public static void main(String[] args) {
        Root.INSTANCE.boot();
    }

    public void boot() {
        initArchive();
        initIconGenerator();
        initNameGenerator();
        initWinUI();
    }

    public void initArchive() {
        String userHome = System.getProperty("user.home");
        archive = new File(userHome + "/.com.oak443.RandomId");
        if (!archive.exists()) {
            if (!archive.mkdir()) {
                throw new RuntimeException();
            }
        }
    }

    public void initIconGenerator() {
        iconGenerator = new IconGenerator(5, 210, 15, Color.WHITE);
    }

    public void initNameGenerator() {
        nameGenerator = new NameGenerator(8, new HashSet<>() {{
            addAll(List.of(
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                    'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                    'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9'
            ));
        }});
    }

    public void initWinUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        winUI = new WinUI();
        winUI.setVisible(true);
    }

    private File archive;

    public File getArchive() {
        return archive;
    }

    private IconGenerator iconGenerator;

    public IconGenerator getIconGenerator() {
        return iconGenerator;
    }

    private NameGenerator nameGenerator;

    public NameGenerator getNameGenerator() {
        return nameGenerator;
    }

    private WinUI winUI;

    public WinUI getWinUI() {
        return winUI;
    }
}
