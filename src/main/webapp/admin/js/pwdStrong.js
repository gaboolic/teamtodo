$(document).ready(function() {
	$(".edit input#new").keydown(function() {
		var thisValue = $(this).val();
		if (thisValue.length < 6 || thisValue.length > 13) {
			$(this).addClass("wrong");
			$(".comment li").removeClass("blue");
		} else {
			if (/[^\da-zA-Z]/.test(thisValue)) {
				$(this).addClass("wrong");
				$(".comment li").removeClass("blue");
			} else {
				$(this).removeClass("wrong");
				var isNum = /[\d]/.test(thisValue) ? 1 : 0,
					isLowCap = /[a-z]/.test(thisValue) ? 1 : 0,
					isUpCap = /[A-Z]/.test(thisValue) ? 1 : 0,
					count = isNum + isLowCap + isUpCap;
				
				for (var i = 0; i < count; i++) {
					$(".comment li").eq(i).addClass("blue");
				}
			}
		}
	});
});