// vertex shader
#version 330

layout (location = 0) in vec3 inWorld;
layout (location = 1) in vec2 inTexture;
layout (location = 2) in vec3 inNormal;
out vec2 outTexture;
out vec3 outNormal;

uniform mat4 W;
uniform mat4 VP;

void main() {
    gl_Position = VP * W * vec4(inWorld, 1.0f);
    outTexture = inTexture;
    outNormal = (W * vec4(inNormal, 0.0f)).xyz;
}

// fragment shader
#version 330

in vec2 outTexture;
in vec3 outNormal;

uniform sampler2D colorMap;

out vec4 pixel;
void main() {
    pixel = texture(colorMap, outTexture);
}
