$(document).ready(function() {
	$(".clickable-row").click(function() {
		// alert($(this).find('td').eq(1).text());
		$(".panel-body").load("/storages/details table", {"rootPath": $(this).find('td').eq(2).text()});
	});

	$("a.refresh").click(function() {
		// alert($(this).find('td').eq(1).text());
		$(".panel-body").load("/storages table");
		window.location.reload();
	});
});