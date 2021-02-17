package com.cerrealic.neuteredcreepers;

import com.cerrealic.cerspilib.Cerspi;
import com.cerrealic.cerspilib.CerspiPlugin;
import com.cerrealic.cerspilib.config.CerspiPluginConfig;
import com.cerrealic.neuteredcreepers.commands.CommandNeuteredCreepers;
import com.cerrealic.neuteredcreepers.explosion.INeuteredExplosion;
import com.cerrealic.neuteredcreepers.explosion.NeuteredExplosion_v1_16_R3;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.Explosion;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftCreeper;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Plugin(name = "NeuteredCreepers", version = "0.1.0-SNAPSHOT")
@Description("Neuters your creepers! All the damage, none of the griefing.")
@Author(value = "cerrealic")
public class NeuteredCreepersPlugin extends CerspiPlugin {
	private Map<Integer, NeuteredCreeper> neuteredCreepers;
	private NeuteredCreepersConfig config;

	public static NeuteredCreepersPlugin getInstance() {
		return NeuteredCreepersPlugin.getPlugin(NeuteredCreepersPlugin.class);
	}

	@Override
	public void onEnable() {
		super.onEnable();

		Cerspi.registerListeners(this, new GeneralEventListener(this));
		Cerspi.registerCommands(this, false, new CommandNeuteredCreepers(this));
		neuteredCreepers = new HashMap<>();
	}

	@Override
	protected CerspiPluginConfig initConfig() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		config = new NeuteredCreepersConfig(this, getConfig());
		return config;
	}

	public NeuteredCreepersConfig getNeuteredCreepersConfig() {
		return config;
	}

	/**
	 * Mostly copied from NMS world class.
	 */
	public static INeuteredExplosion createNeuteredExplosion(World world, @Nullable Entity source, double x, double y, double z, float force, Explosion.Effect explosionEffect) {
		INeuteredExplosion explosion = new NeuteredExplosion_v1_16_R3(world, source, null, x, y, z, force, false, explosionEffect);
		explosion.explode();
		return explosion;
	}

	public Map<Integer, NeuteredCreeper> getNeuteredCreepers() {
		return neuteredCreepers;
	}

	public void registerCreeper(NeuteredCreeper neuteredCreeper) {
		neuteredCreepers.put(neuteredCreeper.getCreeper().getEntityId(), neuteredCreeper);
	}

	public void forgetCreeper(NeuteredCreeper neuteredCreeper) {
		neuteredCreepers.remove(neuteredCreeper.getCreeper().getEntityId());
	}

	public boolean isNeuteredCreeper(CraftCreeper creeper) {
		return neuteredCreepers.containsKey(creeper.getEntityId());
	}

	public NeuteredCreeper getNeuteredCreeper(int entityId) {
		return neuteredCreepers.get(entityId);
	}
}
