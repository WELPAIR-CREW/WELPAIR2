// 수량변경시 이벤트 작성
function btn_plus(count) {
    let amount = Number(document.getElementById("item_count_box" + count).getAttribute('value'));

    if (amount >= 100) {
        alert("더 이상 추가할 수 없습니다.");
    } else {
        amount++;
        cartAmountChange(amount, count);
    }
    ;
};

function btn_minus(count) {
    let amount = Number(document.getElementById("item_count_box" + count).getAttribute('value'));

    if (amount <= 1) {
        alert("수량은 1개 이상 이어야 합니다.");
    } else {
        amount--;
        cartAmountChange(amount, count);
    }
    ;
};




function cartAmountChange(amount, count) {

    let changeAmount = amount;
    let productId = document.getElementById("productName" + count).getAttribute('value');
    // let productId = e.target.parentElement.querySelector(".prd_productName").attributes.value.textContent;
    console.log(changeAmount);
    console.log(productId);

    // productId.attributes(2);

    const data = {
        cartAmount: changeAmount,
        sellProductId: productId,
    }

    $.ajax({
        url: "/order/cart/amount-change",
        type: "POST",
        data: data,   // object 형식으로 가져오기 (key-value)
        success: function (response) {  // 요청이 성공한 경우 실행할 함수
            console.log("message");  // 서버로부터의 응답 데이터
            console.log(response);  // 서버로부터의 응답 데이터
            location.href = "cart";
        },
        error: function (error) {  // 요청이 실패한 경우 실행할 함수
            console.log(error);  // 오류 메시지
            alert("오류 발생. 다시 시도해주세요.");
        }
    });
};


// 체크박스 선택시 이벤트 작성
let $all_select = document.getElementById("all_select");
let prdCheckbox = document.querySelectorAll('input[class^="cart_select"]');


// 전체선택시 이벤트
$all_select.addEventListener("change", (e => {

        console.log("체크박스 선택한 js 들어옴");
        if (e.target.checked) {
            prdCheckbox.forEach(checkbox => checkbox.checked = true);

        } else {
            prdCheckbox.forEach(checkbox => checkbox.checked = false);
        }
        checkboxTrueList();
    }
));



// 체크박스 선택시마다 변동되는 가격 서버에 전달하기
function checkboxTrueList(){
    const exptPrice = document.getElementById("exptPrice");
    const exptDeliveryPrice = document.getElementById("exptpay_del");
    const exptTotalPrice = document.getElementById("exptTotalPrice");

    // checke true인 경우만 가져온다.
    const checkboxTrueList = Array.from(document.querySelectorAll('input[class^="cart_select"]:checked'));
    // 가격부분은 data-value 속성을 사용해 html내에서 value를 숨기고 가져온다.
    let selectValue = checkboxTrueList.filter(checkbox => checkbox.checked).map(checkbox => checkbox.value.split('/'));

    let price1 = 0;
    let price2 = 0;
    let price3 = 0;

    for(let i = 0; i < selectValue.length; i++){

            price1 += Number(selectValue[i][0]);
            price2 += Number(selectValue[i][1]);
            price3 += Number(selectValue[i][2]);
    }
    exptPrice.innerText = price1.toLocaleString();
    exptDeliveryPrice.innerText = price2.toLocaleString();
    exptTotalPrice.innerText = price3.toLocaleString();

}



// 선택삭제 버튼 누를 시 이벤트 작성
let $btn_del = document.getElementById("btn_del");

$btn_del.addEventListener("click", (e => {
    console.log("삭제함수 들어옴");
// 삭제할 아이템 담기
    let $deleteItem = Array.from(prdCheckbox)
        .filter(item => item.checked)
        .map(item => item.getAttribute('id'));


    console.log($deleteItem);
    const data = JSON.stringify($deleteItem)   // 데이터값만 변환해주기

    fetch("/order/cart/delete", {

        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: data
        })
        // .then(data => {
        //     data.json().then();
        // })
        .then(response => {
            console.log("전송완료");  // 서버로부터의 응답 데이터
            alert(response);
            location.href = 'cart';
        })
        .catch(error => {
            console.log("실패");
        })

    // $.ajax({
    //     url: "/order/cart/delete",
    //     type: "POST",
    //     data: data,   // object 형식으로 가져오기 (key-value)
    //     success: function (response) {  // 요청이 성공한 경우 실행할 함수
    //         console.log(response.message);  // 서버로부터의 응답 데이터
    //         location.href = "cart";
    //     },
    //     error: function (error) {  // 요청이 실패한 경우 실행할 함수
    //         console.log(error);  // 오류 메시지
    //         alert("오류 발생. 다시 시도해주세요.");
    //     }
    // });

}));


// 선택상품 주문하기 post 요청,
function gotopay(){

    const from = document.getElementById("cartPayForm");

    let checkboxes = form.elements["sellProductId"];
    let selectedProducts = [];

    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            selectedProducts.push(checkboxes[i].value);
        }
    }
    let json = JSON.stringify(selectedProducts);
    let hiddenInput = document.createElement("input");
    hiddenInput.setAttribute("type", "hidden");
    hiddenInput.setAttribute("name", "selectedProducts");
    hiddenInput.setAttribute("value", json);
    form.appendChild(hiddenInput);
    form.submit();

}