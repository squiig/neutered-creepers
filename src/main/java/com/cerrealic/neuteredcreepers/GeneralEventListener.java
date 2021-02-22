package com.cerrealic.neuteredcreepers;

import com.cerrealic.neuteredcreepers.events.DebugToggleEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class GeneralEventListener implements Listener {
	private final NeuteredCreepersPlugin plugin;

	public GeneralEventListener(NeuteredCreepersPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (!Util.isExplodingEntity(event.getEntityType())) {
			return;
		}

		event.blockList().clear();
	}

	@EventHandler
	public void onDebugToggle(DebugToggleEvent event) {
	}
}
