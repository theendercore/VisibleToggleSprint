package com.theendercore.visibletogglesprint.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.theendercore.visibletogglesprint.VisibleToggleSprint;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> VisibleToggleSprint.getConfig().makeScreen(parent);
    }
}