package jbl.assertion.generator;

import jbl.assertion.generator.junit.JUnitContext;
import org.jetbrains.annotations.NotNull;

public class JUnit5IntentionAction extends BaseAssertionsGenerator {

    @Override
    public @NotNull String getText() {
        return "Generate JUnit5 assertions";
    }

    @Override
    public JUnitContext getJUnitContext() {
        return JUnitContext.JUNIT5_CONTEXT;
    }

}
