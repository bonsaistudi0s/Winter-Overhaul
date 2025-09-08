package tech.thatgravyboat.winteroverhaul.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics.CosmeticsRenderer;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.registry.ModArmorMaterials;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;

import java.util.function.Consumer;

public class GolemUpgradeItem extends Item implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final GolemUpgradeSlot slot;

    public GolemUpgradeItem(GolemUpgradeSlot slot, Item.Properties pProperties) {
        super(pProperties.stacksTo(1).humanoidArmor(ModArmorMaterials.GOLEM_UPGRADE, ArmorType.HELMET));
        this.slot = slot;

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        EquipmentSlot equipmentslot = pPlayer.getEquipmentSlotForItem(itemstack);
        ItemStack itemstack1 = pPlayer.getItemBySlot(equipmentslot);
        if (itemstack1.isEmpty()) {
            pPlayer.setItemSlot(equipmentslot, itemstack.copy());
            if (!pLevel.isClientSide()) {
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
            }

            itemstack.setCount(0);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
        if (!(target instanceof SnowGolem snowGolem) || snowGolem.hasPumpkin()) return super.interactLivingEntity(stack, player, target, hand);
        if (!(snowGolem instanceof IUpgradeAbleSnowGolem upgradeAbleSnowGolem)) {
            WinterOverhaul.LOGGER.error("Snow Golem was not an instance of IUpgradeAbleSnowGolem so mixin error occurred!");
            return super.interactLivingEntity(stack, player, target, hand);
        }
        var oldHat = upgradeAbleSnowGolem.getGolemUpgradeInSlot(slot);
        if (!oldHat.isEmpty()) player.drop(oldHat, true);
        upgradeAbleSnowGolem.setGolemUpgradeInSlot(slot, stack.copy());
        stack.shrink(1);
        return InteractionResult.SUCCESS;
    }

    public void tick(ItemStack stack, SnowGolem golem) {
        Item item = stack.getItem();
        if (!golem.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            if (item.equals(ModItems.YELLOW_SCARF.get()) || item.equals(ModItems.YELLOW_HAT.get())) {
                golem.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 0, false, false));
            }
        }
        if (item.equals(ModItems.TOP_HAT.get()) && golem.tickCount % 20 == 0) {
            golem.heal(0.5f);
        }
        if (item.equals(ModItems.TOP_HAT.get())) {
            MobEffectInstance effectInstance = golem.getEffect(MobEffects.HEALTH_BOOST);
            if (effectInstance == null || golem.tickCount % 5 == 0) {
                MobEffectInstance healthBoost = new MobEffectInstance(MobEffects.HEALTH_BOOST, 20, 0, false, false);
                if (effectInstance == null) golem.addEffect(healthBoost);
                else effectInstance.update(healthBoost);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("item.winteroverhaul.upgrade.header").withStyle(ChatFormatting.GRAY));
        tooltip.accept(getDesc().withStyle(ChatFormatting.BLUE));
    }

    public MutableComponent getDesc() {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?, ?> renderer;

            @Override
            public @Nullable <S extends HumanoidRenderState> GeoArmorRenderer<?, ?> getGeoArmorRenderer(@Nullable S renderState, ItemStack itemStack, EquipmentSlot equipmentSlot, EquipmentClientInfo.LayerType type, @Nullable HumanoidModel<S> original) {
                if (this.renderer == null)
                    this.renderer = new CosmeticsRenderer();

                //this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
