package org.flowutils.ui;

import javax.swing.*;

public abstract class UiComponentBase implements UiComponent {

    private JComponent ui;

    @Override public final JComponent getUi() {

        // Create if needed
        if (ui == null) {
            ui = createUi();
        }

        return ui;
    }

    /**
     * @return a new Swing user interface for this class.
     */
    protected abstract JComponent createUi();

}
