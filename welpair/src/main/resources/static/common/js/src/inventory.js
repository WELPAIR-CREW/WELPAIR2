
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


/* admin_inventory_register */

// 검색 버튼
$("#sendInvenSearch").click(function (){

    let productCode = $("#productCode").val().toUpperCase();
    let productName = $("#productName").val().toUpperCase();
    let categoryCode = $("#categoryCode").val();
    let productAmount = $("#productAmount").val();

    let data = {
        productCode : productCode,
        productName : productName,
        categoryCode : categoryCode,
        productAmount : productAmount
    };

    $.ajax({
        url: "/inventory/admin_inventory_register",
        data : data,
        type : 'post',
        success: function (data){

            $("#searchResultTable tbody").empty();

            $.each(data, function (index, stock) {
                let row = $("<tr></tr>");
                row.append("<td><input type='checkbox'></td>");
                row.append("<td>" + stock.productCode + "</td>");
                row.append("<td>" + stock.productName + "</td>");
                row.append("<td>" + stock.category.categoryName + "</td>");
                row.append("<td>" + stock.productAmount + "</td>");
                row.append("<td></td>");
                row.append("<td></td>");
                row.append("<td></td>");
                row.append("<td></td>");
                $("#searchResultTable tbody").append(row);
            });
        },
        error: function (error) {
            alert(error);
        }
    });
});

// 일괄반영 버튼
$("#allOption").click(function (){


    let stockType = $("#inventypeOption option:selected").val();
    let stockDate = $("#stockDate").val();
    let stockComment = $("#stockComment").val();
    let stockAmount = $("#stockAmount").val();

    if(stockAmount <= 0){
        alert("수량 확인 필요");
        return false
    }


    $("#searchResultTable tbody tr").each(function() {
        let row = $(this);

        row.find("td:eq(5)").text(stockType);
        row.find("td:eq(6)").text(stockDate);
        row.find("td:eq(7)").text(stockComment);
        row.find("td:eq(8)").text(stockAmount);
    });
});

// 다시입력 버튼
$(document).ready(function() {

    $('input[type="reset"]').on('click', function (event) {
        event.preventDefault();
        resetTable();
    });

    function resetTable() {
        $('#inventypeOption').val('');
        $('#stockDate').val('');
        $('#stockComment').val('');
        $('#stockAmount').val('0');

        $('.section-regSearchResult-table input[type="checkbox"]').prop('checked', false);
        $('.section-regSearchResult-table tbody tr td:nth-child(6)').text('');
        $('.section-regSearchResult-table tbody tr td:nth-child(7)').text('');
        $('.section-regSearchResult-table tbody tr td:nth-child(8)').text('');
        $('.section-regSearchResult-table tbody tr td:nth-child(9)').text('');

    }

    // 등록버튼
    $('.btn_basic2').on('click', function(event) {
        event.preventDefault();

        let tableRows = $('.section-regSearchResult-table tbody tr');
        let rowData = [];
        let isCheckedExists = false;

        tableRows.each(function() {
            let row = $(this);
            let isChecked = row.find('input[type="checkbox"]').prop('checked');

            if (isChecked) {
                isCheckedExists = true;

                let cells = row.find('td');
                let productCode = cells.eq(1).text();
                let productAmount = cells.eq(4).text();
                let stockType = cells.eq(5).text();
                let stockDate = cells.eq(6).text();
                let stockComment = cells.eq(7).text();
                let stockAmount = cells.eq(8).text();

                if (stockType === '입고') {
                    stockAmount = parseInt(stockAmount);
                } else if (stockType === '출고') {
                    stockAmount = -parseInt(stockAmount);
                }

                if (productCode.trim() === '' || stockType.trim() === '' || stockDate.trim() === '') {
                    alert('입력값을 확인하세요');
                    return false;
                }

                rowData.push({
                    productCode: productCode,
                    productAmount: productAmount,
                    stockType: stockType,
                    stockDate: stockDate,
                    stockComment: stockComment,
                    stockAmount: stockAmount
                });
            }
        });

        if (!isCheckedExists) {
            alert("선택 항목 없음");
            return;
        }

        // let resultMessage = Response.resultMaessage;

        if (rowData.length > 0) {
            $.ajax({
                url: '/inventory/stockRegist',
                type: 'POST',
                data: JSON.stringify(rowData),
                contentType: 'application/json',


                success: function(response) {
                    console.log('Response:', response);
                    let resultMessage = response.resultMessage;
                    console.log('resultMessage:', resultMessage);
                    if (resultMessage === "success") {
                        alert("등록 성공");
                    } else if (resultMessage === "fail") {
                        alert("출고 등록 실패-수량 부족");
                    } else {
                        alert("등록 실패");
                    }
                    location.reload();
                },
                error: function(error) {
                    console.log('Error:', error);
                    alert("등록 실패");
                }
            });
        }
    });
});


/* admin_inventory_search */


// 날짜 기본값 반영
let today = new Date();
let stockDateString = today.toISOString().split('T')[0];
$("#stockDate").val(stockDateString);

// 입고 일자 오늘로
$(document).ready(function() {
    let currentDate = new Date().toISOString().split('T')[0];
    $('#invenRegInDate').val(currentDate);
});

// 체크박스
$(document).ready(function() {
    $('.section-regSearchResult-table th input[type="checkbox"]').on('click', function() {
        let isChecked = $(this).prop('checked');

        $('.section-regSearchResult-table td input[type="checkbox"]').prop('checked', isChecked);
    });
});



// 날짜 기본값 반영
let today = new Date();
let startDate = new Date();
startDate.setDate(today.getDate() - 7);

let startDateString = startDate.toISOString().split('T')[0];
let endDateString = today.toISOString().split('T')[0];


$("#startDate").val(startDateString);
$("#endDate").val(endDateString);


// 검색 버튼
$("#historySearch").click(function (){

    let productCode = $("#productCode").val().toUpperCase();
    let productName = $("#productName").val().toUpperCase();
    let stockComment = $("#stockComment").val();
    let stockTypeDefalut = $("#stockType").val();
    let startDate = $("#startDate").val();
    let endDate = $("#endDate").val();

    let data = {
        productCode: productCode,
        productName: productName,
        stockComment: stockComment,
        stockType: stockTypeDefalut,
        startDate: startDate,
        endDate: endDate
    };
    if (productCode != "" || productName != "" || stockComment != "" || stockTypeDefalut != "" || startDate != "" ||  endDate != "") {

        console.log(data);
        $.ajax({

            url: "/inventory/admin_inventory_search",
            data: data,
            type: 'post',
            success: function (data) {
                console.log(data);

                $("#searchResultTable tbody").empty();

                $.each(data, function (index, stock) {
                    console.log(data)
                    let row = $("<tr></tr>");
                    row.append("<td>" + stock.stockNo + "</td>");
                    row.append("<td>" + stock.productCode + "</td>");
                    row.append("<td>" + stock.product.productName + "</td>");
                    row.append("<td>" + stock.stockType + "</td>");
                    row.append("<td>" + stock.stockDate + "</td>");
                    row.append("<td>" + stock.stockAmount + "</td>");
                    row.append("<td>" + stock.product.productAmount + "</td>");
                    row.append("<td>" + stock.stockComment + "</td>");

                    $("#searchResultTable tbody").append(row);
                });
            },
            error: function (error) {
                alert(error);
            }
        });
    } else {
        alert("최소 1개 이상의 옵션 입력해야 함")
    }
});

// 테이블 정렬
$(document).ready(function() {
    $('#searchResultTable').DataTable();
});
