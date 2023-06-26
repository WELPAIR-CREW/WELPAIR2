let sellProductTotalCount;

sellProductCount();
sellProductLoad();

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

function sellProductLoad(pageNo = 1) {

    let url = "/sellproduct/sellproductlist/" + pageNo;
    let method = 'get';

    call(url, method, null)
        .then(data => {
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
        })
        .catch(error => {
            console.log(error);
        });

    document.createElement('a').href = 'javascript:void(0)';
}