package com.oak443.randomId;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WinUI extends JFrame {{
    setName("WinUI");
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("RandomId 0.2 - WinUI");
    setSize(320, 512);
    setLocationRelativeTo(null);
    JTabbedPane tabbedPane = new JTabbedPane() {{
        setName("TabbedPane");
        setTabPlacement(JTabbedPane.BOTTOM);
        JPanel generate = new JPanel() {{
            setName("Generate");
            JButton icon = new JButton() {{
                setName("Icon");
                addActionListener(e -> {
                        Toolkit toolkit = Toolkit.getDefaultToolkit();
                        Clipboard clipboard = toolkit.getSystemClipboard();
                        ImageSelection imageSelection = new ImageSelection(((ImageIcon) getIcon()).getImage());
                        clipboard.setContents(imageSelection, null);
                        JLabel label = new JLabel("[Icon] Copied to clipboard!", JLabel.CENTER);
                        getParent().add(label);
                        getParent().revalidate();
                        getParent().repaint();
                        new Thread(() -> {
                            try { Thread.sleep(1024); } catch (InterruptedException ignored) {}
                            getParent().remove(label);
                            getParent().revalidate();
                            getParent().repaint();
                        }).start();
                    });
                }

                static class ImageSelection implements Transferable {
                    private final Image image;
                    ImageSelection(Image image) {
                        this.image = image;
                    }
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[] { DataFlavor.imageFlavor };
                    }
                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return DataFlavor.imageFlavor.equals(flavor);
                    }
                    @Override
                    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                        if (isDataFlavorSupported(flavor)) {
                            return image;
                        } else {
                            throw new UnsupportedFlavorException(flavor);
                        }
                    }
                }
            };
            add(icon, 0);
            JButton name = new JButton() {{
                setName("Name");
                setFont(new Font(getFont().getName(), getFont().getStyle(), 32));
                addActionListener(e -> {
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    Clipboard clipboard = toolkit.getSystemClipboard();
                    StringSelection stringSelection = new StringSelection(getText());
                    clipboard.setContents(stringSelection, null);
                    JLabel label = new JLabel("[Name] Copied to clipboard!", JLabel.CENTER);
                    getParent().add(label);
                    getParent().revalidate();
                    getParent().repaint();
                    new Thread(() -> {
                        try { Thread.sleep(1024); } catch (InterruptedException ignored) {}
                        getParent().remove(label);
                        getParent().revalidate();
                        getParent().repaint();
                    }).start();
                });
            }};
            add(name, 1);
            JPanel seed = new JPanel() {{
                setName("Seed");
                JLabel label = new JLabel("Custom Seed: ");
                add(label, 0);
                JTextField seed = new JTextField() {{
                    setName("Seed");
                    setColumns(20);
                }};
                add(seed, 1);
            }};
            add(seed, 2);
            JButton regenerate = new JButton() {{
                setName("Regenerate");
                setFont(new Font(getFont().getName(), getFont().getStyle(), 18));
                setText("[ Regenerate ]");
                addActionListener(e -> {
                    JButton name = (JButton) getParent().getComponent(1);
                    JTextField seed = (JTextField) ((JPanel) getParent().getComponent(2)).getComponent(1);
                    if (Objects.equals(seed.getText(), "")) {
                        name.setText(Root.INSTANCE.getNameGenerator().next());
                    } else {
                        name.setText(seed.getText());
                        seed.setText("");
                    }
                    JButton icon = (JButton) getParent().getComponent(0);
                    icon.setIcon(new ImageIcon(Root.INSTANCE.getIconGenerator().fromString(name.getText())));
                });
            }};
            add(regenerate, 3);
            JButton archive = new JButton() {{
                setName("Archive");
                setFont(new Font(getFont().getName(), getFont().getStyle(), 18));
                setText("[ Archive ]");
                addActionListener(e -> {
                    setEnabled(false);
                    try {
                        JButton name = (JButton) getParent().getComponent(1);
                        File folder = new File(Root.INSTANCE.getArchive().getPath()
                                + "/" + System.currentTimeMillis() + "_" + name.getText());
                        if (folder.exists()) throw new Exception();
                        if (!folder.mkdir()) throw new Exception();
                        BufferedImage icon = Root.INSTANCE.getIconGenerator().fromString(name.getText());
                        ImageIO.write(icon, "png", new File(folder.getPath() + "/icon.png"));
                        JLabel label = new JLabel("[Archive] Succeeded!", JLabel.CENTER);
                        getParent().add(label);
                        getParent().revalidate();
                        getParent().repaint();
                        new Thread(() -> {
                            try { Thread.sleep(1024); } catch (InterruptedException ignored) {}
                            getParent().remove(label);
                            getParent().revalidate();
                            getParent().repaint();
                        }).start();
                        try {
                            Desktop.getDesktop().open(folder);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } catch (Exception ex) {
                        JLabel label = new JLabel("[Archive] Failed!", JLabel.CENTER);
                        getParent().add(label);
                        getParent().revalidate();
                        getParent().repaint();
                        new Thread(() -> {
                            try { Thread.sleep(1024); } catch (InterruptedException ignored) {}
                            getParent().remove(label);
                            getParent().revalidate();
                            getParent().repaint();
                        }).start();
                    }
                    setEnabled(true);
                });
            }};
            add(archive, 4);
            regenerate.doClick();
        }};
        addTab("Generate", generate);
        JPanel archive = new JPanel() {{
            setName("Archives");
            JButton openInExplorer = new JButton() {{
                setName("OpenInExplorer");
                setText("Open Archive Folder in Explorer");
                addActionListener(e -> {
                    try {
                        Desktop.getDesktop().open(Root.INSTANCE.getArchive());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }};
            add(openInExplorer);
        }};
        addTab("Archives", archive);
    }};
    setContentPane(tabbedPane);
}}