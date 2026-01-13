/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.DrawContext
 *  net.minecraft.client.gui.Drawable
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.gui.screen.TitleScreen
 *  net.minecraft.client.gui.widget.ButtonWidget
 *  net.minecraft.client.util.math.MatrixStack
 *  org.lwjgl.glfw.GLFW
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.gzsakura_miitong.asm.mixins;

import dev.gzsakura_miitong.Vitality;
import dev.gzsakura_miitong.api.utils.render.Render2DUtil;
import dev.gzsakura_miitong.asm.accessors.IScreen;
import dev.gzsakura_miitong.core.impl.FontManager;
import dev.gzsakura_miitong.core.impl.ShaderManager;
import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;

@Mixin(value={TitleScreen.class})
public class MixinTitleScreen {
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void vitalityBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        float py;
        float px;
        float ry;
        float rx;
        float keyCodec;
        int i;
        float x0;
        float i2;
        float w = context.getScaledWindowWidth();
        float h = context.getScaledWindowHeight();
        MatrixStack m = context.getMatrices();
        long now = System.currentTimeMillis();
        float tNorm = (float)(now % 8000L) / 8000.0f;
        Color blue = new Color(28, 60, 110, 255);
        Color purple = new Color(190, 50, 160, 255);
        float mix = (float)((Math.sin((double)tNorm * Math.PI * 2.0) + 1.0) * 0.5);
        int topR = (int)((float)blue.getRed() * (1.0f - mix) + (float)purple.getRed() * mix);
        int topG = (int)((float)blue.getGreen() * (1.0f - mix) + (float)purple.getGreen() * mix);
        int topB = (int)((float)blue.getBlue() * (1.0f - mix) + (float)purple.getBlue() * mix);
        int botR = (int)((float)purple.getRed() * (1.0f - mix) + (float)blue.getRed() * mix);
        int botG = (int)((float)purple.getGreen() * (1.0f - mix) + (float)blue.getGreen() * mix);
        int botB = (int)((float)purple.getBlue() * (1.0f - mix) + (float)blue.getBlue() * mix);
        Color g1 = new Color(topR, topG, topB, 255);
        Color g2 = new Color(botR, botG, botB, 255);
        Render2DUtil.verticalGradient(m, 0.0f, 0.0f, w, h, g1, g2);
        context.fillGradient(0, 0, (int)w, (int)h, g1.getRGB(), g2.getRGB());
        float phase = (float)(now % 7000L) / 7000.0f;
        float angle = 0.523599f;
        float dx = (float)Math.tan(angle) * h;
        float base = -h;
        float spacing = 68.0f;
        float shift = phase * spacing * 3.1f;
        Color c1 = new Color(255, 150, 240, 26);
        Color c2 = new Color(120, 220, 255, 20);
        for (i2 = base; i2 < w; i2 += spacing) {
            x0 = i2 + shift;
            Render2DUtil.drawLine(m, x0, 0.0f, x0 + dx, h, c1.getRGB());
        }
        for (i2 = base + spacing / 2.0f; i2 < w; i2 += spacing) {
            x0 = i2 + shift * 0.85f;
            Render2DUtil.drawLine(m, x0, 0.0f, x0 + dx, h, c2.getRGB());
        }
        float pulse = (float)Math.sin((double)((float)(now % 4000L) / 4000.0f) * Math.PI * 2.0) * 0.5f + 0.5f;
        float radius = Math.min(w, h) * (0.12f + 0.08f * pulse);
        Color ring = new Color(255, 255, 255, 30);
        Render2DUtil.drawCircle(m, w / 2.0f, h / 2.0f, radius, ring, 80);
        Render2DUtil.drawCircle(m, w / 2.0f, h / 2.0f, radius * 1.2f, new Color(120, 220, 255, 24), 80);
        int dots = 22;
        for (i = 0; i < dots; ++i) {
            keyCodec = (float)(Math.PI * 2 * (double)i / (double)dots + (double)(phase * 2.0f) * Math.PI);
            rx = (float)Math.cos(keyCodec) * (radius * 1.2f);
            ry = (float)Math.sin(keyCodec) * (radius * 0.7f);
            px = w / 2.0f + rx;
            py = h / 2.0f + ry + (float)Math.sin(keyCodec * 2.0f + phase * 4.0f) * 6.0f;
            Render2DUtil.drawCircle(m, px, py, 1.5f, new Color(255, 255, 255, 38), 30);
        }
        for (i = 0; i < 12; ++i) {
            keyCodec = (float)(Math.PI * 2 * (double)i / 12.0 + (double)(phase * 3.1f));
            rx = (float)Math.cos(keyCodec) * (radius * 1.6f);
            ry = (float)Math.sin(keyCodec) * (radius * 1.0f);
            px = w / 2.0f + rx;
            py = h / 2.0f + ry;
            Render2DUtil.drawCircle(m, px, py, 2.4f, new Color(120, 220, 255, 36), 36);
        }
        int y = 0;
        while ((float)y < h) {
            int alpha = 18;
            int c = new Color(0, 0, 0, alpha).getRGB();
            Render2DUtil.drawLine(m, 0.0f, y, w, y, c);
            y += 3;
        }
        if (!Vitality.SHADER.fullNullCheck()) {
            ManagedShaderEffect gradient = Vitality.SHADER.getShader(ShaderManager.Shader.Gradient);
            gradient.setUniformValue("alpha2", 0.36f);
            gradient.setUniformValue("rgb", 0.1f, 0.75f, 1.0f);
            gradient.setUniformValue("rgb1", 0.98f, 0.35f, 0.74f);
            gradient.setUniformValue("rgb2", 0.46f, 0.19f, 0.81f);
            gradient.setUniformValue("rgb3", 0.12f, 0.5f, 0.95f);
            gradient.setUniformValue("step", 99.0f);
            gradient.setUniformValue("radius", 1.6f);
            gradient.setUniformValue("quality", 0.8f);
            gradient.setUniformValue("divider", 220.0f);
            gradient.setUniformValue("maxSample", 6.0f);
            gradient.setUniformValue("resolution", w, h);
            float t = (float)(now % 100000L) / 1000.0f;
            gradient.setUniformValue("time", t * 220.0f);
            gradient.render(MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true));
        }
        m.push();
        float titleScale = 3.6f;
        m.translate(w / 2.0f, h * 0.24f, 0.0f);
        m.scale(titleScale, titleScale, 1.0f);
        if (FontManager.ui == null) {
            FontManager.init();
        }
        if (FontManager.ui != null) {
            FontManager.ui.drawCenteredString(m, Vitality.NAME, 0.0, 0.0, new Color(230, 255, 255, 255));
            FontManager.ui.drawCenteredString(m, Vitality.NAME, 0.0, (double)2.2f, new Color(120, 220, 255, 180));
            FontManager.ui.drawCenteredString(m, Vitality.NAME, 0.0, (double)-2.2f, new Color(255, 160, 240, 160));
        }
        m.pop();
        Screen c = MinecraftClient.getInstance().currentScreen;
        if (c instanceof TitleScreen) {
            TitleScreen ts = (TitleScreen)c;
            int idx = 0;
            for (Drawable d : ((IScreen)IScreen.class.cast(ts)).getDrawables()) {
                if (d instanceof ButtonWidget) {
                    ButtonWidget bw = (ButtonWidget)d;
                    if (idx < 3) {
                        int bx = bw.getX();
                        int by = bw.getY();
                        int bwid = bw.getWidth();
                        int bhei = bw.getHeight();
                        boolean hovered = mouseX >= bx && mouseX <= bx + bwid && mouseY >= by && mouseY <= by + bhei;
                        boolean pressed = hovered && GLFW.glfwGetMouseButton((long)MinecraftClient.getInstance().getWindow().getHandle(), (int)0) == 1;
                        Color accent = new Color(0, 120, 212, 255);
                        Color neon = new Color(0, 224, 255, 180);
                        Color amber = new Color(255, 210, 0, 160);
                        Render2DUtil.drawRoundedStroke(context.getMatrices(), bx, by, bwid, bhei, 5.0f, hovered ? new Color(220, 224, 230, 200) : new Color(220, 224, 230, 160), 64);
                        Render2DUtil.drawLine(context.getMatrices(), (float)bx + 2.0f, (float)by + 2.0f, (float)(bx + bwid) - 2.0f, (float)by + 2.0f, new Color(255, 255, 255, 80).getRGB());
                        Render2DUtil.drawLine(context.getMatrices(), (float)bx + 2.0f, (float)(by + bhei) - 2.2f, (float)(bx + bwid) - 2.0f, (float)(by + bhei) - 2.2f, new Color(120, 130, 140, 60).getRGB());
                        Render2DUtil.drawLine(context.getMatrices(), (float)bx + 4.0f, (float)by + 4.0f, (float)bx + 18.0f, (float)by + 10.0f, neon.getRGB());
                        Render2DUtil.drawLine(context.getMatrices(), (float)(bx + bwid) - 18.0f, (float)(by + bhei) - 6.0f, (float)(bx + bwid) - 6.0f, (float)(by + bhei) - 2.0f, amber.getRGB());
                        if (pressed) {
                            Render2DUtil.drawGlow(context.getMatrices(), (float)bx - 2.0f, (float)by - 2.0f, (float)bwid + 4.0f, (float)bhei + 4.0f, new Color(0, 0, 0, 18).getRGB());
                            Render2DUtil.verticalGradient(context.getMatrices(), (float)bx + 1.5f, (float)by + 1.5f, (float)(bx + bwid) - 1.5f, (float)(by + bhei) - 1.5f, new Color(255, 255, 255, 50), new Color(0, 0, 0, 46));
                            Render2DUtil.drawRoundedStroke(context.getMatrices(), (float)bx + 1.0f, (float)by + 1.0f, (float)bwid - 2.0f, (float)bhei - 2.0f, 4.2f, new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 160), 96);
                            Render2DUtil.drawRoundedStroke(context.getMatrices(), (float)bx + 1.6f, (float)by + 1.6f, (float)bwid - 3.2f, (float)bhei - 3.2f, 3.8f, new Color(255, 255, 255, 140), 96);
                            Render2DUtil.drawRoundedStroke(context.getMatrices(), (float)bx + 2.0f, (float)(by + bhei) - 2.8f, (float)bwid - 4.0f, 1.6f, 2.0f, new Color(90, 100, 110, 100), 96);
                            by = (int)((float)by + 1.2f);
                            bx = (int)((float)bx + 0.6f);
                        } else if (hovered) {
                            Render2DUtil.drawRoundedStroke(context.getMatrices(), (float)bx - 0.5f, (float)by - 0.5f, (float)bwid + 1.0f, (float)bhei + 1.0f, 5.4f, new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 160), 96);
                        }
                        d.render(context, mouseX, mouseY, delta);
                    }
                }
                if (d instanceof ButtonWidget) {
                    ++idx;
                }
                if (!(d instanceof ButtonWidget)) {
                    d.render(context, mouseX, mouseY, delta);
                    continue;
                }
                if (idx < 3) continue;
                d.render(context, mouseX, mouseY, delta);
            }
        }
        ci.cancel();
    }

    @Inject(method={"init()V"}, at={@At(value="TAIL")})
    private void vitalityLayout(CallbackInfo ci) {
        TitleScreen self = (TitleScreen)(Object)this;
        int w = self.width;
        int h = self.height;
        int btnW = Math.min(300, (int)((double)w * 0.42));
        int btnH = 24;
        int spacing = 8 + btnH;
        int gap = 12;
        int xLeft = w / 2 - gap / 2 - btnW;
        int xRight = w / 2 + gap / 2;
        int xCenter = w / 2 - btnW / 2;
        int startY = (int)((double)h * 0.44);

        java.util.ArrayList<ButtonWidget> largeButtons = new java.util.ArrayList<>();
        for (Drawable d : ((IScreen)IScreen.class.cast(self)).getDrawables()) {
            if (!(d instanceof ButtonWidget)) {
                continue;
            }
            ButtonWidget bw = (ButtonWidget)d;
            if (bw.getWidth() >= 80 && bw.getHeight() >= 20) {
                largeButtons.add(bw);
            }
        }
        largeButtons.sort((a, b) -> {
            int dy = Integer.compare(a.getY(), b.getY());
            if (dy != 0) {
                return dy;
            }
            return Integer.compare(a.getX(), b.getX());
        });

        for (int i = 0; i < largeButtons.size(); i++) {
            ButtonWidget bw = largeButtons.get(i);
            bw.setWidth(btnW);
            if (i == 0) {
                bw.setPosition(xLeft, startY);
            } else if (i == 1) {
                bw.setPosition(xRight, startY);
            } else if (i == 2) {
                bw.setPosition(xCenter, startY + spacing);
            } else if (i == 3) {
                bw.setPosition(xLeft, startY + spacing * 2);
            } else if (i == 4) {
                bw.setPosition(xRight, startY + spacing * 2);
            } else {
                int j = i - 5;
                int col = j % 2;
                int row = j / 2 + 3;
                bw.setPosition(col == 0 ? xLeft : xRight, startY + spacing * row);
            }
        }
    }
}

