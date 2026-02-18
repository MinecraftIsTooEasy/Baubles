package baubles.common.network;

import net.minecraft.NetHandler;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PacketOpenBaublesInventory extends Packet {
	public int windowId;

	public PacketOpenBaublesInventory() {}
	public PacketOpenBaublesInventory(int windowId) {
		this.windowId = windowId;
	}

	public int getPacketSize() {
		return 1;
	}

	public void processPacket(NetHandler var1) {
		((BaublesNetHandler)var1).baubles$handlerOpenBaublesInventory(this);
	}

	public void readPacketData(DataInput dataInput) throws IOException {
		this.windowId = dataInput.readByte() & 0xFF;
	}

	public void writePacketData(DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(this.windowId & 0xFF);
	}

//	@Override
//	public IMessage onMessage(PacketOpenBaublesInventory message, MessageContext ctx) {
//		ctx.getServerHandler().playerEntity.openGui(Baubles.instance, Baubles.GUI, ctx.getServerHandler().playerEntity.worldObj, (int)ctx.getServerHandler().playerEntity.posX, (int)ctx.getServerHandler().playerEntity.posY, (int)ctx.getServerHandler().playerEntity.posZ);
//		return null;
//	}

}
