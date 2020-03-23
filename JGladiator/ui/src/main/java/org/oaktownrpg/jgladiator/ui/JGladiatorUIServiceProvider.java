/**
 * 
 */
package org.oaktownrpg.jgladiator.ui;

import org.oaktownrpg.jgladiator.framework.helper.AbstractServiceProvider;

/**
 * @author michaelmartak
 *
 */
public class JGladiatorUIServiceProvider extends AbstractServiceProvider {

    private final JGladiatorUI ui = new JGladiatorUI(this);
    
    /**
     * 
     */
    public JGladiatorUIServiceProvider() {
        super("ui.provider");
        provide(ui);
    }

}
