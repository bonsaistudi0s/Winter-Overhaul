package tech.thatgravyboat.winteroverhaul.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(WinterOverhaul.MODID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> ROBIN_AMBIENT = SOUNDS.register("entity.robin.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WinterOverhaul.MODID, "entity.robin.ambient")));
}
