#version 120

uniform sampler2D u_texture;
uniform float u_alpha;

void main() {
    vec4 center = texture2D(u_texture, gl_TexCoord[0].xy);
    if (center.a == 0) {
        gl_FragColor = vec4(center.rgb, 0);
    } else {
        gl_FragColor = vec4(center.rgb, u_alpha);
    }
}
