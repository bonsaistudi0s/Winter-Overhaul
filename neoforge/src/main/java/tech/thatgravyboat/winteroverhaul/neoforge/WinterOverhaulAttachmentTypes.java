package tech.thatgravyboat.winteroverhaul.neoforge;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.GolemUpgradeAttachmentHelper;

import java.util.function.Supplier;

public class WinterOverhaulAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, WinterOverhaul.MODID);

    public static final Supplier<AttachmentType<NonNullList<ItemStack>>> GOLEM_UPGRADES = ATTACHMENT_TYPES.register(
        "upgrades",
        () -> AttachmentType.builder(GolemUpgradeAttachmentHelper::createDefault)
            .sync(GolemUpgradeAttachmentHelper.STREAM_CODEC)
            .build()
    );
}
