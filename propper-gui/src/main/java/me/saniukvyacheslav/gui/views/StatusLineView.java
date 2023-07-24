package me.saniukvyacheslav.gui.views;

import javafx.scene.Node;

/**
 * StatusLine view used to stylize application status line.
 */
public class StatusLineView {

    private final Node rootNode; // HBox layout;

    /**
     * Construct new view.
     * @param aStatusLineRootNode - HBox layout.
     */
    public StatusLineView(Node aStatusLineRootNode) {
        this.rootNode = aStatusLineRootNode;
    }

    /**
     * Stylize status line.
     * Set css classes to embedded nodes.
     */
    public void stylize() {

        // Set classes:
        this.rootNode.getStyleClass().addAll(
                "status_line_root"
        );
    }

}
