#version 150

uniform sampler2D GlowSampler;
uniform sampler2D OriginalSampler;
uniform float GlowIntensity;
uniform float OriginalIntensity;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 glow = texture(GlowSampler, texCoord);
    vec4 original = texture(OriginalSampler, texCoord);

    vec4 result = glow * GlowIntensity + original * OriginalIntensity;

    fragColor = min(result, vec4(1.0));
}
