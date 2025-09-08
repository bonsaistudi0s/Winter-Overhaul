package tech.thatgravyboat.winteroverhaul.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CauldronInteraction.class)
public interface AccessorCauldronInteraction {
    @Invoker
    static InteractionResult callDyedItemIteration(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        throw new IllegalStateException();
    }
}
