package i.gishreloaded.gishcode.value.types;

import i.gishreloaded.gishcode.value.Value;

public class IntegerValue extends Value<Integer> {

	protected int min, max;

	public IntegerValue(String name, int defaultValue, int min, int max) {
		super(name, defaultValue);
		this.min = min;
		this.max = max;
	}

	@Override
	public Integer getValue() {
		Number number = super.getValue();
		int value = number.intValue();
		return value; //Integer.parseInt(String.valueOf(super.getValue().intValue()));
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}
}
