

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
usepoint.addEventListener("blur", e => {

    let totalPrice = document.getElementById("totalPriceHidden");
    let restPrice = document.getElementById("restPrice");
    let availablePoint = document.getElementById("availablePointHidden");

    if(usepoint.value <= totalPrice.value && usepoint.value <= availablePoint.value && usepoint.value >= 0){
        restPrice.textContent = (totalPrice.value - usepoint.value).toLocaleString();
    } else {
        alert("사용가능한 포인트를 다시 입력해주세요.");
        usepoint.value = 0;
    }
});






