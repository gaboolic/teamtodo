var ckeditor = window.ckeditor;
var directives = angular.module('ckeditor', []);

directives.directive('ckeditor', function() {
    return {
        require : '?ngModel',
        link : function(scope, element, attrs, ngModel) {
            var ckeditor = CKEDITOR.replace(element[0], {
                filebrowserUploadUrl : 'uploadImage',
            height: 500,
            width : 720,
//            toolbar :
//                [
//                  [ 'Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink','-','linkbutton' ]
//                ],
                extraPlugins:"linkbutton"    //注册linkbutton,也可在config.js中注册
            });
            if (!ngModel) {
                return;
            }
            ckeditor.on('instanceReady', function() {
                ckeditor.setData(ngModel.$viewValue);
            });
            ckeditor.on('pasteState', function() {
                scope.$apply(function() {
                    ngModel.$setViewValue(ckeditor.getData());
                });
            });
            ngModel.$render = function(value) {
                ckeditor.setData(ngModel.$viewValue);
            };
        }
    };
});