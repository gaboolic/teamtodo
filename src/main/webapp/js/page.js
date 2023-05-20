var animateApp = angular.module('animateApp', ['ngRoute', 'ngAnimate']);
animateApp.directive('whenScrolled', function() {
    return function(scope, elm, attr) {
        var raw = elm[0];
        elm.bind('scroll', function() {
            if (raw.scrollTop+raw.offsetHeight >= raw.scrollHeight) {
                scope.$apply(attr.whenScrolled);
            }
        });
    };
});
animateApp.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'page-home.html'
            //controller: 'mainController'
        })
        .when('/about', {
            templateUrl: 'page-about.html'
            //controller: 'aboutController'
        })
        .when('/contact', {
            templateUrl: 'page-contact.html'
            //controller: 'contactController'
        });

});

animateApp.controller('MainCtrl', function($rootScope, $scope, $http) {

    $scope.next = function(){
        console.log("sdfsd")
       // location.href="/test/page.html#/about"
    }
});

animateApp.controller('mainController', function($scope) {
    $scope.pageClass = 'page-home';
    $scope.next = function(){
        console.log("sdfsd")
        location.href="/test/page.html#/about"
    }
});

animateApp.controller('aboutController', function($scope) {
    $scope.pageClass = 'page-about';
    $scope.next = function(){
        location.href="/test/page.html#/contact"
    }
});

animateApp.controller('contactController', function($scope) {
    $scope.pageClass = 'page-contact';
    $scope.next = function(){
        location.href="/test/page.html#/home"
    }
});