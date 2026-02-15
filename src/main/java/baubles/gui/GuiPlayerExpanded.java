package baubles.gui;

import baubles.imixin.GameSettingsAccessor;
import net.minecraft.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import baubles.common.container.ContainerPlayerExpanded;

public class GuiPlayerExpanded extends InventoryEffectRenderer {
	
	public static final ResourceLocation background =
			new ResourceLocation("textures/gui/expanded_inventory.png");
    
	/**
     * x size of the inventory window in pixels. Defined as  float, passed as int
     */
    private float xSizeFloat;
    /**
     * y size of the inventory window in pixels. Defined as  float, passed as int.
     */
    private float ySizeFloat;

    public GuiPlayerExpanded(EntityPlayer player)
    {
        super(new ContainerPlayerExpanded(player));
        this.allowUserInput = true;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override 
    public void updateScreen()
    {
        super.updateScreen();
    	try {
			((ContainerPlayerExpanded)inventorySlots).baubles.blockEvents=false;
		} catch (Exception ignored) {	}
    }

    
    
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui()
    {
        this.buttonList.clear();
        super.initGui();

        int xSize = 176;
        int ySize = 166;

        int guiLeft = (this.width - xSize) / 2;
        int guiTop = (this.height - ySize) / 2;

        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty() || this.mc.thePlayer.isMalnourished()
                || this.mc.thePlayer.isInsulinResistant() || this.mc.thePlayer.is_cursed) {
            guiLeft = 160 + (this.width - xSize - 200) / 2;
        }

        this.buttonList.add(new GuiBaublesButton(55, guiLeft + 66, guiTop + 9, 10, 10,
                I18n.getString("button.baubles") + ":" + I18n.getString("button.normal")));
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRenderer.drawString(I18n.getString("container.crafting"), 106, 16, 4210752);
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);
        this.xSizeFloat = (float)par1;
        this.ySizeFloat = (float)par2;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int k = this.guiLeft;
        int l = this.guiTop;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1)
        {
            Slot slot = (Slot)this.inventorySlots.inventorySlots.get(i1);
            if (slot.getHasStack() && slot.getSlotStackLimit()==1)
            {
            	this.drawTexturedModalRect(k+slot.xDisplayPosition, l+slot.yDisplayPosition, 200, 0, 16, 16);
            }
        }
        
        drawPlayerModel(k + 51, l + 75, 30, (float)(k + 51) - this.xSizeFloat, (float)(l + 75 - 50) - this.ySizeFloat, this.mc.thePlayer);
    }

    public static void drawPlayerModel(int x, int y, int scale, float yaw, float pitch, EntityLivingBase playerdrawn)
    {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, 50.0F);
        GL11.glScalef((float)(-scale), (float)scale, (float)scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = playerdrawn.renderYawOffset;
        float f3 = playerdrawn.rotationYaw;
        float f4 = playerdrawn.rotationPitch;
        float f5 = playerdrawn.prevRotationYawHead;
        float f6 = playerdrawn.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float)Math.atan((double)(pitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        playerdrawn.renderYawOffset = (float)Math.atan((double)(yaw / 40.0F)) * 20.0F;
        playerdrawn.rotationYaw = (float)Math.atan((double)(yaw / 40.0F)) * 40.0F;
        playerdrawn.rotationPitch = -((float)Math.atan((double)(pitch / 40.0F))) * 20.0F;
        playerdrawn.rotationYawHead = playerdrawn.rotationYaw;
        playerdrawn.prevRotationYawHead = playerdrawn.rotationYaw;
        GL11.glTranslatef(0.0F, playerdrawn.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(playerdrawn, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        playerdrawn.renderYawOffset = f2;
        playerdrawn.rotationYaw = f3;
        playerdrawn.rotationPitch = f4;
        playerdrawn.prevRotationYawHead = f5;
        playerdrawn.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
        }

        if (button.id == 55) {
            this.mc.thePlayer.sendPacket(new Packet101CloseWindow());
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }

	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == ((GameSettingsAccessor) Minecraft.getMinecraft().gameSettings)
                .getKeyBindingBaublesInventory().keyCode)
        {
            this.mc.thePlayer.closeScreen();
        } else super.keyTyped(par1, par2);
	}
}
