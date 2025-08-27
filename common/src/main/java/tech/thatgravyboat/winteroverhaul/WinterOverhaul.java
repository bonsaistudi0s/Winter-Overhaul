package tech.thatgravyboat.winteroverhaul;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.entity.Robin;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.registry.ModEntities;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;
import tech.thatgravyboat.winteroverhaul.common.registry.ModParticles;
import tech.thatgravyboat.winteroverhaul.common.registry.ModSounds;
import tech.thatgravyboat.winteroverhaul.common.util.BiomeSpawns;
import tech.thatgravyboat.winteroverhaul.common.util.EntityAttributesBuilder;

import java.util.Collection;
import java.util.List;

public class WinterOverhaul {

    public static final String MODID = "winteroverhaul";
    public static final Logger LOGGER = LogManager.getLogger();

    public void register() {
        ModItems.ITEMS.register();
        ModParticles.PARTICLES.register();
        ModEntities.ENTITIES.register();
        ModSounds.SOUNDS.register();
    }

    public InteractionResult onEntityRightClick(Entity target, ItemStack stack, Player player) {
        if (!(target instanceof SnowGolem snowGolem) || snowGolem.hasPumpkin()) return InteractionResult.PASS;
        if (!(snowGolem instanceof IUpgradeAbleSnowGolem upgradeAbleSnowGolem)) return InteractionResult.PASS;
        if (stack.is(Items.CARROT) || stack.is(Items.GOLDEN_CARROT)) {
            var oldItem = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.FACE);
            if (!oldItem.isEmpty()) player.drop(oldItem, true);
            ItemStack newStack = stack.copy();
            newStack.setCount(1);
            upgradeAbleSnowGolem.setGolemUpgradeInSlot(GolemUpgradeSlot.FACE, newStack);
            stack.shrink(1);
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }
        if (stack.isEmpty() && player.isShiftKeyDown()) {
            for (GolemUpgradeSlot value : GolemUpgradeSlot.values()) {
                ItemStack oldStack = upgradeAbleSnowGolem.setGolemUpgradeInSlot(value, ItemStack.EMPTY);
                if (oldStack.isEmpty()) continue;
                player.drop(oldStack, true);
            }
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }

        return InteractionResult.PASS;
    }

    public void onEntitySpawn(LivingEntity entity, LevelReader level) {
        if (!(entity instanceof Mob mob)) return;

        EntityType<?> type = mob.getType();

        boolean isSkeleton = type.equals(EntityType.SKELETON) || type.equals(EntityType.STRAY);
        boolean isZombie = type.equals(EntityType.ZOMBIE);

        if (isSkeleton || isZombie) {
            Holder<Biome> biome = level.getBiomeManager().getBiome(entity.blockPosition());

            if (biome.isBound() && biome.value().hasPrecipitation() && biome.value().getPrecipitationAt(entity.blockPosition()).equals(Biome.Precipitation.SNOW)) {
                if (mob.getRandom().nextFloat() > 0.90f && mob.getRandom().nextFloat() > 0.5f){
                    Item item = getRandomHatAndScarf(mob.getRandom().nextInt(8));
                    mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(item));
                }
            }
        }
    }

    public void addSpawns(BiomeSpawns spawns) {
        spawns.addSpawn(
            biome -> biome.is(BiomeTags.IS_TAIGA) && biome.value().getBaseTemperature() < 0.15f,
            MobCategory.CREATURE,
            new MobSpawnSettings.SpawnerData(ModEntities.ROBIN.get(), 25, 1, 2)
        );
    }

    public void onMobDrops(LivingEntity livingEntity, Collection<ItemEntity> drops) {
        if (livingEntity instanceof IUpgradeAbleSnowGolem golem) {
            for (GolemUpgradeSlot value : GolemUpgradeSlot.values()) {
                ItemStack stack = golem.getGolemUpgradeInSlot(value);
                BlockPos pos = livingEntity.blockPosition();
                if (!stack.isEmpty()) drops.add(new ItemEntity(livingEntity.level(), pos.getX(), pos.getY(), pos.getZ(), stack.copy()));
            }
        }
    }

    public boolean onEntityDamage(Entity entity, DamageSource source) {
        if (entity instanceof SnowGolem && source.getDirectEntity() instanceof Snowball) {
            return true;
        }

        return false;
    }

    public void addAttributes(EntityAttributesBuilder builder) {
        builder.register(ModEntities.ROBIN.get(), Robin.createAttributes().build());
    }

    public void onComplete() {
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

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}


