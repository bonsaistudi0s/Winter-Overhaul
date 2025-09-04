package tech.thatgravyboat.winteroverhaul.neoforge;

import net.minecraft.world.InteractionResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.util.BiomeSpawns;

@Mod(WinterOverhaul.MODID)
public class WinterOverhaulNeoForge {
    public static final WinterOverhaul MOD = new WinterOverhaul();

    private static final BiomeSpawns biomeSpawns = new BiomeSpawns();

    public WinterOverhaulNeoForge(IEventBus modBus) {
        NeoForge.EVENT_BUS.register(this);
        modBus.register(WinterOverhaulModBusEvents.class);
        MOD.register();
    }

    @SubscribeEvent
    public void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        InteractionResult result = MOD.onEntityRightClick(event.getTarget(), event.getItemStack(), event.getEntity());
        if (result != InteractionResult.PASS) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(MobSpawnEvent.PositionCheck event) {
        MOD.onEntitySpawn(event.getEntity(), event.getLevel());
    }

    @SubscribeEvent
    public void onAddSpawns(LevelEvent.PotentialSpawns potentialSpawns) {
        if (biomeSpawns.getSpawns().isEmpty())
            MOD.addSpawns(biomeSpawns);

        for (BiomeSpawns.SpawnData spawn : biomeSpawns.getSpawns()) {
            if (potentialSpawns.getMobCategory() == spawn.category() && spawn.selector().test(potentialSpawns.getLevel().getBiome(potentialSpawns.getPos()))) {
                potentialSpawns.addSpawnerData(spawn.spawnerData());
            }
        }
    }

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {
        MOD.onMobDrops(event.getEntity(), event.getDrops());
    }

    @SubscribeEvent
    public void onEntityDamage(LivingIncomingDamageEvent event) {
        if (MOD.onEntityDamage(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
        }
    }
}
