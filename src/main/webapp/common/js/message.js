$(document).ready(function () {
    $("#messageLi").attr("class", "on");
});


$(document).ready(function () {
    $("#messageBtn").click(function () {
        var form = $("#messageForm");
        $.post("/message/add",
            form.serialize(),
            function (data) {
                data = $.parseJSON(data);
                if(data.code == 0) {
                    $("#messageDiv").modal('hide');
                    var div = $("<div class='well'></div>");
                    div.html(data.content);
                    $("#messages").prepend(div);
                    console.log(data.content)
                } else {
                    $("#messageMsg").html(data.desc+":"+data.message);
                }
            });
    });
});