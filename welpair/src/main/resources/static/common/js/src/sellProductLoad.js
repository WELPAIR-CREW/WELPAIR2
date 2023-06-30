import { includeHTML } from './include.js'
import {call, createPaging, createTable, pagination, setPagination} from './App.js'

const searchBtn = document.querySelector(".first-button");
searchBtn.addEventListener('click', fetchData);

const deleteBtn = document.querySelectorAll(".first-button")[1];
deleteBtn.addEventListener('click', deleteSellProduct);

const headerCheckBox = document.querySelector("thead input");
headerCheckBox.addEventListener('click', function () {
    const items = document.querySelectorAll("tbody input");
    items.forEach(item => item.checked = headerCheckBox.checked);
})

let code = null;
let name = null;

async function fetchData() {
    code = document.querySelector(".product-code").value
    name = document.querySelector(".name").value
    const pageNo = 1;
    const map = {code, name, pageNo};

    const urls = ['/sellproduct/sellProductCountAPI', '/sellproduct/sellProductListAPI'];
    const requests = urls.map(url => call(url, 'post', map))
    const [data1, data2] = await Promise.all(requests)

    pagination.currentPageNo = 1;
    setPagination(data1);
    createTable(data2);
    createPaging(selectSellProduct);
}

async function selectSellProduct(pageNo = 1) {
    const map = {code, name, pageNo};

    call('/sellproduct/sellProductListAPI', 'post', map)
        .then(data => {
            createTable(data)
        })
}

async function deleteSellProduct() {
    const items = document.querySelectorAll("tbody input");
    const request = [...items].filter(item => item.checked)
        .map(item => item.parentElement.nextElementSibling.textContent);
    console.log(request);

    call("/sellproduct/sellProductDeleteAPI", "post", request)
        .then(data => {
            if (data > 0) {
                alert("삭제에 성공하였습니다.");
                fetchData();
            } else {
                alert("삭제에 실패하였습니다.");
            }
        })
}

await fetchData();
includeHTML();