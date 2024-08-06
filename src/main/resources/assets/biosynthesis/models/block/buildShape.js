const fs = require('fs');
const path = require('path');

const target = "src\\main\\resources\\assets\\biosynthesis\\models\\block\\branch";

let out = "";

fs.readdirSync(target).forEach(file => {
    const data = JSON.parse(fs.readFileSync(path.join(target, file), 'utf8'));

    const mesh = [];
    const modelName = file.replace(/\.json$/, "").replace(/\\/g, '/')

    data.elements.forEach((element) => {
        const bounds = [
            ...element.from, 
            ...element.to
            ].map((x) => Math.min(16, Math.max(0, x)));

        mesh.push(`Shape.box(${bounds.join(", ")})`);
    });

    out += `put("${modelName}", List.of(\n${mesh.join(", \n")}\n));\n\n`
});

console.log(out);
