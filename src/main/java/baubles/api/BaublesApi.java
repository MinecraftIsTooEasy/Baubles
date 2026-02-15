package baubles.api;

import net.minecraft.EntityPlayer;
import net.minecraft.IInventory;
import net.xiaoyu233.fml.FishModLoader;

import java.lang.reflect.Method;

/**
 * @author Azanor
 */
public class BaublesApi 
{
	static Method getBaubles;
	
	/**
	 * Retrieves the baubles inventory for the supplied player
	 */
	public static IInventory getBaubles(EntityPlayer player)
	{
		IInventory ot = null;
		
	    try
	    {
	        if(getBaubles == null) 
	        {
	            Class<?> fake = Class.forName("baubles.common.lib.PlayerHandler");
	            getBaubles = fake.getMethod("getPlayerBaubles", EntityPlayer.class);
	        }
	        
	        ot = (IInventory) getBaubles.invoke(null, player);
	    } 
	    catch(Exception ex) 
	    { 
	    	FishModLoader.LOGGER.warn("[Baubles API] Could not invoke baubles.common.lib.PlayerHandler method getPlayerBaubles");
	    }
	    
		return ot;
	}
	
}
