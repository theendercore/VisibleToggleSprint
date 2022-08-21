package com.theenercore.visibletogglesprint;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisibleToggleSprint implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("visible-toggle-sprint");

	@Override
	public void onInitialize() {
//		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
//			dispatcher.register(CommandManager.literal("foo").executes(context -> {
//				PlayerEntity player = context.getSource().getPlayer();
//				if (player.isSprinting()){
//					player.sendMessage(Text.literal("Sprinting"), true);
//				}
//				return 1;
//			}));
//		});
	}
}
