/* admin_inventory */
// 1-1. 기본 정보 출력

$(document).ready(function() {
    $.ajax({
        url: "/inventory/getInventoryInfo",
        dataType: "json",
        method: "GET",
        success: function(response) {

            $("#totalInvenAmount").text(response.totalInvenAmount.toLocaleString());
            $("#alertStock").text(response.alertStock.toLocaleString());

            console.log(response);
            console.log(response.totalInvenAmount);
            console.log(response.alertStock);
        },
        error: function(xhr, status, error) {
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
    });
});