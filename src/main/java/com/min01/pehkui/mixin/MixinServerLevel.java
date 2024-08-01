package com.min01.pehkui.mixin;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.pehkui.EventHandlerForge;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level
{
	protected MixinServerLevel(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, Supplier<ProfilerFiller> p_270692_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) 
	{
		super(p_270739_, p_270683_, p_270200_, p_270240_, p_270692_, p_270904_, p_270470_, p_270248_, p_270466_);
	}

	@Inject(at = @At("HEAD"), method = "addFreshEntity")
	private void addFreshEntity(Entity p_8837_, CallbackInfoReturnable<Boolean> ci)
	{
		MySecurityManager manager = new MySecurityManager();
		Entity entity1 = null;
		Entity entity2 = null;
		Class<?>[] ctx = manager.getContext();
		int i = 0;
		int i2 = 0;
		do
		{
			entity1 = ServerLevel.class.cast(this).getEntity(EventHandlerForge.ENTITY_MAP.get(ctx[i].hashCode()));
			i++;
		}
		while(entity1 == null && i < ctx.length);
		do
		{
			entity2 = ServerLevel.class.cast(this).getEntity(EventHandlerForge.ENTITY_MAP2.get(ctx[i2].hashCode()));
			i2++;
		}
		while(entity2 == null && i2 < ctx.length);
		Entity entity = entity1 != null ? entity1 : entity2;
		if(entity != null)
		{
			ScaleUtils.loadScale(p_8837_, entity);
		}
	}

	private static class MySecurityManager extends SecurityManager
	{
		public Class<?>[] getContext()
		{
			return this.getClassContext();
		}
	}
}