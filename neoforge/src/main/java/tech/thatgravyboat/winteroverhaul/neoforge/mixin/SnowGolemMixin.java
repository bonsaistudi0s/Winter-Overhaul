package tech.thatgravyboat.winteroverhaul.neoforge.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.neoforge.WinterOverhaulAttachmentTypes;

@Mixin(SnowGolem.class)
public abstract class SnowGolemMixin extends AbstractGolem implements IUpgradeAbleSnowGolem {
    protected SnowGolemMixin(EntityType<? extends AbstractGolem> arg, Level arg2) {
        super(arg, arg2);
    }

    @Override
    public NonNullList<ItemStack> winteroverhaul_getUpgrades() {
        return this.getData(WinterOverhaulAttachmentTypes.GOLEM_UPGRADES);
    }

    @Override
    public void winteroverhaul_setUpgrades(NonNullList<ItemStack> upgrades) {
        this.setData(WinterOverhaulAttachmentTypes.GOLEM_UPGRADES, upgrades);
    }
}
