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

    exptPrice.value = new Intl.NumberFormat().format(price1);
    exptDeliveryPrice.value = new Intl.NumberFormat().format(price2);
    exptTotalPrice.value = new Intl.NumberFormat().format(price3);

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
}));

document.getElementById("btn_gopay").onclick =
function(){



    const sellProductId = document.querySelectorAll("input[class^='cart_select']");
    const cartAmount = document.querySelectorAll('input[class="item_count_box"]');
    const prdName = document.getElementsByClassName("prd_productName");

    const price = document.querySelectorAll('div[class^="price_box"]');
    const deliveryPrice = document.querySelectorAll('div[class^="delivery_box"]');
    const totalPrice = document.querySelectorAll('div[class^="totla_price_box"]');

    const exptPrice = document.getElementById("exptPrice").value;
    const exptDeliveryPrice = document.getElementById("exptpay_del").value;
    const exptTotalPrice = document.getElementById("exptTotalPrice").value;

    let checkTotalPrice = 0;

    let checkboxes = document.querySelectorAll('input[class^="cart_select"]');

    if([...checkboxes].filter(item => item.checked).length == 0) {
        alert("주문할 상품을 선택하지 않았습니다. 다시 시도해주세요.");

    } else {
        let selectedProducts = [];

        // 동적으로 폼 생성
        const formData = document.createElement('form');

        formData.method = 'get';
        formData.action = '/payment/pay'; // 폼을 제출할 엔드포인트 URL

        let count = 0;
        for (let i = 0; i < checkboxes.length; i++) {

            let orderPrdList =
                {
                    sellProductId:  "",
                    productOrderAmount: 0,
                    productOrderPrice: 0,
                };

            if (checkboxes[i].checked) {

                {
                    const input = document.createElement('input');
                    input.name = 'orderPrdList[' + count + '].sellProductId';
                    input.type = 'hidden';
                    input.value = sellProductId[i].getAttribute("id");
                    formData.append(input);
                }
                {
                    const input = document.createElement('input');
                    input.name = 'orderPrdList[' + count + '].productOrderAmount';
                    input.type = 'hidden';
                    // input.value = orderPrdList.cartSellProduct.cartAmount = parseInt(cartAmount[i].getAttribute("value"));
                    input.value = parseInt(cartAmount[i].getAttribute("value"));
                    formData.append(input);

                }
                {
                    // 상품명
                    const input = document.createElement('input');
                    input.name = 'orderPrdList[' + count + '].Sellproduct.product.productName';
                    input.type = 'hidden';
                    input.value = prdName[i].getAttribute("name");
                    formData.append(input);
                }
                {
                    const input = document.createElement('input');
                    input.name = 'orderPrdList[' + count + '].productOrderPrice';
                    input.type = 'hidden';
                    // input.value = orderPrdList.price = parseInt(price[i].getAttribute("value"));
                    input.value = parseInt(price[i].getAttribute("value"))
                                + parseInt(deliveryPrice[i].getAttribute("value"));
                                // +  parseInt(totalPrice[i].getAttribute("value"));

                    formData.append(input);

                    checkTotalPrice += Number(input.value);
                }
            ++count;
            }
        }
        console.log(checkTotalPrice);
        // let expttotal =  parseInt(exptTotalPrice.replace(/,/g, ''));
        if(checkTotalPrice == parseInt(exptTotalPrice.replace(/,/g, ''))){

            document.body.append(formData);
            formData.submit()

        } else {
            alert("주문 합계 금액이 상이합니다. 오류발생")
        }

    }

}



// 선택상품 주문하기 post 요청,
// document.getElementById("btn_gopay").onclick = function(){
//
    // $.ajax({
    //     url: "/payment/pay",
    //     type: "POST",
    //     contentType: "application/json",
    //     data: JSON.stringify(selectedProducts),
    //     success : function (data){
    //         console.log("전송완료");  // 서버로부터의 응답 데이터
    //
    //         console.log(data);
    //         if(data == 'success'){
    //             location.href = "/payment/pay";  // 겟매핑 주범....
    //         }
    //         else {
    //             alert("선택하신 상품은 주문하실 수 없습니다. 해당 상품을 제외하고 다시 시도해주세요.")
    //         }
    //     },
    //     error: function (error) {
    //         console.log("실패");
    //         alert("에러")
    //     }
    // })

// }
