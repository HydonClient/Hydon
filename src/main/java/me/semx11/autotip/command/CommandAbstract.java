package me.semx11.autotip.command;

import java.util.List;
import javax.annotation.Nullable;
import me.semx11.autotip.Autotip;
import net.hydonclient.managers.impl.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public abstract class CommandAbstract extends Command {

    public final Autotip autotip;

    public CommandAbstract(Autotip autotip) {
        this.autotip = autotip;
    }

    @Override
    public void onCommand(String[] args) {
        autotip.getTaskManager().getExecutor().execute(() -> this.onCommand(Minecraft.getMinecraft().thePlayer, args));
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args,
            BlockPos pos) {
        return this.onTabComplete(sender, args);
    }

    public abstract void onCommand(ICommandSender sender, String[] args);

    public abstract List<String> onTabComplete(ICommandSender sender, String[] args);

}