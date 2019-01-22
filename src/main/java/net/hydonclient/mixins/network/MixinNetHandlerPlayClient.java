package net.hydonclient.mixins.network;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.network.chat.ServerChatEvent;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.managers.impl.command.Command;
import net.hydonclient.mods.timechanger.config.TimeChangerConfig;
import net.hydonclient.util.maps.ChatColor;
import net.hydonclient.util.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {

    @Shadow
    private Minecraft gameController;

    @Shadow
    @Final
    private NetworkManager netManager;

    @Shadow
    public abstract NetworkManager getNetworkManager();

    @Shadow
    private WorldClient clientWorldController;

    /**
     * @author Koding
     */
    @Overwrite
    public void handleTabComplete(S3APacketTabComplete packetIn) {
        PacketThreadUtil
                .checkThreadAndEnqueue(packetIn, Minecraft.getMinecraft().thePlayer.sendQueue,
                        this.gameController);
        String[] astring = packetIn.func_149630_c();

        List<String> newOptions = new ArrayList<>(Arrays.asList(astring));

        try {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
                Field inputField = ReflectionUtils
                        .getField(Minecraft.getMinecraft().currentScreen.getClass(),
                                new String[]{"a", "inputField"});

                if (inputField != null) {
                    inputField.setAccessible(true);
                    GuiTextField input = (GuiTextField) inputField
                            .get(Minecraft.getMinecraft().currentScreen);

                    if (input.getText().startsWith("/")) {

                        String[] astring1 = input.getText().substring(1).split(" ", -1);
                        String s = astring1[0];

                        if (astring1.length == 1) {
                            for (Command c : HydonManagers.INSTANCE.getCommandManager()
                                    .getCommands()) {
                                if (CommandBase.doesStringStartWith(s, c.getName())) {
                                    newOptions.add("/" + c.getName());
                                }
                            }

                            astring = newOptions.toArray(new String[0]);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (this.gameController.currentScreen instanceof GuiChat) {
            GuiChat guichat = (GuiChat) this.gameController.currentScreen;
            guichat.onAutocompleteResponse(astring);
        }
    }

    /**
     * @author Sk1er
     * @reason ResourceExploitFix - Fixes an exploit where servers could look at what files you have through Server Resource Packs
     */
    @Inject(method = "handleResourcePack", at = @At("HEAD"), cancellable = true)
    private void handleResourcePack(S48PacketResourcePackSend packetIn, CallbackInfo ci) {
        if (!validateResourcePackURL(packetIn.getHash(), packetIn.getURL()))
            ci.cancel();
    }

    private boolean validateResourcePackURL(String hash, String url) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            boolean isLevelProtocol = "level".equals(scheme);

            if (!"http".equals(scheme) && !"https".equals(scheme) && !isLevelProtocol) {
                netManager.sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                throw new URISyntaxException(url, "Wrong protocol");
            }

            url = URLDecoder.decode(url.substring("level://".length()), StandardCharsets.UTF_8.toString());

            if (isLevelProtocol && (url.contains("..") || !url.endsWith("/resources.zip"))) {
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                if (player != null) {
                    player.addChatMessage(new ChatComponentText(ChatColor.RED + "[EXPLOIT FIX WARNING] The current server has attempted to be malicious but it has been stopped."));
                }
                throw new URISyntaxException(url, "Invalid levelstorage resourcepack path");
            }
            return true;
        } catch (URISyntaxException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Byte values for the event
     * 0 : Standard text message, displayed in chat
     * 1: 'System' message, displayed as standard text in chat
     * 2: 'Status' message, displayed as an action bar above the hotbar
     *
     * @author boomboompower
     * @reason Detect incoming chat packets being sent from the server
     */
    @Overwrite
    public void handleChat(S02PacketChat packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, (INetHandlerPlayClient) getNetworkManager().getNetHandler(), this.gameController);

        ServerChatEvent event = new ServerChatEvent(packetIn.getType(), packetIn.getChatComponent());

        EventBus.call(event);

        if (event.isCancelled() || event.getChat().getFormattedText().isEmpty()) {
            return;
        }

        if (packetIn.getType() == 2) {
            gameController.ingameGUI.setRecordPlaying(event.getChat(), false);
        } else {
            gameController.ingameGUI.getChatGUI().printChatMessage(event.getChat());
        }
    }

    /**
     * @author Koding
     */
    @Overwrite
    public void handleTimeUpdate(S03PacketTimeUpdate packetIn) {
        long worldTime = packetIn.getWorldTime();
        TimeChangerConfig config = HydonManagers.INSTANCE.getModManager().getTimeChangerMod().getConfig();

        if (config.enabled) {
            worldTime = config.time;
        }

        PacketThreadUtil.checkThreadAndEnqueue(packetIn, Minecraft.getMinecraft().thePlayer.sendQueue, this.gameController);
        this.gameController.theWorld.setTotalWorldTime(packetIn.getTotalWorldTime());
        this.gameController.theWorld.setWorldTime(worldTime);
    }

    /**
     * @author asbyth
     * @reason NPE
     */
    @Overwrite
    public void handleAnimation(S0BPacketAnimation packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, (INetHandlerPlayClient) getNetworkManager().getNetHandler(), this.gameController);

        if (this.clientWorldController == null) {
            return;
        }

        Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());

        if (entity != null) {
            if (packetIn.getAnimationType() == 0) {
                EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
                entitylivingbase.swingItem();
            } else if (packetIn.getAnimationType() == 1) {
                entity.performHurtAnimation();
            } else if (packetIn.getAnimationType() == 2) {
                EntityPlayer entityplayer = (EntityPlayer) entity;
                entityplayer.wakeUpPlayer(false, false, false);
            } else if (packetIn.getAnimationType() == 4) {
                this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
            } else if (packetIn.getAnimationType() == 5) {
                this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
            }
        }
    }
}
