package jbl.assertion.generator;

import com.intellij.codeInsight.intention.IntentionAction;

public class GenerateAssertsJUnit4TestCase extends LightJavaCodeBaseTestCase {

    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testData/junit4";
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        loadJUnit4Library(myFixture.getProjectDisposable(), getModule());
    }

    public void testGenerateAssertAvailable() {
        myFixture.configureByFiles("ComputationTest.java", "Computation.java", "Result.java");

        final IntentionAction genJUnit4Intention = myFixture.getAvailableIntention("Generate JUnit4 assertions");
        assertNotNull(genJUnit4Intention);

        final IntentionAction genJUnit5Intention = myFixture.getAvailableIntention("Generate JUnit5 assertions");
        assertNotNull(genJUnit5Intention);
    }


}
