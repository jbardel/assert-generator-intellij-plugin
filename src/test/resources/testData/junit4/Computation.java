package demo.jbl;

public class Computation {

    public Result compute(final int a, final int b) {
        return new Result(a + b, a * b, a % b);
    }

}
