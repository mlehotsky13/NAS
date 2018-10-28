$(document).ready(function () {
  $(".clickable-row").click(function () {
    // $(".content-box-large").load("/storages/details .content-box-large >*", {
    //   "path": $(this).attr('path')
    // }, function () {
    //   $.getScript("/js/scripts.js");
    // });

    window.location = '/storages/details?path=' + $(this).closest("tr").attr('path');
  });
});

$(document).ready(function () {
  $("#createDirBtn").click(function () {
    $.post("/storages/createDir", { "path": $("h4").text(), "dirname": $("input[name=dirname]").val() }, function () {
      $("#createDirModal").modal('hide');
    });
    window.location.reload();
  });
});

$(document).ready(function () {
  $(".deleteRecord").click(function () {
    $.ajax({
      url: '/storages/deleteDir?path=' + $(this).closest("tr").attr("path"),
      type: 'DELETE',
    });
    window.location.reload();
  });
});

$(document).ready(function () {
  $("a.refresh-storages").click(function () {
    $(".panel-body").load("/storages table");
    window.location.reload();
  });
});

$(document).ready(function () {
  $("a.refresh-details").click(function () {
    $(".panel-body").load("/storages/details table", "path=?" + $("h4").text());
    window.location.reload();
  });
});