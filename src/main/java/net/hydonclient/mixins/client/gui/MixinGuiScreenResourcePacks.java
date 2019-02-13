package net.hydonclient.mixins.client.gui;

import com.google.common.collect.Lists;
import me.semx11.autotip.universal.ReflectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiResourcePackSelected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepository.Entry;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Mixin(GuiScreenResourcePacks.class)
public abstract class MixinGuiScreenResourcePacks extends GuiScreen {

    @Shadow
    private boolean changed;
    @Shadow
    private List<ResourcePackListEntry> availableResourcePacks;
    @Shadow
    private List<ResourcePackListEntry> selectedResourcePacks;
    @Shadow
    private GuiResourcePackAvailable availableResourcePacksList;
    @Shadow
    private GuiResourcePackSelected selectedResourcePacksList;
    private GuiTextField textField;

    /**
     * @author asbyth
     * @reason pack organizer (sort of)
     */
    @Overwrite
    public void initGui() {
        buttonList.add(new GuiOptionButton(1, width / 2 + 20, height - 48, I18n.format("gui.done")));
        buttonList.add(new GuiOptionButton(2, width / 2 - 4 - 200, height - 24, 200, 20, I18n.format("resourcePack.openFolder")));

        String text = textField == null ? "" : textField.getText();
        textField = new GuiTextField(3, fontRendererObj, width / 2 - 4 - 200, height - 48, 200, 20);
        textField.setText(text);

        if (!changed) {
            availableResourcePacks = Lists.newArrayList();
            selectedResourcePacks = Lists.newArrayList();
            ResourcePackRepository resourcePackRepository = mc.getResourcePackRepository();
            resourcePackRepository.updateRepositoryEntriesAll();

            List<Entry> list = Lists.newArrayList(resourcePackRepository.getRepositoryEntriesAll());
            list.removeAll(resourcePackRepository.getRepositoryEntries());

            for (Entry entry : list) {
                availableResourcePacks.add(new ResourcePackListEntryFound(new GuiScreenResourcePacks(this), entry));
            }

            for (Entry entry1 : Lists.reverse(resourcePackRepository.getRepositoryEntries())) {
                selectedResourcePacks.add(new ResourcePackListEntryFound(new GuiScreenResourcePacks(this), entry1));
            }

            selectedResourcePacks.add(new ResourcePackListEntryDefault(new GuiScreenResourcePacks(this)));
        }

        availableResourcePacksList = new GuiResourcePackAvailable(mc, 200, height, availableResourcePacks);
        availableResourcePacksList.setSlotXBoundsFromLeft(width / 2 - 4 - 200);
        availableResourcePacksList.registerScrollButtons(7, 8);

        selectedResourcePacksList = new GuiResourcePackSelected(mc, 200, height, selectedResourcePacks);
        selectedResourcePacksList.setSlotXBoundsFromLeft(width / 2 + 4);
        selectedResourcePacksList.registerScrollButtons(7, 8);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        if (textField.isFocused()) {
            textField.textboxKeyTyped(typedChar, keyCode);
        }

        updateList();
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        textField.mouseClicked(mouseX, mouseY, mouseButton);

        if (textField != null) {
            textField.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    /**
     * @author asbyth
     * @reason pack organizer (sort of)
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground(0);
        availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRendererObj, I18n.format("resourcePack.title"), width / 2, 16, 16777215);
        drawCenteredString(fontRendererObj, I18n.format("resourcePack.folderInfo"), width / 2 - 102, height - 26, 8421504);
        super.drawScreen(mouseX, mouseY, partialTicks);
        textField.drawTextBox();
    }

    private void updateList() {
        availableResourcePacksList = updatePackList(textField, availableResourcePacksList, availableResourcePacks, mc, height, width);
    }

    private GuiResourcePackAvailable updatePackList(GuiTextField searchField, GuiResourcePackAvailable availableGUI, List<ResourcePackListEntry> availablePacks, Minecraft mc, int height, int width) {
        if (searchField == null || searchField.getText().isEmpty()) {
            availableResourcePacksList = new GuiResourcePackAvailable(mc, 200, height, availablePacks);
            availableResourcePacksList.setSlotXBoundsFromLeft(width / 2 - 4 - 200);
            availableResourcePacksList.registerScrollButtons(7, 8);
        } else {
            availableGUI = new GuiResourcePackAvailable(mc, 200, height, Arrays.asList(availableGUI.getList().stream().filter(resourcePackListEntry -> {
                try {
                    Method method = ReflectionUtil.findDeclaredMethod(resourcePackListEntry.getClass(), new String[] { "func_148312_b", "c" });
                    method.setAccessible(true);
                    String name = EnumChatFormatting.getTextWithoutFormattingCodes((String) method.invoke(resourcePackListEntry)).
                            replaceAll("[^A-Za-z0-9]", "").trim().toLowerCase();
                    String text = searchField.getText().toLowerCase();

                    if (name.endsWith("zip")) {
                        name = name.subSequence(0, name.length() - 3).toString();
                    }

                    for (String s : text.split(" ")) {
                        if (!name.contains(s.toLowerCase())) {
                            return false;
                        }
                    }

                    return name.startsWith(text) || name.contains(text) || name.equalsIgnoreCase(text);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    return true;
                }
            }).toArray(ResourcePackListEntry[]::new)));
            availableResourcePacksList = new GuiResourcePackAvailable(mc, 200, height, availablePacks);
            availableResourcePacksList.setSlotXBoundsFromLeft(width / 2 - 4 - 200);
            availableResourcePacksList.registerScrollButtons(7, 8);
        }

        return availableGUI;
    }

    /**
     * @author asbyth
     * @reason pack organizer (sort of)
     */
    @Overwrite
    public boolean hasResourcePackEntry(ResourcePackListEntry entry) {
        return selectedResourcePacks.contains(entry);
    }

    /**
     * @author asbyth
     * @reason pack organizer (sort of)
     */
    @Overwrite
    public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry entry) {
        return hasResourcePackEntry(entry) ? selectedResourcePacks : availableResourcePacks;
    }

    /**
     * @author asbyth
     * @reason pack organizer (sort of)
     */
    @Overwrite
    public List<ResourcePackListEntry> getAvailableResourcePacks() {
        return availableResourcePacks;
    }

    /**
     * @author asbyth
     * @reason pack organizer (sort of)
     */
    @Overwrite
    public List<ResourcePackListEntry> getSelectedResourcePacks() {
        return selectedResourcePacks;
    }
}