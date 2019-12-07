/**
 * 
 */
package org.oaktownrpg.jgladiator.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Swing action that holds an internal Runnable to invoke.
 * 
 * @author michaelmartak
 *
 */
public class RunnableAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = -4046184570971590505L;

    private Runnable runnable = () -> {
    };

    /**
     * 
     */
    public RunnableAction() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        runnable.run();
    }

    public void setRunnable(Runnable r) {
        this.runnable = (r == null ? () -> {
        } : r);
    }

}
