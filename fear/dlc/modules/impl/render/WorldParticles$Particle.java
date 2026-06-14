/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import fear.dlc.Api;
import net.minecraft.class_243;

private class WorldParticles.Particle {
    float x;
    float y;
    float z;
    float prevX;
    float prevY;
    float prevZ;
    float motionX;
    float motionY;
    float motionZ;
    long spawnTime;
    int lifetime;
    float rotation;
    float rotationSpeed;
    float randomHue;
    boolean followsPlayer;

    WorldParticles.Particle(float x, float y, float z, float motionX, float motionY, float motionZ, long spawnTime, int lifetime) {
        this.prevX = x;
        this.x = x;
        this.prevY = y;
        this.y = y;
        this.prevZ = z;
        this.z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.spawnTime = spawnTime;
        this.lifetime = lifetime;
        this.rotation = worldParticles.random.method_43057() * 360f;
        this.rotationSpeed = (worldParticles.random.method_43057() - 0.5f) * 2f;
        this.randomHue = worldParticles.random.method_43057();
        this.followsPlayer = worldParticles.random.method_43057() < 0.08f;
    }

    void update(double centerY, float vSpread) {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.x = this.x + this.motionX;
        this.y = this.y + this.motionY;
        this.z = this.z + this.motionZ;
        if (WorldParticles.this.particlesRotation.getEnabled().booleanValue()) {
            this.rotation = this.rotation + this.rotationSpeed;
        }
        float dyFromCenter = ((double)this.y - centerY);
        if (Math.abs(dyFromCenter) > vSpread) {
            float pull = -Math.signum(dyFromCenter) * 0.005f;
            this.motionY = this.motionY + pull;
        } else {
            this.motionY = this.motionY * 0.985f;
        }
        if (Api.mc.field_1724 == null) { /* goto L271; */ }
        class_243 playerPos = Api.mc.field_1724.method_19538();
        float followSpeed = this.followsPlayer ? 0.008f : 0.002f;
        float dx = (playerPos.field_1352 - (double)this.x);
        float dz = (playerPos.field_1350 - (double)this.z);
        float distXZ = Math.sqrt((double)(dx * dx + dz * dz));
        if (distXZ > 2f) {
            this.motionX = this.motionX + dx / distXZ * followSpeed;
            this.motionZ = this.motionZ + dz / distXZ * followSpeed;
        }
        this.motionX = this.motionX * 0.985f;
        this.motionZ = this.motionZ * 0.985f;
        float speed = Math.sqrt((double)(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ));
        float maxSpeed = 0.08f * WorldParticles.this.particlesSpeed.getValue().floatValue();
        this.motionX = this.motionX / speed * maxSpeed;
        if (speed > maxSpeed && speed > 0f) {
            this.motionY = this.motionY / speed * maxSpeed;
            this.motionZ = this.motionZ / speed * maxSpeed;
        }
    }

    float getAlpha() {
        long age = System.currentTimeMillis() - this.spawnTime;
        float fadeIn = Math.min(1f, (float)age / 800f);
        float fadeOut = age > (long)this.lifetime ? Math.max(0f, 1f - (float)(age - (long)this.lifetime) / 1000f) : 1f;
        return fadeIn * fadeOut;
    }
}
