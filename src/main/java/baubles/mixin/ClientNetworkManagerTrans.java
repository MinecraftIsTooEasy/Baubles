package baubles.mixin;

import baubles.common.container.ContainerPlayerExpanded;
import baubles.common.lib.PlayerHandler;
import baubles.common.network.BaublesNetHandler;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.common.network.SPacketSyncBauble;
import baubles.imixin.EntityPlayerAccessor;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NetClientHandler.class)
public abstract class ClientNetworkManagerTrans extends NetHandler implements BaublesNetHandler {
	@Shadow private Minecraft mc;

	@Unique
	private boolean baubles$hasReceivedSync = false;

	@Override
    public void baubles$handleSyncBauble(SPacketSyncBauble packet) {
        World world = Minecraft.getMinecraft().theWorld;
		if (world == null) return;
		Entity entity = world.getEntityByID(packet.playerId);
		if (entity instanceof EntityPlayer player) {

			if (!baubles$hasReceivedSync) {
				PlayerHandler.clearPlayerBaubles(player);
				baubles$hasReceivedSync = true;
			}

			if (this.mc.thePlayer.openContainer instanceof ContainerPlayerExpanded containerPlayerExpanded) {
				containerPlayerExpanded.baubles.stackList[packet.slot] = packet.bauble;
			}
			// Update PlayerHandler HashMap with synced data from server
			PlayerHandler.getPlayerBaubles(player).stackList[packet.slot] = packet.bauble;
		}
    }

	public void baubles$handlerOpenBaublesInventory(PacketOpenBaublesInventory packet) {
		// Reset sync flag when opening inventory to ensure fresh sync
		baubles$hasReceivedSync = false;
		((EntityPlayerAccessor)(Object) this.mc.thePlayer).baubles$displayGuiPlayerBaubles();
		this.mc.thePlayer.openContainer.windowId = packet.windowId;
	}

}