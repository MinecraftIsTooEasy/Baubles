package baubles.common.network;

public interface BaublesNetHandler {
    void handlerOpenBaublesInventory(PacketOpenBaublesInventory packet);
    void handleSyncBauble(SPacketSyncBauble packet);
}
