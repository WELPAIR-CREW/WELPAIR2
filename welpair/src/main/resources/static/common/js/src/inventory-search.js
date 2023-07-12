/* admin_inventory_search */

let currentPage = 1;
const rowsPerPage = 10;
let totalPages = null;

function showPage(page) {
    $("#searchResultTable tbody tr").hide();
    const startIndex = (page - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;
    $("#searchResultTable tbody tr").slice(startIndex, endIndex).show();
}


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

                const totalRows = $("#searchResultTable tbody tr").length;
                totalPages = Math.ceil(totalRows / rowsPerPage);

                showPage(currentPage);
            },
            error: function (error) {
                alert(error);
            }
        });
    } else {
        alert("최소 1개 이상의 옵션 입력해야 함")
    }
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