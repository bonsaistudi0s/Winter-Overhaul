package tech.thatgravyboat.winteroverhaul.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public class SnowflakeParticle extends TextureSheetParticle {


    protected SnowflakeParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    protected SnowflakeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, 0, 0, 0);
        this.scale(0.25f);
        this.xd /= 4;
        this.yd /= 4;
        this.zd /= 4;
    }



    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
