$(document).ready(function () {
  $(".clickable-row").click(function () {
    $(".content-box-large").load("/storages/details .content-box-large >*", {
      "path": $(this).attr('path')
    }, function () {
      $.getScript("/js/scripts.js");
    });
  });
});

$(document).ready(function () {
  $("a.refresh").click(function () {
    $(".panel-body").load("/storages table");
    window.location.reload();
  });
});