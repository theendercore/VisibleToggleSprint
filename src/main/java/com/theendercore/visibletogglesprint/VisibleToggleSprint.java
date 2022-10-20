package com.theendercore.visibletogglesprint;

import com.theendercore.visibletogglesprint.config.VTSConfig;
import com.theendercore.visibletogglesprint.config.ModConfigOld;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisibleToggleSprint implements ClientModInitializer {
	public static final String MODID = "visible_toggle_sprint";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitializeClient() {
		ModConfigOld.getConfig().load();
		getConfig().load();
		LOGGER.info("I am my planting roots.");
	}
	public static VTSConfig getConfig() {
		return VTSConfig.INSTANCE;
	}
}
