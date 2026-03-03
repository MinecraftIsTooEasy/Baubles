package baubles.mixin;

import baubles.common.container.ContainerPlayerExpanded;
import baubles.common.lib.PlayerHandler;
import baubles.common.network.BaublesNetHandler;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.common.network.SPacketSyncBauble;
import baubles.gui.GuiPlayerExpanded;
import baubles.imixin.EntityPlayerAccessor;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetClientHandler.class)
public abstract class ClientNetworkManagerTrans extends NetHandler implements BaublesNetHandler {
	@Shadow private Minecraft mc;

	@Override
	public void baubles$handleSyncBauble(SPacketSyncBauble packet) {
		World world = Minecraft.getMinecraft().theWorld;
		if (world == null) return;
		Entity entity = world.getEntityByID(packet.playerId);
		if (!(entity instanceof EntityPlayer player)) return;


		PlayerHandler.getPlayerBaubles(player).stackList[packet.slot] = packet.bauble;

		if (mc.currentScreen instanceof GuiPlayerExpanded gui) {
			ContainerPlayerExpanded container = (ContainerPlayerExpanded) gui.inventorySlots;
			container.baubles.blockEvents = true;
			container.baubles.stackList[packet.slot] = packet.bauble;
			container.baubles.blockEvents = false;
		}
	}

	@Override
	public void baubles$handlerOpenBaublesInventory(PacketOpenBaublesInventory packet) {
		((EntityPlayerAccessor)(Object) this.mc.thePlayer).baubles$displayGuiPlayerBaubles();
		this.mc.thePlayer.openContainer.windowId = packet.windowId;

		if (mc.currentScreen instanceof GuiPlayerExpanded gui) {
			ContainerPlayerExpanded container = (ContainerPlayerExpanded) gui.inventorySlots;
			ItemStack[] src = PlayerHandler.getPlayerBaubles(this.mc.thePlayer).stackList;
			container.baubles.blockEvents = true;
			System.arraycopy(src, 0, container.baubles.stackList, 0, src.length);
			container.baubles.blockEvents = false;
		}
	}

}