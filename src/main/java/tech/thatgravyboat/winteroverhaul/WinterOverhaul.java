package tech.thatgravyboat.winteroverhaul;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
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

    @Nullable
    public static InteractionResult onEntityRightClick(Entity target, Vec3 loc, ItemStack stack, Player player) {
        if (!(target instanceof SnowGolem snowGolem) || snowGolem.hasPumpkin()) return null;
        if (!(snowGolem instanceof IUpgradeAbleSnowGolem upgradeAbleSnowGolem)) return null;
        if (stack.is(Items.CARROT) || stack.is(Items.GOLDEN_CARROT)) {
            var oldItem = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.FACE);
            if (!oldItem.isEmpty()) player.drop(oldItem, true);
            ItemStack newStack = stack.copy();
            newStack.setCount(1);
            upgradeAbleSnowGolem.setGolemUpgradeInSlot(GolemUpgradeSlot.FACE, newStack);
            stack.shrink(1);
            return InteractionResult.sidedSuccess(player.level.isClientSide);
        }
        if (stack.isEmpty() && player.isShiftKeyDown()) {
            //Snow Golem height = 1.9
            double y = loc.y;
            ItemStack oldStack = ItemStack.EMPTY;
            if (y >= 1.75) oldStack = upgradeAbleSnowGolem.setGolemUpgradeInSlot(GolemUpgradeSlot.HAT, ItemStack.EMPTY);
            else if (y >= 1.50) oldStack = upgradeAbleSnowGolem.setGolemUpgradeInSlot(GolemUpgradeSlot.FACE, ItemStack.EMPTY);
            else if ( y >= 1.35) oldStack = upgradeAbleSnowGolem.setGolemUpgradeInSlot(GolemUpgradeSlot.SCARF, ItemStack.EMPTY);

            if (!oldStack.isEmpty()) {
                target.spawnAtLocation(oldStack);
                return InteractionResult.sidedSuccess(player.level.isClientSide);
            }
        }
        return null;
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.CheckSpawn event) {
        Mob mob = event.getEntity();
        EntityType<?> type = mob.getType();
        if (type.is(EntityTypeTags.SKELETONS) || type.equals(EntityType.ZOMBIE)) {
            Holder<Biome> biome = event.getLevel().getBiomeManager().getBiome(event.getEntity().blockPosition());

            if (biome.isBound() && biome.value().getPrecipitation().equals(Biome.Precipitation.SNOW)) {
                if (mob.getRandom().nextFloat() > 0.90f && mob.getRandom().nextFloat() > 0.5f){
                    Item item = getRandomHatAndScarf(mob.getRandom().nextInt(8));
                    mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(item));
                }
            }
        }
    }

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {
        LivingEntity livingEntity = event.getEntity();
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


