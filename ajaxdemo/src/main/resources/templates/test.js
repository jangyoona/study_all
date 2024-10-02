$('#stock-quotes-btn2').on('click', function(e) {
    e.preventDefault();
    new Promise((resolve, reject) => {
        $.ajax({
            url : "/demo/historical-stock-quotes2",
            method : "GET",
            type : "json",
            success : function(data, status, xhr) {
                resolve(data);
            },
            error : function(xhr, status, err) {
                reject(err);
            }
        });
    })
    .then(data => {
        console.log(data);
        const xLabels = [], yValues = [];
        $.each(data, function(idx, quote) {
            xLabels.push(quote.date);
            yValues.push(quote.close);
        });
        showDataOnLineChart(xLabels, "NVDA", yValues);
    })
    .catch(err => {
        console.log(err);
        console.log(err.status);
    });


});