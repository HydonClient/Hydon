package me.semx11.autotip.command;

import me.semx11.autotip.Autotip;
import net.hydonclient.managers.impl.command.Command;

public abstract class CommandAbstract extends Command {

    public final Autotip autotip;

    public CommandAbstract(Autotip autotip) {
        this.autotip = autotip;
    }
}
