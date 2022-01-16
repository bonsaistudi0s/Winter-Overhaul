package tech.thatgravyboat.winteroverhaul.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;

import java.util.List;

public class GolemUpgradeItem extends GeoArmorItem implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private final GolemUpgradeSlot slot;

    public GolemUpgradeItem(GolemUpgradeSlot slot, Properties pProperties) {
        super(GolemUpgradeArmorMaterial.INSTANCE, EquipmentSlot.HEAD, pProperties.stacksTo(1));
        this.slot = slot;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
        ItemStack itemstack1 = pPlayer.getItemBySlot(equipmentslot);
        if (itemstack1.isEmpty()) {
            pPlayer.setItemSlot(equipmentslot, itemstack.copy());
            if (!pLevel.isClientSide()) {
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
            }

            itemstack.setCount(0);
            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        } else {
            return InteractionResultHolder.pass(itemstack);
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
        return InteractionResult.sidedSuccess(player.level.isClientSide);
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
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
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level pLevel, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(TextComponent.EMPTY);
        tooltip.add(new TranslatableComponent("item.winteroverhaul.upgrade.header").withStyle(ChatFormatting.GRAY));
        tooltip.add(getDesc().withStyle(ChatFormatting.BLUE));
    }

    public MutableComponent getDesc() {
        return new TranslatableComponent(this.getDescriptionId() + ".desc");
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        return ImmutableMultimap.of();
    }
}
