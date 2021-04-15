package demo.jbl;

import org.junit.jupiter.api.Test;

class ComputationTest {

    @Test
    void compute() {

        final Computation computation = new Computation();

        final Result comp<caret>ute = computation.compute(2, 3);

    }
}