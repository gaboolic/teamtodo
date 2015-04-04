Zepto(function ($) {
    $("p").on("dragstart", function (ev) {
        console.log("zepto:"+ev.target.id);
        ev.dataTransfer.setData("Text", ev.target.id);
    });

    $("a").click(function(event){
        event.preventDefault();
    });
});