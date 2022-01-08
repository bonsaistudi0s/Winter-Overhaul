package tech.thatgravyboat.winteroverhaul.common.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, WinterOverhaul.MODID);

    public static final RegistryObject<SimpleParticleType> SNOWFAKE_1 = PARTICLES.register("snowflake_1", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SNOWFAKE_2 = PARTICLES.register("snowflake_2", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SNOWFAKE_3 = PARTICLES.register("snowflake_3", () -> new SimpleParticleType(true));
}
