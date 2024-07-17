const fs = require("fs");

const dir = "./vessel/body/";
const files = fs.readdirSync(dir);
const out = [];

files.forEach((file) => {
    const data = JSON.parse(fs.readFileSync(dir + file, "utf8"));
    const geometry = data["minecraft:geometry"][0];

    const content = [];
    geometry.bones.forEach((bone) => {
        bone.cubes.forEach((cube) => {
            const matrix = [, ...cube.size].map((v) => Math.round(v));
            content.push(`box(${matrix.join(", ")})`);
        });
    });

    out.push(`Stream.of(\n${content.join(",\n")}\n)`);
});

console.log(out.join(",\n"));