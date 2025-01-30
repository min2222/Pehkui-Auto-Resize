package com.min01.pehkui.mixin;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.pehkui.EventHandlerForge;

import net.minecraft.core.Holder;
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
	protected MixinServerLevel(WritableLevelData p_220352_, ResourceKey<Level> p_220353_, Holder<DimensionType> p_220354_, Supplier<ProfilerFiller> p_220355_, boolean p_220356_, boolean p_220357_, long p_220358_, int p_220359_)
	{
		super(p_220352_, p_220353_, p_220354_, p_220355_, p_220356_, p_220357_, p_220358_, p_220359_);
	}
	
	@Inject(at = @At("HEAD"), method = "addFreshEntity")
	private void addFreshEntity(Entity p_8837_, CallbackInfoReturnable<Boolean> ci)
	{
		MySecurityManager manager = new MySecurityManager();
		Class<?>[] ctx = manager.getContext();
		for(Class<?> clazz : ctx)
		{
			if(EventHandlerForge.ENTITY_MAP.containsKey(clazz.hashCode()))
			{
				Entity entity = EventHandlerForge.ENTITY_MAP.get(clazz.hashCode());
				if(entity != null)
				{
					ScaleUtils.loadScale(p_8837_, entity);
				}
			}
			else if(EventHandlerForge.ENTITY_MAP2.containsKey(clazz.hashCode()))
			{
				Entity entity = EventHandlerForge.ENTITY_MAP2.get(clazz.hashCode());
				if(entity != null)
				{
					ScaleUtils.loadScale(p_8837_, entity);
				}
			}
		}
	}

	@SuppressWarnings("removal")
	private static class MySecurityManager extends SecurityManager
	{
		public Class<?>[] getContext()
		{
			return this.getClassContext();
		}
	}
}