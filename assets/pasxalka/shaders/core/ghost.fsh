#version 150

in vec2 FragCoord;
in vec2 TexCoord;
in vec4 FragColor;
in float Time;

uniform sampler2D Sampler0;
uniform vec2 Size;

out vec4 OutColor;

void main() {
    vec2 uv = TexCoord;
    vec2 center = vec2(0.5);
    
    // Расстояние от центра
    float dist = length(uv - center);
    
    // Призрачные волны
    float wave1 = sin(dist * 15.0 - Time * 4.0) * 0.5 + 0.5;
    float wave2 = sin(dist * 25.0 - Time * 6.0) * 0.3 + 0.7;
    float wave3 = sin(dist * 35.0 + Time * 2.0) * 0.2 + 0.8;
    
    // Комбинируем волны
    float waves = wave1 * wave2 * wave3;
    
    // Основное свечение (убывает от центра)
    float glow = 1.0 - smoothstep(0.0, 0.6, dist);
    glow = pow(glow, 1.5);
    
    // Внешнее кольцо свечения
    float outerGlow = 1.0 - smoothstep(0.4, 0.8, dist);
    outerGlow = pow(outerGlow, 3.0);
    
    // Пульсация
    float pulse = sin(Time * 3.0) * 0.3 + 0.7;
    
    // Призрачное мерцание
    float flicker = sin(Time * 8.0 + dist * 20.0) * 0.1 + 0.9;
    
    // Финальный цвет
    vec3 ghostColor = FragColor.rgb;
    
    // Добавляем свечение
    ghostColor += vec3(0.8, 0.9, 1.0) * glow * pulse;
    ghostColor += vec3(0.5, 0.7, 1.0) * outerGlow * waves;
    
    // Альфа канал с красивым затуханием
    float alpha = (glow + outerGlow * 0.5) * pulse * flicker * FragColor.a;
    alpha = clamp(alpha, 0.0, 1.0);
    
    OutColor = vec4(ghostColor, alpha);
}
