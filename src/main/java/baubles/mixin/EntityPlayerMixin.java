package baubles.mixin;

import baubles.common.network.PacketOpenBaublesInventory;
import baubles.gui.GuiPlayerExpanded;
import baubles.imixin.EntityPlayerAccessor;
import baubles.imixin.GameSettingsAccessor;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLivingBase
        implements ICommandSender, EntityPlayerAccessor {

    public EntityPlayerMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = "onUpdate", at = @At("RETURN"))
    public void playerTick(CallbackInfo ci) {
        checkKey();
    }

    @Unique
    public void checkKey() {
        if (this.getEntityWorld().isRemote) {
            if (((GameSettingsAccessor) Minecraft.getMinecraft().gameSettings)
                    .getKeyBindingBaublesInventory().pressed && Minecraft.getMinecraft().inGameHasFocus) {
                this.getAsEntityClientPlayerMP().sendPacket(new PacketOpenBaublesInventory());
                Minecraft.getMinecraft().displayGuiScreen(new GuiPlayerExpanded(this.getAsEntityClientPlayerMP()));
            }
        }
    }

    @Override
    public void displayGuiPlayerBaubles() {}


    @Shadow public abstract World getEntityWorld();

    @Shadow public abstract EntityClientPlayerMP getAsEntityClientPlayerMP();

    @Shadow public abstract String getCommandSenderName();

}
