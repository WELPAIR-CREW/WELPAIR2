

// 주문인과 동일버튼 작동시
const sameorderer = document.getElementById("sameorderer");

sameorderer.addEventListener("click", e => {

    const ordererName = document.getElementById("ordererName");
    const ordererPhone = document.getElementById("ordererPhone");

    let recipientName = document.getElementById("recipientName");
    let recipientPhone = document.getElementById("recipientPhone");

    if(sameorderer.checked){
        recipientName.value = ordererName.value;
        recipientPhone.value = ordererPhone.value;
    } else {
        recipientName.value = '';
        recipientPhone.value = '';
    }
})

function addressFunc(addr){

    let addressList = addr;

    console.log(addr);
    const select = document.getElementById("recipientAddress");

    select.addEventListener("change", function() {

        let zipcode = document.getElementById("zipcode");
        let address1 = document.getElementById("address1");
        let address2 = document.getElementById("address2");

        let recipientName = document.getElementById("recipientName");
        let recipientPhone = document.getElementById("recipientPhone");


        for (let i = 0; i < select.length-1; i++) {
            if(select[i+1].selected){
                zipcode.value = addressList[i].addressDetail.split("/")[0];
                address1.value = addressList[i].addressDetail.split("/")[1];
                address2.value = addressList[i].addressDetail.split("/")[2];
                recipientName.value =  addressList[i].addressName;
                recipientPhone.value = parseInt(addressList[i].addressPhone.replace(/-/g, ''));
            }
        }
    })
}


let usepoint = document.getElementById("usePoint");

let totalPrice = parseInt(document.getElementById("totalPriceHidden").value);
let restPrice = document.getElementById("restPrice");
let restPriceHidden = document.getElementById("restPriceHidden");
let availablePoint = parseInt(document.getElementById("availablePointHidden").value);
restPriceHidden.value =  totalPrice -usepoint.value;

usepoint.addEventListener("blur", e => {


    if(usepoint.value <= totalPrice && usepoint.value <= availablePoint && usepoint.value >= 0){
        restPrice.textContent = (totalPrice - usepoint.value).toLocaleString();
        restPriceHidden.value =  totalPrice -usepoint.value;

    } else {
        alert("사용가능한 포인트를 다시 입력해주세요.");
        usepoint.value = 0;
    }
});



function josnDataSet(){

    const sellProductId = document.getElementsByName("sellProductId");
    const productOrderAmount = document.getElementsByName("productOrderAmount");
    const productOrderPrice = document.getElementsByName("productOrderPrice");
    const deliveryDate = document.getElementById("deliveryDate");
    const addressSelect = document.getElementsByName("addressSelect");

    let productOrderList = [];

    for (let i = 0; i < sellProductId.length; i++) {
        let productOrder = {
            sellProductId : ""
            , productOrderAmount : 0
            , productOrderPrice : 0
            , deliveryDate : ""
        };
        productOrder.sellProductId = sellProductId[i].value;
        productOrder.productOrderAmount = productOrderAmount[i].value;
        productOrder.productOrderPrice = productOrderPrice[i].value;
        productOrder.deliveryDate = deliveryDate.value;

        productOrderList.push(productOrder);
    };

    let paymentList = [];

    let orderPayment = { paymentList : paymentList };

    let order = {
        totalPrice :totalPrice
        , orderType : '일반주문'
        , addressId : [...addressSelect].filter( i => i.selected)[0].value
        , productOrderList : productOrderList
        , orderPayment : orderPayment
    };

    // --- 복지포인트 사용시
    // if(usepoint.value > 0){

            let payment = {
                paymentPrice : 0
                , paymentType : ""
            };

            payment.paymentPrice = usepoint.value;
            payment.paymentType = '복지포인트';

            paymentList.push(payment);

            orderPayment.paymentList = paymentList;
            order.orderPayment = orderPayment;

            // 복지포인트 전액 결제시
        if(totalPrice === parseInt(usepoint.value) && parseInt(restPriceHidden.value) === 0){

            const xhr = new XMLHttpRequest();
            xhr.open('POST', '/payment/payment.go');
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify(order));
            xhr.onreadystatechange = function () {
                if (this.readyState !== XMLHttpRequest.DONE) return;

                if (this.status === 200) {
                    alert("결제에 성공하였습니다.")
                    location.href = "/mypage/myorder/detail/" + this.responseText;
                }
            }

        }


    // ----- 카카오 -------
    // const KakaoPay_btn = document.getElementById("KakaoPay_btn");

    else{

        let payment = {
            paymentPrice : 0
            , paymentType : ""
        };

        payment.paymentPrice = restPriceHidden.value;
        payment.paymentType = '카카오페이';

        paymentList.push(payment);

        orderPayment.paymentList = paymentList;
        order.orderPayment = orderPayment;

        const item_name = document.getElementById("itemName").textContent;


        $.ajax({
            type:'post',
            // url:'/payment/kakaopay/do',
            url:'/payment/payment.go',
            contentType:"application/json",
            data: JSON.stringify(order), //  결제요청 데이터 전송
            success:function(response){
                location.href = response.next_redirect_pc_url;
            },
            error:function(e){
                alert("결제요청 실패 : 응답 없음");
            }
        });

        // -----  복지포인트만
    }

}










