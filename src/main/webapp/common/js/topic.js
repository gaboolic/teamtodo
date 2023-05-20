$(document).ready(function () {
    $("#topicLi").attr("class", "on");
});
$(document).ready(function () {
    $("#replyBtn").click(function () {
        var form = $("#replyForm");
        $.post("/reply/add",
            form.serialize(),
            function (data) {
                data = $.parseJSON(data);
                if(data.code == 0) {
                    $("#replyDiv").modal('hide');
                    var div = $("<div class='well'></div>");
                    div.html(data.content);
                    $("#replys").append(div);
                    console.log(data.content)
                } else {
                    $("#replyMsg").html(data.desc+":"+data.message);
                }
            });
    });
});