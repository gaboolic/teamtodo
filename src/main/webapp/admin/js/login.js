$(document).ready(function() {
	/*$("input").each(function() {
		var defaultValue = $(this).val();
		$(this).focus(function() {
			if ($(this).val() === defaultValue) {
				$(this).val("");
				$(this).addClass("focus");
			}
		});
		$(this).blur(function() {
			if($(this).val() === ""){
				$(this).val(defaultValue);
				$(this).removeClass("focus");
			}
		});
	});*/
	$(".select-box ul li").click(function() {
		var thisParent = $(this).parent().parent(),
			thisVal = $(this).text();
		
		if (thisParent.find("input").val() !== thisVal) {
			thisParent.find("input").val(thisVal);
			thisParent.find("li").show();
			$(this).hide();
		}
	});
	$(".select-box ul li").eq(0).click();
});