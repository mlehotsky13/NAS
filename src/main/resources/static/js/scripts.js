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
  $(document).on("click", "#createDirBtn", function () {
    $("#createDirModal").find("input[name=path]").val($("h4").text());
    $("#createDirModal").modal('show');
  });
});

$(document).ready(function () {
  $(document).on("click", "#uploadFileBtn", function () {
    $("#uploadFileModal").find("input[name=path]").val($("h4").text());
    $("#uploadFileModal").modal('show');
  });
});

$(document).ready(function () {
  $(document).on("click", ".deleteRecord", function () {
    $("#deleteRecordModal").find("input[name=path]").val($(this).closest("tr").attr("path"));
    $("#deleteRecordModal").modal('show');
  });
});

$(document).ready(function () {
  $(document).on("click", ".editRecord", function () {
    $("#editRecordModal").find("input[name=path]").val($(this).closest("tr").attr("path"));
    $("#editRecordModal").find("input[name=newname]").val($(this).closest("tr").find("span").first().text());
    $("#editRecordModal").modal('show');
  });
});

$(document).ready(function () {
  $(document).on("click", ".downloadRecord", function () {
	  var baseURL = window.location.origin;
	  var fileRecordURL = baseURL + "/storages/fileRecord" + $(this).closest("tr").attr("path");
	  var encoded = encodeURI(fileRecordURL);
	  
	  window.location.replace(encoded);
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
    $(".panel-body").load("/storages/details table", "path=" + $("h4").text());
    window.location.reload();
  });
});

$(document).ready(function () {
  $(document).on("click", "#logoutBtn", function () {
    window.location = '/logout';
  });
});

// $(document).ready(function() {
//     $('#uploadBtn').click(function(event){
//     	event.preventDefault();
//     	
//     	var form = $('#uploadForm')[0];
//     	var url = $('#uploadForm').first().attr('action');
//     	var data = new FormData(form);
// 
//     	$.ajax({
//     		type: "POST",
//             enctype: 'multipart/form-data',
//             // url: "./upload",
//             url: url,
//             data: data,
//             processData: false,
//             contentType: false,
//             cache: false,
//             success: function() {
//             	alert("bla");
//             	// window.location.reload();
//             }
//     	});
//     });     
// });       