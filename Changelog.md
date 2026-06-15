# Baubles v1.1.2

---

- Reworked and polished the baubles inventory interface.
- Fixed several bugs.
- The Miner's Ring effect can now stack.

# Baubles v1.1.1-fix

---

- Added compatibility with the latest Keep Inventory behavior.

# Baubles v1.1.1

---

- Fixed server-side desync issues with bauble detection.
- Added `InventoryBaubles.isItemValidForSlot` support for right-click equipping.
- Added `baubles.api.BaubleSlotHelper` helpers so other mods can check equipped baubles, for example:
if (BaubleSlotHelper.hasAmuletOfType(player, YourItems.SILVER_AMULET))

# Baubles v1.1.0

---

- Fixed lingering bauble detection issues.
- Added a dedicated creative tab for baubles.
- Expanded the bauble inventory to 12 slots:
- HEAD - 1 slot
- AMULET - 1 slot
- BACK - 1 slot
- FEET - 1 slot
- BRACELET - 2 slots
- HAND - 2 slots
- RING - 2 slots
- BELT - 1 slot
- CHARM - 1 slot
