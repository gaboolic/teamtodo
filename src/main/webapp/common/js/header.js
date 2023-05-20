$(document).ready(function () {
    $("#usernameInput").val(localStorage.username);
    $("#passwordInput").val(localStorage.password);
    $("#loginBtn").click(function () {
        var form = $("#loginForm");
        $.post("/login",
            form.serialize(),
            function (data) {
                data = $.parseJSON(data);
                if (data.code == 0) {
                    localStorage.username = $("#usernameInput").val();
                    localStorage.password = $("#passwordInput").val();
                    $("#loginDiv").modal('hide');
                    $("#loginoutLi").html("<a href='/logout'>退出(" + $("#usernameInput").val() + ")</a>");
                    refreshLeft();
                } else {
                    $("#loginMsg").html(data.desc + ":" + data.message);
                }
            });
    });
    $("#registerBtn").click(function () {
        var form = $("#registerForm");
        $.post("/register",
            form.serialize(),
            function (data) {
                data = $.parseJSON(data);
                if (data.code == 0) {
                    localStorage.username = $("#regUsernameIpt").val();
                    localStorage.password = $("#regPasswordIpt").val();
                    $("#registerDiv").modal('hide');
                    $("#loginoutLi").html("<a href='/logout'>退出(" + $("#regUsernameIpt").val() + ")</a>");
                    refreshLeft();
                } else {
                    $("#registerMsg").html(data.desc + ":" + data.message);
                }
            });
    });
});
function refreshLeft() {
    $("#addMessageLi").html("<a data-toggle='modal' data-target='#messageDiv' href='#'>添加留言</a>");
    $("#addReplyLi").html("<a data-toggle='modal' data-target='#replyDiv' href='#'>添加评论</a>");
}


$(document).ready(function () {
    var returnTop = $(".return-top");
    var returnBottom = $(".return-bottom");
    var height = $(document).height() - $(window).height();
    var top = $(document).scrollTop();
    if (top == 0) {
        returnTop.hide();
    }
    if (top > height - 100) {
        returnBottom.hide();
    } else {
        returnBottom.show();
    }

    returnTop.click(function () {
        $('html,body').animate({scrollTop: 0}, 600);
    });
    returnBottom.click(function () {
        $('html,body').animate({scrollTop: $(document).height()}, 600);
    });
    $(document).scroll(function () {
        var height = $(document).height() - $(window).height();
        var top = $(document).scrollTop();
        if (top > 0) {
            returnTop.show();
        } else {
            returnTop.hide();
        }
        if (top > height - 100) {
            returnBottom.hide();
        } else {
            returnBottom.show();
        }
    });
});