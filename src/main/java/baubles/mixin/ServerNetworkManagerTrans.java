package baubles.mixin;

import baubles.common.network.BaublesNetHandler;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.imixin.EntityPlayerAccessor;
import net.minecraft.NetHandler;
import net.minecraft.NetServerHandler;
import net.minecraft.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetServerHandler.class)
public abstract class ServerNetworkManagerTrans extends NetHandler implements BaublesNetHandler {

    @Shadow public ServerPlayer playerEntity;

    @Override
    public void handlerOpenBaublesInventory(PacketOpenBaublesInventory packet) {
        ((EntityPlayerAccessor)(Object) this.playerEntity).displayGuiPlayerBaubles();
    }

//    public void handlerOpenNormalInventory(CPacketOpenNormalInventory packet) {
//        this.playerEntity.closeContainer();
//    }
}
