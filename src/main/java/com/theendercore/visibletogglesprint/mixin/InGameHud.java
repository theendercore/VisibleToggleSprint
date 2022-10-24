package com.theendercore.visibletogglesprint.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.theendercore.visibletogglesprint.lib.PlayerState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.BooleanUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.theendercore.visibletogglesprint.VisibleToggleSprint.*;

@Mixin(net.minecraft.client.gui.hud.InGameHud.class)
public abstract class InGameHud extends DrawableHelper {
    private final Identifier modIcons = new Identifier(MODID, "textures/gui/icons.png");
    PlayerState sprint = getConfig().sprint;
    PlayerState sneak = getConfig().sneak;
    @Final
    @Shadow
    private MinecraftClient client;
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;

    @Shadow
    private PlayerEntity getCameraPlayer() {
        return null;
    }

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void renderCrosshair(MatrixStack matrices, CallbackInfo ci) {
        RenderSystem.setShaderTexture(0, modIcons);

        if (this.client.options.sprintKey.isPressed() && sprint.crosshair.enable) {
            this.drawTexture(matrices, (this.scaledWidth) / 2 + sprint.crosshair.location.x, (this.scaledHeight) / 2 + sprint.crosshair.location.y, sprint.crosshair.icon.x, 0, 4, 4);
        }
        RenderSystem.setShaderTexture(0, modIcons);
        if (this.client.options.sneakKey.isPressed() && sneak.crosshair.enable) {
            this.drawTexture(matrices, (this.scaledWidth) / 2 + sneak.crosshair.location.x, (this.scaledHeight) / 2 + sneak.crosshair.location.y, sneak.crosshair.icon.x, 4, 4, 4);
        }
        RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_TEXTURE);
    }

    @Inject(method = "renderHotbar", at = @At("TAIL"))
    private void renderHotbar(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        assert this.getCameraPlayer() != null;
        PlayerEntity pl = this.getCameraPlayer();
        Arm arm = pl.getMainArm().getOpposite();
        int k = 28 * BooleanUtils.toInteger(!pl.getOffHandStack().isEmpty());
        RenderSystem.setShaderTexture(0, modIcons);

        if (this.client.options.sprintKey.isPressed() && sprint.hotbarEnabled) {
            int x = (this.scaledWidth) / 2 - 113 -k;
            if (arm == Arm.RIGHT) {
                x = (this.scaledWidth) / 2 + 97 + k;
            }
            this.drawTexture(matrices, x, (this.scaledHeight) - 18, 0, 16, 16, 16);

        }
        if (this.client.options.sneakKey.isPressed() && sneak.hotbarEnabled) {
            int l = 22 * BooleanUtils.toInteger(this.client.options.sprintKey.isPressed() && sprint.hotbarEnabled);
            int x = (this.scaledWidth) / 2 - 113 - l - k;
            if (arm == Arm.RIGHT) {
                x = (this.scaledWidth) / 2 + 97 + l + k;
            }
            this.drawTexture(matrices, x, (this.scaledHeight) - 18, 16, 16, 16, 16);
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
