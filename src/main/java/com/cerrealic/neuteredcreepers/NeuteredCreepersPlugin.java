package com.cerrealic.neuteredcreepers;

import com.cerrealic.cerspilib.Cerspi;
import com.cerrealic.cerspilib.CerspiPlugin;
import com.cerrealic.cerspilib.config.CerspiPluginConfig;
import com.cerrealic.neuteredcreepers.commands.CommandNeuteredCreepers;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "NeuteredCreepers", version = "0.1.0-SNAPSHOT")
@Description("Neuters your creepers! All the damage, none of the griefing.")
@Author(value = "cerrealic")
public class NeuteredCreepersPlugin extends CerspiPlugin {
	private NeuteredCreepersConfig config;

	public static NeuteredCreepersPlugin getInstance() {
		return NeuteredCreepersPlugin.getPlugin(NeuteredCreepersPlugin.class);
	}

	@Override
	public void onEnable() {
		super.onEnable();

		Cerspi.registerListeners(this, new GeneralEventListener(this));
		Cerspi.registerCommands(this, false, new CommandNeuteredCreepers(this));
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
}
