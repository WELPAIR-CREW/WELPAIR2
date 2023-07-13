import { appendLink, call, createPaging, createTable, pagination, setPagination } from './App.js';
import { includeHTML } from './include.js';

const searchBtn = document.querySelector(".first-button");
searchBtn.addEventListener('click', fetchSellProductListData);

const deleteBtn = document.querySelectorAll(".first-button")[1];
deleteBtn.addEventListener('click', deleteSellProduct);

const privateBtn = document.querySelector(".private");
privateBtn.addEventListener('click', updatePrivateSellPage);

const headerCheckBox = document.querySelector("thead input");
headerCheckBox.addEventListener('click', function () {
    const items = document.querySelectorAll("tbody input");
    items.forEach(item => item.checked = headerCheckBox.checked);
})

let code = null;
let name = null;
let categoryCode = null;
let productStatus = null;
let sellStatus = null;

async function fetchOptionListData() {
    const urls = ['/sellproduct/categoryList', '/sellproduct/statusList'];
    const requests = urls.map(url => call(url, 'post'))
    const [category, status] = await Promise.all(requests);
    const categoryInput = document.querySelector("#select-category");
    const statusInput = document.querySelector("#productStatus");

    [...category].filter(item => item.categoryCode > 2)
        .forEach((item, index) => {
            if (index == 0) {
                const option = document.createElement("option");
                option.value = '';
                option.textContent = '선택';
                categoryInput.append(option);
            }

            const option = document.createElement("option");
            option.value = item.categoryCode;
            option.textContent = item.categoryName;
            categoryInput.append(option);
        });

    [...status].forEach((item, index) => {
        if (index == 0) {
            const option = document.createElement("option");
            option.value = '';
            option.textContent = '선택';
            statusInput.append(option);
        }

        const option = document.createElement("option");
        option.value = item.productStatus;
        option.textContent = item.productStatus;
        statusInput.append(option);
    })
}

async function fetchSellProductListData() {
    code = document.querySelector(".product-code").value
    name = document.querySelector(".name").value
    categoryCode = document.querySelector("#select-category").value;
    productStatus = document.querySelector("#productStatus").value;
    sellStatus = document.querySelector("#sellStatus").value;
    
    const pageNo = 1;
    const map = { code, name, categoryCode, productStatus, sellStatus, pageNo };

    const urls = ['/sellproduct/sellProductCountAPI', '/sellproduct/sellProductListAPI'];
    const requests = urls.map(url => call(url, 'post', map))
    const [data1, data2] = await Promise.all(requests)

    pagination.currentPageNo = 1;
    setPagination(data1);
    createTable(data2);
    createSellPageLink();
    createPaging(selectSellProduct);
}

async function selectSellProduct(pageNo = 1) {
    const map = { code, name, categoryCode, productStatus, pageNo };

    call('/sellproduct/sellProductListAPI', 'post', map)
        .then(data => {
            createTable(data);
            createSellPageLink();   
        })
}

function deleteSellProduct() {
    const items = document.querySelectorAll("tbody input");
    const request = [...items].filter(item => item.checked)
        .map(item => item.parentElement.nextElementSibling.textContent);

    if (!confirm("삭제하시겠습니까?")) {
        return;
    }

    call("/sellproduct/sellProductDeleteAPI", "post", request)
        .then(data => {
            if (data > 0) {
                alert("삭제에 성공하였습니다.");
                fetchSellProductListData();
            } else {
                alert("삭제에 실패하였습니다.");
            }
        })
}

function updatePrivateSellPage() {
    const items = document.querySelectorAll("tbody input");
    const request = [...items].filter(item => item.checked)
        .map(item => item.parentElement.parentElement.children[2].textContent);

    call("/sellproduct/updateSellPageByPrivate", "post", request)
        .then(data => {
            if (data > 0) {
                alert("공개 성공하였습니다.");
                fetchSellProductListData();
            } else {
                alert("삭제에 실패하였습니다.");
            }
        })
}
function createSellPageLink() {
    let selectCells = document.querySelectorAll(".section-product-table tr td:nth-child(3)");
    selectCells.forEach(cell => {
        appendLink(cell.textContent, cell, 'modify/' + cell.textContent);
        cell.firstChild.textContent = '';
    })
}

await fetchSellProductListData();
await fetchOptionListData();
includeHTML();