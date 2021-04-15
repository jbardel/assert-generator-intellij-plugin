package jbl.assertion.generator.junit;

public class JUnitContext {

    public static final JUnitContext JUNIT4_CONTEXT = new JUnitContext(
        JUnitIdentifiers.JUNIT4_TEST_FQN,
        JUnitIdentifiers.JUNIT4_ASSERTIONS_FQN
    );

    public static final JUnitContext JUNIT5_CONTEXT = new JUnitContext(
        JUnitIdentifiers.JUNIT5_TEST_FQN,
        JUnitIdentifiers.JUNIT5_ASSERTIONS_FQN
    );

    private final String testFqn;
    private final String assertionsFqn;

    public JUnitContext(final String testFqn, final String assertionsFqn) {
        this.testFqn = testFqn;
        this.assertionsFqn = assertionsFqn;
    }

    public String getTestFqn() {
        return testFqn;
    }

    public String getAssertionsFqn() {
        return assertionsFqn;
    }
}
