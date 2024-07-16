var fs = require('fs');
var path = require('path');

let out = "";

function walk(dirPath) {
    const files = fs.readdirSync(dirPath);

    files.forEach((file) => {
        const filePath = path.join(dirPath, file);
        const stat = fs.statSync(filePath);

        if (stat.isDirectory()) {
            walk(filePath);
        } else if (stat.isFile() && path.extname(filePath) === '.json') {
            parseFile(filePath);
        }
    });
}

function parseFile(file) {
    const raw = fs.readFileSync(file, 'utf8');

    try {
        const parsed = JSON.parse(raw);

        const mesh = [];
        const modelName = file.replace(/\.json$/, "").replace(/\\/g, '/')

        parsed.elements.forEach((element) => {
            const bounds = [...element.from, ...element.to].map((x) => Math.min(16, Math.max(0, x)));

            mesh.push(`box(${bounds.join(", ")})`);
        });

        out += `case \"${modelName}\" -> Stream.of(\n${mesh.join(", \n")}\n);\n\n`;


    } catch (error) {
        console.error(`Error parsing file: ${file}`);
        console.error(error);
        return;
    }
}



walk('./stem');
console.log(out);