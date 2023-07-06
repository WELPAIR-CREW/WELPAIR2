let currentPage = 1;
const rowsPerPage = 10;
let totalPages = null;

function showPage(page) {
    $("#searchResultTable tbody tr").hide();
    const startIndex = (page - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;
    $("#searchResultTable tbody tr").slice(startIndex, endIndex).show();
}



// 상세 검색 버튼
$("#detailSearch").click(function (){

    console.log("검색 들어옴 ")

    let title = $("#title").val().toUpperCase();
    let categoryCode = $("#categoryCode").val();
    let minPrice = $("#minPrice").val();
    let maxPrice = $("#maxPrice").val();

    let data = {
        title: title,
        categoryCode: categoryCode,
        minPrice: minPrice,
        maxPrice: maxPrice
    };
    if (title != "" || categoryCode != "" || minPrice != "" ||  maxPrice != "") {

        console.log(data);
        const json = JSON.stringify(data)
        console.log(json);

        $.ajax({

            url: "/search/detail",
            data: json,
            contentType: 'application/json',
            type: 'post',
            success: function (data) {
                console.log(data);

                // $("#searchResultTable tbody").empty();

                // $.each(data, function (index, stock) {
                //     console.log(data)
                //     let row = $("<tr></tr>");
                //     row.append("<td>" + stock.stockNo + "</td>");
                //     row.append("<td>" + stock.productCode + "</td>");
                //     row.append("<td>" + stock.product.productName + "</td>");
                //     row.append("<td>" + stock.stockType + "</td>");
                //     row.append("<td>" + stock.stockDate + "</td>");
                //     row.append("<td>" + stock.stockAmount + "</td>");
                //     row.append("<td>" + stock.product.productAmount + "</td>");
                //     row.append("<td>" + stock.stockComment + "</td>");
                //
                //     $("#searchResultTable tbody").append(row);
                // });

                // const totalRows = $("#searchResultTable tbody tr").length;
                // totalPages = Math.ceil(totalRows / rowsPerPage);
                //
                // showPage(currentPage);
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
    $(".paging").empty();

    const startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
    const endPage = Math.min(startPage + 4, totalPages);

    // if (startPage > 1) {
    $(".paging").append("<span id='prevPage'>&lt;</span>");
    // }

    for (let i = startPage; i <= endPage; i++) {
        if (i === currentPage) {
            $(".paging").append("<span class='currentPage' style='color: #4D4D4D;'>" + i + "</span>");
        } else {
            $(".paging").append("<span class='pageNum'>" + i + "</span>");
        }
    }

    // if (endPage < totalPages) {
    $(".paging").append("<span id='nextPage'>&gt;</span>");
    // }
}

$(".paging").on("click", ".pageNum", function() {
    currentPage = parseInt($(this).text());
    showPage(currentPage);
    updatePaginationButtons();
});

$(".paging").on("click", "#prevPage", function() {
    if (currentPage > 1) {
        currentPage--;
        showPage(currentPage);
        updatePaginationButtons();
    }
});

$(".paging").on("click", "#nextPage", function() {
    if (currentPage < totalPages) {
        currentPage++;
        showPage(currentPage);
        updatePaginationButtons();
    }
});