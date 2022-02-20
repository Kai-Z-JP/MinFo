package jp.kaiz.minfo;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jp.kaiz.minfo.api.MinFoCustomFontRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

@Mod(modid = MinFoCore.MODID, version = MinFoCore.VERSION, name = MinFoCore.MODID)
public class MinFoCore {
    public static final String MODID = "MinFo";

    public static final String VERSION = "1.3.0";

    @Mod.Instance(MODID)
    public static MinFoCore INSTANCE;


    @Mod.EventHandler
    public void init(FMLPreInitializationEvent FMLInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load e) {
        if (e.world.isRemote) {
            MinFoCustomFontRenderer.loadedFontList.forEach(MinFoCustomFontRenderer::loadCJKCharSets);
        }
    }
}
