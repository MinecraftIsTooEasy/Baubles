package baubles.mixin;

import baubles.imixin.GameSettingsAccessor;
import net.minecraft.GameSettings;
import net.minecraft.KeyBinding;
import net.minecraft.StatCollector;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(GameSettings.class)
public class GameSettingsMixin implements GameSettingsAccessor {
    @Shadow
    public KeyBinding[] keyBindings;
    @Unique
    public KeyBinding keyBindingBaublesInventory = new KeyBinding("keybind.baublesinventory", Keyboard.KEY_B);

    @Inject(method = "initKeybindings", at = @At("RETURN"))
    public void initKeybindings(CallbackInfo ci) {
        this.keyBindings = Arrays.copyOf(this.keyBindings, keyBindings.length + 1);
        keyBindings[keyBindings.length - 1] = this.keyBindingBaublesInventory;
    }

    @Override
    public KeyBinding getKeyBindingBaublesInventory() {
        return keyBindingBaublesInventory;
    }
}
