package com.theendercore.visibletogglesprint.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.theendercore.visibletogglesprint.lib.*;
import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.ColorController;
import dev.isxander.yacl.gui.controllers.EnumController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.theendercore.visibletogglesprint.VisibleToggleSprint.*;

public class VisibleToggleSprintConfig {

    public static final VisibleToggleSprintConfig INSTANCE = new VisibleToggleSprintConfig();
    public final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("visible_toggle_sprint.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public PlayerState sprint =  new PlayerState(true, new Vec2i(-6,-6), CrosshairIcons.DEFAULT, false,false, new Vec2i(10, 10), -1);
    public PlayerState sneak =  new PlayerState(true, new Vec2i(1,1), CrosshairIcons.DEFAULT, false, false, new Vec2i(10, 30), -1);
    public void save() {
        try {
            Files.deleteIfExists(configFile);

            JsonObject j = new JsonObject();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            j.addProperty("Version", ConfigVersion);
            j.add("sprint", gson.toJsonTree(sprint));
            j.add("sneak", gson.toJsonTree(sneak));

            Files.writeString(configFile, gson.toJson(j));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (Files.notExists(configFile)) {
                save();
                return;
            }

            JsonObject json = gson.fromJson(Files.readString(configFile), JsonObject.class);

            if (json.getAsJsonPrimitive("Version") != null && json.getAsJsonPrimitive("Version").getAsInt() == ConfigVersion) {
                if (json.has("sprint")) sprint = gson.fromJson(json.getAsJsonObject("sprint"), PlayerState.class);
                if (json.has("sneak")) sneak = gson.fromJson(json.getAsJsonObject("sneak"), PlayerState.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Screen makeScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("config."+MODID+".title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config."+MODID+".sprint.tab"))
                        .group(OptionGroup.createBuilder()
                            .name(Text.translatable("config."+MODID+".group.crosshair"))
                            .options(List.of(
                                    Option.createBuilder(boolean.class)
                                    .name(Text.translatable("config."+MODID+".enable"))
                                    .binding(
                                            true,
                                            () -> sprint.crosshair.enable,
                                            value -> sprint.crosshair.enable = value
                                    )
                                    .controller(BooleanController::new)
                                    .build(),

                                Option.createBuilder(int.class)
                                    .name(Text.translatable("config."+MODID+".location.x"))
                                    .binding(
                                            -6,
                                            () -> sprint.crosshair.location.x,
                                            value -> sprint.crosshair.location.x = value
                                    )
                                    .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                    .build(),

                                Option.createBuilder(int.class)
                                    .name(Text.translatable("config."+MODID+".location.y"))
                                    .binding(
                                            -6,
                                            () -> sprint.crosshair.location.y,
                                            value -> sprint.crosshair.location.y = value
                                    )
                                    .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                    .build(),
                                Option.createBuilder(CrosshairIcons.class)
                                    .name(Text.translatable("config."+MODID+".icon"))
                                    .binding(
                                            CrosshairIcons.DEFAULT,
                                            () -> sprint.crosshair.icon,
                                            newValue -> sprint.crosshair.icon = newValue
                                    )
                                    .controller(EnumController::new)
                                    .build()
                                    )
                            ).build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config."+MODID+".group.hotbar"))
                                .option(Option.createBuilder(boolean.class)
                                                .name(Text.translatable("config."+MODID+".enable"))
                                                .binding(
                                                        false,
                                                        () -> sprint.hotbarEnabled,
                                                        value -> sprint.hotbarEnabled = value
                                                )
                                                .controller(BooleanController::new)
                                                .build()
                                )
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config."+MODID+".group.text")) //TEXT -------------------------------------------------------------------
                                .options(
                                        List.of(
                                                Option.createBuilder(boolean.class)
                                                        .name(Text.translatable("config."+MODID+".enable"))
                                                        .binding(
                                                                false,
                                                                () -> sprint.text.enable,
                                                                value -> sprint.text.enable = value
                                                        )
                                                        .controller(BooleanController::new)
                                                        .build(),
                                                Option.createBuilder(int.class)
                                                        .name(Text.translatable("config."+MODID+".location.x"))
                                                        .binding(
                                                                10,
                                                                () -> sprint.text.location.x,
                                                                value -> sprint.text.location.x = value
                                                        )
                                                        .controller(yacl -> new IntegerSliderController(yacl, 0, 1920, 10))
                                                        .build(),

                                                Option.createBuilder(int.class)
                                                        .name(Text.translatable("config."+MODID+".location.y"))
                                                        .binding(
                                                                10,
                                                                () -> sprint.text.location.y,
                                                                value -> sprint.text.location.y = value
                                                        )
                                                        .controller(yacl -> new IntegerSliderController(yacl, 0, 1080, 10))
                                                        .build(),
                                                Option.createBuilder(Color.class)
                                                        .name(Text.translatable("config."+MODID+".color"))
                                                        .binding(
                                                                Color.WHITE,
                                                                () -> new Color(sprint.text.color),
                                                                value -> sprint.text.color = value.getRGB()
                                                        )
                                                        .controller(ColorController::new)
                                                        .build()
                                        )
                                )
                                .build())
                    .build()
                )
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config."+MODID+".sneak.tab"))
                        .group(OptionGroup.createBuilder()
                            .name(Text.translatable("config."+MODID+".group.crosshair"))
                                .options(List.of(
                            Option.createBuilder(boolean.class)
                                    .name(Text.translatable("config."+MODID+".enable"))
                                    .binding(
                                            true,
                                            () -> sneak.crosshair.enable,
                                            value -> sneak.crosshair.enable = value
                                    )
                                    .controller(BooleanController::new)
                                    .build(),
                           Option.createBuilder(int.class)
                                    .name(Text.translatable("config."+MODID+".location.x"))
                                    .binding(
                                            1,
                                            () -> sneak.crosshair.location.x,
                                            value -> sneak.crosshair.location.x = value
                                    )
                                    .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                    .build(),
                            Option.createBuilder(int.class)
                                    .name(Text.translatable("config."+MODID+".location.y"))
                                    .binding(
                                            1,
                                            () -> sneak.crosshair.location.y,
                                            value -> sneak.crosshair.location.y = value
                                    )
                                    .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                    .build(),
                            Option.createBuilder(CrosshairIcons.class)
                                    .name(Text.translatable("config."+MODID+".icon"))
                                    .binding(
                                            CrosshairIcons.DEFAULT,
                                            () -> sneak.crosshair.icon,
                                            newValue -> sneak.crosshair.icon = newValue
                                    )
                                    .controller(EnumController::new)
                                    .build()
                                ))
                            .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config."+MODID+".group.hotbar"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("config."+MODID+".enable"))
                                        .binding(
                                                false,
                                                () -> sneak.hotbarEnabled,
                                                value -> sneak.hotbarEnabled = value
                                        )
                                        .controller(BooleanController::new)
                                        .build()
                                )
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config."+MODID+".group.text")) //TEXT -------------------------------------------------------------------
                                .options(
                                        List.of(
                                            Option.createBuilder(boolean.class)
                                                    .name(Text.translatable("config."+MODID+".enable"))
                                                    .binding(
                                                            false,
                                                            () -> sneak.text.enable,
                                                            value -> sneak.text.enable = value
                                                    )
                                                    .controller(BooleanController::new)
                                                    .build(),
                                            Option.createBuilder(int.class)
                                                    .name(Text.translatable("config."+MODID+".location.x"))
                                                    .binding(
                                                            10,
                                                            () -> sneak.text.location.x,
                                                            value -> sneak.text.location.x = value
                                                    )
                                                    .controller(yacl -> new IntegerSliderController(yacl, 0, 1920, 10))
                                                    .build(),

                                            Option.createBuilder(int.class)
                                                    .name(Text.translatable("config."+MODID+".location.y"))
                                                    .binding(
                                                            20,
                                                            () -> sneak.text.location.y,
                                                            value -> sneak.text.location.y = value
                                                    )
                                                    .controller(yacl -> new IntegerSliderController(yacl, 0, 1080, 10))
                                                    .build(),
                                                Option.createBuilder(Color.class)
                                                        .name(Text.translatable("config."+MODID+".color"))
                                                        .binding(
                                                                Color.WHITE,
                                                                () -> new Color(sneak.text.color),
                                                                value -> sneak.text.color = value.getRGB()
                                                        )
                                                        .controller(ColorController::new)
                                                        .build()
                                        )
                                )
                                .build())
                        .build())
                .save(this::save)
                .build()
                .generateScreen(parent);
    }
}