#version 150

in vec3 Position;
in vec2 UV0;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform float GameTime;

out vec2 FragCoord;
out vec2 TexCoord;
out vec4 FragColor;
out float Time;

void main() {
    FragCoord = vec2(0.0);
    TexCoord = UV0;
    FragColor = Color;
    Time = GameTime * 1000.0;
    
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}
