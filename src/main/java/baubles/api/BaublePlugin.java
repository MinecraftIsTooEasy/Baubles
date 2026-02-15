package baubles.api;

import net.minecraft.*;

public class BaublePlugin implements IBaublePlugin {
    @Override
    public boolean canPutBaubleSlot(ItemStack itemStack, BaubleType type) {
        return type == BaubleType.BELT && itemStack.getItem() instanceof ItemBlock
                && itemStack.getItemAsBlock().getBlock() == Block.dirt;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (itemstack.getItem() instanceof ItemBlock
                && itemstack.getItemAsBlock().getBlock() == Block.dirt) {
            player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 0));
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }
}
