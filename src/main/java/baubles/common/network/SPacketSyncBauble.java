package baubles.common.network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import baubles.common.lib.PlayerHandler;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.NetHandler;
import net.minecraft.Packet;

public class SPacketSyncBauble extends Packet {
	
	public int slot;
	public int playerId;
	public ItemStack bauble = null;

	public SPacketSyncBauble() {}

	public SPacketSyncBauble(EntityPlayer player, int slot) {
		this.slot = slot;
		this.bauble = PlayerHandler.getPlayerBaubles(player).getStackInSlot(slot);
		this.playerId = player.entityId;
	}


	@Override
	public void readPacketData(DataInput dataInput) throws IOException {
		slot = dataInput.readByte();
		playerId = dataInput.readInt();
		bauble = SPacketSyncBauble.readItemStack(dataInput);
	}

	@Override
	public void writePacketData(DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(slot);
		dataOutput.writeInt(playerId);
		SPacketSyncBauble.writeItemStack(bauble, dataOutput);
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		((BaublesNetHandler) netHandler).handleSyncBauble(this);
	}

	@Override
	public int getPacketSize() {
		return 5 + Packet.getPacketSizeOfItemStack(this.bauble);
	}

//	@Override
//	public IMessage onMessage(SPacketSyncBauble message, MessageContext ctx) {
//		World world = Baubles.proxy.getClientWorld();
//		if (world==null) return null;
//		Entity p = world.getEntityByID(message.playerId);
//		if (p !=null && p instanceof EntityPlayer) {
//			PlayerHandler.getPlayerBaubles((EntityPlayer) p).stackList[message.slot]=message.bauble;
//		}
//		return null;
//	}


}
