var form = $("#form");
$(function() {
    form.submit(function (e) {
        e.preventDefault();
        var fieldValue = $("#phoneNumber").val();
        if (validate(fieldValue)) {
            $.ajax({
                type: 'get',
                url: '/api/identifyCountry/' + fieldValue,
                data: form.serialize(),
                dataType: 'json',
                statusCode: {
                    200: function (data) {
                        var elementById = document.getElementById("resp");
                        var resp = data['response'];
                        if (data['responseCode'] === 200) {
                            elementById.innerHTML = "Country: " + resp;
                        } else {
                            elementById.innerHTML = "Error: " + resp;
                        }
                    }
                }
            })
        }
    });
});

function validate(text) {
    var elementById = document.getElementById("resp");
    if (text.length < 8) {
        elementById.innerHTML = "Number you've entered is too short";
        return false;
    }
    if (text.length > 15) {
        elementById.innerHTML = "Number you've entered is too long";
        return false;
    }
    return true;

}