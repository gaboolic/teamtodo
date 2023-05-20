
function nano(template, data) {
    return template.replace(/\{([\w\.]*)\}/g, function (str, key) {
//        alert(key.split(".").shift());
//        alert(key.split(".").shift());
        var keys = key.split("."), v = data[keys.shift()];
        alert(v)
        for (var i = 0, l = keys.length; i < l; i++) v = v[keys[i]];
        return (typeof v !== "undefined" && v !== null) ? v : "";
    });
}

$(document).ready(function () {
    $.get("http://localhost/static/demo/messageXml.htm", function (response) {
        var str = "";
        $(response).children().each(function (index) {
            //alert($(this).text());
//            str += "<div>" + $(this).find("mid").text() + "楼   " +
//                $(this).find("uname").text() +
//                $(this).find("content").text() + "<p>" + $(this).find("msgTime").text() + "</p></div>";

            var message={"mis":$(this).find("mid"),"uname":$(this).find("uname").text(),"content":$(this).find("content").text(),"msgTime":$(this).find("msgTime").text()};
            var data = {"message":message};
            //str += nano("<div>{message.mid}楼{message.uname} {message.content}<p>{message.msgTime}</p></div>",data);
            str += nano("<div>{mid}楼{uname} {content}<p>{msgTime}</p></div>",message);
        });

        $("#messages").append(str);
    })
});
