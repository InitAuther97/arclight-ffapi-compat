package io.github.initauther97.afc;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ArclightFFAPICompat.MODID)
public class ArclightFFAPICompat {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "afc";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String FFAPI = "fabric_api";
    public static final String ARCLIGHT = "arclight";

    public ArclightFFAPICompat(IEventBus modEventBus) {
        modEventBus.addListener(FMLCommonSetupEvent.class, this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("Arclight FFAPI compatibility initialized, provided by InitAuther97");
        event.enqueueWork(() -> {
            for (ModInfo info : LoadingModList.get().getMods()) {
                if (info.getModId().equals(FFAPI)) {
                    LOGGER.info("Detected Forgified Fabric API {}", info.getVersion());
                } else if (info.getModId().equals(ARCLIGHT)) {
                    LOGGER.info("Detected Arclight {}", info.getVersion());
                }
            }
        });
    }
}
