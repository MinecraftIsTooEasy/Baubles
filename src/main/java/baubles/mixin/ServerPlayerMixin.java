package baubles.mixin;

import baubles.MITE_Baubles;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.common.container.ContainerPlayerExpanded;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.imixin.EntityPlayerAccessor;
import baubles.util.Config;
import moddedmite.keepinventory.api.KeepInventoryApi;
import net.minecraft.*;
import net.xiaoyu233.fml.FishModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends EntityPlayer implements ICrafting, EntityPlayerAccessor {

    @Shadow protected abstract void incrementWindowID();

    @Shadow public NetServerHandler playerNetServerHandler;
    @Shadow private int currentWindowId;
    // player directory
    @Unique
    private File playerDirectory;

    // hash containing game mode of all players
    @Unique
    private final Map<String,Boolean> playerModes = new HashMap<>();

    public ServerPlayerMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Inject(method = "onDeath", at = @At("RETURN"))
    public void playerDeath(DamageSource par1DamageSource, CallbackInfo ci) {
        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")
                && !(FishModLoader.hasMod("keep-inventory-mod") && KeepInventoryApi.canKeepInventory(this))) {
            for(int i = 0; i < BaublesApi.getBaubles(this).getSizeInventory(); i++) {
                ItemStack stack = BaublesApi.getBaubles(this).getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof IBauble bauble && bauble.dropBaubleOnDeath()) {
                    ((IBauble) stack.getItem()).onUnequipped(stack, this);
                } else if (stack != null && !(stack.getItem() instanceof IBauble) &&
                        MITE_Baubles.baublePlugins.stream().allMatch(x -> x.dropBaubleOnDeath(stack, this.getAsPlayer()))) {
                    MITE_Baubles.baublePlugins.forEach(x -> x.onUnequipped(stack, this.getAsPlayer()));
                }
            }
            PlayerHandler.getPlayerBaubles(this).dropItemsAt(this);
        }
    }

    @Inject(method = "onUpdate", at = @At("RETURN"))
    public void playerTick(CallbackInfo ci) {
        if (Config.SPLIT_SURVIVAL_CREATIVE.get()) {
            // detect game mode changes
            if (playerModes.containsKey(this.getCommandSenderName()) && (playerDirectory != null)) {
                Boolean mode = playerModes.get(this.getCommandSenderName());
                if (mode && !this.capabilities.isCreativeMode) {
                    PlayerHandler.playerSaveDo(this, playerDirectory, true);
                    PlayerHandler.playerLoadDo(this, playerDirectory, false);
                } else if (!mode && this.capabilities.isCreativeMode) {
                    PlayerHandler.playerSaveDo(this, playerDirectory, false);
                    PlayerHandler.playerLoadDo(this, playerDirectory, true);
                }
            }
            playerModes.put(this.getCommandSenderName(), this.capabilities.isCreativeMode);
        }

        InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(this);
        for (int a = 0; a < baubles.getSizeInventory(); a++) {
            if (baubles.getStackInSlot(a) != null) {
                ItemStack stack = baubles.getStackInSlot(a);
                if (stack.getItem() instanceof IBauble bauble) {
                    bauble.onWornTick(stack, this);
                } else {
                    MITE_Baubles.baublePlugins.forEach(x -> x.onWornTick(stack, this.getAsPlayer()));
                }
            }
        }
    }

    @Override
    public File mITE_Baubles$getPlayerDirectory() {
        return this.playerDirectory;
    }

    @Override
    public void mITE_Baubles$setPlayerDirectory(File file) {
        this.playerDirectory = file;
    }

    @Override
    public void baubles$displayGuiPlayerBaubles() {
        incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new PacketOpenBaublesInventory(this.currentWindowId));
        this.openContainer = new ContainerPlayerExpanded(this);
        this.openContainer.windowId = this.currentWindowId;
        this.sendContainerAndContentsToPlayer(this.openContainer, this.openContainer.getInventory());
        this.openContainer.addCraftingToCrafters(this);
    }

}
