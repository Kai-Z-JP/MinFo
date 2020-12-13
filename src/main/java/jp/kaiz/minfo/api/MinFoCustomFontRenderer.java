package jp.kaiz.minfo.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MinFoCustomFontRenderer extends FontRenderer {
    private UnicodeFont uFont;

    //必ずTTF
    //OTFはTYPE2だから無理
    public MinFoCustomFontRenderer(ResourceLocation location, int fontSize) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);

        try (InputStream fontStream = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream()) {
            if (fontStream != null) {
                this.uFont = new UnicodeFont(Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont((float) fontSize));
                this.loadJapaneseCharSets();
            }
        } catch (FontFormatException | IOException | SlickException exception) {
            throw new RuntimeException(exception);
        }
    }


    public MinFoCustomFontRenderer(String name, int fontSize) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.uFont = new UnicodeFont(new Font(name, Font.PLAIN, fontSize));
        try {
            this.loadJapaneseCharSets();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private void loadJapaneseCharSets() throws SlickException {
        //http://welovy.hatenablog.com/?page=1358762469
        this.uFont.addAsciiGlyphs();
        this.uFont.addGlyphs(0x3000, 0x30ff); // Hiragana + katakanab + fullwidth punctuations
        this.uFont.addGlyphs(0x4e00, 0x9fc0); // Kanji
        this.uFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        this.uFont.loadGlyphs();
    }

    @Override
    public int drawString(String string, int x, int y, int color) {
        if (string == null) {
            return 0;
        } else {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            this.uFont.drawString(x, y, string, new org.newdawn.slick.Color(color));
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
            return x;
        }
    }

    @Override
    public int drawStringWithShadow(String string, int x, int y, int color) {
        return drawString(string, x, y, color);
    }

    @Override
    public int getCharWidth(char c) {
        return getStringWidth(Character.toString(c));
    }

    @Override
    public int getStringWidth(String p_78256_1_) {
        return this.uFont.getWidth(p_78256_1_) / 2;
    }
}
