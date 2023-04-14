package me.h3xadecimal.milktweak;

import me.h3xadecimal.milktweak.events.MilkTweakEB;
import me.h3xadecimal.milktweak.features.halo.Halo;
import me.h3xadecimal.milktweak.files.FileConfig;
import me.h3xadecimal.milktweak.files.FilesManager;
import me.h3xadecimal.milktweak.gui.GuiFunctions;
import me.h3xadecimal.milktweak.gui.MainUI;
import me.h3xadecimal.milktweak.utils.FMLUtils;
import me.h3xadecimal.milktweak.utils.GitUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("milktweak")
public class MilkTweak {
    public static MilkTweak INSTANCE;

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    private MainUI uiInstance;
    private FileConfig config;
    private MilkTweakEB eventBus;

    public MilkTweak() {
        INSTANCE = this;
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::lateinit);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Loading MilkTweak " + getVersion());

        uiInstance = new MainUI();
        eventBus = new MilkTweakEB();
        config = FileConfig.load(FilesManager.getMainConfig());

        FilesManager.load();
        GuiFunctions.loadConfig();

//        Halo.loadHaloFromCloud();
//        MinecraftForge.EVENT_BUS.register(Halo.INSTANCE);
    }

    private void lateinit(final FMLLoadCompleteEvent event) {
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
//        System.out.println(event.getKey());
        if (event.getKey() == 345) {
            Minecraft.getInstance().displayGuiScreen(uiInstance);
        }
    }

    public FileConfig getConfig() {
        return config;
    }

    public MainUI getUiInstance() {
        return uiInstance;
    }

    public MilkTweakEB getEventBus() {
        return eventBus;
    }

    public void saveConfig() {
        FilesManager.saveConfig(config, FilesManager.getMainConfig());
    }

    public ModContainer getContainer() {
        return FMLUtils.getModContainer("milktweak");
    }

    public String getVersion() {
        return GitUtils.getBuildVersion() + " (git-" + GitUtils.getCommitHash() + ")";
    }
}
