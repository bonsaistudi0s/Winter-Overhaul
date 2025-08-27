package tech.thatgravyboat.winteroverhaul.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import tech.thatgravyboat.winteroverhaul.client.ModClient;

public class WinterOverhaulFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModClient.onItemColors();
        ModClient.setupEntityRenderers();
        ModClient.setupParticles();
    }
}
