package com.min01.pehkui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PehkuiAutoResize.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandlerForge
{
	public static final Map<Integer, Entity> ENTITY_MAP = new HashMap<>();
	public static final Map<Integer, Entity> ENTITY_MAP2 = new HashMap<>();
	
	@SubscribeEvent
	public static void onEntityJoinLevel(EntityJoinLevelEvent event)
	{
		ENTITY_MAP.put(event.getEntity().getClass().hashCode(), event.getEntity());
		ENTITY_MAP2.put(event.getEntity().getClass().getSuperclass().hashCode(), event.getEntity());
	}
}
