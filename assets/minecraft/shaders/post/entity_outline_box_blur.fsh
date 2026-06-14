#version 150

uniform sampler2D InSampler;
uniform float BlurWeight0;
uniform float BlurWeight1;
uniform float BlurWeight2;
uniform float BlurWeight3;
uniform float BlurRadius1;
uniform float BlurRadius2;
uniform float BlurRadius3;
uniform float Brightness;

in vec2 texCoord;
in vec2 sampleStep;

out vec4 fragColor;

void main() {
    vec4 result = texture(InSampler, texCoord) * BlurWeight0;

    vec2 s1 = sampleStep * BlurRadius1;
    vec2 s2 = sampleStep * BlurRadius2;
    vec2 s3 = sampleStep * BlurRadius3;

    result += (texture(InSampler, texCoord + s1) + texture(InSampler, texCoord - s1)) * BlurWeight1;
    result += (texture(InSampler, texCoord + s2) + texture(InSampler, texCoord - s2)) * BlurWeight2;
    result += (texture(InSampler, texCoord + s3) + texture(InSampler, texCoord - s3)) * BlurWeight3;

    fragColor = result * Brightness;
}
