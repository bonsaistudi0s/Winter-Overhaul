package tech.thatgravyboat.winteroverhaul;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.entity.Robin;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.registry.ModEntities;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;
import tech.thatgravyboat.winteroverhaul.common.registry.ModParticles;
import tech.thatgravyboat.winteroverhaul.common.registry.ModSounds;

@Mod(WinterOverhaul.MODID)
public class WinterOverhaul {

    public static final String MODID = "winteroverhaul";
    public static final Logger LOGGER = LogManager.getLogger();

    public WinterOverhaul() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        modEventBus.addListener(this::addAttributes);
        modEventBus.addListener(this::onComplete);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        Entity target = event.getTarget();
        if (!(target instanceof SnowGolem snowGolem) || snowGolem.hasPumpkin()) return;
        if (!(snowGolem instanceof IUpgradeAbleSnowGolem upgradeAbleSnowGolem)) return;
        ItemStack stack = event.getItemStack();
        if (stack.is(Items.CARROT) || stack.is(Items.GOLDEN_CARROT)) {
            var oldItem = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.FACE);
            if (!oldItem.isEmpty()) event.getPlayer().drop(oldItem, true);
            ItemStack newStack = stack.copy();
            newStack.setCount(1);
            upgradeAbleSnowGolem.setGolemUpgradeInSlot(GolemUpgradeSlot.FACE, newStack);
            stack.shrink(1);
            event.setCancellationResult(InteractionResult.sidedSuccess(event.getPlayer().level.isClientSide));
            event.setCanceled(true);
        }
        if (stack.isEmpty() && event.getPlayer().isShiftKeyDown()) {
            for (GolemUpgradeSlot value : GolemUpgradeSlot.values()) {
                ItemStack oldStack = upgradeAbleSnowGolem.setGolemUpgradeInSlot(value, ItemStack.EMPTY);
                if (oldStack.isEmpty()) continue;
                event.getPlayer().drop(oldStack, true);
            }
            event.setCancellationResult(InteractionResult.sidedSuccess(event.getPlayer().level.isClientSide));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.CheckSpawn event) {
        if (!(event.getEntity() instanceof Mob mob)) return;

        EntityType<?> type = mob.getType();

        boolean isSkeleton = type.equals(EntityType.SKELETON) || type.equals(EntityType.STRAY);
        boolean isZombie = type.equals(EntityType.ZOMBIE);

        if (isSkeleton || isZombie) {
            Biome biome = event.getWorld().getBiome(event.getEntity().blockPosition());

            if (biome.getPrecipitation().equals(Biome.Precipitation.SNOW)) {
                if (mob.getRandom().nextFloat() > 0.90f && mob.getRandom().nextFloat() > 0.5f){
                    Item item = getRandomHatAndScarf(mob.getRandom().nextInt(8));
                    mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(item));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void addSpawns(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.BiomeCategory.TAIGA) && event.getClimate().precipitation.equals(Biome.Precipitation.SNOW)) {
            event.getSpawns().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.ROBIN.get(), 25, 1, 2));
        }
    }

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity instanceof IUpgradeAbleSnowGolem golem) {
            for (GolemUpgradeSlot value : GolemUpgradeSlot.values()) {
                ItemStack stack = golem.getGolemUpgradeInSlot(value);
                BlockPos pos = livingEntity.blockPosition();
                if (!stack.isEmpty()) event.getDrops().add(new ItemEntity(livingEntity.level, pos.getX(), pos.getY(), pos.getZ(), stack.copy()));
            }
        }
    }

    public void addAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ROBIN.get(), Robin.createAttributes().build());
    }

    public void onComplete(FMLLoadCompleteEvent event) {
        SpawnPlacements.register(ModEntities.ROBIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                (entity, level, spawn, pos, random) -> {
                    BlockState state = level.getBlockState(pos.below());
                    boolean isLeaves = state.is(BlockTags.LEAVES);
                    boolean isSnow = state.is(BlockTags.SNOW);
                    boolean isGrass = state.is(Blocks.GRASS_BLOCK);

                    return (isLeaves || isSnow || isGrass || state.isAir()) && level.getRawBrightness(pos, 0) > 8;

                });
        CauldronInteraction.WATER.put(ModItems.SKATES.get(), CauldronInteraction.DYED_ITEM);
    }

    private static Item getRandomHatAndScarf(int randomInt) {
        return switch (randomInt) {
            case 0 -> ModItems.GREEN_HAT.get();
            case 1 -> ModItems.GREEN_SCARF.get();
            case 2 -> ModItems.YELLOW_HAT.get();
            case 3 -> ModItems.YELLOW_SCARF.get();
            case 4 -> ModItems.RED_HAT.get();
            case 5 -> ModItems.RED_SCARF.get();
            case 6 -> ModItems.CYAN_HAT.get();
            default -> ModItems.CYAN_SCARF.get();
        };
    }
}


