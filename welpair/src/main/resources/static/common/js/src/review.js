
function createTable(data) {
    document.querySelector(".section-product-table tbody").innerHTML = ''

    for (let index in data) {
        const items = data[index];
        const tr = document.createElement("tr");
        const keys = Object.keys(items);
        keys.forEach(key => {
            tr.append(createTableCell(items[key]));
        });

        // 체크박스 추가 (각 행에 모두 추가)
        const td = document.createElement("td");
        const input = document.createElement("input");
        input.type = 'checkbox';
        td.append(input);
        tr.prepend(td);

        document.querySelector(".section-product-table tbody").append(tr);
    }
}

function createTableCell(text) {
    const td = document.createElement("td");
    td.textContent = text;
    return td;
}