package jp.kaiz.minfo.api;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MinFoCustomFontRenderer {
    public static final List<MinFoCustomFontRenderer> loadedFontList = new CopyOnWriteArrayList<>();
    public int FONT_HEIGHT;

    private final ResourceLocation location;
    private final String name;
    private final int fontSize;
    private UnicodeFont uFont;
    private volatile boolean glyphsLoaded = false;

    //必ずTTF
    //OTFはTYPE2だから無理
    public MinFoCustomFontRenderer(ResourceLocation location, int fontSize) {
        this.location = location;
        this.name = null;
        this.fontSize = fontSize;
        this.FONT_HEIGHT = fontSize;
        loadedFontList.add(this);
    }


    public MinFoCustomFontRenderer(String name, int fontSize) {
        this.location = null;
        this.name = name;
        this.fontSize = fontSize;
        this.FONT_HEIGHT = fontSize;
        loadedFontList.add(this);
    }

    public static MinFoCustomFontRenderer create(final ResourceLocation location, final int fontSize) {
        return new MinFoCustomFontRenderer(location, fontSize);
    }

    public static MinFoCustomFontRenderer create(final String name, final int fontSize) {
        return new MinFoCustomFontRenderer(name, fontSize);
    }

    @SuppressWarnings("unchecked")
    private static <T> T runOnMinecraftThread(Callable<T> callable) {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft.func_152345_ab()) {
            try {
                return callable.call();
            } catch (Exception exception) {
                throw asRuntimeException(exception);
            }
        }

        Future<T> future = minecraft.func_152343_a(callable);
        try {
            return future.get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(exception);
        } catch (ExecutionException exception) {
            throw asRuntimeException(exception.getCause());
        }
    }

    private static RuntimeException asRuntimeException(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        }
        if (throwable instanceof Error) {
            throw (Error) throwable;
        }
        return new RuntimeException(throwable);
    }

    public UnicodeFont getUnicodeFont() {
        this.initFont();
        return this.uFont;
    }

    private void initFont() {
        runOnMinecraftThread((Callable<Void>) () -> {
            initFontOnMinecraftThread();
            return null;
        });
    }

    private synchronized void initFontOnMinecraftThread() {
        if (this.uFont != null) {
            return;
        }

        if (this.location != null) {
            try (InputStream fontStream = Minecraft.getMinecraft().getResourceManager().getResource(this.location).getInputStream()) {
                if (fontStream != null) {
                    this.uFont = new UnicodeFont(Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont((float) this.fontSize));
                }
            } catch (FontFormatException | IOException exception) {
                throw new RuntimeException(exception);
            }
        } else {
            this.uFont = new UnicodeFont(new Font(this.name, Font.PLAIN, this.fontSize));
        }
    }

    public void loadCJKCharSets() {
        runOnMinecraftThread((Callable<Void>) () -> {
            loadCJKCharSetsOnMinecraftThread();
            return null;
        });
    }

    private synchronized void loadCJKCharSetsOnMinecraftThread() {
        this.initFontOnMinecraftThread();
        if (!this.glyphsLoaded) {
            try {
                //http://welovy.hatenablog.com/?page=1358762469
                this.uFont.addAsciiGlyphs();
                this.uFont.addGlyphs(0x3000, 0x30ff); // Hiragana + katakana + fullwidth punctuations
                this.uFont.addGlyphs(0x4e00, 0x9fc0); // Kanji
                this.uFont.addGlyphs(0xac00, 0xd7a3); // Korean
                this.uFont.addGlyphs(0xff00, 0xff9f); // Halfwidth and Fullwidth Forms
                this.uFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
                this.uFont.loadGlyphs();
                this.glyphsLoaded = true;
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public int drawString(String string, int x, int y, int color) {
        if (string == null) {
            return 0;
        } else {
            this.loadCJKCharSets();
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

    public int drawString(String string, int x, int y, int color, boolean dropShadow) {
        return drawString(string, x, y, color);
    }

    public int func_78276_b(String string, int x, int y, int color) {
        return drawString(string, x, y, color);
    }

    public int func_78275_b(String string, int x, int y, int color, boolean dropShadow) {
        return drawString(string, x, y, color, dropShadow);
    }

    public void drawText(String string, int color) {
        if (string != null) {
            this.loadCJKCharSets();
            GL11.glPushMatrix();
            GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            this.uFont.drawString(0, 0, string, new org.newdawn.slick.Color(color));
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    public int drawStringWithShadow(String string, int x, int y, int color) {
        return drawString(string, x, y, color);
    }

    public int func_78261_a(String string, int x, int y, int color) {
        return drawStringWithShadow(string, x, y, color);
    }

    public int getCharWidth(char c) {
        return getStringWidth(Character.toString(c));
    }

    public int func_78263_a(char c) {
        return getCharWidth(c);
    }

    public int getStringWidth(String p_78256_1_) {
        this.loadCJKCharSets();
        return this.uFont.getWidth(p_78256_1_);
    }

    public int func_78256_a(String text) {
        return getStringWidth(text);
    }

    public int getTextWidth(String text) {
        return this.getStringWidth(text);
    }
}
