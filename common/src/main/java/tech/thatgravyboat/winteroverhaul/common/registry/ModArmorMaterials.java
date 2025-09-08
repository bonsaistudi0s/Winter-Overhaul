package tech.thatgravyboat.winteroverhaul.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.EquipmentAssets;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

import java.util.Map;

public class ModArmorMaterials {
    public static final ArmorMaterial GOLEM_UPGRADE = new ArmorMaterial(0, Map.of(), 1, SoundEvents.ARMOR_EQUIP_LEATHER, 0f, 0f, TagKey.create(Registries.ITEM, WinterOverhaul.id("golem_upgrade_repair_material")), ResourceKey.create(EquipmentAssets.ROOT_ID, WinterOverhaul.id("golem_upgrade")));
}
