package baubles.common.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import baubles.MITE_Baubles;
import baubles.common.container.InventoryBaubles;

import baubles.common.event.MITEModEvents;
import baubles.util.Config;
import com.google.common.io.Files;
import net.minecraft.CompressedStreamTools;
import net.minecraft.EntityPlayer;
import net.minecraft.NBTTagCompound;
import org.spongepowered.asm.mixin.Unique;

public class PlayerHandler {

	private static HashMap<String, InventoryBaubles> playerBaubles = new HashMap<>();

	public static void clearPlayerBaubles(EntityPlayer player) {
		playerBaubles.remove(player.getCommandSenderName());
	}

	public static InventoryBaubles getPlayerBaubles(EntityPlayer player) {
		if (!playerBaubles.containsKey(player.getCommandSenderName())) {
			InventoryBaubles inventory = new InventoryBaubles(player);
			playerBaubles.put(player.getCommandSenderName(), inventory);
		}
		return playerBaubles.get(player.getCommandSenderName());
	}

	public static void setPlayerBaubles(EntityPlayer player,
			InventoryBaubles inventory) {
		playerBaubles.put(player.getCommandSenderName(), inventory);
	}

	public static void loadPlayerBaubles(EntityPlayer player, File file1, File file2) {
		if (player != null && !player.worldObj.isRemote) {
			try {
				NBTTagCompound data = null;
				boolean save = false;
				if (file1 != null && file1.exists()) {
					try {
						FileInputStream fileinputstream = new FileInputStream(
								file1);
						data = CompressedStreamTools
								.readCompressed(fileinputstream);
						fileinputstream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (file1 == null || !file1.exists() || data == null
						|| data.hasNoTags()) {
					MITE_Baubles.LOGGER.warn("Data not found for "
							+ player.getCommandSenderName()
							+ ". Trying to load backup data.");
					if (file2 != null && file2.exists()) {
						try {
							FileInputStream fileinputstream = new FileInputStream(
									file2);
							data = CompressedStreamTools
									.readCompressed(fileinputstream);
							fileinputstream.close();
							save = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				if (data != null) {
					InventoryBaubles inventory = new InventoryBaubles(player);
					inventory.readNBT(data);
					playerBaubles.put(player.getCommandSenderName(), inventory);
					if (save)
						savePlayerBaubles(player, file1, file2);
				}
			} catch (Exception exception1) {
				MITE_Baubles.LOGGER.fatal("Error loading baubles inventory");
				exception1.printStackTrace();
			}
		}
	}

	public static void savePlayerBaubles(EntityPlayer player, File file1, File file2) {
		if (player != null && !player.worldObj.isRemote) {
			try {
				if (file1 != null && file1.exists()) {
					try {
						Files.copy(file1, file2);
					} catch (Exception e) {
						MITE_Baubles.LOGGER
								.error("Could not backup old baubles file for player "
										+ player.getCommandSenderName());
					}
				}

				try {
					if (file1 != null) {
						InventoryBaubles inventory = getPlayerBaubles(player);
						NBTTagCompound data = new NBTTagCompound();
						inventory.saveNBT(data);

						FileOutputStream fileoutputstream = new FileOutputStream(
								file1);
						CompressedStreamTools.writeCompressed(data,
								fileoutputstream);
						fileoutputstream.close();

					}
				} catch (Exception e) {
					MITE_Baubles.LOGGER.error("Could not save baubles file for player "
							+ player.getCommandSenderName());
					e.printStackTrace();
					if (file1.exists()) {
						try {
							file1.delete();
						} catch (Exception ignored) {
						}
					}
				}
			} catch (Exception exception1) {
				MITE_Baubles.LOGGER.fatal("Error saving baubles inventory");
				exception1.printStackTrace();
			}
		}
	}

	public static void playerLoadDo(EntityPlayer player, File directory, Boolean gamemode) {
		PlayerHandler.clearPlayerBaubles(player);

		File file1, file2;
		String fileName, fileNameBackup;
		if (gamemode || !Config.SPLIT_SURVIVAL_CREATIVE.get()) {
			fileName = "baub";
			fileNameBackup = "baubback";
		}
		else {
			fileName = "baubs";
			fileNameBackup = "baubsback";
		}

		// look for normal files first
		file1 = getPlayerFile(fileName, directory, player.getCommandSenderName());
		file2 = getPlayerFile(fileNameBackup, directory, player.getCommandSenderName());

		// look for uuid files when normal file missing
		if (!file1.exists()) {
			File filep = getPlayerFileUUID(fileName, directory, player.getUniqueID().toString());
			if (filep.exists()) {
				try {
					Files.copy(filep, file1);
					MITE_Baubles.LOGGER.info("Using and converting UUID Baubles savefile for "+player.getCommandSenderName());
					filep.delete();
					File fb = getPlayerFileUUID(fileNameBackup, directory, player.getUniqueID().toString());
					if (fb.exists()) fb.delete();
				} catch (IOException ignored) {}
			} else {
				File filet = getLegacyFileFromPlayer(player);
				if (filet != null && filet.exists()) {
					try {
						Files.copy(filet, file1);
						MITE_Baubles.LOGGER.info("Using pre MC 1.7.10 Baubles savefile for " + player.getCommandSenderName());
					} catch (IOException ignored) {
					}
				}
			}
		}

		PlayerHandler.loadPlayerBaubles(player, file1, file2);
		MITEModEvents.syncBaubles(player);
	}

	public static void playerSaveDo(EntityPlayer player, File directory, Boolean gamemode) {
		if (gamemode || !Config.SPLIT_SURVIVAL_CREATIVE.get()) {
			PlayerHandler.savePlayerBaubles(player,
					getPlayerFile("baub", directory, player.getCommandSenderName()),
					getPlayerFile("baubback", directory, player.getCommandSenderName()));
		}
		else {
			PlayerHandler.savePlayerBaubles(player,
					getPlayerFile("baubs", directory, player.getCommandSenderName()),
					getPlayerFile("baubsback", directory, player.getCommandSenderName()));
		}
	}

	private static File getLegacyFileFromPlayer(EntityPlayer player)
	{
		try {
			File playersDirectory = new File(player.worldObj.getSaveHandler().getWorldDirectoryName(), "players");
			return new File(playersDirectory, player.getCommandSenderName() + ".baub");
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}

	private static File getPlayerFile(String suffix, File playerDirectory, String playername)
	{
		if ("dat".equals(suffix)) throw new IllegalArgumentException("The suffix 'dat' is reserved");
		return new File(playerDirectory, playername+"."+suffix);
	}

	private static File getPlayerFileUUID(String suffix, File playerDirectory, String playerUUID)
	{
		if ("dat".equals(suffix)) throw new IllegalArgumentException("The suffix 'dat' is reserved");
		return new File(playerDirectory, playerUUID+"."+suffix);
	}
}
