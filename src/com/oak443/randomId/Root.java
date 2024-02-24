package com.oak443.randomId;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

public enum Root {
    INSTANCE;

    public static void main(String[] args) {
        Root.INSTANCE.boot();
    }

    public void boot() {
        initArchive();
        initPreferences();
        initIconGenerator();
        initNameGenerator();
        initWinUI();
    }

    private File preferencesFile;

    public void initPreferences() {
        String userHome = System.getProperty("user.home");
        preferencesFile = new File(userHome + "/.com.oak443.RandomId/preferences.ini");
        if (!preferencesFile.exists()) {
            try {
                if (!preferencesFile.createNewFile()) throw new Exception();
                new Properties().store(new FileOutputStream(preferencesFile), null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Properties preferences = new Properties();
        try {
            preferences.load(new FileInputStream(preferencesFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.preferences = preferences;
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
        } catch (Exception ignored) {}
        WinUI winUI = new WinUI();
        winUI.setVisible(true);
    }

    private Properties preferences;

    public Properties getPreferences() {
        return preferences;
    }

    public void storePreferences() throws IOException {
        preferences.store(new FileOutputStream(preferencesFile), null);
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
}
