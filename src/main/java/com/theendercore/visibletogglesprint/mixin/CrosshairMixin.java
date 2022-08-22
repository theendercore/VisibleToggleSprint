package com.theendercore.visibletogglesprint.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class CrosshairMixin {
    @Final
    @Shadow
    private MinecraftClient client;
    private boolean clean = false;

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void renderCrosshair(MatrixStack matrices, CallbackInfo ci) {
        GameOptions gameOptions = this.client.options;
        if (gameOptions.sprintKey.isPressed()) {
            client.inGameHud.setOverlayMessage(Text.translatable("overlay.visible_toggle_sprint.text.on").formatted(Formatting.ITALIC), false);
            clean = true;
        }
        if (clean && !gameOptions.sprintKey.isPressed()) {
            client.inGameHud.setOverlayMessage(Text.translatable("overlay.visible_toggle_sprint.text.off").formatted(Formatting.RED, Formatting.ITALIC), false);
            clean = false;
        }
    }
}
