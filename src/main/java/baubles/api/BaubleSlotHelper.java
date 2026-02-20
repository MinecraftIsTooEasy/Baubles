package baubles.api;

import net.minecraft.EntityPlayer;
import net.minecraft.IInventory;
import net.minecraft.Item;
import net.minecraft.ItemStack;

/**
 * Helper class to check bauble slots in a type-safe way
 * Slot layout:
 * - Slot 1: AMULET
 * - Slot 2: BACK
 * - Slot 3: FEET
 * - Slot 4-5: BRACELET
 * - Slot 6-7: HAND
 * - Slot 8-9: RING
 * - Slot 10: BELT
 * - Slot 11: CHARM
 */

public class BaubleSlotHelper {

    public static final int AMULET_SLOT = 1;
    public static final int BACK_SLOT = 2;
    public static final int FEET_SLOT = 3;
    public static final int BRACELET_SLOT_1 = 4;
    public static final int BRACELET_SLOT_2 = 5;
    public static final int HAND_SLOT_1 = 6;
    public static final int HAND_SLOT_2 = 7;
    public static final int RING_SLOT_1 = 8;
    public static final int RING_SLOT_2 = 9;
    public static final int BELT_SLOT = 10;
    public static final int CHARM_SLOT = 11;

    public static IInventory getBaubles(EntityPlayer player) {
        return BaublesApi.getBaubles(player);
    }

    /**
     * Check if player has a specific item in any ring slot
     */
    public static boolean hasRingOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return false;

        ItemStack ring1 = baubles.getStackInSlot(RING_SLOT_1);
        ItemStack ring2 = baubles.getStackInSlot(RING_SLOT_2);

        boolean hasRing1 = ring1 != null && ring1.getItem() == item;
        boolean hasRing2 = ring2 != null && ring2.getItem() == item;

        return hasRing1 || hasRing2;
    }

    /**
     * Count how many of a specific item the player has equipped in ring slots
     */
    public static int countRingsOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return 0;

        int count = 0;

        ItemStack ring1 = baubles.getStackInSlot(RING_SLOT_1);
        ItemStack ring2 = baubles.getStackInSlot(RING_SLOT_2);

        if (ring1 != null && ring1.getItem() == item) {
            count++;
        }
        if (ring2 != null && ring2.getItem() == item) {
            count++;
        }

        return count;
    }

    /**
     * Check if player has a specific item in the amulet slot
     */
    public static boolean hasAmuletOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return false;

        ItemStack amulet = baubles.getStackInSlot(AMULET_SLOT);
        return amulet != null && amulet.getItem() == item;
    }

    /**
     * Check if player has a specific item in the belt slot
     */
    public static boolean hasBeltOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return false;

        ItemStack belt = baubles.getStackInSlot(BELT_SLOT);
        return belt != null && belt.getItem() == item;
    }

    /**
     * Get the amulet stack
     */
    public static ItemStack getAmulet(EntityPlayer player) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return null;
        return baubles.getStackInSlot(AMULET_SLOT);
    }

    /**
     * Get the belt stack
     */
    public static ItemStack getBelt(EntityPlayer player) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return null;
        return baubles.getStackInSlot(BELT_SLOT);
    }

    /**
     * Check if player has a specific item in the back slot
     */
    public static boolean hasBackOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return false;

        ItemStack back = baubles.getStackInSlot(BACK_SLOT);
        return back != null && back.getItem() == item;
    }

    /**
     * Get the back stack
     */
    public static ItemStack getBack(EntityPlayer player) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return null;
        return baubles.getStackInSlot(BACK_SLOT);
    }

    /**
     * Check if player has a specific item in the feet slot
     */
    public static boolean hasFeetOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return false;

        ItemStack feet = baubles.getStackInSlot(FEET_SLOT);
        return feet != null && feet.getItem() == item;
    }

    /**
     * Get the feet stack
     */
    public static ItemStack getFeet(EntityPlayer player) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return null;
        return baubles.getStackInSlot(FEET_SLOT);
    }

    /**
     * Check if player has a specific item in any bracelet slot
     */
    public static boolean hasBraceletOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return false;

        ItemStack bracelet1 = baubles.getStackInSlot(BRACELET_SLOT_1);
        ItemStack bracelet2 = baubles.getStackInSlot(BRACELET_SLOT_2);

        boolean hasBracelet1 = bracelet1 != null && bracelet1.getItem() == item;
        boolean hasBracelet2 = bracelet2 != null && bracelet2.getItem() == item;

        return hasBracelet1 || hasBracelet2;
    }

    /**
     * Count how many of a specific item the player has equipped in bracelet slots
     */
    public static int countBraceletsOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return 0;

        int count = 0;

        ItemStack bracelet1 = baubles.getStackInSlot(BRACELET_SLOT_1);
        ItemStack bracelet2 = baubles.getStackInSlot(BRACELET_SLOT_2);

        if (bracelet1 != null && bracelet1.getItem() == item) {
            count++;
        }
        if (bracelet2 != null && bracelet2.getItem() == item) {
            count++;
        }

        return count;
    }

    /**
     * Check if player has a specific item in any hand slot
     */
    public static boolean hasHandOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return false;

        ItemStack hand1 = baubles.getStackInSlot(HAND_SLOT_1);
        ItemStack hand2 = baubles.getStackInSlot(HAND_SLOT_2);

        boolean hasHand1 = hand1 != null && hand1.getItem() == item;
        boolean hasHand2 = hand2 != null && hand2.getItem() == item;

        return hasHand1 || hasHand2;
    }

    /**
     * Count how many of a specific item the player has equipped in hand slots
     */
    public static int countHandsOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return 0;

        int count = 0;

        ItemStack hand1 = baubles.getStackInSlot(HAND_SLOT_1);
        ItemStack hand2 = baubles.getStackInSlot(HAND_SLOT_2);

        if (hand1 != null && hand1.getItem() == item) {
            count++;
        }
        if (hand2 != null && hand2.getItem() == item) {
            count++;
        }

        return count;
    }

    /**
     * Check if player has a specific item in the charm slot
     */
    public static boolean hasCharmOfType(EntityPlayer player, Item item) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return false;

        ItemStack charm = baubles.getStackInSlot(CHARM_SLOT);
        return charm != null && charm.getItem() == item;
    }

    /**
     * Get the charm stack
     */
    public static ItemStack getCharm(EntityPlayer player) {
        IInventory baubles = getBaubles(player);
        if (baubles == null) return null;
        return baubles.getStackInSlot(CHARM_SLOT);
    }

}