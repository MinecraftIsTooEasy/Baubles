package baubles.api;

import net.minecraft.EntityLivingBase;
import net.minecraft.ItemStack;

public interface IBaublePlugin {
    boolean canPutBaubleSlot(ItemStack itemStack, BaubleType type);

    void onWornTick(ItemStack itemstack, EntityLivingBase player);

    void onEquipped(ItemStack itemstack, EntityLivingBase player);

    void onUnequipped(ItemStack itemstack, EntityLivingBase player);

    default boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    default boolean dropBaubleOnDeath(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
