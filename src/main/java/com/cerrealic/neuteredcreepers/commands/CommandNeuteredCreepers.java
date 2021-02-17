package com.cerrealic.neuteredcreepers.commands;

import com.cerrealic.cerspilib.CerspiCommand;
import com.cerrealic.cerspilib.logging.Formatter;
import com.cerrealic.cerspilib.logging.Logger;
import com.cerrealic.neuteredcreepers.NeuteredCreepersConfig;
import com.cerrealic.neuteredcreepers.NeuteredCreepersPlugin;
import com.cerrealic.neuteredcreepers.events.DebugToggleEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.permission.Permissions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Commands(
		@Command(
				name = "neuteredcreepers",
				desc = "General NeuteredCreepers command.",
				usage = "/<command> debug",
				aliases = "nc",
				permission = "neuteredcreepers.command"
		)
)
@Permissions(
		@Permission(
				name = "neuteredcreepers.command",
				desc = "Allows use of the /neuteredcreepers command.",
				defaultValue = PermissionDefault.OP
		)
)
public class CommandNeuteredCreepers extends CerspiCommand {
	private static final String LABEL = "neuteredcreepers";
	private final NeuteredCreepersPlugin plugin;
	private final Logger logger;
	private static final String OPT_DEBUG = "debug";

	public CommandNeuteredCreepers(NeuteredCreepersPlugin neuteredCreepersPluginInstance) {
		this.plugin = neuteredCreepersPluginInstance;
		this.logger = neuteredCreepersPluginInstance.getCerspiLogger();
	}

	@Override
	public String getLabel() {
		return LABEL;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		if (args.length <= 0) {
			return false;
		}

		NeuteredCreepersConfig config = plugin.getNeuteredCreepersConfig();

		switch (args[0].toLowerCase()) {
			case OPT_DEBUG:
				plugin.setDebugMode(!plugin.getDebugger().isEnabled());
				DebugToggleEvent debugToggleEvent = new DebugToggleEvent(plugin.getDebugger().isEnabled());
				Bukkit.getPluginManager().callEvent(debugToggleEvent);
				return true;
		}

		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
		List<String> argNames = Collections.singletonList(OPT_DEBUG);

		if (args.length > 1) {
			return null;
		}

		// return unfiltered
		if (args[0].isEmpty()) {
			return argNames;
		}

		// filter based on current input
		String[] result = argNames.stream()
				.filter((name) -> name.startsWith(args[0]))
				.toArray(String[]::new);

		return Arrays.asList(result);
	}

	private void alertCurrentValue(String key, Object value) {
		logger.log(
				new Formatter("Current value of %s: " + value)
						.format(key)
						.stylizeInfo().toString(),
				false);
	}
}
