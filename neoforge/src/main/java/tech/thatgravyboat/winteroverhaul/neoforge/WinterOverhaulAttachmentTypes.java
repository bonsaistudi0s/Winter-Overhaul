package tech.thatgravyboat.winteroverhaul.neoforge;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.GolemUpgradeAttachmentHelper;

public class WinterOverhaulAttachmentTypes {
    public static final AttachmentType<NonNullList<ItemStack>> GOLEM_UPGRADES = Registry.register(
        NeoForgeRegistries.ATTACHMENT_TYPES,
        WinterOverhaul.id("upgrades"),
        AttachmentType.builder(GolemUpgradeAttachmentHelper::createDefault)
            .sync(GolemUpgradeAttachmentHelper.STREAM_CODEC)
            .build()
    );

    public static void init() {}
}
