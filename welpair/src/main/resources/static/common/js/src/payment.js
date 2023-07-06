
const sameorderer = document.getElementById("sameorderer");

sameorderer.addEventListener("click", e => {

    const ordererName = document.getElementById("ordererName").val;
    const ordererPhone = document.getElementById("ordererPhone").val;

    let recipientName = document.getElementById("recipientName").val;
    let recipientPhone = document.getElementById("recipientPhone").val;

    recipientName = ordererName;
    recipientPhone = ordererPhone;

})