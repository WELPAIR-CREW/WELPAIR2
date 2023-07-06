
// 결제유형, 상품 카테고리 검색
document.addEventListener('DOMContentLoaded', function () {

    let salesForm = document.querySelector('.section-salesSearch form');

    salesForm.addEventListener('submit', function (event) {
        event.preventDefault();
        let formData = new FormData(salesForm);
        let searchParams = new URLSearchParams(formData);
        console.log('검색-----------------------------------');
        console.log('salesForm : ' + salesForm);
        console.log('formData : ' + formData);
        console.log('searchParams : ' + searchParams);
        console.log('검색1-----------------------------------');

        $.ajax({
            url: '/sales/admin_sales',
            method: 'post',
            data: searchParams.toString(),
            dataType: 'json',
            success: function (response) {
                console.log('성공 왔어??');
                console.log('response : ' + response);
                monthlyList = response;
                console.log('monthlyList : ' + monthlyList);
                displaySalesData(monthlyList);
                salesChart (monthlyList);
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);

            }
        });
    });
});


function displaySalesData(monthlyList) {
    console.log(monthlyList);

    let salesTable1 = document.querySelector('.salesTable tbody');
    salesTable1.innerHTML = '';

    if (!monthlyList || monthlyList.length === 0) {
        return;
    }

    let months = monthlyList.map(sales => sales.month);
    let totalSales = monthlyList.map(sales => sales.totalSales);

    // 1-1. 매출 데이터 테이블

    let salesTable = document.querySelector('.salesTable tbody')
    salesTable.innerHTML = '';

    // 1행 : 월
    let row1 = document.createElement('tr');
    let monthHeader = document.createElement('th');
    monthHeader.textContent = '월';
    row1.appendChild(monthHeader);

    for (let i = 0; i < monthlyList.length; i++) {
        let sales = monthlyList[i];
        let monthCell = document.createElement('td');
        monthCell.textContent = sales.month + '월';
        row1.appendChild(monthCell);
    }
    salesTable.appendChild(row1);

    // 2행 : 매출액
    let row2 = document.createElement('tr');
    let salesHeader = document.createElement('th');
    salesHeader.textContent = '매출액';
    row2.appendChild(salesHeader);

    for (let i = 0; i < monthlyList.length; i++) {
        let sales = monthlyList[i];
        let totalSalesCell = document.createElement('td');
        totalSalesCell.textContent = formatNumber(sales.totalSales);
        row2.appendChild(totalSalesCell);
    }

    salesTable.appendChild(row2);

}

function salesChart (monthlyList){
    // 1-2. 매출 데이터 그래프
    let months = monthlyList.map(sales => sales.month);
    let totalSales = monthlyList.map(sales => sales.totalSales);

    let salesChart = document.getElementById('salesChart').getContext('2d');

    if (salesChart.chart) {
        salesChart.chart.destroy();
    }

    salesChart.chart = new Chart(salesChart, {
        type: 'bar',
        data: {
            labels: months.map(month => month + '월'),
            datasets: [{
                label: 'Monthly Sales',
                data: totalSales,
                backgroundColor: 'rgba(75, 192, 192, 0.5)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    display: true
                },
                y: {
                    display: false
                }
            },
            plugins: {
                legend: {
                    display: false
                }
            },
            layout: {
                padding: {
                    left: 10,
                    right: 10,
                    top: 10,
                    bottom: 10
                }
            },
            barPercentage: 0.6, // Adjust the width of the bars (0.1 to 1)
            categoryPercentage: 0.8 // Adjust the space between bars (0.1 to 1)
        }
    });
}

function formatNumber(number) {
    return number.toLocaleString() + '원';
}