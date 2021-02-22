package com.cerrealic.neuteredcreepers;

import org.bukkit.entity.EntityType;

public final class Util {
	public static boolean isExplodingEntity(EntityType entityType) {
		return entityType.equals(EntityType.CREEPER)
				|| entityType.equals(EntityType.ENDER_CRYSTAL)
				|| entityType.equals(EntityType.FIREBALL)
				|| entityType.equals(EntityType.MINECART_TNT)
				|| entityType.equals(EntityType.PRIMED_TNT)
				|| entityType.equals(EntityType.WITHER_SKULL);
	}
}
