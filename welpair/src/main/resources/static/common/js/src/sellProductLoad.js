import {includeHTML} from './include.js'

let sellProductTotalCount;

sellProductCount();
sellProductTableLoad();
includeHTML();

function call(url, method, request) {
    let options = {
        headers: {
            'Content-Type': 'application/json',
        },
        method: method
    };

    if (request) {
        options.body = JSON.stringify(request);
    }

    return fetch(url, options)
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            return Promise.reject(response);
        })
        .catch(error => {
            console.log(error);
            return Promise.reject(error);
        })
}

function createTableCell(text) {
    const td = document.createElement("td");
    td.textContent = text;
    return td;
}

function sellProductCount() {
    let url = "/sellproduct/totalCount"
    let method = 'post'

    call(url, method, null)
        .then(data => sellProductTotalCount = data)
        .catch(error => console.log(error))
}

function createTable(data) {
    document.querySelector(".section-product-table tbody").innerHTML = ''

    for (let index in data) {

        const sellProduct = data[index];
        const sellItemPageList = sellProduct.sellItemPageList;

        for (let i = 0; i < sellItemPageList.length; i++) {
            const sellItemPage = sellItemPageList[i];
            const tr = document.createElement("tr");

            // SellProductDTO 출력 (각 행에 모두 출력)
            const sellProductKeys = Object.keys(sellProduct);
            sellProductKeys.forEach(key => {
                if (key === 'sellItemPageList') return;
                tr.append(createTableCell(sellProduct[key]));
            });

            // SellItemPageDTO 출력
            const sellItemPageKeys = Object.keys(sellItemPage);
            sellItemPageKeys.forEach(key => {
                if (key === 'sellPage' || key === 'id') return;
                tr.append(createTableCell(sellItemPage[key]));
            });

            // SellPageDTO 출력
            const sellPage = sellItemPage.sellPage;

            // 테이블 형식을 맞추기 위해 SellPageDTO가 null일 경우 빈값을 가진 td 생성
            if (sellPage) {
                const sellPageKeys = Object.keys(sellPage);
                sellPageKeys.forEach(key => {
                    if (key === 'no') return;
                    tr.append(createTableCell(sellPage[key]));
                });
            } else {
                tr.append(createTableCell(''));
            }

            // 체크박스 추가 (각 행에 모두 추가)
            const td = document.createElement("td");
            const input = document.createElement("input");
            input.type = 'checkbox';
            td.append(input);
            tr.prepend(td);

            document.querySelector(".section-product-table tbody").append(tr);
        }
    }
}

function sellProductTableLoad(pageNo = 1) {

    let url = "/sellproduct/list/" + pageNo;
    let method = 'get';

    call(url, method, null)
        .then(data => {
            createTable(data);
        })
        .catch(error => {
            console.log(error);
        });

    document.createElement('a').href = 'javascript:void(0)';
}

window.addEventListener('load', function() {
    searchSellProduct();
    deleteSellProduct();
})

function searchSellProduct() {
    let searchBtn = document.querySelector(".first-button")
    searchBtn.addEventListener('click', function () {
        const code = document.querySelector(".product-code").value
        const name = document.querySelector(".name").value

        const map = {code, name};
        if (code === '' && name === '') {
            sellProductTableLoad()
            return;
        }

        call("/sellproduct/search", "post", map)
            .then(data => {
                createTable(data);
            })
    })
}

function deleteSellProduct() {
    let deleteBtn = document.querySelectorAll(".first-button")[1];
    deleteBtn.addEventListener('click', function () {

        const items = document.querySelectorAll("tbody input");
        const request = [...items].filter(item => item.checked)
            .map(item => item.parentElement.nextElementSibling.textContent);
        console.log(request);

        call("/sellproduct/delete", "post", request)
            .then(data => {
                if (data > 0) {
                    alert("삭제에 성공하였습니다.");
                    sellProductTableLoad();
                } else {
                    alert("삭제에 실패하였습니다.");
                }
            })

    })
}

const headerCheckBox = document.querySelector("thead input");
headerCheckBox.addEventListener('click', function() {
    const items = document.querySelectorAll("tbody input");
    items.forEach(item => item.checked = headerCheckBox.checked);
})