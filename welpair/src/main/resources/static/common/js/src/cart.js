// 수량변경시 이벤트 작성
function btn_plus(count) {
    let amount = document.getElementsByName("item_count_box" + count)[0].getAttribute('value');

    if (amount >= 100) {
        alert("더 이상 추가할 수 없습니다.");
    } else {
        amount++;
        cartAmountChange(amount, count);
    }
    ;
};

function btn_minus(count) {
    let amount = document.getElementsByName("item_count_box" + count)[0].getAttribute('value');

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
    let productId = document.getElementsByName("productName" + count)[0].getAttribute('value');
    // let productId = e.target.parentElement.querySelector(".prd_productName").attributes.value.textContent;
    //
    console.log(changeAmount);
    console.log(productId);

    // productId.attributes(2);

    let data = {
        cartAmount: changeAmount,
        sellProductId: productId,
        empNo: 'E00033'  // ****** 변경해주기(나중에) ******
    }
    $.ajax({
        url: "/order/cart/amount-change",
        type: "POST",
        data: data,   // object 형식으로 가져오기 (key-value)
        success: function (response) {  // 요청이 성공한 경우 실행할 함수
            console.log(response);  // 서버로부터의 응답 데이터
            location.href = '/order/cart?empNo=' + data.empNo;
        },
        error: function (error) {  // 요청이 실패한 경우 실행할 함수
            console.log(error);  // 오류 메시지
            alert(response["수량변경에 실패하였습니다. 다시 시도해주세요."]);
        }
    });
};


// 체크박스 선택시 이벤트 작성
let $all_select = document.getElementById("all_select");
let checkbox = document.querySelectorAll("input[type='checkbox']");

// 전체선택시 이벤트
$all_select.addEventListener("click", (e => {

        console.log("체크박스 선택한 js 들어옴");
        if (e.target.checked) {
            checkbox.forEach(checkbox => checkbox.checked = true);
        } else {
            checkbox.forEach(checkbox => checkbox.checked = false);
        }
    }
));


// 선택삭제 버튼 누를 시 이벤트 작성
let $btn_del = document.getElementById("btn_del");

$btn_del.addEventListener("click", (e => {
    console.log("삭제함수 들어옴");
// 삭제할 아이템 담기
    let $deleteItem = Array.from(checkbox)
        .filter(item => item.checked)
        .map(item => item.getAttribute('name'));


    console.log($deleteItem);
    if ($all_select.checked) {
        $deleteItem.shift(); // 전체선택 체크박스는 제외
    }
    console.log($deleteItem);

    // let data = [];
    // $deleteItem.forEach(item => data.push( {
    //     sellProductId : item
    // }));

    // let $deletelist = document.getElementById("cart_basic_list"+[index]);
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
            location.href = '/order/cart?empNo=E00033';
        })
        .catch(error => {
            console.log("실패");
        })

}));















