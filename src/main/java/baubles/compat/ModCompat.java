package baubles.compat;

import net.minecraft.EntityPlayer;
import net.xiaoyu233.fml.FishModLoader;
import moddedmite.keepinventory.api.KeepInventoryApi;

public class ModCompat {

    public static final boolean HAS_KEEP_INVENTORY = FishModLoader.hasMod("keep-inventory-mod");

    public static boolean canKeepInventory(EntityPlayer player) {
        return KeepInventoryApi.canKeepInventory(player);
    }
}