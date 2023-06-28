import {includeHTML} from './include.js'
import {call} from './App.js'

const searchBtn = document.querySelector(".first-button")
searchBtn.addEventListener('click', async function () {
    await fetchData();
});

const deleteBtn = document.querySelectorAll(".first-button")[1];
deleteBtn.addEventListener('click', deleteSellProduct);

const headerCheckBox = document.querySelector("thead input");
headerCheckBox.addEventListener('click', function () {
    const items = document.querySelectorAll("tbody input");
    items.forEach(item => item.checked = headerCheckBox.checked);
})

let currentPageNo = 1;
let maxPageNo = 0;
let startPageNo = 0;
let endPageNo = 0;
let code = null;
let name = null;

function createPaging() {
    const paging = document.querySelector('.paging');
    paging.innerHTML = '';

    if (endPageNo < currentPageNo) {
        startPageNo = currentPageNo / endPageNo * endPageNo + 1;
        endPageNo *= 2;
    }

    const createLink = (text) => {
        const link = document.createElement('a');
        link.textContent = text;
        return link;
    };

    const appendLink = (text, parent) => {
        const link = createLink(text);
        parent.append(link);
        return link;
    };

    const leftArrow = document.createElement('span');
    const leftLink = appendLink('<', leftArrow);
    paging.append(leftArrow);

    const pageLinks = [];

    for (let i = startPageNo; i <= maxPageNo && i <= endPageNo; i++) {
        const span = document.createElement('span');
        const link = appendLink(i, span);

        if (i === startPageNo) {
            link.classList.add('select');
        }

        paging.append(span);
        pageLinks.push(link);
    }

    const rightArrow = document.createElement('span');
    const rightLink = appendLink('>', rightArrow);
    paging.append(rightArrow);

    const updatePage = async (pageNumber) => {
        if (pageNumber === currentPageNo) return;

        const selectedLink = document.querySelector('.select');
        selectedLink.classList.remove('select');

        const targetLink = pageLinks[pageNumber - startPageNo];
        targetLink.classList.add('select');

        currentPageNo = pageNumber;
        await selectSellProduct(currentPageNo);
    };

    leftLink.addEventListener('click', () => {
        if (currentPageNo > 1) {
            updatePage(currentPageNo - 1);
        }
    });

    rightLink.addEventListener('click', () => {
        if (currentPageNo < maxPageNo) {
            updatePage(currentPageNo + 1);
        }
    });

    pageLinks.forEach((link, index) => {
        link.addEventListener('click', async function () {
            if (link.classList.contains('select')) return;
            await updatePage(startPageNo + index);
        });
    });
}

function createTableCell(text) {
    const td = document.createElement("td");
    td.textContent = text;
    return td;
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
                if (key === 'sellItemPageList' || key === 'product') return;
                if (key === 'code') {

                    tr.append(createTableCell(sellProduct['product']['productName']))
                } else {

                    tr.append(createTableCell(sellProduct[key]));
                }
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

function setPagination(data) {
    maxPageNo = data.maxPageNo;
    startPageNo = data.startPageNo;
    endPageNo = data.endPageNo;
}

async function fetchData() {
    code = document.querySelector(".product-code").value
    name = document.querySelector(".name").value
    const pageNo = 1;
    const map = {code, name, pageNo};

    // const urls = ['/sellproduct/count', '/sellproduct/list'];
    const urls = ['/sellproduct/count', '/sellproduct/list'];
    const requests = urls.map(url => call(url, 'post', map))
    const [data1, data2] = await Promise.all(requests)

    currentPageNo = 1;
    setPagination(data1);
    createTable(data2);
    createPaging();
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