import { includeHTML } from './include.js'
import {call, createPaging, createTable, pagination, setPagination} from './App.js'

const searchBtn = document.querySelector(".first-button");
searchBtn.addEventListener('click', fetchReviewListData);

const headerCheckBox = document.querySelector("thead input");
headerCheckBox.addEventListener('click', function () {
    const items = document.querySelectorAll("tbody input");
    items.forEach(item => item.checked = headerCheckBox.checked);
})

let code = null;
let name = null;

async function fetchReviewListData() {
    code = document.querySelector(".product-code").value
    name = document.querySelector(".name").value
    const pageNo = 1;
    const map = {code, name, pageNo};

    const urls = ['/sellproduct/reviewCountAPI', '/sellproduct/reviewListAPI'];
    const requests = urls.map(url => call(url, 'post', map))
    const [data1, data2] = await Promise.all(requests)

    pagination.currentPageNo = 1;
    setPagination(data1);
    createTable(data2);
    createPaging(selectReview);
}

async function selectReview(pageNo = 1) {
    const map = {code, name, pageNo};

    call('/sellproduct/reviewListAPI', 'post', map)
        .then(data => {
            createTable(data)
        })
}

await fetchReviewListData();
includeHTML();