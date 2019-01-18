package pw.cinque.keystrokesmod;

import net.hydonclient.managers.impl.command.Command;

class KeystrokesCommand extends Command {

    private final KeystrokesMod keystrokesMod;

    @java.beans.ConstructorProperties({"keystrokesMod"})
    public KeystrokesCommand(KeystrokesMod keystrokesMod) {
        this.keystrokesMod = keystrokesMod;
    }

    @Override
    public String getName() {
        return "keystrokesmod";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public void onCommand(String[] args) {
        keystrokesMod.openGui();
    }
}
