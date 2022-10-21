package com.theendercore.visibletogglesprint.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.theendercore.visibletogglesprint.config.VisibleToggleSprintConfig;
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
import static com.theendercore.visibletogglesprint.VisibleToggleSprint.getConfig;

@Mixin(InGameHud.class)
public abstract class CrosshairMixin extends DrawableHelper {
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
        VisibleToggleSprintConfig c = getConfig();
        RenderSystem.setShaderTexture(0, modIcons);

        if (this.client.options.sprintKey.isPressed() && c.sprint) {
            this.drawTexture(matrices, (this.scaledWidth) / 2 + c.sprintLocation.x, (this.scaledHeight) / 2 + c.sprintLocation.x, 0, 0, 4, 4);
        }
        RenderSystem.setShaderTexture(0, modIcons);
        if (this.client.options.sneakKey.isPressed() && c.sneak) {
            this.drawTexture(matrices, (this.scaledWidth) / 2 + c.sneakLocation.x, (this.scaledHeight) / 2 + c.sneakLocation.y, 4, 0, 4, 4);
        }
        RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_TEXTURE);
    }
}
//            this.client.textRenderer.drawWithShadow(matrices, "pp", 100,100 , 100 );
