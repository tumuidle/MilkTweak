package me.h3xadecimal.milktweak.utils;

public class Timer {
    private long start;

    public Timer() {
        start = System.currentTimeMillis();
    }

    public boolean hasPassed(long ms) {
        return (System.currentTimeMillis() - start) >= ms;
    }

    public boolean hasPassedS(int s) {
        return (System.currentTimeMillis() - start) >= s* 1000L;
    }

    public void reset() {
        start = System.currentTimeMillis();
    }
}
