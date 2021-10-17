package jp.kaiz.minfo;

import cpw.mods.fml.common.Mod;

@Mod(modid = MinFoCore.MODID, version = MinFoCore.VERSION, name = MinFoCore.MODID)
public class MinFoCore {
    public static final String MODID = "MinFo";

    public static final String VERSION = "1.1.0";

    @Mod.Instance(MODID)
    public static MinFoCore INSTANCE;
}
