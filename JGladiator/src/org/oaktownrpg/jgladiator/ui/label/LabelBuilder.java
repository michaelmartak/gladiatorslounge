/**
 * 
 */
package org.oaktownrpg.jgladiator.ui.label;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Builder for label components.
 * 
 * @author michaelmartak
 *
 */
public abstract class LabelBuilder<B extends LabelBuilder<B>> {

    protected int width;
    protected int height;
    protected int padding = 4;

    /**
     * Package protected constructor
     */
    LabelBuilder() {
    }

    public LabelBuilder<B> width(int value) {
        this.width = value;
        return this;
    }

    public LabelBuilder<B> height(int value) {
        this.height = value;
        return this;
    }

    public abstract JLabel build();

    public static class SvgLabelBuilder extends LabelBuilder<SvgLabelBuilder> {

        private byte[] bytes;

        SvgLabelBuilder() {
        }

        @Override
        public JLabel build() {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedImage image;
            try {
                image = ImageIO.read(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BufferedImage resized = new BufferedImage(width, height, image.getType());
            Graphics2D g = resized.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(), image.getHeight(), null);
            g.dispose();
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(resized));
            label.setPreferredSize(new Dimension(width + padding, height + padding));
            return label;
        }

        public SvgLabelBuilder bytes(byte[] value) {
            this.bytes = value;
            return this;
        }

    }

    public static SvgLabelBuilder svgLabel() {
        return new SvgLabelBuilder();
    }

}
