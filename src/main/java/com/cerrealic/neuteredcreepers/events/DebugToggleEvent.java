package com.cerrealic.neuteredcreepers.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DebugToggleEvent extends Event {
	private static final HandlerList HANDLERS = new HandlerList();
	private boolean isDebugEnabled;

	public DebugToggleEvent(boolean isDebugEnabled) {
		this.isDebugEnabled = isDebugEnabled;
	}

	public boolean isDebugEnabled() {
		return isDebugEnabled;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
