package tech.thatgravyboat.winteroverhaul.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SnowflakeParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprites;

    public SnowflakeParticleProvider(SpriteSet pSprites) {
        this.sprites = pSprites;
    }

    @Nullable
    @Override
    public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        SnowflakeParticle particle = new SnowflakeParticle(level, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        particle.pickSprite(this.sprites);
        return particle;
    }
}
