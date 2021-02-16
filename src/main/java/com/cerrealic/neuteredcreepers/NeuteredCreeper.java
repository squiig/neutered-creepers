package com.cerrealic.neuteredcreepers;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftCreeper;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class NeuteredCreeper {
	private final NeuteredCreepersPlugin neuteredCreepersPlugin;
	private final EntityCreeper nmsCreeper;
	private final CraftCreeper creeper;
	private final double explosionRadius;
	private final Random random;

	public NeuteredCreeper(CraftCreeper targetCreeper) {
		neuteredCreepersPlugin = NeuteredCreepersPlugin.getPlugin(NeuteredCreepersPlugin.class);
		random = new Random();
		nmsCreeper = targetCreeper.getHandle();
		creeper = targetCreeper;
		explosionRadius = targetCreeper.getExplosionRadius();

		if (neuteredCreepersPlugin.getDebugger().isEnabled()) {
			creeper.setGlowing(true);
		}
	}

	public void onDeath() {
		neuteredCreepersPlugin.forgetCreeper(this);
	}

	public CraftCreeper getCreeper() {
		return creeper;
	}

	public double getExplosionRadius() {
		return explosionRadius;
	}

	/**
	 * Mostly copied from NMS creeper class.
	 */
	public void explode() {
		if (!nmsCreeper.world.isClientSide) {

			// this is the holy bit of course
			Explosion.Effect effect = Explosion.Effect.NONE;

			float f = nmsCreeper.isPowered() ? 2.0F : 1.0F;

//			creeper.killed = true;

			NeuteredCreepersPlugin.createNeuteredExplosion(nmsCreeper.getWorld(), nmsCreeper, nmsCreeper.locX(), nmsCreeper.locY(), nmsCreeper.locZ(), (float) explosionRadius * f, effect);

			nmsCreeper.die();

			// calling the death event ourselves
			EntityDeathEvent deathEvent = new EntityDeathEvent(creeper, null);
			creeper.getServer().getPluginManager().callEvent(deathEvent);

			createEffectCloud();
		}
	}

	/**
	 * Mostly copied from NMS creeper class.
	 */
	public void createEffectCloud() {
		Collection<MobEffect> collection = nmsCreeper.getEffects();
		if (!collection.isEmpty()) {
			EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(nmsCreeper.world, nmsCreeper.locX(), nmsCreeper.locY(), nmsCreeper.locZ());
			entityareaeffectcloud.setSource(nmsCreeper);
			entityareaeffectcloud.setRadius(2.5F);
			entityareaeffectcloud.setRadiusOnUse(-0.5F);
			entityareaeffectcloud.setWaitTime(10);
			entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
			entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float) entityareaeffectcloud.getDuration());
			Iterator iterator = collection.iterator();

			while (iterator.hasNext()) {
				MobEffect mobeffect = (MobEffect) iterator.next();
				entityareaeffectcloud.addEffect(new MobEffect(mobeffect));
			}

			nmsCreeper.world.addEntity(entityareaeffectcloud, CreatureSpawnEvent.SpawnReason.EXPLOSION);
		}
	}
}
