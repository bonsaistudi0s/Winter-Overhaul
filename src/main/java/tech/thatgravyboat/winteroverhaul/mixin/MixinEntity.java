package tech.thatgravyboat.winteroverhaul.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
    public void onInteractAt(Player player, Vec3 loc, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        var interactionResult = WinterOverhaul.onEntityRightClick((Entity) (Object) this, loc, player.getItemInHand(hand), player);
        if (interactionResult != null) {
            cir.setReturnValue(interactionResult);
        }
    }
}
