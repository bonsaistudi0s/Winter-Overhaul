package tech.thatgravyboat.winteroverhaul.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.Robin;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(WinterOverhaul.MODID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<Robin>> ROBIN = ENTITIES.register("robin",
        () -> EntityType.Builder.of(Robin::new, MobCategory.CREATURE).sized(0.5F, 0.9F).build("robin"));
}
