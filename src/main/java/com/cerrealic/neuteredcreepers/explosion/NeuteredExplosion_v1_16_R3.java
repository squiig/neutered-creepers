package com.cerrealic.neuteredcreepers.explosion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.ObjectListIterator;
import org.bukkit.craftbukkit.v1_16_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import javax.annotation.Nullable;
import java.util.*;

public class NeuteredExplosion_v1_16_R3 extends Explosion implements INeuteredExplosion {
	private static final ExplosionDamageCalculator a = new ExplosionDamageCalculator();
	private final boolean b;
	private final Explosion.Effect effect;
	private final Random random = new Random();
	private final World world;
	private final double posX;
	private final double posY;
	private final double posZ;
	private final float size;
	private final ExplosionDamageCalculator l;
	private final List<BlockPosition> blocks = Lists.newArrayList();
	private final Map<EntityHuman, Vec3D> damagedPlayers = Maps.newHashMap();

	public NeuteredExplosion_v1_16_R3(World world, @Nullable Entity source, @Nullable DamageSource damageSource, double x, double y, double z, float force, boolean flag, Effect explosionEffect) {
		super(world, source, damageSource, null, x, y, z, force, flag, explosionEffect);
		this.world = world;
		this.size = (float) Math.max((double) force, 0.0D);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.b = flag;
		this.effect = explosionEffect;
		this.l = this.a(source);
	}

	private ExplosionDamageCalculator a(@Nullable Entity entity) {
		return (ExplosionDamageCalculator)(entity == null ? a : new ExplosionDamageCalculatorEntity(entity));
	}

	private static void a(ObjectArrayList<Pair<ItemStack, BlockPosition>> objectarraylist, ItemStack itemstack, BlockPosition blockposition) {
		if (!itemstack.isEmpty()) {
			int i = objectarraylist.size();

			for (int j = 0; j < i; ++j) {
				Pair<ItemStack, BlockPosition> pair = (Pair) objectarraylist.get(j);
				ItemStack itemstack1 = (ItemStack) pair.getFirst();
				if (EntityItem.a(itemstack1, itemstack)) {
					ItemStack itemstack2 = EntityItem.a(itemstack1, itemstack, 16);
					objectarraylist.set(j, Pair.of(itemstack2, (BlockPosition) pair.getSecond()));
					if (itemstack.isEmpty()) {
						return;
					}
				}
			}

			objectarraylist.add(Pair.of(itemstack, blockposition));
		}
	}

	public void explode() {
		a();
		a(true);
	}

	@Override
	public void a() {
		if (!(this.size < 0.1F)) {
			Set<BlockPosition> set = Sets.newHashSet();
			boolean flag = true;

			int i;
			int j;
			for(int k = 0; k < 16; ++k) {
				for(i = 0; i < 16; ++i) {
					for(j = 0; j < 16; ++j) {
						if (k == 0 || k == 15 || i == 0 || i == 15 || j == 0 || j == 15) {
							double d0 = (double)((float)k / 15.0F * 2.0F - 1.0F);
							double d1 = (double)((float)i / 15.0F * 2.0F - 1.0F);
							double d2 = (double)((float)j / 15.0F * 2.0F - 1.0F);
							double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
							d0 /= d3;
							d1 /= d3;
							d2 /= d3;
							float f = this.size * (0.7F + this.world.random.nextFloat() * 0.6F);
							double d4 = this.posX;
							double d5 = this.posY;
							double d6 = this.posZ;

							for(float var21 = 0.3F; f > 0.0F; f -= 0.22500001F) {
								BlockPosition blockposition = new BlockPosition(d4, d5, d6);
								IBlockData iblockdata = this.world.getType(blockposition);
								Fluid fluid = this.world.getFluid(blockposition);
								Optional<Float> optional = this.l.a(this, this.world, blockposition, iblockdata, fluid);
								if (optional.isPresent()) {
									f -= ((Float)optional.get() + 0.3F) * 0.3F;
								}

								if (f > 0.0F && this.l.a(this, this.world, blockposition, iblockdata, f) && blockposition.getY() < 256 && blockposition.getY() >= 0) {
									set.add(blockposition);
								}

								d4 += d0 * 0.30000001192092896D;
								d5 += d1 * 0.30000001192092896D;
								d6 += d2 * 0.30000001192092896D;
							}
						}
					}
				}
			}

			this.blocks.addAll(set);
			float f2 = this.size * 2.0F;
			i = MathHelper.floor(this.posX - (double)f2 - 1.0D);
			j = MathHelper.floor(this.posX + (double)f2 + 1.0D);
			int l = MathHelper.floor(this.posY - (double)f2 - 1.0D);
			int i1 = MathHelper.floor(this.posY + (double)f2 + 1.0D);
			int j1 = MathHelper.floor(this.posZ - (double)f2 - 1.0D);
			int k1 = MathHelper.floor(this.posZ + (double)f2 + 1.0D);
			List<Entity> list = this.world.getEntities(this.source, new AxisAlignedBB((double)i, (double)l, (double)j1, (double)j, (double)i1, (double)k1));
			Vec3D vec3d = new Vec3D(this.posX, this.posY, this.posZ);

			for(int l1 = 0; l1 < list.size(); ++l1) {
				Entity entity = (Entity)list.get(l1);
				if (!entity.ci()) {
					double d7 = (double)(MathHelper.sqrt(entity.e(vec3d)) / f2);
					if (d7 <= 1.0D) {
						double d8 = entity.locX() - this.posX;
						double d9 = (entity instanceof EntityTNTPrimed ? entity.locY() : entity.getHeadY()) - this.posY;
						double d10 = entity.locZ() - this.posZ;
						double d11 = (double)MathHelper.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
						if (d11 != 0.0D) {
							d8 /= d11;
							d9 /= d11;
							d10 /= d11;
							double d12 = (double)a(vec3d, entity);
							double d13 = (1.0D - d7) * d12;
							CraftEventFactory.entityDamage = this.source;
							entity.forceExplosionKnockback = false;
							boolean wasDamaged = entity.damageEntity(this.b(), (float)((int)((d13 * d13 + d13) / 2.0D * 7.0D * (double)f2 + 1.0D)));
							CraftEventFactory.entityDamage = null;
							if (wasDamaged || entity instanceof EntityTNTPrimed || entity instanceof EntityFallingBlock || entity.forceExplosionKnockback) {
								double d14 = d13;
								if (entity instanceof EntityLiving) {
									d14 = EnchantmentProtection.a((EntityLiving)entity, d13);
								}

								entity.setMot(entity.getMot().add(d8 * d14, d9 * d14, d10 * d14));
								if (entity instanceof EntityHuman) {
									EntityHuman entityhuman = (EntityHuman)entity;
									if (!entityhuman.isSpectator() && (!entityhuman.isCreative() || !entityhuman.abilities.isFlying)) {
										this.damagedPlayers.put(entityhuman, new Vec3D(d8 * d13, d9 * d13, d10 * d13));
									}
								}
							}
						}
					}
				}
			}

		}
	}

	@Override
	public void a(boolean flag) {
		if (this.world.isClientSide) {
			this.world.a(this.posX, this.posY, this.posZ, SoundEffects.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F, false);
		}

		boolean flag1 = this.effect != Explosion.Effect.NONE;
		if (flag) {
			if (this.size >= 2.0F && flag1) {
				this.world.addParticle(Particles.EXPLOSION_EMITTER, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
			} else {
				this.world.addParticle(Particles.EXPLOSION, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
			}
		}

		if (flag1) {
			ObjectArrayList<Pair<ItemStack, BlockPosition>> objectarraylist = new ObjectArrayList();
			Collections.shuffle(this.blocks, this.world.random);
			Iterator iterator = this.blocks.iterator();
			org.bukkit.World bworld = this.world.getWorld();
			org.bukkit.entity.Entity explode = this.source == null ? null : this.source.getBukkitEntity();
			Location location = new Location(bworld, this.posX, this.posY, this.posZ);
			List<org.bukkit.block.Block> blockList = Lists.newArrayList();

			for(int i1 = this.blocks.size() - 1; i1 >= 0; --i1) {
				BlockPosition cpos = (BlockPosition)this.blocks.get(i1);
				org.bukkit.block.Block bblock = bworld.getBlockAt(cpos.getX(), cpos.getY(), cpos.getZ());
				if (!bblock.getType().isAir()) {
					blockList.add(bblock);
				}
			}

			boolean cancelled;
			List bukkitBlocks;
			float yield;
			if (explode != null) {
				EntityExplodeEvent event = new EntityExplodeEvent(explode, location, blockList, this.effect == Explosion.Effect.DESTROY ? 1.0F / this.size : 1.0F);
				this.world.getServer().getPluginManager().callEvent(event);
				cancelled = event.isCancelled();
				bukkitBlocks = event.blockList();
				yield = event.getYield();
			} else {
				BlockExplodeEvent event = new BlockExplodeEvent(location.getBlock(), blockList, this.effect == Explosion.Effect.DESTROY ? 1.0F / this.size : 1.0F);
				this.world.getServer().getPluginManager().callEvent(event);
				cancelled = event.isCancelled();
				bukkitBlocks = event.blockList();
				yield = event.getYield();
			}

			this.blocks.clear();
			Iterator var13 = bukkitBlocks.iterator();

			while(var13.hasNext()) {
				org.bukkit.block.Block bblock = (org.bukkit.block.Block)var13.next();
				BlockPosition coords = new BlockPosition(bblock.getX(), bblock.getY(), bblock.getZ());
				this.blocks.add(coords);
			}

			if (cancelled) {
				this.wasCanceled = true;
				return;
			}

			iterator = this.blocks.iterator();

			label111:
			while(true) {
				BlockPosition blockposition;
				IBlockData iblockdata;
				net.minecraft.server.v1_16_R3.Block block;
				do {
					if (!iterator.hasNext()) {
						ObjectListIterator objectlistiterator = objectarraylist.iterator();

						while(objectlistiterator.hasNext()) {
							Pair<ItemStack, BlockPosition> pair = (Pair)objectlistiterator.next();
							net.minecraft.server.v1_16_R3.Block.a(this.world, (BlockPosition)pair.getSecond(), (ItemStack)pair.getFirst());
						}
						break label111;
					}

					blockposition = (BlockPosition)iterator.next();
					iblockdata = this.world.getType(blockposition);
					block = iblockdata.getBlock();
				} while(iblockdata.isAir());

				BlockPosition blockposition1 = blockposition.immutableCopy();
				this.world.getMethodProfiler().enter("explosion_blocks");
				if (block.a(this) && this.world instanceof WorldServer) {
					TileEntity tileentity = block.isTileEntity() ? this.world.getTileEntity(blockposition) : null;
					LootTableInfo.Builder loottableinfo_builder = (new LootTableInfo.Builder((WorldServer)this.world)).a(this.world.random).set(LootContextParameters.ORIGIN, Vec3D.a(blockposition)).set(LootContextParameters.TOOL, ItemStack.b).setOptional(LootContextParameters.BLOCK_ENTITY, tileentity).setOptional(LootContextParameters.THIS_ENTITY, this.source);
					if (this.effect == Explosion.Effect.DESTROY || yield < 1.0F) {
						loottableinfo_builder.set(LootContextParameters.EXPLOSION_RADIUS, 1.0F / yield);
					}

					iblockdata.a(loottableinfo_builder).forEach((itemstack) -> {
						a(objectarraylist, itemstack, blockposition1);
					});
				}

				this.world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
				block.wasExploded(this.world, blockposition, this);
				this.world.getMethodProfiler().exit();
			}
		}

		if (this.b) {
			Iterator iterator1 = this.blocks.iterator();

			while(iterator1.hasNext()) {
				BlockPosition blockposition2 = (BlockPosition)iterator1.next();
				if (this.random.nextInt(3) == 0 && this.world.getType(blockposition2).isAir() && this.world.getType(blockposition2.down()).i(this.world, blockposition2.down()) && !CraftEventFactory.callBlockIgniteEvent(this.world, blockposition2.getX(), blockposition2.getY(), blockposition2.getZ(), this).isCancelled()) {
					this.world.setTypeUpdate(blockposition2, BlockFireAbstract.a(this.world, blockposition2));
				}
			}
		}

	}
}
