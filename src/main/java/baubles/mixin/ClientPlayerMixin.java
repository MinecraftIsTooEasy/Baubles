package baubles.mixin;

import baubles.gui.GuiPlayerExpanded;
import baubles.imixin.EntityPlayerAccessor;
import net.minecraft.AbstractClientPlayer;
import net.minecraft.ClientPlayer;
import net.minecraft.Minecraft;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayer.class)
public abstract class ClientPlayerMixin extends AbstractClientPlayer implements EntityPlayerAccessor {
    @Shadow protected Minecraft mc;

    public ClientPlayerMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Override
    public void displayGuiPlayerBaubles() {
        this.mc.displayGuiScreen(new GuiPlayerExpanded(this));
    }
}
