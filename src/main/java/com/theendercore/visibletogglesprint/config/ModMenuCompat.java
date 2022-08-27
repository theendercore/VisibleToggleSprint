package com.theendercore.visibletogglesprint.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static com.theendercore.visibletogglesprint.VisibleToggleSprint.MODID;

@Environment(EnvType.CLIENT)
public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) screen -> {
            ConfigBuilder builder = ConfigBuilder.create();
            builder.setTitle(Text.translatable("config."+MODID+".title"));
            builder.setSavingRunnable(() -> ModConfig.getConfig().save());

            ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();
            ModConfig config = ModConfig.getConfig();

            ConfigCategory customizationCategory = builder.getOrCreateCategory(Text.translatable("config."+MODID+".category.customization"));

            customizationCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config."+MODID+".customization.sprint_enable"), config.getSprintEnabled())
                    .setSaveConsumer((config::setSprintEnabled))
                    .setDefaultValue(ModConfig.DEFAULT_SPRINT_ENABLED)
                    .build());
            customizationCategory.addEntry(entryBuilder
                    .startIntField(Text.translatable("config."+MODID+".customization.sprint_location_x"), config.getSprintLocationX())
                    .setSaveConsumer((config::setSprintLocationX))
                    .setDefaultValue(ModConfig.DEFAULT_SPRINT_LOCATION)
                    .build());
            customizationCategory.addEntry(entryBuilder
                    .startIntField(Text.translatable("config."+MODID+".customization.sprint_location_y"), config.getSprintLocationY())
                    .setSaveConsumer((config::setSprintLocationY))
                    .setDefaultValue(ModConfig.DEFAULT_SPRINT_LOCATION)
                    .build());
            customizationCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config."+MODID+".customization.sneak_enable"), config.getSneakEnabled())
                    .setSaveConsumer((config::setSneakEnabled))
                    .setDefaultValue(ModConfig.DEFAULT_SNEAK_ENABLED)
                    .build());
            customizationCategory.addEntry(entryBuilder
                    .startIntField(Text.translatable("config."+MODID+".customization.sneak_location_x"), config.getSneakLocationX())
                    .setSaveConsumer((config::setSneakLocationX))
                    .setDefaultValue(ModConfig.DEFAULT_SNEAK_LOCATION)
                    .build());
            customizationCategory.addEntry(entryBuilder
                    .startIntField(Text.translatable("config."+MODID+".customization.sneak_location_y"), config.getSneakLocationY())
                    .setSaveConsumer((config::setSneakLocationY))
                    .setDefaultValue(ModConfig.DEFAULT_SNEAK_LOCATION)
                    .build());
            return builder.build();
        };
    }
}
