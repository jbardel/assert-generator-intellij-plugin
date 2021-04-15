
package jbl.assertion.generator.settings;

import javax.swing.*;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;

public class AssertionsGeneratorComponent {

    private final JPanel myMainPanel;
    private final JBCheckBox experimentalFeaturesStatus = new JBCheckBox("Enable experimental features ?");

    public AssertionsGeneratorComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
            .addComponent(experimentalFeaturesStatus, 1)
            .addComponentFillVertically(new JPanel(), 0)
            .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public boolean isExperimentalFeaturesEnabled() {
        return experimentalFeaturesStatus.isSelected();
    }

    public void setExperimentalFeaturesEnabled(boolean newStatus) {
        experimentalFeaturesStatus.setSelected(newStatus);
    }

}
