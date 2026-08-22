uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 baseColor = texture(DiffuseSampler0, texCoord);

    fragColor = vec4(1.0 - baseColor.rgb, 1);
}