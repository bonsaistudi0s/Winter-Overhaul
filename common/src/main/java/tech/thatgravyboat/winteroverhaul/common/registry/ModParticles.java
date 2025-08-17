package tech.thatgravyboat.winteroverhaul.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(WinterOverhaul.MODID, Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> SNOWFLAKE_1 = PARTICLES.register("snowflake_1", () -> new SimpleParticleType(true) {});
    public static final RegistrySupplier<SimpleParticleType> SNOWFLAKE_2 = PARTICLES.register("snowflake_2", () -> new SimpleParticleType(true) {});
    public static final RegistrySupplier<SimpleParticleType> SNOWFLAKE_3 = PARTICLES.register("snowflake_3", () -> new SimpleParticleType(true) {});
}
