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

// function createTable(data) {
//     document.querySelector(".section-product-table tbody").innerHTML = ''
//
//     for (let index in data) {
//
//         const sellProduct = data[index];
//         const sellItemPageList = sellProduct.sellItemPageList;
//
//         for (let i = 0; i < sellItemPageList.length; i++) {
//             const sellItemPage = sellItemPageList[i];
//             const tr = document.createElement("tr");
//
//             // SellProductDTO 출력 (각 행에 모두 출력)
//             const sellProductKeys = Object.keys(sellProduct);
//             sellProductKeys.forEach(key => {
//                 if (key === 'sellItemPageList' || key === 'product') return;
//                 if (key === 'code') {
//
//                     tr.append(createTableCell(sellProduct['product']['productName']))
//                 } else {
//
//                     tr.append(createTableCell(sellProduct[key]));
//                 }
//             });
//
//             // SellItemPageDTO 출력
//             const sellItemPageKeys = Object.keys(sellItemPage);
//             sellItemPageKeys.forEach(key => {
//                 if (key === 'sellPage' || key === 'id') return;
//                 tr.append(createTableCell(sellItemPage[key]));
//             });
//
//             // SellPageDTO 출력
//             const sellPage = sellItemPage.sellPage;
//
//             // 테이블 형식을 맞추기 위해 SellPageDTO가 null일 경우 빈값을 가진 td 생성
//             if (sellPage) {
//                 const sellPageKeys = Object.keys(sellPage);
//                 sellPageKeys.forEach(key => {
//                     if (key === 'no') return;
//                     tr.append(createTableCell(sellPage[key]));
//                 });
//             } else {
//                 tr.append(createTableCell(''));
//             }
//
//             // 체크박스 추가 (각 행에 모두 추가)
//             const td = document.createElement("td");
//             const input = document.createElement("input");
//             input.type = 'checkbox';
//             td.append(input);
//             tr.prepend(td);
//
//             document.querySelector(".section-product-table tbody").append(tr);
//         }
//     }
// }



async function fetchData() {
    code = document.querySelector(".product-code").value
    name = document.querySelector(".name").value
    const pageNo = 1;
    const map = {code, name, pageNo};

    const urls = ['/sellproduct/count', '/sellproduct/list'];
    const requests = urls.map(url => call(url, 'post', map))
    const [data1, data2] = await Promise.all(requests)

    pagination.currentPageNo = 1;
    setPagination(data1);
    createTable(data2);
    createPaging(selectSellProduct);
}

async function selectSellProduct(pageNo = 1) {
    const map = {code, name, pageNo};

    call('/sellproduct/list', 'post', map)
        .then(data => {
            createTable(data)
        })
}

async function deleteSellProduct() {
    const items = document.querySelectorAll("tbody input");
    const request = [...items].filter(item => item.checked)
        .map(item => item.parentElement.nextElementSibling.textContent);
    console.log(request);

    call("/sellproduct/delete", "post", request)
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