// vertex shader
#version 330

layout (location = 0) in vec3 inWorld;
layout (location = 1) in vec2 inTexture;
layout (location = 2) in vec3 inNormal;
out vec2 outTexture;

void main() {
    gl_Position = vec4(inWorld, 1.0f);
    outTexture = inTexture;
}

// fragment shader
#version 330

in vec2 outTexture;
out vec4 pixel;

uniform sampler2D colorMap;

void main() {
    pixel = texture(colorMap, outTexture);
}
