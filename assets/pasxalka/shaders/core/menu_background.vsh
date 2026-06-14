#version 150

in vec3 Position;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 texCoord;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    // Передаем нормализованные координаты (0-1) в фрагментный шейдер
    texCoord = (Position.xy + 1.0) * 0.5;
}
