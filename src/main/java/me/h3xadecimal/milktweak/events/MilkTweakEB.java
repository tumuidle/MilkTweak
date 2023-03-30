package me.h3xadecimal.milktweak.events;

import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.BusBuilder;

public class MilkTweakEB extends EventBus {
    public MilkTweakEB() {
        super(BusBuilder.builder());
    }
}
