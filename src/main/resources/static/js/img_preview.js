$('#input_img').change(function(e) {
  const [file] = e.target.files;
  const preview = $('#preview');
  
  if (file) {
    preview.attr('src', URL.createObjectURL(file)); // 画面が更新されるまで画像のURLを保持
    preview.show();
  } else {
    preview.hide();
  }
});