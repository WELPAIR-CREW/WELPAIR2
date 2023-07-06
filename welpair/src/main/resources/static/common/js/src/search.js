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
        $.ajax({
            url: "/search/detail",
            data: data,
            type: 'post',
            success: function (data) {
                console.log(data);

                const searchResultsList = $(".section-searchResult ul");
                searchResultsList.empty();

                $.each(data, function (index, search) {
                    console.log(data)
                    let listItem = $("<li></li>");
                    listItem.addClass("section-searchResult-product");

                    let anchor = $("<a></a>").attr("href", "#");

                    let thumbnailDiv = $("<div></div>").addClass("thumbnailImage");
                    thumbnailDiv.append($("<span></span>").text(search.thumbnailImage.thumbnailImageFileName));

                    let image = $("<img>").attr("src", "#").attr("alt", "");

                    anchor.append(thumbnailDiv);
                    anchor.append(image);
                    listItem.append(anchor);

                    let productInfoDiv = $("<div></div>").addClass("product-info");

                    let titleDiv = $("<div></div>").addClass("searchProdTitle");
                    titleDiv.append($("<span></span>").text(search.sellPage.title));

                    let priceDiv = $("<div></div>").addClass("searchProdPrice");
                    priceDiv.append($("<span></span>").text(search.sellPrice));

                    let sellPageNoDiv = $("<div></div>").addClass("sellPageNo");
                    sellPageNoDiv.append($("<span></span>").text(search.sellItemPage.no));

                    productInfoDiv.append(titleDiv);
                    productInfoDiv.append(priceDiv);
                    productInfoDiv.append(sellPageNoDiv);

                    listItem.append(productInfoDiv);

                    $(".section-searchResult ul").append(listItem);
                });

                const totalItems = $(".section-searchResult ul li").length;

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