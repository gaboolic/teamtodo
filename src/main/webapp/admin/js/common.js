$(document).ready(function(){
	$(".selector ul li").click(function(){
		$(".selector ul li").removeClass("selected");
		$(this).addClass("selected");
		var selectVal=$(this).attr("name");
		$(".container div").each(function(){
			if($(this).attr("id")===selectVal){
				$(".container div").hide();
				$(this).show();
			}
		});
	});
	$(".container section ul li").eq(0).click();

	$("input.to-check-all").click(function() {
		var isChecked = $(this).attr("checked") === "checked";
		
		if (isChecked) {
			$(this).parent().parent().parent().find("input[type='checkbox']").attr("checked", "checked");
		} else {
			$(this).parent().parent().parent().find("input[type='checkbox']").removeAttr("checked");
		}
	});
	$("li.to-check-all").click(function() {
		var isChecked = $(this).parent().parent().find("input.to-check-all").attr("checked") === "checked";
		
		if (!isChecked) {
			$(this).parent().parent().find("input[type='checkbox']").attr("checked", "checked");
		} else {
			$(this).parent().parent().find("input[type='checkbox']").removeAttr("checked");
		}
	});

	$(".to-for-file").click(function() {
		var thisParent = $(this).parent();
		thisParent.find(".for-file").click();
	});
	$(".for-file").change(function() {
		var thisVal = $(this).val();
		$(this).parent().find(".for-file-addr").val(thisVal);
	});
});