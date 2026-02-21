# 饰品栏-Baubles-v1.1.0

---

- 修复饰品检测残留问题
- 新增一个专属创造模式物品栏
- 将饰品槽位扩展到12个
- 
- HEAD (头饰) - 1个
- AMULET (项链) - 1个
- BACK (背饰) - 1个
- FEET (足部) - 1个
- BRACELET (手镯) - 2个
- HAND (手饰) - 2个
- RING (戒指) - 2个
- BELT (腰带) - 1个
- CHARM (护符) - 1个

# 饰品栏-Baubles-v1.1.1

---

- 修复服务器里饰品检测不同步问题
- 补充InventoryBaubles.isItemValidForSlot方法，用于右键穿戴饰品
- 后续的MOD可以通过调用baubles.api.BaubleSlotHelper的相关方法检测饰品，例如
- if (BaubleSlotHelper.hasAmuletOfType(player, YourItems.SILVER_AMULET)) {

# 饰品栏-Baubles-v1.1.1-fix

---

- 兼容最新的死亡不掉落