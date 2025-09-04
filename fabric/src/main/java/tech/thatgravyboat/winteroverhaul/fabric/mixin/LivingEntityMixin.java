package tech.thatgravyboat.winteroverhaul.fabric.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.winteroverhaul.fabric.WinterOverhaulFabric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "dropAllDeathLoot", at = @At("TAIL"))
    private void handleMobDrops(ServerLevel level, DamageSource damageSource, CallbackInfo ci) {
        List<ItemEntity> drops = new ArrayList<>();

        WinterOverhaulFabric.MOD.onMobDrops((LivingEntity) (Object) this, drops);

        for (ItemEntity drop : drops) {
            this.level().addFreshEntity(drop);
        }
    }
}
