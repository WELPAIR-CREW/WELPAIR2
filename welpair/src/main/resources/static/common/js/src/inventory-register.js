
let currentPage = 1;
const rowsPerPage = 10;
let totalPages = null;

function showPage(page) {
    $("#searchResultTable tbody tr").hide();
    const startIndex = (page - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;
    $("#searchResultTable tbody tr").slice(startIndex, endIndex).show();
}

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

            const totalRows = $("#searchResultTable tbody tr").length;
            totalPages = Math.ceil(totalRows / rowsPerPage);

            showPage(currentPage);

        },
        error: function (error) {
            alert(error);
        }
    });
});

// 일괄반영 버튼
$("#allOption").click(function () {


    let stockType = $("#inventypeOption option:selected").val();
    let stockDate = $("#stockDate").val();
    let stockComment = $("#stockComment").val();
    let stockAmount = $("#stockAmount").val();

    if (stockAmount <= 0) {
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

// 페이징

function updatePaginationButtons() {
    $(".paging1").empty();

    const startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
    const endPage = Math.min(startPage + 4, totalPages);

    // if (startPage > 1) {
    $(".paging1").append("<span id='prevPage'>&lt;</span>");
    // }

    for (let i = startPage; i <= endPage; i++) {
        if (i === currentPage) {
            $(".paging1").append("<span class='currentPage' style='color: #4D4D4D;'>" + i + "</span>");
        } else {
            $(".paging1").append("<span class='pageNum'>" + i + "</span>");
        }
    }

    // if (endPage < totalPages) {
    $(".paging1").append("<span id='nextPage'>&gt;</span>");
    // }
}

$(".paging1").on("click", ".pageNum", function() {
    currentPage = parseInt($(this).text());
    showPage(currentPage);
    updatePaginationButtons();
});

$(".paging1").on("click", "#prevPage", function() {
    if (currentPage > 1) {
        currentPage--;
        showPage(currentPage);
        updatePaginationButtons();
    }
});

$(".paging1").on("click", "#nextPage", function() {
    if (currentPage < totalPages) {
        currentPage++;
        showPage(currentPage);
        updatePaginationButtons();
    }
});
