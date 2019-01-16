package net.hydonclient.managers.impl;

import java.util.ArrayList;
import java.util.List;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.ChatMessageSendEvent;
import net.hydonclient.managers.impl.command.Command;
import net.minecraft.client.Minecraft;
import org.reflections.Reflections;

public class CommandManager {

    private List<Command> commands = new ArrayList<>();

    public void load() {
        EventBus.register(this);
        new Reflections("net.hydonclient.managers.impl.command.imp").getSubTypesOf(Command.class)
            .forEach(aClass -> {
                try {
                    commands.add(aClass.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
    }

    public void register(Command command) {
        commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    @EventListener
    public void onChatMessageSend(ChatMessageSendEvent e) {
        String message = e.getMessage();
        if (message.isEmpty() || !message.startsWith("/")) {
            return;
        }
        String command = e.getMessage().substring(1).split(" ")[0];
        String[] args =
            e.getMessage().substring(command.length() + 1).trim().isEmpty() ? new String[0]
                : e.getMessage().substring(command.length() + 1).trim().split(" ");
        for (Command c : commands) {
            if (c.getName().equalsIgnoreCase(command)) {
                Minecraft.getMinecraft().displayGuiScreen(null);
                e.setCancelled(true);

                c.onCommand(args);
                return;
            }
        }
    }

}
