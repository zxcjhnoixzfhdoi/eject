precision highp float;

uniform float time, count;
uniform vec2 resolution;

#define PI 3.141592653589793

void main(void) {
    vec2 screen = (2 * gl_FragCoord.xy - resolution) / min(resolution.x, resolution.y);
    vec3 color = vec3(0);
    for (float x = 0; x < count / 8; x++) {
        for (float y = 0; y < count; y++) {
            float timeValue = PI * 2 * y / count + (time - x);
            color += 0.001 / distance(screen, (0.4 + x / 20) * vec2(cos(timeValue) * sin(timeValue), sin(timeValue)));
        }
    }
    gl_FragColor = vec4(color * vec3(1, 1, 1), color.r);
}