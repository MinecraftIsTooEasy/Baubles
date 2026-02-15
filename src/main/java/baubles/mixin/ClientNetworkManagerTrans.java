package baubles.mixin;

import baubles.common.container.ContainerPlayerExpanded;
import baubles.common.network.BaublesNetHandler;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.common.network.SPacketSyncBauble;
import baubles.imixin.EntityPlayerAccessor;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetClientHandler.class)
public abstract class ClientNetworkManagerTrans extends NetHandler implements BaublesNetHandler {
	@Shadow private Minecraft mc;

	@Override
    public void handleSyncBauble(SPacketSyncBauble packet) {
        World world = Minecraft.getMinecraft().theWorld;
		if (world == null) return;
		Entity p = world.getEntityByID(packet.playerId);
		if (p != null && p instanceof EntityPlayer) {
			if (this.mc.thePlayer.openContainer instanceof ContainerPlayerExpanded containerPlayerExpanded) {
				containerPlayerExpanded.baubles.stackList[packet.slot] = packet.bauble;
			}
//			PlayerHandler.getPlayerBaubles((EntityPlayer) p).stackList[packet.slot] = packet.bauble;
		}
    }

	public void handlerOpenBaublesInventory(PacketOpenBaublesInventory packet) {
		((EntityPlayerAccessor)(Object) this.mc.thePlayer).displayGuiPlayerBaubles();
		this.mc.thePlayer.openContainer.windowId = packet.windowId;
	}

}
