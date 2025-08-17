package tech.thatgravyboat.winteroverhaul.common.util;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.HashMap;
import java.util.Map;

public class EntityAttributesBuilder {
    private final Map<EntityType<? extends LivingEntity>, AttributeSupplier> attributeSupplierMap = new HashMap<>();

    public void register(EntityType<? extends LivingEntity> type, AttributeSupplier supplier) {
        this.attributeSupplierMap.put(type, supplier);
    }

    public Map<EntityType<? extends LivingEntity>, AttributeSupplier> getAttributeSupplierMap() {
        return attributeSupplierMap;
    }
}
