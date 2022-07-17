package jp.kaiz.minfo;

import jp.kaiz.minfo.api.MinFoCustomFontRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = MinFoCore.MODID, version = MinFoCore.VERSION, name = MinFoCore.NAME)
public class MinFoCore {
    public static final String MODID = "minfo";
    public static final String NAME = "MinFo";
    public static final String VERSION = "1.4.0";

    @Mod.Instance(MODID)
    public static MinFoCore INSTANCE;


    @Mod.EventHandler
    public void init(FMLPreInitializationEvent FMLInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load e) {
        if (e.getWorld().isRemote) {
            MinFoCustomFontRenderer.loadedFontList.forEach(MinFoCustomFontRenderer::loadCJKCharSets);
        }
    }
}
