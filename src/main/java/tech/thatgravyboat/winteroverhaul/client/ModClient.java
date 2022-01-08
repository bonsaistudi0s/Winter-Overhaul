package tech.thatgravyboat.winteroverhaul.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.client.particles.SnowflakeParticleProvider;
import tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics.CosmeticsRenderer;
import tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates.SkatesRenderer;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.ReplacedSnowGolemRenderer;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.RobinRenderer;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;
import tech.thatgravyboat.winteroverhaul.common.registry.ModEntities;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;
import tech.thatgravyboat.winteroverhaul.common.registry.ModParticles;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WinterOverhaul.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClient {

    @SubscribeEvent
    public static void onItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, index) -> {
            if (stack.getItem() instanceof DyeableLeatherItem dyeableArmorItem) {
                return index == 0 ? dyeableArmorItem.getColor(stack) : -1;
            }
            return -1;
        }, ModItems.SKATES.get());
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(GolemUpgradeItem.class, new CosmeticsRenderer());
        GeoArmorRenderer.registerArmorRenderer(SkateItem.class, new SkatesRenderer());
    }

    @SubscribeEvent
    public static void onClient(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityType.SNOW_GOLEM, ReplacedSnowGolemRenderer::new);
        EntityRenderers.register(ModEntities.ROBIN.get(), RobinRenderer::new);
    }

    @SubscribeEvent
    public static void onParticleRegister(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.SNOWFAKE_1.get(), SnowflakeParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.SNOWFAKE_2.get(), SnowflakeParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.SNOWFAKE_3.get(), SnowflakeParticleProvider::new);
    }
}
