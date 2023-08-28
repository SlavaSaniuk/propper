package me.saniukvyacheslav.gui.models;

import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;
import me.saniukvyacheslav.gui.views.StatusLineView;

import java.util.Objects;

/**
 * Application status line model.
 */
public class StatusLineModel {

    private final Label mainLabel; // JavaFx Label;
    @Getter private final StatusLineView statusLineView; // Status line view;
    @Setter private int updatesCount; // Updates count;
    @Setter private String repositoryObj; // Repository object;
    private final StringBuilder textBuilder = new StringBuilder(); // StringBuilder for getText method;

    /**
     * Construct new StatusLineModel model.
     * @param aRootNode - FX HBox layout by default.
     * @param aMainLabel - FX Label instance by default.
     */
    public StatusLineModel(Node aRootNode, Label aMainLabel) {
        // Check parameters:
        Objects.requireNonNull(aRootNode, "FX HBox [aRootNode] must be not null.");
        Objects.requireNonNull(aMainLabel, "FX Label [aMainLabel] must be not null.");

        // Map:
        this.mainLabel = aMainLabel;

        // Create view:
        this.statusLineView = new StatusLineView(aRootNode);
        this.statusLineView.stylize();

        // Set default text:
        this.mainLabel.setText("");
    }

    /**
     * Update status line text from model fields.
     */
    public void updateText() {
        // Make line text and set it:
        this.mainLabel.setText(this.makeText());
    }

    /**
     * Make status line label text from model fields.
     * @return - label text.
     */
    private String makeText() {
        boolean isTextBefore = false;
        String tmp;

        // Create result status line text:
        // Path on repository object:
        if (this.repositoryObj != null && !(this.repositoryObj.isEmpty())) {
            this.textBuilder.append(this.repositoryObj);
            isTextBefore = true;
        }

        // Check actions counters:

        // Updates counter:
        if (this.updatesCount != 0) {
            tmp = String.format("Updates: %d;", this.updatesCount);
            if (isTextBefore) this.textBuilder.append(" ").append(tmp);
            else this.textBuilder.append(tmp);
        }

        tmp = this.textBuilder.toString();
        this.textBuilder.setLength(0); // Clear StringBuilder;
        return tmp;
    }

}
