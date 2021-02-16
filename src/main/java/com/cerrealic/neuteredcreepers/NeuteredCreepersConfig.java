package com.cerrealic.neuteredcreepers;

import com.cerrealic.cerspilib.config.CerspiPluginConfig;
import com.cerrealic.cerspilib.config.ConfigNode;
import com.google.common.collect.Sets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class NeuteredCreepersConfig extends CerspiPluginConfig {

	public NeuteredCreepersConfig(JavaPlugin plugin, FileConfiguration fileConfiguration) {
		super(plugin, fileConfiguration);
	}

	@Override
	protected HashSet<ConfigNode> getDefinedNodes() {
		return Sets.newHashSet();
	}
}
