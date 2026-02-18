package baubles.common.event;

import baubles.common.items.Items;
import baubles.common.lib.PlayerHandler;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.common.network.SPacketSyncBauble;
import com.google.common.eventbus.Subscribe;
import net.minecraft.EntityPlayer;
import net.xiaoyu233.fml.reload.event.*;

public class MITEModEvents {

    @Subscribe
    public void onItemRegister(ItemRegistryEvent event){
        Items.registerItems(event);
    }

    @Subscribe
    public void onRecipeRegister(RecipeRegistryEvent event){
    }
    
    @Subscribe
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        syncBaubles(event.getPlayer());
    }

    @Subscribe
    public void onEntityRendererRegister(EntityRendererRegistryEvent event){

    }

    @Subscribe
    public void onPacketRegister(PacketRegisterEvent event){
        event.register(true, true, PacketOpenBaublesInventory.class);
        event.register(true, false, SPacketSyncBauble.class);
    }

    public static void syncBaubles(EntityPlayer player) {
        for (int a = 0; a < 12; a++) {
            PlayerHandler.getPlayerBaubles(player).syncSlotToClients(a);
        }
    }
}