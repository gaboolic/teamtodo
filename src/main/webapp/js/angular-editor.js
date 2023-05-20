/*global window,location*/
(function (window) {
    'use strict';

    var Simditor = window.Simditor;
    var directives = angular.module('simditor', []);

    directives.directive('simditor', function () {

        var TOOLBAR_DEFAULT = [ 'title', 'bold', 'italic', 'underline', 'strikethrough',
            'color', 'ol', 'ul', 'blockquote', 'code', 'table',
            'link', 'image', 'attachment','myat', 'hr', 'indent', 'outdent' ];

        return {
            require: "?^ngModel",
            link: function (scope, element, attrs, ngModel) {
                element.append("<div></div>");

                var toolbar = scope.$eval(attrs.toolbar) || TOOLBAR_DEFAULT;
                scope.simditor = new Simditor({
                    textarea: element.children()[0],
                    pasteImage: true,
                    toolbar: toolbar,
                    defaultImage: 'assets/images/image.png',
                    upload: {
                        url: '/upload/file.do', //文件上传的接口地址
                        params: null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
                        fileKey: 'fileDataFileName', //服务器端获取文件数据的参数名
                        connectionCount: 3,
                        leaveConfirm: '正在上传文件'
                    },
                    mention: {
                        items: [
                            {
                                id: 1,
                                name: "春雨",
                                pinyin: "chunyu",
                                abbr: "cy",
                                url: "http://www.example.com"
                            },
                            {
                                id: 2,
                                name: "夏荷",
                                pinyin: "xiahe",
                                abbr: "xh"
                            },
                            {
                                id: 3,
                                name: "秋叶",
                                pinyin: "qiuye",
                                abbr: "qy"
                            },
                            {
                                id: 4,
                                name: "冬雪",
                                pinyin: "dongxue",
                                abbr: "dx"
                            }
                        ]
                    }
                });

                var $target = element.find('.simditor-body');

                function readViewText() {

                    ngModel.$setViewValue($target.html());

                    if (attrs.ngRequired != undefined && attrs.ngRequired != "false") {

                        var text = $target.text();

                        if (text.trim() === "") {
                            ngModel.$setValidity("required", false);
                        } else {
                            ngModel.$setValidity("required", true);
                        }
                    }

                }
                scope.simditor.on('instanceReady', function() {
                    scope.simditor.setData(ngModel.$viewValue);
                });
                scope.simditor.on('pasteState', function() {
                    scope.$apply(function() {
                        ngModel.$setViewValue(scope.simditor.getData());
                    });
                });
                scope.simditor.on('valuechanged', function () {
                    scope.$apply(readViewText);
                });

                ngModel.$render = function () {
                    $(".simditor-toolbar").after($(".editTitleArea"));

                    scope.simditor.focus();
                    $target.html(ngModel.$viewValue);
                };


            }
        };
    });
}(window));
