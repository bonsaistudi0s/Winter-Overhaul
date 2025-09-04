package tech.thatgravyboat.winteroverhaul.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

import java.util.List;
import java.util.Map;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(WinterOverhaul.MODID, Registries.ARMOR_MATERIAL);

    public static final RegistrySupplier<ArmorMaterial> GOLEM_UPGRADE = ARMOR_MATERIALS.register("golemupgrade", () -> new ArmorMaterial(Map.of(), 0, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.EMPTY, List.of(), 0f, 0f));
}
