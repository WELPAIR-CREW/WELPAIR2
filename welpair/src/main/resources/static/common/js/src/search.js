$(document).ready(function() {

    let currentPage = 1;
    const rowsPerPage = 10;
    let totalPages = null;

    function showPage(page) {
        // $(".section-searchResult ul li").hide();
        const startIndex = (page - 1) * rowsPerPage;
        const endIndex = startIndex + rowsPerPage;
        const $listItems = $(".section-searchResult ul li");

        $listItems.hide().slice(startIndex, endIndex).show();
    }


// 상세 검색 버튼
    $("#detailSearch").click(function () {

        console.log("검색 들어옴 ")
        // 이전 검색 결과 삭제
        $("#section-searchResult ul").empty();

        // 검색어 입력 값 가져오기
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
        if (title != "" || categoryCode != "" || minPrice != "" || maxPrice != "") {

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

                        let anchor = $("<a></a>").attr("href", "/products/" + search.sellItemPage.no);

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

                        productInfoDiv.append(titleDiv);
                        productInfoDiv.append(priceDiv);

                        listItem.append(productInfoDiv);

                        $(".section-searchResult ul").append(listItem);
                    });

                    const totalItems = data.length;
                    totalPages = Math.ceil(totalItems / rowsPerPage);

                    showPage(currentPage);
                    updatePaginationButtons();
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

        if (totalPages <= 1) {
            return;
        }

        const startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
        const endPage = Math.min(startPage + 4, totalPages);

        if (startPage > 1) {
            $(".paging").append("<span id='prevPage'><</span>");
        }

        for (let i = startPage; i <= endPage; i++) {
            if (i === currentPage) {
                $(".paging").append("<span class='currentPage' style='color: #4D4D4D;'>" + i + "</span>");
            } else {
                $(".paging").append("<span class='pageNum'>" + i + "</span>");
            }
        }

        if (endPage < totalPages) {
            $(".paging").append("<span id='nextPage'>></span>");
        }
    }

    $(".paging").on("click", ".pageNum", function () {
        currentPage = parseInt($(this).text());
        showPage(currentPage);
        updatePaginationButtons();
    });

    $(".paging").on("click", "#prevPage", function () {
        if (currentPage > 1) {
            currentPage--;
            showPage(currentPage);
            updatePaginationButtons();
        }
    });

    $(".paging").on("click", "#nextPage", function () {
        if (currentPage < totalPages) {
            currentPage++;
            showPage(currentPage);
            updatePaginationButtons();
        }
    });
});