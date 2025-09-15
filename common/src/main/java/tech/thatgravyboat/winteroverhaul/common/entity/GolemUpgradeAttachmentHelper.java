package tech.thatgravyboat.winteroverhaul.common.entity;

import com.mojang.serialization.Codec;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;

import java.util.function.Function;

public class GolemUpgradeAttachmentHelper {
    public static final Codec<NonNullList<ItemStack>> CODEC = ItemStack.OPTIONAL_CODEC.listOf()
        .xmap(list -> NonNullList.of(ItemStack.EMPTY, list.toArray(new ItemStack[0])), Function.identity());

    public static final StreamCodec<RegistryFriendlyByteBuf, NonNullList<ItemStack>> STREAM_CODEC = ItemStack.OPTIONAL_LIST_STREAM_CODEC
        .map(list -> NonNullList.of(ItemStack.EMPTY, list.toArray(new ItemStack[0])), Function.identity());

    public static NonNullList<ItemStack> createDefault() {
        return NonNullList.withSize(GolemUpgradeSlot.values().length, ItemStack.EMPTY);
    }
}
