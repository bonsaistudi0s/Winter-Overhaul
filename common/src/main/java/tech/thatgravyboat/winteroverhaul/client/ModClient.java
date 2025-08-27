package tech.thatgravyboat.winteroverhaul.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeableLeatherItem;
import tech.thatgravyboat.winteroverhaul.client.particles.SnowflakeParticleProvider;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.ReplacedSnowGolemRenderer;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.RobinRenderer;
import tech.thatgravyboat.winteroverhaul.common.registry.ModEntities;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;
import tech.thatgravyboat.winteroverhaul.common.registry.ModParticles;

public class ModClient {
    public static void onItemColors() {
        ColorHandlerRegistry.registerItemColors((stack, index) -> {
            if (stack.getItem() instanceof DyeableLeatherItem dyeableArmorItem) {
                return index == 0 ? dyeableArmorItem.getColor(stack) : -1;
            }
            return -1;
        }, ModItems.SKATES.get());
    }

    public static void setupEntityRenderers() {
        EntityRendererRegistry.register(() -> EntityType.SNOW_GOLEM, ReplacedSnowGolemRenderer::new);
        EntityRendererRegistry.register(ModEntities.ROBIN, RobinRenderer::new);
    }

    public static void setupParticles() {
        ParticleProviderRegistry.register(ModParticles.SNOWFLAKE_1.get(), SnowflakeParticleProvider::new);
        ParticleProviderRegistry.register(ModParticles.SNOWFLAKE_2.get(), SnowflakeParticleProvider::new);
        ParticleProviderRegistry.register(ModParticles.SNOWFLAKE_3.get(), SnowflakeParticleProvider::new);
    }
}
