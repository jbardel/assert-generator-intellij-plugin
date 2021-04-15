package jbl.assertion.generator;

import jbl.assertion.generator.junit.JUnitContext;
import org.jetbrains.annotations.NotNull;

public class JUnit4IntentionAction extends BaseAssertionsGenerator {

    @Override
    public @NotNull String getText() {
        return "Generate JUnit4 assertions";
    }

    @Override
    public JUnitContext getJUnitContext() {
        return JUnitContext.JUNIT4_CONTEXT;
    }

}
