package com.bitset.javagame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Surface {
    BufferedImage image;

    public int width, height;

    public static Surface fromFile(String path) {
        BufferedImage image;

        try {
            image = ImageIO.read(Surface.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Surface(image);
    }

    Surface(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();

        this.image = image;
    }

    public Surface rotate(double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        var transform = new AffineTransform();
        transform.rotate(angleRadians, image.getWidth() / 2.0f, image.getHeight() / 2.0f);

        var rect = new Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight());
        var bounds = transform.createTransformedShape(rect).getBounds2D();
        var translation = new AffineTransform();
        translation.translate(-bounds.getX(), -bounds.getY());
        transform.preConcatenate(translation);

        var op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        var rotated = op.filter(image, null);

        var result = new BufferedImage((int) bounds.getWidth(), (int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) result.getGraphics();
        graphics.drawImage(rotated, 0, 0, null);

        return new Surface(result);
    }

    public Surface(int width, int height) {
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void fill(Color color) {
        var graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(new java.awt.Color(color.red, color.green, color.blue, color.alpha));
        graphics.fillRect(0, 0, width, height);
    }

    public void line(Color color, int x1, int y1, int x2, int y2, int width) {
        var graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(new java.awt.Color(color.red, color.green, color.blue, color.alpha));
        graphics.setStroke(new BasicStroke(width));
        graphics.drawLine(x1, y1, x2, y2);
    }

    public void rect(Color color, int x, int y, int width, int height, int thickness) {
        var graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(new java.awt.Color(color.red, color.green, color.blue, color.alpha));

        if (thickness == 0) {
            graphics.fillRect(x, y, width, height);
            return;
        }

        graphics.setStroke(new BasicStroke(thickness));
        graphics.drawRect(x + thickness / 2, y + thickness / 2, width - thickness / 2, height - thickness / 2);
    }

    public void surface(Surface surface, int x, int y, int width, int height, int sx, int sy, int swidth, int sheight) {
        var graphics = (Graphics2D) image.getGraphics();
        graphics.drawImage(surface.image, x, y, x + width, y + height, sx, sy, sx + swidth, sy + sheight, null);
    }

    public void surface(Surface surface, int x, int y, int width, int height) {
        var graphics = (Graphics2D) image.getGraphics();
        graphics.drawImage(surface.image, x, y, width, height, null);
    }

    public void surface(Surface surface, int x, int y) {
        var graphics = (Graphics2D) image.getGraphics();
        graphics.drawImage(surface.image, x, y, null);
    }

    public void oval(Color color, int x, int y, int width, int height, int thickness) {
        var graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(new java.awt.Color(color.red, color.green, color.blue, color.alpha));

        if (thickness == 0) {
            graphics.fillOval(x, y, width, height);
            return;
        }

        graphics.setStroke(new BasicStroke(thickness));
        graphics.drawOval(x, y, width, height);
    }
}
