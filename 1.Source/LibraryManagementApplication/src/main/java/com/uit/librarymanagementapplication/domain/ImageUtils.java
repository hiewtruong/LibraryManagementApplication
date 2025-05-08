package com.uit.librarymanagementapplication.domain;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUtils {
    private static final String COVERS_DIR = "src/main/resources/book_covers";
    private static final String RESOURCE_PREFIX = "/book_covers/";

    /**
     * Loads and scales an image for display in a JLabel or JTable.
     * 
     * @param imagePath Path to the image (file path or resource path).
     * @param width     Desired width of the scaled image.
     * @param height    Desired height of the scaled image.
     * @return ImageIcon if successful, null otherwise.
     */
    public static ImageIcon loadImage(String imagePath, int width, int height) {
        try {
            BufferedImage image = null;

            // Check if imagePath is a resource path
            if (imagePath != null && imagePath.startsWith(RESOURCE_PREFIX)) {
                java.net.URL imageURL = ImageUtils.class.getResource(imagePath);
                if (imageURL != null) {
                    image = ImageIO.read(imageURL);
                }
            } else if (imagePath != null && !imagePath.isEmpty()) {
                // Treat as file path
                File file = new File(imagePath);
                if (file.exists()) {
                    image = ImageIO.read(file);
                }
            }

            if (image != null) {
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (IOException e) {
            System.err.println("Failed to load image: " + imagePath + " - " + e.getMessage());
        }
        return null;
    }

    /**
     * Saves an image file to the book_covers directory.
     * 
     * @param sourceFile The source image file to copy.
     * @return The filename of the saved image if successful, null otherwise.
     * @throws IOException If saving fails.
     */
    public static String saveImage(File sourceFile) throws IOException {
        if (!sourceFile.exists()) {
            throw new IOException("Source file does not exist: " + sourceFile.getAbsolutePath());
        }

        // Create book_covers directory if it doesn't exist
        Path coversDir = Paths.get(COVERS_DIR);
        if (!Files.exists(coversDir)) {
            Files.createDirectories(coversDir);
        }

        String fileName = sourceFile.getName();
        Path targetPath = Paths.get(COVERS_DIR, fileName);
        Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // Verify the file was copied
        if (!Files.exists(targetPath)) {
            throw new IOException("Failed to copy image to: " + targetPath);
        }

        return fileName;
    }

    /**
     * Returns a TableCellRenderer for displaying images in a JTable.
     * 
     * @return TableCellRenderer instance.
     */
    public static TableCellRenderer getImageRenderer() {
        return new ImageTableCellRenderer();
    }

    private static class ImageTableCellRenderer extends JLabel implements TableCellRenderer {
        public ImageTableCellRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
            } else {
                setIcon(null);
            }

            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            return this;
        }
    }
}