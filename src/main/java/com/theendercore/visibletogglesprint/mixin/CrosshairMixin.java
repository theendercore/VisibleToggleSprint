package com.theendercore.visibletogglesprint.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.theendercore.visibletogglesprint.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.theendercore.visibletogglesprint.VisibleToggleSprint.MODID;

@Mixin(InGameHud.class)
public class CrosshairMixin extends DrawableHelper {
    private final Identifier modIcons = new Identifier(MODID, "textures/gui/icons.png");
    @Final
    @Shadow
    private MinecraftClient client;
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void renderCrosshair(MatrixStack matrices, CallbackInfo ci) {
        ModConfig c = ModConfig.getConfig();
        RenderSystem.setShaderTexture(0, modIcons);

        if (this.client.options.sprintKey.isPressed() && c.getSprintEnabled()) {
            this.drawTexture(matrices, (this.scaledWidth - 3) / 2 + c.getSprintLocationX(), (this.scaledHeight - 3) / 2 + c.getSprintLocationY(), 0, 0, 4, 4);
        }
        if (this.client.options.sneakKey.isPressed() && c.getSneakEnabled()) {
            this.drawTexture(matrices, (this.scaledWidth - 3) / 2 + c.getSneakLocationX(), (this.scaledHeight - 3) / 2 + c.getSneakLocationY(), 4, 0, 4, 4);
        }
        RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_TEXTURE);
    }
}
