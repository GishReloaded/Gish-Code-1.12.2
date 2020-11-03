package i.gishreloaded.gishcode.value.types;

import i.gishreloaded.gishcode.value.Value;

public class DoubleValue extends Value<Double> {

	protected double min, max;

	public DoubleValue(String name, double defaultValue, double min, double max) {
		super(name, defaultValue);
		this.min = min;
		this.max = max;
	}
	
	@Override
	public Double getValue() {
		Number number = super.getValue();
		double value = number.doubleValue();
		return value;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}
}
