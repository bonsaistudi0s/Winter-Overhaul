package tech.thatgravyboat.winteroverhaul.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import tech.thatgravyboat.winteroverhaul.client.ModClient;

public class WinterOverhaulFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModClient.onClient();
    }
}
