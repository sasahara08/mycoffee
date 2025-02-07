$(function () {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");

  $("form[id^='fov_']").on("submit", function (e) {
    // preventDefaultはブラウザの持っている元々の処理を抑制。プリティプリンター遷移対策
    e.preventDefault();
    
    var $form = $(this);

    $.ajax({
      url: $form.attr("action"),
      method: "get",
      dataType: "json",
      beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
      }
    }).done(function (data) {
      // fav:true / notfav:false
      if (data.isFav) {
        $form.find("span").css("color", "tomato")
      } else {
        $form.find("span").css("color", "#5f6368")
      }
    }).fail(function (data) {
      console.log(data.isFav);
      alert('送信エラー' + data);
    });
  });
});

// $(function () {
//   var token = $("meta[name='_csrf']").attr("content");
//   var header = $("meta[name='_csrf_header']").attr("content");

//   $("form[id^='fov_']").on("submit", function (e) {
//     // preventDefaultはブラウザの持っている元々の処理を抑制。プリティプリンター遷移対策
//     e.preventDefault();
    
//     var $form = $(this);

//     $.ajax({
//       url: $form.attr("action"),
//       method: "get",
//       dataType: "json",
//       beforeSend: function (xhr) {
//         xhr.setRequestHeader(header, token);
//       }
//     }).done(function (data) {
//       // fav:true / notfav:false
//       if (data.isFav) {
//         $form.children("input").val("fav中").css("background-color", "tomato")
//       } else {
//         $form.children("input").val("favなし").css("background-color", "lightblue")
//       }
//     }).fail(function (data) {
//       console.log(data.isFav);
//       alert('送信エラー' + data);
//     });
//   });
// });