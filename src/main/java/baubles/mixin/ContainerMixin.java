package baubles.mixin;

import baubles.api.IBauble;
import baubles.common.container.ContainerPlayerExpanded;
import baubles.common.container.SlotBauble;

import net.minecraft.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Container.class)
public abstract class ContainerMixin {

    @Inject(method = "slotClick", at = @At("HEAD"), cancellable = true)
    private void blockBaubleDoubleClick(int par1, int par2, int par3, boolean holding_shift, EntityPlayer par4EntityPlayer, CallbackInfoReturnable<ItemStack> cir)
    {
        if (par3 != 6) return;

        Container self = (Container)(Object)this;

        if (!(self instanceof ContainerPlayerExpanded)) return;

        ItemStack held = par4EntityPlayer.inventory.getItemStack();

        if (held != null && held.getItem() instanceof IBauble)
        {
            cir.setReturnValue(null);
            return;
        }

        if (par1 >= 0 && par1 < self.inventorySlots.size())
        {
            Slot slot = (Slot) self.inventorySlots.get(par1);
            if (slot instanceof SlotBauble) {
                cir.setReturnValue(null);
            }
        }
    }
}