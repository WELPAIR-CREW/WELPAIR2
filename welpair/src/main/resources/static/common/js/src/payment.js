
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