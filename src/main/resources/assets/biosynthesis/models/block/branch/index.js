var fs = require('fs');
var path = require('path');

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

        parsed.elements.forEach((element) => {
            console.log([...element.from, ...element.to]);
            const modelName = file.replace(/\.json$/, "").replace(/\\/g, '/')
            console.log(modelName);
        });

    
    } catch (error) {
        console.error(`Error parsing file: ${file}`);
        console.error(error);
        return;
    }
}

function convertToShape(mesh) {
    
}

walk('.');
