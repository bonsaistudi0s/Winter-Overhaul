package tech.thatgravyboat.winteroverhaul.datagen;

import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;

import java.util.stream.Stream;

public interface KnownEntityTypeProvider {
    @PlatformOnly("fabric")
    Stream<Holder.Reference<EntityType<?>>> winteroverhaul$getKnownEntityTypes();
}
