package com.cerrealic.UNTITLED;

import org.bukkit.plugin.java.JavaPlugin;

public class TemplatePlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		if (!checkDependencies()) {
			return;
		}

		Context.config = this.getConfig();
		this.saveDefaultConfig();

//		Debug.enabled = this.getConfig().getBoolean("debug", false);
//		if (Debug.enabled) {
//			getLogger().info("Debug enabled.");
//		}

//		registerCommand();
	}

	boolean checkDependencies() {
		if (!isSpigotServer()) {
			getLogger().severe("You're probably running a CraftBukkit server. For this to plugin to work you need to switch to Spigot.");
			disablePlugin();
			return false;
		}

		return true;
	}

//	void registerCommand() {
//		PluginCommand command = this.getCommand(CommandSomething.LABEL);
//		if (command == null) {
//			getLogger().severe("Failed to register /something command!");
//			disablePlugin();
//			return;
//		}
//
//		CommandSomething exec = new CommandSomething();
//		command.setExecutor(exec);
//		command.setTabCompleter(exec);
//	}

	public void disablePlugin() {
		getServer().getPluginManager().disablePlugin(this);
	}

	private boolean isSpigotServer() {
		return getServer().getVersion().contains("Spigot");
	}
}
