var editor = null;
function getCK(){//<!-- ckeditor的初始化 -->
    if(typeof CKEDITOR == "undefined")
        alert("加载失败！");
    else{
        editor = CKEDITOR.replace('ckeditor',{
                filebrowserUploadUrl : 'uploadImage',
                height: 300,
                width : 500
            }
        );

    }

}
window.onload =function(){
    getCK();//<!-- ckeditor的初始化 -->
    setContent();
};