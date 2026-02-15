package baubles.common.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import net.minecraft.*;

public class ItemRing extends Item implements IBauble
{
	public ItemRing(int id)
	{
		super(id, Material.silver, "ring");
		this.setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

	@Override
	public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
		InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
		ItemStack item_stack = player.getHeldItemStack();
		for (int i = 0; i < baubles.getSizeInventory(); i++) {
			if (baubles.getStackInSlot(i) == null && baubles.isItemValidForSlot(i, item_stack)) {
				if (player.onServer()) {
					baubles.setInventorySlotContents(i, item_stack.copy());
					if (!player.capabilities.isCreativeMode) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
					onEquipped(item_stack, player);
				}
				return true;
			}
		}

		return false;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (!player.isPotionActive(Potion.digSpeed)) {
			player.addPotionEffect(new PotionEffect(Potion.digSpeed.id,40,0,true));
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		if (!player.worldObj.isRemote) {
			player.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 1.3f);
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	public boolean dropBaubleOnDeath() { return false; }
}
