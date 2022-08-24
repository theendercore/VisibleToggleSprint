package com.theendercore.visibletogglesprint.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.theendercore.visibletogglesprint.VisibleToggleSprint.MODID;

@Mixin(InGameHud.class)
public class CrosshairMixin extends DrawableHelper {
    private final Identifier modIcons = new Identifier(MODID, "textures/icons.png");
    @Final
    @Shadow
    private MinecraftClient client;
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    private boolean clean = false;

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void renderCrosshair(MatrixStack matrices, CallbackInfo ci) {

        RenderSystem.setShaderTexture(0, modIcons);
        this.drawTexture(matrices, (this.scaledWidth - 15) / 2 - 15, (this.scaledHeight - 15) / 2, 0, 0, 15, 15);

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
