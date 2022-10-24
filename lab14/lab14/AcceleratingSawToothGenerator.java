package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this. factor = factor;
    }

    public double next() {
        state = (state + 1);
        int times = 0;
        int sum = period;
        while (state > sum) {
            times ++;
            sum += period * Math.pow(factor, times);
        }
        sum -= period * Math.pow(factor, times);
        double a = state - sum;
        return 2 * a / (period * Math.pow(factor, times)) - 1;
    }
}
