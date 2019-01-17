package me.semx11.autotip.event;

import me.semx11.autotip.Autotip;
import me.semx11.autotip.chat.MessageOption;
import me.semx11.autotip.command.impl.CommandLimbo;
import me.semx11.autotip.config.Config;
import me.semx11.autotip.config.GlobalSettings;
import me.semx11.autotip.message.Message;
import me.semx11.autotip.message.MessageMatcher;
import me.semx11.autotip.message.StatsMessage;
import me.semx11.autotip.message.StatsMessageMatcher;
import me.semx11.autotip.stats.StatsDaily;
import me.semx11.autotip.universal.UniversalUtil;
import net.hydonclient.event.Event;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.network.chat.ServerChatEvent;

public class EventChatReceived extends Event {

    private final Autotip autotip;

    public EventChatReceived(Autotip autotip) {
        this.autotip = autotip;
    }

    @EventListener
    public void onChat(ServerChatEvent event) {
        Config config = autotip.getConfig();

        if (!autotip.getSessionManager().isOnHypixel()) {
            return;
        }

        String msg = UniversalUtil.getUnformattedText(event);

        CommandLimbo limboCommand = autotip.getCommand(CommandLimbo.class);
        if (limboCommand.hasExecuted()) {
            if (msg.startsWith("A kick occured in your connection")) {
                event.setCancelled(true);
            } else if (msg.startsWith("Illegal characters in chat")) {
                event.setCancelled(true);
                limboCommand.setExecuted(false);
            }
        }

        if (!config.isEnabled()) {
            return;
        }

        GlobalSettings settings = autotip.getGlobalSettings();
        MessageOption option = config.getMessageOption();

        for (Message message : settings.getMessages()) {
            MessageMatcher matcher = message.getMatcherFor(msg);
            if (matcher.matches()) {
                event.setCancelled(message.shouldHide(option));
                return;
            }
        }

        String hover = UniversalUtil.getHoverText(event);
        for (StatsMessage message : settings.getStatsMessages()) {
            StatsMessageMatcher matcher = message.getMatcherFor(msg);
            if (!matcher.matches()) {
                continue;
            }

            StatsDaily stats = getStats();
            matcher.applyStats(stats);
            message.applyHoverStats(hover, stats);
            event.setCancelled(message.shouldHide(option));
        }
    }

    private StatsDaily getStats() {
        return autotip.getStatsManager().getToday();
    }
}
