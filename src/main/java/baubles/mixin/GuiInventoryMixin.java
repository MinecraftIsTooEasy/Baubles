package baubles.mixin;

import baubles.common.network.PacketOpenBaublesInventory;
import baubles.gui.GuiBaublesButton;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiInventory.class)
public abstract class GuiInventoryMixin extends InventoryEffectRenderer {

    public GuiInventoryMixin(Container par1Container) {
        super(par1Container);
    }

    @Inject(method = "initGui", at = @At("RETURN"))
    public void addBaubles(CallbackInfo ci) {
        int xSize = 176;
        int ySize = 166;

        int guiLeft = (this.width - xSize) / 2;
        int guiTop = (this.height - ySize) / 2;

        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty() || this.mc.thePlayer.isMalnourished()
                || this.mc.thePlayer.isInsulinResistant() || this.mc.thePlayer.is_cursed) {
            guiLeft = 160 + (this.width - xSize - 200) / 2;
        }

        this.buttonList.add(new GuiBaublesButton(55, guiLeft + 108, guiTop + 6, 10, 10,
                I18n.getString("button.baubles") + ":" + I18n.getString("button.normal")));

    }

    @Inject(method = "actionPerformed", at = @At("RETURN"))
    public void actionPerformed(GuiButton par1GuiButton, CallbackInfo ci) {
        if (par1GuiButton.id == 55) {
            this.mc.thePlayer.sendPacket(new PacketOpenBaublesInventory());
        }
    }
}
