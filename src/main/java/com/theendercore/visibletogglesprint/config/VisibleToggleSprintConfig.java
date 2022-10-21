package com.theendercore.visibletogglesprint.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.theendercore.visibletogglesprint.lib.Vec2i;
import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.gui.controllers.TickBoxController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.theendercore.visibletogglesprint.VisibleToggleSprint.MODID;

public class VisibleToggleSprintConfig {
    public static final VisibleToggleSprintConfig INSTANCE = new VisibleToggleSprintConfig();
    public final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("visible_toggle_sprint.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Vec2i sprintLocation = new Vec2i(-6,-6);
    public Vec2i sneakLocation = new Vec2i(1,1);
    public boolean sprint = true;
    public boolean sneak = true;

    public void save() {
        try {
            Files.deleteIfExists(configFile);
            JsonObject j = new JsonObject();
            j.addProperty("sprint", sprint);
            j.add("sprint.location", sprintLocation.toJSON());
            j.addProperty("sneak", sneak);
            j.add("sneak.location", sneakLocation.toJSON());

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

            if (json.has("sprint")) sprint = json.getAsJsonPrimitive("sprint").getAsBoolean();
            if (json.has("sprint.location")) {
                sprintLocation.x = json.getAsJsonObject("sprint.location").getAsJsonPrimitive("x").getAsInt();
                sprintLocation.y = json.getAsJsonObject("sprint.location").getAsJsonPrimitive("y").getAsInt();
            }
            if (json.has("sneak")) sneak = json.getAsJsonPrimitive("sneak").getAsBoolean();
            if (json.has("sneak.location")) {
                sneakLocation.x = json.getAsJsonObject("sneak.location").getAsJsonPrimitive("x").getAsInt();
                sneakLocation.y = json.getAsJsonObject("sneak.location").getAsJsonPrimitive("y").getAsInt();
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
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("config."+MODID+".sprint.enable"))
                                .binding(
                                        true,
                                        () -> sprint,
                                        value -> sprint = value
                                )
                                .controller(TickBoxController::new)
                                .build())

                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("config."+MODID+".sprint.location.x"))
                                .binding(
                                        -6,
                                        () -> sprintLocation.x,
                                        value -> sprintLocation.x = value
                                )
                                .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("config."+MODID+".sprint.location.y"))
                                .binding(
                                        -6,
                                        () -> sprintLocation.y,
                                        value -> sprintLocation.y = value
                                )
                                .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config."+MODID+".sneak.tab"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("config."+MODID+".sneak.enable"))
                                .binding(
                                        true,
                                        () -> sneak,
                                        value -> sneak = value
                                )
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("config."+MODID+".sneak.location.x"))
                                .binding(
                                        1,
                                        () -> sneakLocation.x,
                                        value -> sneakLocation.x = value
                                )
                                .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("config."+MODID+".sneak.location.y"))
                                .binding(
                                        1,
                                        () -> sneakLocation.y,
                                        value -> sneakLocation.y = value
                                )
                                .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                .build())
                        .build())
                .save(this::save)
                .build()
                .generateScreen(parent);
    }
}