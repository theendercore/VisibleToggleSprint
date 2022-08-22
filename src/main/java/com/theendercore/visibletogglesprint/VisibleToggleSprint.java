package com.theendercore.visibletogglesprint;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisibleToggleSprint implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("visible_toggle_sprint");

	@Override
	public void onInitializeClient() {
		LOGGER.info("I am my planting roots.");

	}
}
