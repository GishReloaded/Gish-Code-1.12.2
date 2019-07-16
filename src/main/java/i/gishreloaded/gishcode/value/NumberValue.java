package i.gishreloaded.gishcode.value;

public class NumberValue extends Value<Double> {

    protected Double min, max;

    public NumberValue(String name, Double defaultValue, Double min, Double max) {

        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    public Double getMin() {

        return min;
    }

    public Double getMax() {

        return max;
    }
}
