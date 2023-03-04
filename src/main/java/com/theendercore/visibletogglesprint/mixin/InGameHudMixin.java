package com.theendercore.visibletogglesprint.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.theendercore.visibletogglesprint.lib.PlayerState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.theendercore.visibletogglesprint.VisibleToggleSprint.*;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    private final Identifier MOD_ICONS = new Identifier(MODID, "textures/gui/icons.png");
    PlayerState sprint = getConfig().sprint;
    PlayerState sneak = getConfig().sneak;
    @Final
    @Shadow
    private MinecraftClient client;
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void renderCrosshair(MatrixStack matrices, CallbackInfo ci) {
        RenderSystem.setShaderTexture(0, MOD_ICONS);
        GameOptions gameOptions = client.options;
        if (!gameOptions.debugEnabled && gameOptions.getPerspective().isFirstPerson()) {
            assert client.interactionManager != null;
            if (client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR || this.shouldRenderSpectatorCrosshair(this.client.crosshairTarget)) {
                if (gameOptions.sprintKey.isPressed() && sprint.crosshair.enable) {
                    this.drawTexture(matrices, (this.scaledWidth) / 2 + sprint.crosshair.location.x, (this.scaledHeight) / 2 + sprint.crosshair.location.y, sprint.crosshair.icon.x, 0, 4, 4);
                }
                RenderSystem.setShaderTexture(0, MOD_ICONS);
                if (gameOptions.sneakKey.isPressed() && sneak.crosshair.enable) {
                    this.drawTexture(matrices, (this.scaledWidth) / 2 + sneak.crosshair.location.x, (this.scaledHeight) / 2 + sneak.crosshair.location.y, sneak.crosshair.icon.x, 4, 4, 4);
                }
            }
        }
        RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_TEXTURE);
    }

    @Shadow
    private boolean shouldRenderSpectatorCrosshair(HitResult crosshairTarget) {
        return false;
    }

    @Inject(method = "renderHotbar", at = @At("TAIL"))
    private void renderHotbar(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        RenderSystem.setShaderTexture(0, MOD_ICONS);

        if (this.client.options.sprintKey.isPressed() && sprint.hotbar.enable) {
            int x = (this.scaledWidth / 2) + sprint.hotbar.location.x;
            this.drawTexture(matrices, x, (this.scaledHeight) - sprint.hotbar.location.y, 0, 16, 16, 16);
        }

        if (this.client.options.sneakKey.isPressed() && sneak.hotbar.enable) {
            int x = (this.scaledWidth / 2) + sneak.hotbar.location.x;
            this.drawTexture(matrices, x, (this.scaledHeight) - sneak.hotbar.location.y, 16, 16, 16, 16);
        }
    }

    @Inject(method = "renderAutosaveIndicator", at = @At("TAIL"))
    private void renderAutosaveIndicator(MatrixStack matrices, CallbackInfo ci) {
        if (this.client.options.sprintKey.isPressed() && sprint.text.enable) {
            this.client.textRenderer.drawWithShadow(matrices, Text.translatable("hud.visible_toggle_sprint.sprint"), sprint.text.location.x, sprint.text.location.y, sprint.text.color);
        }
        if (this.client.options.sneakKey.isPressed() && sneak.text.enable) {
            this.client.textRenderer.drawWithShadow(matrices, Text.translatable("hud.visible_toggle_sprint.sneak"), sneak.text.location.x, sneak.text.location.y, sneak.text.color);
        }

    }

}
