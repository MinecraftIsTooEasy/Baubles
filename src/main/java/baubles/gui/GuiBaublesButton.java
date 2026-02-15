package baubles.gui;

import net.minecraft.FontRenderer;
import net.minecraft.GuiButton;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiBaublesButton extends GuiButton {

	public GuiBaublesButton(int par1, int par2, int par3,
			int par4, int par5, String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}
	
	public void drawButton(Minecraft mc, int xx, int yy)
    {
        if (this.drawButton)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(GuiPlayerExpanded.background);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = xx >= this.xPosition && yy >= this.yPosition && xx < this.xPosition + this.width && yy < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
//            GL11.glEnable(GL11.GL_BLEND);
//            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            
            
            if (k == 1) {
            	this.drawTexturedModalRect(this.xPosition, this.yPosition, 200, 48, 10, 10);	
            } else {
            	this.drawTexturedModalRect(this.xPosition, this.yPosition, 210, 48, 10, 10);
            	this.drawCenteredString(fontrenderer, this.displayString, 
            			this.xPosition + 5, this.yPosition + this.height, 0xffffff);
            }
            
            this.mouseDragged(mc, xx, yy);

        }
    }

}
