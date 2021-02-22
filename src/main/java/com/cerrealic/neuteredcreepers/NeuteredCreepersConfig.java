package com.cerrealic.neuteredcreepers;

import com.cerrealic.cerspilib.config.CerspiPluginConfig;
import com.cerrealic.cerspilib.config.ConfigNode;
import com.google.common.collect.Sets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class NeuteredCreepersConfig extends CerspiPluginConfig {
	private ConfigNode<Boolean>
			creeperGriefing,
			crystalGriefing,
			fireballGriefing,
			tntMinecartGriefing,
			tntGriefing,
			witherGriefing;

	public NeuteredCreepersConfig(JavaPlugin plugin, FileConfiguration fileConfiguration) {
		super(plugin, fileConfiguration);
	}

	@Override
	protected HashSet<ConfigNode> getDefinedNodes() {
		creeperGriefing = new ConfigNode<>("creeper-griefing", false);
		crystalGriefing = new ConfigNode<>("crystal-griefing", true);
		fireballGriefing = new ConfigNode<>("fireball-griefing", true);
		tntMinecartGriefing = new ConfigNode<>("tnt-minecart-griefing", true);
		tntGriefing = new ConfigNode<>("tnt-griefing", true);
		witherGriefing = new ConfigNode<>("wither-griefing", true);
		return Sets.newHashSet(creeperGriefing, crystalGriefing, fireballGriefing, tntMinecartGriefing, tntGriefing, witherGriefing);
	}

	public boolean getCreeperGriefing() {
		return creeperGriefing.getValue();
	}

	public void setCreeperGriefing(boolean creeperGriefing) {
		setNodeValue(this.creeperGriefing, creeperGriefing);
	}

	public boolean getCrystalGriefing() {
		return crystalGriefing.getValue();
	}

	public void setCrystalGriefing(boolean crystalGriefing) {
		setNodeValue(this.crystalGriefing, crystalGriefing);
	}

	public boolean getFireballGriefing() {
		return fireballGriefing.getValue();
	}

	public void setFireballGriefing(boolean fireballGriefing) {
		setNodeValue(this.fireballGriefing, fireballGriefing);
	}

	public boolean getTntMinecartGriefing() {
		return tntMinecartGriefing.getValue();
	}

	public void setTntMinecartGriefing(boolean tntMinecartGriefing) {
		setNodeValue(this.tntMinecartGriefing, tntMinecartGriefing);
	}

	public boolean getTntGriefing() {
		return tntGriefing.getValue();
	}

	public void setTntGriefing(boolean tntGriefing) {
		setNodeValue(this.tntGriefing, tntGriefing);
	}

	public boolean getWitherGriefing() {
		return witherGriefing.getValue();
	}

	public void setWitherGriefing(boolean witherGriefing) {
		setNodeValue(this.witherGriefing, witherGriefing);
	}
}
