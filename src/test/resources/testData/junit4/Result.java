package demo.jbl;

public class Result {

    private int sum;

    private long mult;

    private Integer mod;

    public Result(int sum, int mult, int mod) {
        this.sum = sum;
        this.mult = mult;
        this.mod = mod;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public long getMult() {
        return mult;
    }

    public void setMult(long mult) {
        this.mult = mult;
    }

    public Integer getMod() {
        return mod;
    }

    public void setMod(Integer mod) {
        this.mod = mod;
    }
}
