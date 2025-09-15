package tech.thatgravyboat.winteroverhaul.fabric.mixin;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.GolemUpgradeAttachmentHelper;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;

@Mixin(SnowGolem.class)
public abstract class SnowGolemMixin extends AbstractGolem implements IUpgradeAbleSnowGolem {
    @Unique
    private static final AttachmentType<NonNullList<ItemStack>> WINTEROVERHAUL_UPGRADES = AttachmentRegistry
        .create(WinterOverhaul.id("upgrades"), builder ->
            builder.initializer(GolemUpgradeAttachmentHelper::createDefault)
                .syncWith(GolemUpgradeAttachmentHelper.STREAM_CODEC, AttachmentSyncPredicate.all())
        );

    protected SnowGolemMixin(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public NonNullList<ItemStack> winteroverhaul_getUpgrades() {
        return this.getAttachedOrCreate(WINTEROVERHAUL_UPGRADES);
    }

    @Override
    public void winteroverhaul_setUpgrades(NonNullList<ItemStack> upgrades) {
        this.setAttached(WINTEROVERHAUL_UPGRADES, upgrades);
    }
}
