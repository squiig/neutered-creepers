package com.cerrealic.neuteredcreepers;

import com.cerrealic.cerspilib.Cerspi;
import com.cerrealic.cerspilib.CerspiPlugin;
import com.cerrealic.cerspilib.config.CerspiPluginConfig;

public class NeuteredCreepersPlugin extends CerspiPlugin {
	public final static int RESOURCE_ID = 89820;
	private NeuteredCreepersConfig config;

	public static NeuteredCreepersPlugin getInstance() {
		return NeuteredCreepersPlugin.getPlugin(NeuteredCreepersPlugin.class);
	}

	@Override
	public Integer getResourceId() {
		return RESOURCE_ID;
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
