#version 120

uniform sampler2D u_texture;
uniform vec2 u_texelSize, u_direction;
uniform float u_radius, u_alpha, u_fade;
uniform vec3 color;

void main() {
    vec4 center = texture2D(u_texture, gl_TexCoord[0].st);
    if (center.a != 0) {
        gl_FragColor = vec4(color, 0);
        return;
    } else {
        vec4 o = vec4(0.0);
        for (float r = -u_radius; r <= u_radius; r++) {
            vec4 current = texture2D(u_texture, gl_TexCoord[0].st + u_texelSize * r * u_direction) / u_fade;
            o += current;
        }
        gl_FragColor = vec4(color, o.a);
    }
}
