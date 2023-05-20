$(document).ready(function(){
	$(".menu li a").click(function(){
		$(".menu li").removeClass("active");
		$(this).parent().addClass("active");
	});
	$(".menu li a").eq(0).click();
});