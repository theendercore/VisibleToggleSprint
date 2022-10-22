package com.theendercore.visibletogglesprint.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.theendercore.visibletogglesprint.lib.Vec2i;
import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.EnumController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.theendercore.visibletogglesprint.VisibleToggleSprint.MODID;

public class VisibleToggleSprintConfig {
    public static final VisibleToggleSprintConfig INSTANCE = new VisibleToggleSprintConfig();
    public final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("visible_toggle_sprint.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public boolean crosshairSprint = true;
    public Vec2i crosshairSprintLocation = new Vec2i(-6,-6);
    public CrosshairIcons crosshairSprintIcon = CrosshairIcons.DEFAULT;
    public boolean crosshairSneak = true;
    public Vec2i crosshairSneakLocation = new Vec2i(1,1);
    public CrosshairIcons crosshairSneakIcon = CrosshairIcons.DEFAULT;
    public void save() {
        try {
            Files.deleteIfExists(configFile);
            JsonObject j = new JsonObject();
            j.addProperty("crosshair.sprint", crosshairSprint);
            j.add("crosshair.sprint.location", crosshairSprintLocation.toJSON());
            j.addProperty("crosshair.sprint.icon", crosshairSprintIcon.name);
            j.addProperty("crosshair.sneak", crosshairSneak);
            j.add("crosshair.sneak.location", crosshairSneakLocation.toJSON());
            j.addProperty("crosshair.sneak.icon", crosshairSneakIcon.name);

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

            if (json.has("crosshair.sprint")) crosshairSprint = json.getAsJsonPrimitive("crosshair.sprint").getAsBoolean();
            if (json.has("crosshair.sprint.location")) {
                crosshairSprintLocation.x = json.getAsJsonObject("crosshair.sprint.location").getAsJsonPrimitive("x").getAsInt();
                crosshairSprintLocation.y = json.getAsJsonObject("crosshair.sprint.location").getAsJsonPrimitive("y").getAsInt();
            }
            if(json.has("crosshair.sprint.icon"))  crosshairSprintIcon = CrosshairIcons.valueOf((json.getAsJsonPrimitive("crosshair.sprint.icon").getAsString()));
            if (json.has("crosshair.sneak")) crosshairSneak = json.getAsJsonPrimitive("crosshair.sneak").getAsBoolean();
            if (json.has("crosshair.sneak.location")) {
                crosshairSneakLocation.x = json.getAsJsonObject("crosshair.sneak.location").getAsJsonPrimitive("x").getAsInt();
                crosshairSneakLocation.y = json.getAsJsonObject("crosshair.sneak.location").getAsJsonPrimitive("y").getAsInt();
            }
            if(json.has("crosshair.sneak.icon"))  crosshairSneakIcon = CrosshairIcons.valueOf((json.getAsJsonPrimitive("crosshair.sneak.icon").getAsString()));
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
                            .name(Text.translatable("config."+MODID+".sprint.group.crosshair"))
                            .options(List.of(
                                    Option.createBuilder(boolean.class)
                                    .name(Text.translatable("config."+MODID+".sprint.crosshair.enable"))
                                    .binding(
                                            true,
                                            () -> crosshairSprint,
                                            value -> crosshairSprint = value
                                    )
                                    .controller(BooleanController::new)
                                    .build(),

                                Option.createBuilder(int.class)
                                    .name(Text.translatable("config."+MODID+".sprint.crosshair.location.x"))
                                    .binding(
                                            -6,
                                            () -> crosshairSprintLocation.x,
                                            value -> crosshairSprintLocation.x = value
                                    )
                                    .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                    .build(),

                                Option.createBuilder(int.class)
                                    .name(Text.translatable("config."+MODID+".sprint.crosshair.location.y"))
                                    .binding(
                                            -6,
                                            () -> crosshairSprintLocation.y,
                                            value -> crosshairSprintLocation.y = value
                                    )
                                    .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                    .build(),
                                Option.createBuilder(CrosshairIcons.class)
                                    .name(Text.translatable("config."+MODID+".sprint.crosshair.icon"))
                                    .binding(
                                            CrosshairIcons.DEFAULT,
                                            () -> this.crosshairSprintIcon,
                                            newValue -> this.crosshairSprintIcon = newValue
                                    )
                                    .controller(EnumController::new)
                                    .build()
                                    )
                            ).build())
                    .build()
                )
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config."+MODID+".sneak.tab"))
                        .group(OptionGroup.createBuilder()
                            .name(Text.translatable("config."+MODID+".sneak.group.crosshair"))
                                .options(List.of(
                            Option.createBuilder(boolean.class)
                                    .name(Text.translatable("config."+MODID+".sneak.crosshair.enable"))
                                    .binding(
                                            true,
                                            () -> crosshairSneak,
                                            value -> crosshairSneak = value
                                    )
                                    .controller(BooleanController::new)
                                    .build(),
                           Option.createBuilder(int.class)
                                    .name(Text.translatable("config."+MODID+".sneak.crosshair.location.x"))
                                    .binding(
                                            1,
                                            () -> crosshairSneakLocation.x,
                                            value -> crosshairSneakLocation.x = value
                                    )
                                    .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                    .build(),
                            Option.createBuilder(int.class)
                                    .name(Text.translatable("config."+MODID+".sneak.crosshair.location.y"))
                                    .binding(
                                            1,
                                            () -> crosshairSneakLocation.y,
                                            value -> crosshairSneakLocation.y = value
                                    )
                                    .controller(yacl -> new IntegerSliderController(yacl, -32, 32, 1))
                                    .build(),
                            Option.createBuilder(CrosshairIcons.class)
                                    .name(Text.translatable("config."+MODID+".sneak.crosshair.icon"))
                                    .binding(
                                            CrosshairIcons.DEFAULT,
                                            () -> this.crosshairSneakIcon,
                                            newValue -> this.crosshairSneakIcon = newValue
                                    )
                                    .controller(EnumController::new)
                                    .build()
                                ))
                            .build())
                        .build())
                .save(this::save)
                .build()
                .generateScreen(parent);
    }

    public enum CrosshairIcons{
        DEFAULT( 0, "DEFAULT"),
        MINIMAL_ONE(4,"MINIMAL_ONE") ,
        MINIMAL_TWO(8, "MINIMAL_TWO"),
        MINIMAL_THREE(12, "MINIMAL_THREE");
        public final int x;
        private final String name;

        CrosshairIcons(int x, String name){ this.x = x; this.name = name;};
    }
}