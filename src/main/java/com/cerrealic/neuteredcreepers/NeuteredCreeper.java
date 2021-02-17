package com.cerrealic.neuteredcreepers;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftCreeper;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;

public class NeuteredCreeper {
	private final NeuteredCreepersPlugin plugin;
	private final EntityCreeper serverCreeper;
	private final CraftCreeper clientCreeper;

	public NeuteredCreeper(CraftCreeper targetCreeper) {
		plugin = NeuteredCreepersPlugin.getPlugin(NeuteredCreepersPlugin.class);
		serverCreeper = targetCreeper.getHandle();
		clientCreeper = targetCreeper;

		if (plugin.getDebugger().isEnabled()) {
			clientCreeper.setGlowing(true);
		}
	}

	public void onDeath() {
		plugin.forgetCreeper(this);
	}

	public EntityCreeper getServerCreeper() {
		return serverCreeper;
	}

	public CraftCreeper getClientCreeper() {
		return clientCreeper;
	}

	/**
	 * Mostly copied from NMS creeper class.
	 */
	public void explode() {
		if (!serverCreeper.world.isClientSide) {
			CraftWorld world = serverCreeper.world.getWorld();
			world.setGameRuleValue(GameRules.MOB_GRIEFING.toString(), "false");

			serverCreeper.explode();

			// calling the death event ourselves
			EntityDeathEvent deathEvent = new EntityDeathEvent(clientCreeper, new ArrayList<>()); // TODO: will this interfere with normal drops?
			clientCreeper.getServer().getPluginManager().callEvent(deathEvent);
		}
	}
}
