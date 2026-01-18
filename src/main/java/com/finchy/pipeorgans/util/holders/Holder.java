package com.finchy.pipeorgans.util.holders;

import java.util.function.UnaryOperator;

public class Holder<T> {
    protected T value;

    public Holder(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public void apply(UnaryOperator<T> operator) {
        this.value = operator.apply(this.value);
    }

    public static <T> Holder<T> of(T value) {
        return new Holder<>(value);
    }
}
