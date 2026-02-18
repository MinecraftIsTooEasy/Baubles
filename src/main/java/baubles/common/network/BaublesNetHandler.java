package baubles.common.network;

public interface BaublesNetHandler {
    void baubles$handlerOpenBaublesInventory(PacketOpenBaublesInventory packet);
    void baubles$handleSyncBauble(SPacketSyncBauble packet);
}
