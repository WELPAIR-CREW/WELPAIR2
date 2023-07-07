export const call = (url, method, request = null) => {
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

export function createTable(data) {
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

    let selectCells = document.querySelectorAll(".section-product-table tr td:nth-child(8)")
    selectCells.forEach(cell => {
        appendLink(cell.textContent, cell, 'modify/' + cell.textContent);
        cell.firstChild.textContent = '';
    })
}

export const pagination = {
    currentPageNo: 1,
    maxPageNo: 0,
    startPageNo: 0,
    endPageNo: 0,
}

export const createLink = (text, location = '') => {
    const link = document.createElement('a');
    link.textContent = text;
    link.href = location;
    return link;
};

export const appendLink = (text, parent, location = '') => {
    const link = createLink(text, location);
    parent.append(link);
    return link;
};

export function createPaging(callbackFn) {
    const paging = document.querySelector('.paging');
    paging.innerHTML = '';

    if (pagination.endPageNo < pagination.currentPageNo) {
        pagination.startPageNo = parseInt(pagination.currentPageNo / pagination.endPageNo) * pagination.endPageNo + 1;
        pagination.endPageNo *= 2;
        callbackFn(pagination.currentPageNo);
    } else if (pagination.startPageNo > pagination.currentPageNo) {
        pagination.endPageNo /= 2;
        pagination.startPageNo = parseInt(pagination.currentPageNo / pagination.startPageNo) * pagination.endPageNo + 1;
        callbackFn(pagination.currentPageNo);
    }

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

        if (pagination.currentPageNo > pagination.endPageNo || pagination.currentPageNo < pagination.startPageNo) {
            createPaging(callbackFn);
            return;
        }

        const selectedLink = document.querySelector('.select');
        selectedLink.classList.remove('select');

        const targetLink = pageLinks[pageNumber - pagination.startPageNo];
        targetLink.classList.add('select');

        await callbackFn(pagination.currentPageNo);
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
        link.addEventListener('click', async function (e) {
            if (link.classList.contains('select')) return;
            e.preventDefault();
            await updatePage(pagination.startPageNo + index);
        });
    });
}

export function createTableCell(text) {
    const td = document.createElement("td");
    td.textContent = text;
    return td;
}

export function setPagination(data) {
    pagination.maxPageNo = data.maxPageNo;
    pagination.startPageNo = data.startPageNo;
    pagination.endPageNo = data.endPageNo;
}
