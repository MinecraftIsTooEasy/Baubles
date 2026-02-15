package baubles.mixin;

import baubles.common.event.MITEModEvents;
import net.minecraft.DedicatedServer;
import net.minecraft.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DedicatedServer.class)
public class DedicatedServerMixin {
    @Inject(method = "playerLoggedIn", at = @At("HEAD"))
    public void playerLoggedIn(ServerPlayer par1EntityPlayerMP, CallbackInfo ci) {
        MITEModEvents.syncBaubles(par1EntityPlayerMP);
    }
}
