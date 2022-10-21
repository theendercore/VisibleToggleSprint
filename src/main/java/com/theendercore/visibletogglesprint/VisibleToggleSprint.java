package com.theendercore.visibletogglesprint;

import com.theendercore.visibletogglesprint.config.VisibleToggleSprintConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisibleToggleSprint implements ClientModInitializer {
	public static final String MODID = "visible_toggle_sprint";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitializeClient() {
		getConfig().load();
		LOGGER.info("I am my planting roots.");
	}
	public static VisibleToggleSprintConfig getConfig() {
		return VisibleToggleSprintConfig.INSTANCE;
	}
}
