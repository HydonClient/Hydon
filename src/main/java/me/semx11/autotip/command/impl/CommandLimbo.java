package me.semx11.autotip.command.impl;

import me.semx11.autotip.Autotip;
import me.semx11.autotip.command.CommandAbstract;
import net.hydonclient.managers.impl.command.Command;

public class CommandLimbo extends Command {

    private boolean executed;

    private Autotip autotip;

    public CommandLimbo(Autotip autotip) {
        this.autotip = autotip;
    }

    public boolean hasExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public void onCommand(String[] args) {

    }
}
