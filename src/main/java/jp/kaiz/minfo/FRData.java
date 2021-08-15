package jp.kaiz.minfo;

import jp.kaiz.minfo.api.MinFoCustomFontRenderer;
import net.minecraft.util.ResourceLocation;

public class FRData<T> {
    private final T o;
    private final int fontSize;

    public FRData(T name, int fontSize) {
        this.o = name;
        this.fontSize = fontSize;
    }

    public void createFontRenderer() {
        if (this.o instanceof ResourceLocation) {
            MinFoCustomFontRenderer.getCustomFontRenderer((ResourceLocation) this.o, this.fontSize);
        } else if (this.o instanceof String) {
            MinFoCustomFontRenderer.getCustomFontRenderer((String) this.o, this.fontSize);
        }
    }
}
