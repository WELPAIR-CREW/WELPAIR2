const pagination = {
    currentPageNo : 1,
    maxPageNo: 0,
    startPageNo: 0,
    endPageNo: 0,
}
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

function createPaging(queryString = '') {
    const paging = document.querySelector('.paging');
    paging.innerHTML = '';
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

    for (let i = pagination.startPageNo; i <= pagination.maxPageNo && i <= pagination.endPageNo; i++) {
        const span = document.createElement('span');
        const link = appendLink(i, span);

        if (i === pagination.currentPageNo) {
            link.classList.add('select');
        }

        paging.append(span);
        pageLinks.push(link);
    }

    const rightArrow = document.createElement('span');
    const rightLink = appendLink('>', rightArrow);
    paging.append(rightArrow);

    const updatePage = async (pageNumber) => {
        if (pageNumber === pagination.currentPageNo) return;
        pagination.currentPageNo = pageNumber;
        location.href =
            window.location.origin + window.location.pathname
            + (queryString ? queryString.concat("&currentPageNo=")
                           : "?currentPageNo=")
            + pageNumber;
    };

    leftLink.addEventListener('click', async () => {
        if (pagination.currentPageNo > 1) {
            await updatePage(pagination.currentPageNo - 1);
        }
    });

    rightLink.addEventListener('click', async () => {
        if (pagination.currentPageNo < pagination.maxPageNo) {
            await updatePage(pagination.currentPageNo + 1);
        }
    });

    pageLinks.forEach((link, index) => {
        link.addEventListener('click', async function () {
            if (link.classList.contains('select')) return;
            await updatePage(pagination.startPageNo + index);
        });
    });
}

function setPagination(data) {
    pagination.currentPageNo = data.currentPageNo;
    pagination.startPageNo = data.startPageNo;
    pagination.endPageNo = data.endPageNo;
    pagination.maxPageNo = data.maxPageNo;
}

const call = async (url, method, request) => {
    let options = {
        headers: {
            'Content-Type': 'application/json',
        },
        method: method
    };

    if (request) {
        options.body = JSON.stringify(request);
    }

    console.log(options.body);
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