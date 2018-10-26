$(document).ready(function () {
  $(".clickable-row").click(function () {
    $(".content-box-large").load("/storages/details .content-box-large >*", {
      "path": $(this).find('td').eq(2).text()
    })
  });
});

$(document).ready(function () {
  $("a.refresh").click(function () {
    $(".panel-body").load("/storages table");
    window.location.reload();
  });
});