package com.cerrealic.neuteredcreepers;

import com.cerrealic.cerspilib.Cerspi;
import com.cerrealic.cerspilib.CerspiPlugin;
import com.cerrealic.cerspilib.config.CerspiPluginConfig;

public class NeuteredCreepersPlugin extends CerspiPlugin {
	private NeuteredCreepersConfig config;

	public static NeuteredCreepersPlugin getInstance() {
		return NeuteredCreepersPlugin.getPlugin(NeuteredCreepersPlugin.class);
	}

	public NeuteredCreepersConfig getNeuteredCreepersConfig() {
		return config;
	}

	@Override
	protected CerspiPluginConfig initConfig() {
		config = new NeuteredCreepersConfig(this, getConfig());
		onConfigLoaded();
		return config;
	}

	private void onConfigLoaded() {
		Cerspi.registerListeners(this, new GeneralEventListener(this));
	}
}
