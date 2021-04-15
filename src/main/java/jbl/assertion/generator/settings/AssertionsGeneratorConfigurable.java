package jbl.assertion.generator.settings;

import javax.swing.*;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

public class AssertionsGeneratorConfigurable implements Configurable {

    private AssertionsGeneratorComponent settingsComponent;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Assertions Generator Configuration";
    }

    @Override
    public @Nullable JComponent createComponent() {
        settingsComponent = new AssertionsGeneratorComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}
