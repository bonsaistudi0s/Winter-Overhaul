package tech.thatgravyboat.winteroverhaul.common.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.Robin;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, WinterOverhaul.MODID);

    public static final RegistryObject<EntityType<Robin>> ROBIN = ENTITIES.register("robin",
            () -> EntityType.Builder.of(Robin::new, MobCategory.CREATURE).sized(0.5F, 0.9F).build("robin"));
}
