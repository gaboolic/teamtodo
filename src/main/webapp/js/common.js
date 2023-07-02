if (true) {
    app = angular.module("app", ['ngDraggable'], function ($httpProvider) {
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
        }
    );
}

app.controller("commonController", function ($scope, $http) {
    $http.get("/message/self.do")
        .success(function (response) {
            $scope.self = response.user;
        });
    $scope.messageList = [];
    $scope.showMessage = function () {
        $scope.isShowMessage = !$scope.isShowMessage;
        if ($scope.isShowMessage) {
            $http.get("/message/show.do")
                .success(function (response) {
                    $scope.messageList = response.messageList;
                    if ($scope.messageList.length == 0) {
                        $scope.isShowMessage = false;
                    }
                });
        }
    };
    $scope.ignoreMessage = function (message) {
        $http.get("/message/ignore.do?id=" + message.id)
            .success(function () {
                console.log("success")
                for (var i = 0; i < $scope.messageList.length; i++) {
                    if (message.id == $scope.messageList[i].id) {
                        $scope.messageList.splice(i, 1);
                    }
                }
                if ($scope.messageList.length == 0) {
                    $scope.isShowMessage = false;
                }
            });
    };
    $scope.refreshMessageList = function () {
        $http.get("/message/show.do")
            .success(function (response) {
                $scope.messageList = response.messageList;
                if ($scope.messageList.length == 0) {
                    $scope.isShowMessage = false;
                }
            });
    };
    $scope.refreshMessageList();
    setInterval(function () {
        $scope.refreshMessageList();
    }, 30000);
});