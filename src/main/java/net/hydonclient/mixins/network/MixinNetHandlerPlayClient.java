package net.hydonclient.mixins.network;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.managers.impl.command.Command;
import net.hydonclient.util.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.CommandBase;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.S3APacketTabComplete;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @Shadow
    private Minecraft gameController;

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
}
