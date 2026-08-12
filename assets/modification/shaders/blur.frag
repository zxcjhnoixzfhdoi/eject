#version 120

uniform sampler2D u_texture;
uniform vec2 u_texelSize;
uniform vec2 u_direction;
uniform vec3 u_color;
uniform float u_radius;
uniform float u_alpha;
float weights[] = float[](0.100346,0.097274,0.088613,0.075856,0.061021,0.046128,0.032768,0.021874,0.013722,0.008089,0.004481
);

void main() {
    vec4 blurred = vec4(0.0);
    float totalStrength = 0.0;
    float totalAlpha = 0.0;
    float totalSamples = 0.0;
    for (float r = -u_radius; r <= u_radius; r++) {
        vec4 ss = texture2D(u_texture, gl_TexCoord[0].st + u_texelSize * r * u_direction) * weights[int(abs(r))];
        blurred += ss;
    }
    gl_FragColor = vec4(u_color.rgb, blurred.a * u_alpha * 10);
}