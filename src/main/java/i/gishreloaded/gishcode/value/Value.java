package i.gishreloaded.gishcode.value;

public class Value<T> {

    public T value;

    private String name;

    private T defaultValue;

    public Value(String name, T defaultValue) {

        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public String getName() {

        return name;
    }

    public T getDefaultValue() {

        return defaultValue;
    }

    public T getValue() {

        return value;
    }

    public void setValue(T value) {

        this.value = value;
    }
}
