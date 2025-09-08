package tech.thatgravyboat.winteroverhaul.fabric.mixin.datagen;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tech.thatgravyboat.winteroverhaul.datagen.KnownEntityTypeProvider;

import java.util.stream.Stream;

@Mixin(EntityLootSubProvider.class)
public abstract class EntityLootSubProviderMixin {
    @ModifyExpressionValue(method = "generate(Ljava/util/function/BiConsumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/DefaultedRegistry;listElements()Ljava/util/stream/Stream;"))
    private Stream<Holder.Reference<EntityType<?>>> useKnownEntityTypes(Stream<Holder.Reference<EntityType<?>>> original) {
        if (this instanceof KnownEntityTypeProvider provider) {
            return provider.winteroverhaul$getKnownEntityTypes();
        }

        return original;
    }
}
