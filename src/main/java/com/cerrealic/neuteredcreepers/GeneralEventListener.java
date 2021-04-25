package com.cerrealic.neuteredcreepers;

import com.cerrealic.neuteredcreepers.events.DebugToggleEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class GeneralEventListener implements Listener {
	private final NeuteredCreepersConfig config;

	public GeneralEventListener(NeuteredCreepersPlugin plugin) {
		this.config = plugin.getNeuteredCreepersConfig();
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (!Util.isExplodingEntity(event.getEntityType())) {
			return;
		}

		boolean allowGrief = false;
		switch (event.getEntityType()) {
			case CREEPER:
				allowGrief = config.getCreeperGriefing();
				break;
			case ENDER_CRYSTAL:
				allowGrief = config.getCrystalGriefing();
				break;
			case FIREBALL:
				allowGrief = config.getFireballGriefing();
				break;
			case MINECART_TNT:
				allowGrief = config.getTntMinecartGriefing();
				break;
			case PRIMED_TNT:
				allowGrief = config.getTntGriefing();
				break;
			case WITHER_SKULL:
				allowGrief = config.getWitherGriefing();
				break;
			default:
				allowGrief = true;
				break;
		}

		if (!allowGrief)
			event.blockList().clear();
	}

	@EventHandler
	public void onDebugToggle(DebugToggleEvent event) {
	}
}
