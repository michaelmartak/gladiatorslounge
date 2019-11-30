/**
 * 
 */
package org.oaktownrpg.jgladiator.app.mtg;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;
import org.oaktownrpg.jgladiator.ui.label.LabelBuilder;

/**
 * Test of mana symbols
 * 
 * @author michaelmartak
 *
 */
public class ManaSymbolTest {

    private static final int SIZE = 50;
    private boolean done;

    /**
     * 
     */
    public ManaSymbolTest() {

    }

    @Test
    public void testViewManaSymbols() throws Exception {
        EventQueue.invokeAndWait(() -> {
            createFrame(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    done = true;
                    synchronized (ManaSymbolTest.this) {
                        ManaSymbolTest.this.notifyAll();
                    }
                }

            });
        });
        while (!done) {
            synchronized (this) {
                wait();
            }
        }
    }

    void createFrame(WindowListener windowListener) {
        JFrame frame = new JFrame("Mana Symbols");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contents = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("SVG");
        if (!readers.hasNext()) {
            throw new RuntimeException("No SVG reader");
        }
        for (ManaSymbol symbol : ManaSymbol.values()) {
            JLabel label = LabelBuilder.svgLabel().bytes(symbol.getSvg().getBytes()).width(SIZE).height(SIZE).build();
            contents.add(label);
        }
        for (GenericManaSymbol symbol : GenericManaSymbol.values()) {
            JLabel label = LabelBuilder.svgLabel().bytes(symbol.getSvg().getBytes()).width(SIZE).height(SIZE).build();
            contents.add(label);
        }
        frame.addWindowListener(windowListener);
        frame.setContentPane(contents);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
