(function($){
$.fn.dialog = function(o) {
	var ops = {
			height: 300,
			title: "",
			content: "",
			contentUrl: "",
			btns: []
		};
	
	$.extend(ops, o);
	
	$(this).click(function() {
		$(".wd-dialog").remove();
		
		$("body").after("<div class=\"wd-dialog\">" +
							"<div class=\"header\">" + 
								"<h1>" + ops.title + "</h1>" +
								"<span class=\"ic ic-close\"></span>" +
							"</div>" +
							"<div class=\"dialog-body\">Loading...</div>" +
						"</div>");
		$(".wd-dialog .header span").click(function() {
			$(this).parent().parent().remove();
		});
		
		$(".wd-dialog").css("height", o.height + "px");
		
		if (ops.content) {
			$(".wd-dialog .dialog-body").html(ops.content);
		} else if (ops.contentUrl) {
			$.ajax({
				url: ops.contentUrl,
				cache: false,
				dataType : 'html', 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8", 
				success: function(html){
					$(".wd-dialog .dialog-body").html(html);
				}
			});
		}
	});
};
})(jQuery);