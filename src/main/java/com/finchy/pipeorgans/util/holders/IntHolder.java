package com.finchy.pipeorgans.util.holders;

public class IntHolder extends Holder<Integer> {
    public IntHolder(int value) {
        super(value);
    }

    public void increment() {
        value++;
    }
}
