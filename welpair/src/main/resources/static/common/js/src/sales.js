//
// let salesJson = /*[[${salesJson}]]*/ '';
//
// // let monthlySales = JSON.parse(salesJson);
// let monthlySales = JSON.parse(document.currentScript.getAttribute('data-sales-data'));
// console.log(monthlySales);
//
// let months = monthlySales.map(sales => sales.month);
// let totalSales = monthlySales.map(sales => sales.totalSales);
//
// // 1-1. 매출 데이터 테이블
//
// let salesTable = document.querySelector('.salesTable tbody')
// salesTable.innerHTML = '';
//
// // 1행 : 월
// let row1 = document.createElement('tr');
// let monthHeader = document.createElement('th');
// monthHeader.textContent = '월';
// row1.appendChild(monthHeader);
//
// for (let i = 0; i < monthlySales.length; i++) {
//     let sales = monthlySales[i];
//     let monthCell = document.createElement('td');
//     monthCell.textContent = sales.month + '월';
//     row1.appendChild(monthCell);
// }
// salesTable.appendChild(row1);
//
// // 2행 : 매출액
// let row2 = document.createElement('tr');
// let salesHeader = document.createElement('th');
// salesHeader.textContent = '매출액';
// row2.appendChild(salesHeader);
//
// for (let i = 0; i < monthlySales.length; i++) {
//     let sales = monthlySales[i];
//     let totalSalesCell = document.createElement('td');
//     totalSalesCell.textContent = formatNumber(sales.totalSales);
//     row2.appendChild(totalSalesCell);
// }
//
// salesTable.appendChild(row2);
//
//
// // 1-2. 매출 데이터 그래프
//
//
// let salesChart = document.getElementById('salesChart').getContext('2d');
//
// new Chart(salesChart, {
//     type: 'bar',
//     data: {
//         labels: months.map(month => month + '월'),
//         datasets: [{
//             label: 'Monthly Sales',
//             data: totalSales,
//             backgroundColor: 'rgba(75, 192, 192, 0.5)',
//             borderColor: 'rgba(75, 192, 192, 1)',
//             borderWidth: 1
//         }]
//     },
//     options: {
//         responsive: true,
//         scales: {
//             x: {
//                 display: true,
//             },
//             y: {
//                 display: false
//             }
//         },
//         plugins: {
//             legend: {
//                 display: false
//             }
//         },
//         layout: {
//             padding: {
//                 left: 10,
//                 right: 10,
//                 top: 10,
//                 bottom: 10
//             }
//         },
//         barPercentage: 0.6, // Adjust the width of the bars (0.1 to 1)
//         categoryPercentage: 0.8 // Adjust the space between bars (0.1 to 1)
//     }
// });
//
//
// function formatNumber(number) {
//     return number.toLocaleString() + '원';
// }
function formatNumber(number) {
    return number.toLocaleString() + '원';
}
document.addEventListener('DOMContentLoaded', function() {
    // let salesJson = /*[[${salesJson}]]*/ '';
    // let monthlySales = JSON.parse(salesJson);
    // console.log(monthlySales);

    let monthlySales = JSON.parse(salesJson);
    console.log(monthlySales);


    let months = monthlySales.map(sales => sales.month);
    let totalSales = monthlySales.map(sales => sales.totalSales);

    // 1-1. 매출 데이터 테이블
    let salesTable = document.querySelector('.salesTable tbody');
    salesTable.innerHTML = '';

    // 1행 : 월
    let row1 = document.createElement('tr');
    let monthHeader = document.createElement('th');
    monthHeader.textContent = '월';
    row1.appendChild(monthHeader);

    for (let i = 0; i < monthlySales.length; i++) {
        let sales = monthlySales[i];
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

    for (let i = 0; i < monthlySales.length; i++) {
        let sales = monthlySales[i];
        let totalSalesCell = document.createElement('td');
        totalSalesCell.textContent = formatNumber(sales.totalSales);
        row2.appendChild(totalSalesCell);
    }

    salesTable.appendChild(row2);

    // 1-2. 매출 데이터 그래프
    let salesChart = document.getElementById('salesChart').getContext('2d');

    new Chart(salesChart, {
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
                    display: true,
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


});

