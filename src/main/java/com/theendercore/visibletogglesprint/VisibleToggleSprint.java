package com.theendercore.visibletogglesprint;

import com.theendercore.visibletogglesprint.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisibleToggleSprint implements ClientModInitializer {
	public static final String MODID = "visible_toggle_sprint";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitializeClient() {
		ModConfig.getConfig().load();
		LOGGER.info("I am my planting roots.");
	}
}
