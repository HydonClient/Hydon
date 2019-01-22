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

    /**
     * List of commands initialized with - extends Command
     */
    private List<Command> commands = new ArrayList<>();

    /**
     * Load the classes that are initialized with - extends Command
     */
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

    /**
     * Register commands that are initialized with this
     *
     * @param command the command class initialized with - extends Command
     */
    public void register(Command command) {
        commands.add(command);
    }

    /**
     * Get the list of commands that are initialized with - extends Command
     *
     * @return the list of commands
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Posts all of the usages of the commands in chat
     *
     * @param e the event being used
     */
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
