package baubles.common.container;

import baubles.MITE_Baubles;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.network.SPacketSyncBauble;
import net.minecraft.*;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class InventoryBaubles implements IInventory {
	public ItemStack[] stackList;

	public WeakReference<EntityPlayer> player;
	public boolean blockEvents = false;
	private ItemStack itemStack;

	public InventoryBaubles(EntityPlayer player) {
		this.stackList = new ItemStack[12];
		this.player = new WeakReference<>(player);
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.stackList.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1) {
		return par1 >= this.getSizeInventory() ? null : this.stackList[par1];
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getCustomNameOrUnlocalized() {
		return "";
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomName() {
		return false;
	}

	public void setItemStack(ItemStack par1ItemStack) {
		this.itemStack = par1ItemStack;
	}

	public ItemStack getItemStack() {
		return this.itemStack;
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.stackList[par1] != null) {
			ItemStack itemstack = this.stackList[par1];
			this.stackList[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.stackList[par1] != null) {
			ItemStack itemstack;

			if (this.stackList[par1].stackSize <= par2) {
				itemstack = this.stackList[par1];

				if (itemstack != null) {
					if (itemstack.getItem() instanceof IBauble) {
						((IBauble) itemstack.getItem()).onUnequipped(itemstack,
								player.get());
					} else {
						MITE_Baubles.baublePlugins.forEach(x -> x.onUnequipped(itemstack, player.get()));
					}
				}

				this.stackList[par1] = null;

				return itemstack;
			}
			itemstack = this.stackList[par1].splitStack(par2);

			if (itemstack != null) {
				if (itemstack.getItem() instanceof IBauble) {
					((IBauble) itemstack.getItem()).onUnequipped(itemstack,
							player.get());
				} else {
					MITE_Baubles.baublePlugins.forEach(x -> x.onUnequipped(itemstack, player.get()));
				}
			}

			if (this.stackList[par1].stackSize == 0) {
				this.stackList[par1] = null;
			}


			return itemstack;
		}

		return null;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int par1, ItemStack stack) {
		
		if(!blockEvents && this.stackList[par1] != null) {
			if (stackList[par1].getItem() instanceof IBauble bauble) {
				bauble.onUnequipped(stackList[par1], player.get());
			} else {
				MITE_Baubles.baublePlugins.forEach(x -> x.onUnequipped(stackList[par1], player.get()));
			}
		}

		if(this.stackList[par1] != stack) {
			this.stackList[par1] = stack;
			if (!blockEvents && stack != null) {
				if (stack.getItem() instanceof IBauble bauble) {
					bauble.onEquipped(stack, player.get());
				} else {
					MITE_Baubles.baublePlugins.forEach(x -> x.onEquipped(stack, player.get()));
				}
			}
		}
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void onInventoryChanged() {

	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved
	 * to disk later - the game won't think it hasn't changed and skip it.
	 */
//	@Override
//	public void markDirty() {
//		try {
//			player.get().inventory.markDirty();
//		} catch (Exception e) {
//		}
//	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return true;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof IBauble)
				|| !((IBauble) stack.getItem()).canEquip(stack, player.get()))
			return false;
		if (i == 0
				&& ((IBauble) stack.getItem()).getBaubleType(stack) == BaubleType.AMULET)
			return true;
		if ((i == 1 || i == 2)
				&& ((IBauble) stack.getItem()).getBaubleType(stack) == BaubleType.RING)
			return true;
		if (i == 3
				&& ((IBauble) stack.getItem()).getBaubleType(stack) == BaubleType.BELT)
			return true;
		return false;
	}

	@Override
	public void destroyInventory() {

	}

//	public void saveNBT(EntityPlayer player) {
//		NBTTagCompound tags = player.getgetTag.getEntityData();
//		saveNBT(tags);
//	}

	public void saveNBT(NBTTagCompound tags) {
		NBTTagList tagList = new NBTTagList();
		NBTTagCompound invSlot;
		for (int i = 0; i < this.stackList.length; ++i) {
			if (this.stackList[i] != null) {
				invSlot = new NBTTagCompound();
				invSlot.setByte("Slot", (byte) i);
				this.stackList[i].writeToNBT(invSlot);
				tagList.appendTag(invSlot);
			}
		}
		tags.setTag("Baubles.Inventory", tagList);
	}

//	public void readNBT(EntityPlayer player) {
//		NBTTagCompound tags = player.getEntityData();
//		readNBT(tags);
//	}

	public void readNBT(NBTTagCompound tags) {
		NBTTagList tagList = tags.getTagList("Baubles.Inventory");
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = (NBTTagCompound) tagList
					.tagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;
			ItemStack itemstack = ItemStack
					.loadItemStackFromNBT(nbttagcompound);
			if (itemstack != null) {
				this.stackList[j] = itemstack;
			}
		}
	}
	
	public void dropItemsAt(EntityPlayer player) {
		for (int i = 0; i < 4; ++i) {
			if (this.stackList[i] != null) {
				ItemStack stack = this.stackList[i];
				if ((!(stack.getItem() instanceof IBauble) &&
						MITE_Baubles.baublePlugins.stream().allMatch(x ->x.dropBaubleOnDeath(stack, player))) ||

						(stack.getItem() instanceof IBauble bauble && bauble.dropBaubleOnDeath())) {
					player.dropPlayerItemWithRandomChoice(this.stackList[i], true);
					this.stackList[i] = null;
					syncSlotToClients(i);
				}
			}
		}
	}

	public void syncSlotToClients(int slot) {
		try {
			if (!Objects.requireNonNull(this.player.get()).worldObj.isRemote &&
					Objects.requireNonNull(this.player.get()).getAsEntityPlayerMP().playerNetServerHandler != null) {
				Objects.requireNonNull(this.player.get()).getAsEntityPlayerMP().sendPacket(new SPacketSyncBauble(player
						.get(), slot));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
