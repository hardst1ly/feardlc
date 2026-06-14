#version 150

in vec2 FragCoord;
in vec2 TexCoord;
in vec4 FragColor;
in float Time;

uniform sampler2D Sampler0;

out vec4 OutColor;

void main() {
    vec2 uv = TexCoord;
    vec2 center = vec2(0.5);
    
    // Расстояние и угол от центра
    float dist = length(uv - center);
    float angle = atan(uv.y - center.y, uv.x - center.x);
    
    // Создаем кристаллические грани (6-угольник)
    float facets = abs(sin(angle * 6.0 + Time * 2.0));
    facets = pow(facets, 0.5);
    
    // Внутреннее ядро кристалла
    float core = 1.0 - smoothstep(0.0, 0.3, dist);
    core = pow(core, 2.0);
    
    // Кристаллические слои
    float layer1 = 1.0 - smoothstep(0.2, 0.5, dist);
    float layer2 = 1.0 - smoothstep(0.4, 0.7, dist);
    
    // Энергетические импульсы
    float pulse1 = sin(Time * 5.0) * 0.3 + 0.7;
    float pulse2 = sin(Time * 3.0 + dist * 10.0) * 0.2 + 0.8;
    
    // Кристаллическое свечение
    float energy = sin(dist * 20.0 - Time * 8.0) * 0.5 + 0.5;
    energy *= facets;
    
    // Цветовые переливы
    vec3 crystalColor = FragColor.rgb;
    
    // Добавляем голубоватое свечение ядра
    crystalColor += vec3(0.3, 0.7, 1.0) * core * pulse1;
    
    // Добавляем кристаллические грани
    crystalColor += vec3(0.8, 0.9, 1.0) * facets * layer1 * pulse2;
    
    // Добавляем энергетические вспышки
    crystalColor += vec3(1.0, 1.0, 0.8) * energy * layer2 * 0.5;
    
    // Внешнее свечение
    float outerGlow = 1.0 - smoothstep(0.3, 0.8, dist);
    outerGlow = pow(outerGlow, 4.0);
    crystalColor += vec3(0.5, 0.8, 1.0) * outerGlow * pulse1;
    
    // Альфа с красивым затуханием
    float alpha = (core + layer1 * facets + outerGlow * 0.3) * pulse1 * FragColor.a;
    alpha = clamp(alpha, 0.0, 1.0);
    
    OutColor = vec4(crystalColor, alpha);
}
