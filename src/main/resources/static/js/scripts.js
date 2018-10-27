$(".clickable-row").click(function () {
  $(".content-box-large").load("/storages/details .content-box-large >*", {
    "path": $(this).find('td').eq(2).text()
  }, function () {
    $.getScript("/js/scripts.js");
  });
});

$("a.refresh").click(function () {
  $(".panel-body").load("/storages table");
  window.location.reload();
});