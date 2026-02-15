package baubles.mixin;

import baubles.common.lib.PlayerHandler;
import baubles.imixin.EntityPlayerAccessor;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerConfigurationManager.class)
public class ServerConfigurationManagerMixin {
    @Shadow private IPlayerFileData playerNBTManagerObj;

    @Inject(method = "readPlayerDataFromFile", at = @At(value = "INVOKE", target = "Lnet/minecraft/ServerPlayer;readFromNBT(Lnet/minecraft/NBTTagCompound;)V"))
    public void readPlayerData(ServerPlayer par1EntityPlayerMP, CallbackInfoReturnable<NBTTagCompound> cir) {
        EntityPlayer player = par1EntityPlayerMP.getAsPlayer();
        ((EntityPlayerAccessor) player).mITE_Baubles$setPlayerDirectory(((SaveHandler)this.playerNBTManagerObj).playersDirectory);
        PlayerHandler.playerLoadDo(player, ((EntityPlayerAccessor) player).mITE_Baubles$getPlayerDirectory(), player.capabilities.isCreativeMode);
    }
}
