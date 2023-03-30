package me.h3xadecimal.milktweak.gui.components;

import me.h3xadecimal.milktweak.MilkTweak;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class OptionButton extends Button {
    private final String configKey;
    private final String displayName;

    public OptionButton(int x, int y, int width, int height, String displayName, String configKey, IPressable press) {
        super(x, y, width, height, new StringTextComponent(displayName), press);
        this.configKey = configKey;
        this.displayName = displayName;
        setMessage(new StringTextComponent(displayName + ": " + getConfigStateString()));
    }

    public OptionButton(int x, int y, int width, int height, String displayName, String configKey) {
        this(x, y, width, height, displayName, configKey, (b) -> {});
    }

    @Override
    public void onPress() {
        putConfigState(!getConfigState());
        setMessage(new StringTextComponent(displayName + ": " + getConfigStateString()));
    }

    private boolean getConfigState() {
        Boolean state = MilkTweak.INSTANCE.getConfig().getBoolean(configKey);
        if (state != null) return state;
        return false;
    }

    private String getConfigStateString() {
        if (getConfigState()) {
            return "§a启用";
        } else {
            return "§c禁用";
        }
    }

    private void putConfigState(boolean state) {
        MilkTweak.INSTANCE.getConfig().put(configKey, state);
    }
}
