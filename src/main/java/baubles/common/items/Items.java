package baubles.common.items;

import baubles.MITE_Baubles;
import baubles.creativetab.BaublesCreativeTab;
import baubles.util.Config;
import net.minecraft.Item;
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent;

public class Items {
    public static final Item RING = new ItemRing(Config.RING_ITEMID.get());

    public static void registerItems(ItemRegistryEvent event) {
        event.register(MITE_Baubles.modId, "ring", RING, BaublesCreativeTab.TAB);
    }
}