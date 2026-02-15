package baubles.mixin;

import baubles.common.lib.PlayerHandler;
import baubles.imixin.EntityPlayerAccessor;
import net.minecraft.EntityPlayer;
import net.minecraft.NBTTagCompound;
import net.minecraft.SaveHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(SaveHandler.class)
public class SaveHandlerMixin {
    @Shadow @Final public File playersDirectory;

    @Inject(method = "writePlayerData", at = @At("RETURN"))
    public void writePlayerData(EntityPlayer player, CallbackInfo ci) {
        ((EntityPlayerAccessor) player).mITE_Baubles$setPlayerDirectory(this.playersDirectory);
        PlayerHandler.playerSaveDo(player, ((EntityPlayerAccessor) player).mITE_Baubles$getPlayerDirectory(), player.capabilities.isCreativeMode);
    }

    @Inject(method = "readPlayerData", at = @At("RETURN"))
    public void readPlayerData(EntityPlayer player, CallbackInfoReturnable<NBTTagCompound> cir) {
        ((EntityPlayerAccessor) player).mITE_Baubles$setPlayerDirectory(this.playersDirectory);
        PlayerHandler.playerLoadDo(player, ((EntityPlayerAccessor) player).mITE_Baubles$getPlayerDirectory(), player.capabilities.isCreativeMode);
    }
}
