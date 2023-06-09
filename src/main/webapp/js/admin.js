app = angular.module("app", [], function ($httpProvider) {
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
app.controller("adminController", function ($scope, $http) {

    $scope.username = localStorage.username;
    $scope.password = localStorage.password;

    $scope.login = function () {
        var login = {};
        login.username = $scope.username;
        login.password = $scope.password;
        login.verifyCode = $scope.verifyCode;

        console.log(login)
        $http.post("/admin/login.do", login)
            .success(function (result) {
                if (result.code != 0) {
                    alert(result.desc);
                } else {
                    localStorage.username = $scope.username;
                    localStorage.password = $scope.password;
                    window.location.href = '/admin/index.html';
                }
            })
            .error(function (result) {
                alert("失败");
            });
    };


    $http.get("/task/show.do?type=0")//收纳箱
        .success(function (response) {
            $scope.acceptList = response.taskList;
        });
    $scope.isDelayTask = function (task) {
        if (task.date < $scope.currentDate.format("yyyy-MM-dd")) {
            return true;
        }
        return false;
    };
});

app.controller("adminLoginListController", function ($scope, $http) {
    $http.get("/admin/loginList.do")//收纳箱
        .success(function (response) {
            $scope.loginList = response.loginList;
        })
});

app.controller("adminUserListController", function ($scope, $http) {
    $scope.initOrg = function () {
        $http.get("/admin/org.do").success(function (data) {
            $scope.members = data.members;
            $scope.departments = data.departments;
        });
    };
    $scope.initOrg();
    $scope.refreshDepartment = function (department) {
        $http.get("/admin/org.do?depId=" + department.id).success(function (data) {

            $scope.members = data.members;
            $scope.departments = data.departments;
        });
    };
    $scope.showAuth = function (mem) {
        $http.get("/admin/authList.do?id=" + mem.id)
            .success(function(response){
                $scope.authList = response.authList;
            })
    };
});