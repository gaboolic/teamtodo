var app = angular.module('animateApp', ['ngRoute', 'ngAnimate'],
    function ($httpProvider) {
        // Use x-www-form-urlencoded Content-Type
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';

        /**
         * The workhorse; converts an object to x-www-form-urlencoded serialization.
         * @param {Object} obj
         * @return {String}
         */
        var param = function (obj) {
            var query = '', name, value, fullSubName, subName, subValue, innerObj, i;

            for (name in obj) {
                value = obj[name];

                if (value instanceof Array) {
                    for (i = 0; i < value.length; ++i) {
                        subValue = value[i];
                        fullSubName = name + '[' + i + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                }
                else if (value instanceof Object) {
                    for (subName in value) {
                        subValue = value[subName];
                        fullSubName = name + '[' + subName + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                }
                else if (value !== undefined && value !== null)
                    query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
            }

            return query.length ? query.substr(0, query.length - 1) : query;
        };

        // Override $http service's default transformRequest
        $httpProvider.defaults.transformRequest = [function (data) {
            return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
        }];
    });
app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'userCenter.html',
            controller: 'userCenterController'
        })
        .when('/message', {
            templateUrl: 'message.html',
            controller: 'messageController'
        })
});

app.controller('userCenterController', function ($scope,$http) {
    $scope.pageClass = 'userCenter';


    $http.get("/message/self.do")
        .success(function (response) {
            $scope.self = response.user;
        });
    $scope.refreshMessageList = function () {
        $http.get("/message/show.do")
            .success(function (response) {
                $scope.messageList = response.messageList;
            });
    };
    $scope.refreshMessageList();

    $scope.returnMessage = function(){
        location.href="/indexApp.html#/message"
    }
});

app.controller('messageController', function ($scope,$http) {
    $scope.pageClass = 'message';

    $scope.refreshMessageList = function () {
        $http.get("/message/show.do")
            .success(function (response) {
                $scope.messageList = response.messageList;
            });
    };
    $scope.refreshMessageList();
});

