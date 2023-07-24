package me.saniukvyacheslav.gui.models;

import lombok.Setter;

/**
 * {@link StatusLine} model used to update status line text.
 */
public class StatusLine {

    @Setter
    private String propertiesFilePath;
    private int updateActionsCounter;
    private int insertActionsCounter;
    private int deleteActionsCounter;
    private final StringBuilder stringBuilder = new StringBuilder(); // Final StringBuilder;

    /**
     * Get line text in format: "File: [PATH_ON_PROPERTIES_FILE]; Inserts: [INSERTS_COUNT]; Updates: [UPDATES_COUNT]; Deletes: [DELETES_COUNT];".
     * @return - status line text.
     */
    public String getLineText() {
        boolean isTextBefore = false;
        String tmp;

        // Create result status line text:
        // Path on properties file:
        if (this.propertiesFilePath != null && !(this.propertiesFilePath.isEmpty())) {
            this.stringBuilder.append("File: ").append(this.propertiesFilePath).append(";");
            isTextBefore = true;
        }

        // Check actions counters:
        // Inserts counter:
        if (this.insertActionsCounter != 0) {
            tmp = String.format("Inserts: %d;", this.insertActionsCounter);
            if (isTextBefore) this.stringBuilder.append(" ").append(tmp);
            else this.stringBuilder.append(tmp);
            isTextBefore = true;
        }
        // Updates counter:
        if (this.updateActionsCounter != 0) {
            tmp = String.format("Updates: %d;", this.updateActionsCounter);
            if (isTextBefore) this.stringBuilder.append(" ").append(tmp);
            else this.stringBuilder.append(tmp);
            isTextBefore = true;
        }
        // Deletes counter:
        if (this.deleteActionsCounter != 0) {
            tmp = String.format("Deletes: %d;", this.deleteActionsCounter);
            if (isTextBefore) this.stringBuilder.append(" ").append(tmp);
            else this.stringBuilder.append(tmp);
        }

        return this.stringBuilder.toString();
    }

    /**
     * Clear path on properties file and reset counters.
     */
    public void clear() {
        this.propertiesFilePath = null;
        this.insertActionsCounter = 0;
        this.updateActionsCounter = 0;
        this.deleteActionsCounter = 0;
        this.stringBuilder.setLength(0);
    }
}
