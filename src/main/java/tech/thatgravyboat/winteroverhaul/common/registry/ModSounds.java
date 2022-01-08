package tech.thatgravyboat.winteroverhaul.common.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WinterOverhaul.MODID);


    public static final RegistryObject<SoundEvent> ROBIN_AMBIENT = SOUNDS.register("entity.robin.ambient",
            () -> new SoundEvent(new ResourceLocation(WinterOverhaul.MODID, "entity.robin.ambient")));
}
