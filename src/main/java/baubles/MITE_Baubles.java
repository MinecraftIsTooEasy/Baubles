package baubles;

import baubles.api.IBaublePlugin;
import baubles.common.event.EventListeners;
import baubles.util.Config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.xiaoyu233.fml.FishModLoader;
import net.xiaoyu233.fml.classloading.Mod;
import net.xiaoyu233.fml.config.ConfigRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MITE_Baubles implements ModInitializer, PreLaunchEntrypoint {
    public static final String modId = "baubles";
    public static final String modName = "Baubles";
    public static final Logger LOGGER = LogManager.getLogger("baubles");
    private transient final ConfigRegistry configRegistry = new ConfigRegistry(Config.baubles, Config.CONFIG_FILE);
    public static List<IBaublePlugin> baublePlugins = FishModLoader.getEntrypointContainers("baubles", IBaublePlugin.class)
            .stream().map(EntrypointContainer::getEntrypoint).toList();

    @Override
    public void onInitialize() {
        EventListeners.registerAllEvents();
    }

    @Override
    public void onPreLaunch() {
        System.out.println("[" + modName + "] Early riser registering chat formatting");
    }

    @Override
    public Optional<ConfigRegistry> createConfig() {
        return Optional.ofNullable(configRegistry);
    }
}
