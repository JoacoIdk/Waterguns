package me.zephi.waterguns.register;

public abstract class Handler {
    public abstract void onRegister(RegisterHandler handler);

    public void onActivate() {}
    public void onDeactivate() {}
}
