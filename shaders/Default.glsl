// vertex shader
#version 330

layout (location = 0) in vec3 inWorld;

void main() {
    gl_Position = vec4(inWorld, 1.0f);
}

// fragment shader
#version 330

out vec4 pixel;
void main() {
    pixel = vec4(1.0f);
}
