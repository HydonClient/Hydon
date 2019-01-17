package me.semx11.autotip;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import me.semx11.autotip.api.RequestHandler;
import me.semx11.autotip.api.reply.impl.LocaleReply;
import me.semx11.autotip.api.reply.impl.SettingsReply;
import me.semx11.autotip.api.request.impl.LocaleRequest;
import me.semx11.autotip.api.request.impl.SettingsRequest;
import me.semx11.autotip.chat.LocaleHolder;
import me.semx11.autotip.command.CommandAbstract;
import me.semx11.autotip.command.impl.CommandAutotip;
import me.semx11.autotip.command.impl.CommandLimbo;
import me.semx11.autotip.config.Config;
import me.semx11.autotip.config.GlobalSettings;
import me.semx11.autotip.event.EventChatReceived;
import me.semx11.autotip.event.EventClientConnection;
import me.semx11.autotip.event.EventClientTick;
import me.semx11.autotip.gson.creator.ConfigCreator;
import me.semx11.autotip.gson.creator.StatsDailyCreator;
import me.semx11.autotip.gson.exclusion.AnnotationExclusiveStrategy;
import me.semx11.autotip.core.MigrationManager;
import me.semx11.autotip.core.SessionManager;
import me.semx11.autotip.core.StatsManager;
import me.semx11.autotip.core.TaskManager;
import me.semx11.autotip.stats.StatsDaily;
import me.semx11.autotip.universal.UniversalUtil;
import me.semx11.autotip.util.AutoTipVersion;
import me.semx11.autotip.util.ErrorReport;
import me.semx11.autotip.util.FileUtil;
import me.semx11.autotip.chat.MessageUtil;
import me.semx11.autotip.util.MinecraftVersion;
import net.hydonclient.Hydon;
import net.hydonclient.event.Event;
import net.hydonclient.event.EventBus;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.managers.impl.command.Command;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Info(name = "Autotip", author = "Semx11", version = "3.0")
public class Autotip extends Mod {

    static final String VERSION = "3.0";

    public static IChatComponent tabHeader;

    private final List<Event> events = new ArrayList<>();
    private final List<Command> commands = new ArrayList<>();

    private boolean initialized = false;

    private Minecraft minecraft;
    private MinecraftVersion mcVersion;
    private AutoTipVersion autoTipVersion;

    private Gson gson;

    private FileUtil fileUtil;
    private MessageUtil messageUtil;

    private Config config;
    private GlobalSettings globalSettings;
    private LocaleHolder localeHolder;

    private TaskManager taskManager;
    private SessionManager sessionManager;
    private MigrationManager migrationManager;
    private StatsManager statsManager;

    public boolean isInitialized() {
        return initialized;
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public GameProfile getGameProfile() {
        return minecraft.getSession().getProfile();
    }

    public MinecraftVersion getMcVersion() {
        return mcVersion;
    }

    public AutoTipVersion getAutoTipVersion() {
        return autoTipVersion;
    }

    public Gson getGson() {
        return gson;
    }

    public FileUtil getFileUtil() {
        return fileUtil;
    }

    public MessageUtil getMessageUtil() {
        return messageUtil;
    }

    public Config getConfig() {
        return config;
    }

    public GlobalSettings getGlobalSettings() {
        return globalSettings;
    }

    public LocaleHolder getLocaleHolder() {
        return localeHolder;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public MigrationManager getMigrationManager() {
        return migrationManager;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    @Override
    public void load() {
        EventBus.register(this);
        ErrorReport.setAutotip(this);
        RequestHandler.setAutotip(this);
        UniversalUtil.setAutotip(this);

        minecraft = Minecraft.getMinecraft();
        mcVersion = UniversalUtil.getMinecraftVersion();
        autoTipVersion = new AutoTipVersion(VERSION);

        messageUtil = new MessageUtil(this);

        HydonManagers.INSTANCE.getCommandManager().register(new CommandAutotip(this));
        HydonManagers.INSTANCE.getCommandManager().register(new CommandLimbo(this));
        HydonManagers.INSTANCE.getConfigManager().register(config);
        EventBus.register(new EventClientConnection(this));
        EventBus.register(new EventChatReceived(this));
        EventBus.register(new EventClientTick(this));

        try {
            fileUtil = new FileUtil(this);
            gson = new GsonBuilder()
                    .registerTypeAdapter(Config.class, new ConfigCreator(this))
                    .registerTypeAdapter(StatsDaily.class, new StatsDailyCreator(this))
                    .setExclusionStrategies(new AnnotationExclusiveStrategy())
                    .setPrettyPrinting()
                    .create();

            config = new Config(this);
            reloadGlobalSettings();
            reloadLocale();

            taskManager = new TaskManager();
            sessionManager = new SessionManager(this);
            statsManager = new StatsManager(this);
            migrationManager = new MigrationManager(this);

            fileUtil.createDirectories();
            config.load();
            taskManager.getExecutor().execute(() -> migrationManager.migrateLegacyFiles());

            Runtime.getRuntime().addShutdownHook(new Thread(sessionManager::logout));
            initialized = true;
        } catch (IOException e) {
            messageUtil.send("Autotip is disabled because it couldn't create the required files.");
            ErrorReport.reportException(e);
        } catch (IllegalStateException e) {
            messageUtil.send("Autotip is disabled because it couldn't connect to the API.");
            ErrorReport.reportException(e);
        }
    }

    public void reloadGlobalSettings() {
        SettingsReply reply = SettingsRequest.of(this).execute();
        if (!reply.isSuccess()) {
            throw new IllegalStateException("Connection error while fetching global settings");
        }
        globalSettings = reply.getSettings();
    }

    public void reloadLocale() {
        LocaleReply reply = LocaleRequest.of(this).execute();
        if (!reply.isSuccess()) {
            throw new IllegalStateException("Could not fetch locale");
        }
        localeHolder = reply.getLocaleHolder();
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> T getEvent(Class<T> clazz) {
        return (T) events.stream().filter(event -> event.getClass().equals(clazz)).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <T extends Command> T getCommand(Class<T> clazz) {
        return (T) commands.stream().filter(command -> command.getClass().equals(clazz)).findFirst().orElse(null);
    }
}
