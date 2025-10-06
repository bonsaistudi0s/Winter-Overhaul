package tech.thatgravyboat.winteroverhaul.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import tech.thatgravyboat.winteroverhaul.client.ModClient;

public class WinterOverhaulFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModClient.onItemColors();
        ModClient.setupEntityRenderers(EntityRendererRegistry::register);
        ModClient.setupParticles();
    }
}
