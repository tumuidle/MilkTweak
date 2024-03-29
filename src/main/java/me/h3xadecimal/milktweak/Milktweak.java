package me.h3xadecimal.milktweak;

import me.h3xadecimal.milktweak.file.file.FileManager;
import me.h3xadecimal.milktweak.gui.UiMain;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("milktweak")
public class Milktweak {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static Milktweak INSTANCE;

    public Milktweak() {
        INSTANCE = this;

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Initializing files...");
        FileManager.INSTANCE.init();
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (event.getKey() == 345) {
            LOGGER.info("Displaying MainUI");
            Minecraft.getInstance().displayGuiScreen(new UiMain());
        }
    }
}
