package com.cerrealic.neuteredcreepers;

import com.cerrealic.neuteredcreepers.events.DebugToggleEvent;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftCreeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import java.util.Collection;

public class GeneralEventListener implements Listener {
	private final NeuteredCreepersPlugin neuteredCreepersPluginInstance;

	public GeneralEventListener(NeuteredCreepersPlugin neuteredCreepersPluginInstance) {
		this.neuteredCreepersPluginInstance = neuteredCreepersPluginInstance;
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (!event.getEntityType().equals(EntityType.CREEPER)) {
			return;
		}

		// Make every creeper a neutered creeper
		neuteredCreepersPluginInstance.registerCreeper(new NeuteredCreeper((CraftCreeper) event.getEntity()));
	}

	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		if (!event.getEntityType().equals(EntityType.CREEPER)) {
			return;
		}

		if (neuteredCreepersPluginInstance.isNeuteredCreeper((CraftCreeper) event.getEntity())) {
			event.setCancelled(true);
			NeuteredCreeper neuteredCreeper = neuteredCreepersPluginInstance.getNeuteredCreeper(event.getEntity().getEntityId());
			neuteredCreeper.explode();
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (!event.getEntityType().equals(EntityType.CREEPER)) {
			return;
		}

		if (neuteredCreepersPluginInstance.isNeuteredCreeper((CraftCreeper) event.getEntity())) {
			NeuteredCreeper neuteredCreeper = neuteredCreepersPluginInstance.getNeuteredCreeper(event.getEntity().getEntityId());
			neuteredCreeper.onDeath();
		}
	}

	@EventHandler
	public void onDebugToggle(DebugToggleEvent event) {
		Collection<NeuteredCreeper> neuteredCreepers = neuteredCreepersPluginInstance.getNeuteredCreepers().values();
		for (NeuteredCreeper neuteredCreeper : neuteredCreepers) {
			neuteredCreeper.getClientCreeper().setGlowing(event.isDebugEnabled());
		}
	}
}
