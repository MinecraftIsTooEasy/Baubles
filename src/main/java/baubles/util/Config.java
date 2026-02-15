package baubles.util;

import net.xiaoyu233.fml.config.ConfigEntry;
import net.xiaoyu233.fml.config.ConfigRoot;
import net.xiaoyu233.fml.util.FieldReference;

import java.io.File;

public class Config {
    public static final File CONFIG_FILE = new File("MITE-Baubles.json");

    // config properties
    public static FieldReference<Integer> RING_ITEMID = new FieldReference<>(6667);
    public static FieldReference<Boolean> SPLIT_SURVIVAL_CREATIVE = new FieldReference<>(false);

    public static final ConfigRoot baubles = ConfigRoot.create(Constant.CONFIG_VERSION).
            addEntry(ConfigEntry.of("ring", RING_ITEMID).withComment("矿工指环的物品ID (4000-31999)")).
            addEntry(ConfigEntry.of("splitSurvivalCreative", SPLIT_SURVIVAL_CREATIVE).withComment("Split Baubles inventory for survival and creative game modes."));;
}