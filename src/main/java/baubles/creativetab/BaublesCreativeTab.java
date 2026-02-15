package baubles.creativetab;

import baubles.common.items.Items;
import huix.glacier.api.extension.creativetab.GlacierCreativeTabs;

public class BaublesCreativeTab extends GlacierCreativeTabs {

    public static final BaublesCreativeTab TAB = new BaublesCreativeTab();

    public BaublesCreativeTab() {
        super("Baubles");
    }

    public int getTabIconItemIndex() {
        return Items.RING.itemID;
    }
}