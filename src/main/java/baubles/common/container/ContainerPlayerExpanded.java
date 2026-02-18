package baubles.common.container;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.lib.PlayerHandler;
import net.minecraft.*;

public class ContainerPlayerExpanded extends ContainerPlayer
{
    public InventoryBaubles baubles;
    /**
     * Determines if inventory manipulation should be handled.
     */
//    public boolean isLocalWorld;

    public ContainerPlayerExpanded(EntityPlayer player)
    {
        super(player);
//        this.isLocalWorld = par2;
    }

    @Override
    public void createSlots() {
        baubles = new InventoryBaubles(player);
        if (!player.worldObj.isRemote) {
            baubles.stackList = PlayerHandler.getPlayerBaubles(player).stackList;
        }

        int x;
        int y;
        this.addSlotToContainer(new SlotCrafting(this.player, this.craft_matrix, this.craft_result, 0, 144, 36));
        for (y = 0; y < 2; ++y) {
            for (x = 0; x < 2; ++x) {
                this.addSlotToContainer(new Slot(this.craft_matrix, x + y * 2, 88 + x * 18, 26 + y * 18));
            }
        }
        for (y = 0; y < 4; ++y) {
            this.addSlotToContainer(new SlotArmor(this, this.player.inventory, this.player.inventory.getSizeInventory() - 1 - y, 8, 8 + y * 18, y));
        }
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.HEAD, 0, 26, 8));
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.AMULET, 1, 26, 8 + 18));
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.BACK, 2, 26, 8 + 36));
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.FEET, 3, 26, 8 + 54));

        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.BRACELET, 4, 44, 8));
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.BRACELET, 5, 44, 8 + 18));
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.HAND, 6, 44, 8 + 36));
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.HAND, 7, 44, 8 + 54));

        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.RING, 8, 62, 8));
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.RING, 9, 62, 8 + 18));
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.BELT, 10, 62, 8 + 36));
        this.addSlotToContainer(new SlotBauble(baubles, BaubleType.CHARM, 11, 62, 8 + 54));

        for (y = 0; y < 3; ++y) {
            for (x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(this.player.inventory, x + (y + 1) * 9, 8 + x * 18, 84 + y * 18));
            }
        }
        for (int hotbar_index = 0; hotbar_index < 9; ++hotbar_index) {
            this.addSlotToContainer(new Slot(this.player.inventory, hotbar_index, 8 + hotbar_index * 18, 142));
        }
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer entity_player) {
        super.onContainerClosed(entity_player);

        if (!player.worldObj.isRemote) {
            PlayerHandler.setPlayerBaubles(player, baubles);
        }
    }




    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer entity_player, int slot_index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slot_index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slot_index == 0) {

                if (!this.mergeItemStack(itemstack1, 21, 57, true, slot)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slot_index < 5)
            {
                if (!this.mergeItemStack(itemstack1, 21, 57, false, slot)) {
                    return null;
                }
            }
            else if (slot_index < 9)
            {
                if (!this.mergeItemStack(itemstack1, 21, 57, false, slot)) {
                    return null;
                }
            }
            else if (slot_index < 21)
            {

                if (!this.mergeItemStack(itemstack1, 21, 57, false, slot)) {
                    return null;
                }
            }
            else if (itemstack.getItem() instanceof ItemArmor &&
                    !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack() &&
                    !this.player.hasCurse(Curse.cannot_wear_armor, true)) {
                int j = 5 + ((ItemArmor)itemstack.getItem()).armorType;
                if (!this.mergeItemStack(itemstack1, j, j + 1, false, slot)) {
                    return null;
                }
            }
            else if (itemstack.getItem() instanceof IBauble) {

                BaubleType type = ((IBauble)itemstack.getItem()).getBaubleType(itemstack);
                boolean equipped = false;

                if (((IBauble)itemstack.getItem()).canEquip(itemstack, player)) {

                    if (type == BaubleType.HEAD && !((Slot)this.inventorySlots.get(9)).getHasStack())
                    {
                        equipped = this.mergeItemStack(itemstack1, 9, 10, false, slot);
                    }
                    else if (type == BaubleType.AMULET && !((Slot)this.inventorySlots.get(10)).getHasStack())
                    {
                        equipped = this.mergeItemStack(itemstack1, 10, 11, false, slot);
                    }
                    else if (type == BaubleType.BACK && !((Slot)this.inventorySlots.get(11)).getHasStack())
                    {
                        equipped = this.mergeItemStack(itemstack1, 11, 12, false, slot);
                    }
                    else if (type == BaubleType.FEET && !((Slot)this.inventorySlots.get(12)).getHasStack())
                    {
                        equipped = this.mergeItemStack(itemstack1, 12, 13, false, slot);
                    }
                    else if (type == BaubleType.BRACELET)
                    {
                        if (!((Slot)this.inventorySlots.get(13)).getHasStack()) {
                            equipped = this.mergeItemStack(itemstack1, 13, 14, false, slot);
                        }
                        else if (!((Slot)this.inventorySlots.get(14)).getHasStack())
                        {
                            equipped = this.mergeItemStack(itemstack1, 14, 15, false, slot);
                        }
                    }
                    else if (type == BaubleType.HAND)
                    {
                        if (!((Slot)this.inventorySlots.get(15)).getHasStack())
                        {
                            equipped = this.mergeItemStack(itemstack1, 15, 16, false, slot);
                        }
                        else if (!((Slot)this.inventorySlots.get(16)).getHasStack())
                        {
                            equipped = this.mergeItemStack(itemstack1, 16, 17, false, slot);
                        }
                    }
                    else if (type == BaubleType.RING) {
                        if (!((Slot)this.inventorySlots.get(17)).getHasStack())
                        {
                            equipped = this.mergeItemStack(itemstack1, 17, 18, false, slot);
                        }
                        else if (!((Slot)this.inventorySlots.get(18)).getHasStack())
                        {
                            equipped = this.mergeItemStack(itemstack1, 18, 19, false, slot);
                        }
                    }
                    else if (type == BaubleType.BELT && !((Slot)this.inventorySlots.get(19)).getHasStack())
                    {
                        equipped = this.mergeItemStack(itemstack1, 19, 20, false, slot);
                    }
                    else if (type == BaubleType.CHARM && !((Slot)this.inventorySlots.get(20)).getHasStack())
                    {
                        equipped = this.mergeItemStack(itemstack1, 20, 21, false, slot);
                    }
                }

                if (!equipped) {
                    return null;
                }
            }
            else if (slot_index < 48)
            {

                if (!this.mergeItemStack(itemstack1, 48, 57, false, slot)) {
                    return null;
                }
            }
            else if (slot_index < 57)
            {
                if (!this.mergeItemStack(itemstack1, 21, 48, false, slot)) {
                    return null;
                }
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(entity_player, itemstack1);
        }


        return itemstack;
    }

    private void unequipBauble(ItemStack stack) {
//    	if (stack.getItem() instanceof IBauble) {
//    		((IBauble)stack.getItem()).onUnequipped(stack, player);
//    	}
    }
    
    @Override
	public void putStacksInSlots(ItemStack[] p_75131_1_) {
		baubles.blockEvents = true;
		super.putStacksInSlots(p_75131_1_);
	}

	protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4, Slot ss)
    {
        boolean flag1 = false;
        int k = par2;

        if (par4)
        {
            k = par3 - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (par1ItemStack.isStackable())
        {
            while (par1ItemStack.stackSize > 0 && (!par4 && k < par3 || par4 && k >= par2))
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && ItemStack.areItemStacksEqual(itemstack1, par1ItemStack, true, false, true))
                {
                    int l = itemstack1.stackSize + par1ItemStack.stackSize;
                    if (l <= par1ItemStack.getMaxStackSize())
                    {
                    	if (ss instanceof SlotBauble) unequipBauble(par1ItemStack);
                    	par1ItemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize())
                    {
                    	if (ss instanceof SlotBauble) unequipBauble(par1ItemStack);
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = par1ItemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (par4)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (par1ItemStack.stackSize > 0)
        {
            if (par4)
            {
                k = par3 - 1;
            }
            else
            {
                k = par2;
            }

            while (!par4 && k < par3 || par4 && k >= par2)
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null && slot.isItemValid(par1ItemStack))
                {
                	if (ss instanceof SlotBauble) unequipBauble(par1ItemStack);
                    slot.putStack(par1ItemStack.copy());
                    slot.onSlotChanged();
                    par1ItemStack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (par4)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }
        return flag1;
    }
}
