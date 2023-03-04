package com.theendercore.visibletogglesprint.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.theendercore.visibletogglesprint.lib.*;
import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.ColorController;
import dev.isxander.yacl.gui.controllers.cycling.EnumController;
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
    public PlayerState sprint = new PlayerState(true, new Vec2i(-6), CrosshairIcons.DEFAULT, false, new Vec2i(125, 18), false, new Vec2i(10), -1);
    public PlayerState sneak = new PlayerState(true, new Vec2i(1), CrosshairIcons.DEFAULT, false, new Vec2i(147, 18), false, new Vec2i(10, 30), -1);

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
                .title(Text.translatable("config." + MODID + ".title"))
                .category(genOptions(sprint, "sprint", new Vec2i(-6, 10), 125))
                .category(genOptions(sneak, "sneak", new Vec2i(1, 30), 150))
                .save(this::save)
                .build()
                .generateScreen(parent);
    }

    private ConfigCategory genOptions(PlayerState ps, String name, Vec2i defVal, int defVal2) {
        return ConfigCategory.createBuilder()
                .name(Text.translatable("config." + MODID + "." + name + ".tab"))
                .group(OptionGroup.createBuilder()
                        .name(Text.translatable("config." + MODID + ".group.crosshair"))
                        .options(List.of(
                                getBooleanOption(ps.crosshair, true),
                                Option.createBuilder(int.class)
                                        .name(Text.translatable("config." + MODID + ".location.x"))
                                        .binding(
                                                defVal.x,
                                                () -> ps.crosshair.location.x,
                                                value -> ps.crosshair.location.x = value
                                        )
                                        .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                        .build(),
                                Option.createBuilder(int.class)
                                        .name(Text.translatable("config." + MODID + ".location.y"))
                                        .binding(
                                                defVal.x,
                                                () -> ps.crosshair.location.y,
                                                value -> ps.crosshair.location.y = value
                                        )
                                        .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                        .build(),
                                Option.createBuilder(CrosshairIcons.class)
                                        .name(Text.translatable("config." + MODID + ".icon"))
                                        .binding(
                                                CrosshairIcons.DEFAULT,
                                                () -> ps.crosshair.icon,
                                                newValue -> ps.crosshair.icon = newValue
                                        )
                                        .controller(EnumController::new)
                                        .build()
                        ))
                        .build())
                .group(OptionGroup.createBuilder()
                        .name(Text.translatable("config." + MODID + ".group.hotbar"))
                        .options(
                                List.of(
                                        getBooleanOption(ps.hotbar, false),
                                        Option.createBuilder(int.class)
                                                .name(Text.translatable("config." + MODID + ".location.x"))
                                                .binding(
                                                        defVal2,
                                                        () -> ps.hotbar.location.x,
                                                        value -> ps.hotbar.location.x = value
                                                )
                                                .controller(yacl -> new IntegerSliderController(yacl, -250, 250, 5))
                                                .build(),

                                        Option.createBuilder(int.class)
                                                .name(Text.translatable("config." + MODID + ".location.y"))
                                                .binding(
                                                        18,
                                                        () -> ps.hotbar.location.y,
                                                        value -> ps.hotbar.location.y = value
                                                )
                                                .controller(yacl -> new IntegerSliderController(yacl, 0, 400, 2))
                                                .build()
                                )

                        )
                        .build())
                .group(OptionGroup.createBuilder()
                        .name(Text.translatable("config." + MODID + ".group.text"))
                        .options(
                                List.of(
                                        getBooleanOption(ps.text, false),
                                        Option.createBuilder(int.class)
                                                .name(Text.translatable("config." + MODID + ".location.x"))
                                                .binding(
                                                        10,
                                                        () -> ps.text.location.x,
                                                        value -> ps.text.location.x = value
                                                )
                                                .controller(yacl -> new IntegerSliderController(yacl, 0, 1920, 10))
                                                .build(),

                                        Option.createBuilder(int.class)
                                                .name(Text.translatable("config." + MODID + ".location.y"))
                                                .binding(
                                                        defVal.y,
                                                        () -> ps.text.location.y,
                                                        value -> ps.text.location.y = value
                                                )
                                                .controller(yacl -> new IntegerSliderController(yacl, 0, 1080, 10))
                                                .build(),
                                        Option.createBuilder(Color.class)
                                                .name(Text.translatable("config." + MODID + ".color"))
                                                .binding(
                                                        Color.WHITE,
                                                        () -> new Color(ps.text.color),
                                                        value -> ps.text.color = value.getRGB()
                                                )
                                                .controller(ColorController::new)
                                                .build()
                                )
                        )
                        .build())
                .build();
    }

    private <T extends IState> Option<Boolean> getBooleanOption(T inVal, boolean defVal) {
        return Option.createBuilder(boolean.class)
                .name(Text.translatable("config." + MODID + ".enable"))
                .binding(
                        defVal,
                        () -> inVal.enable,
                        value -> inVal.enable = value
                )
                .controller(BooleanController::new)
                .build();
    }
}