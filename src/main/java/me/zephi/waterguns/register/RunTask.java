package me.zephi.waterguns.register;

public interface RunTask {
    boolean repeat();
    int ticks();
    void task();
}
