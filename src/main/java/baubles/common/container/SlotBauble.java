package baubles.common.container;

import baubles.MITE_Baubles;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.IBaublePlugin;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.EntityPlayer;
import net.minecraft.IInventory;
import net.minecraft.ItemStack;
import net.minecraft.Slot;
import net.xiaoyu233.fml.FishModLoader;

import java.util.List;

public class SlotBauble extends Slot
{

	BaubleType type;

    public SlotBauble(IInventory par2IInventory, BaubleType type, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.type = type;
    }
    

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if (stack == null || stack.getItem() == null) {
            return false;
        }
    	if (stack.getItem() instanceof IBauble bauble) {
            return bauble.getBaubleType(stack) == this.type &&
                    bauble.canEquip(stack, ((InventoryBaubles) this.inventory).player.get());
        } else {
            return MITE_Baubles.baublePlugins.stream().anyMatch(x -> x.canPutBaubleSlot(stack, this.type));
        }
    }
    

	@Override
	public boolean canTakeStack(EntityPlayer player) {
        if (this.getStack() != null) {
            if (this.getStack().getItem() instanceof IBauble) {
                return ((IBauble) this.getStack().getItem()).canUnequip(this.getStack(), player);
            } else {
                return MITE_Baubles.baublePlugins.stream().anyMatch(x -> x.canUnequip(this.getStack(), player));
            }
        }
        return true;
	}


	@Override
    public int getSlotStackLimit()
    {
        return 1;
    }
    
}
