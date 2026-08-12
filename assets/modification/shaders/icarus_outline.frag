#version 120

uniform sampler2D u_texture;
uniform vec2 u_texelSize, u_direction;
uniform float u_radius, u_alpha;
uniform vec3 color;

void main() {
    vec4 center = texture2D(u_texture, gl_TexCoord[0].st);
    if (center.a != 0) {
        gl_FragColor = vec4(0);
        return;
    } else {
        float rad = u_radius * 3;
        vec4 colAvg = vec4(0, 0, 0, 0);
        for (float xo = -rad; xo <= rad; xo++) {
            for (float yo = -rad; yo <= rad; yo++) {
                vec4 currCol = texture2D(u_texture, gl_TexCoord[0].st + vec2(xo * u_texelSize.x, yo * u_texelSize.y));
                if (currCol.a != 0) {
                    colAvg += clamp((rad * rad) - (xo * xo + yo * yo), 0, rad) * u_alpha / 1000;
                }
            }
        }
        colAvg.a /= u_radius;
        gl_FragColor = vec4(color.rgb, colAvg.a);
    }
}
