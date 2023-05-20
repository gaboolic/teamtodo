var app =
    angular
        .module("app", [
            'simditor'
        ], function ($httpProvider) {
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
app.controller('mainController', function ($scope, $http) {
    $scope.editor = '';

    $scope.clearEditor = function () {
        $scope.editor = '';
    };
    $scope.initOrg = function () {
        if ($scope.simditor != undefined) {
            $scope.simditor.focus();
        }
        $http.get("/team/myColleagues.do").success(function (data) {
            $scope.members = data.members;
            $scope.departments = data.departments;
        });
    };
    $scope.initOrg();
    $scope.refreshDepartment = function (department) {
        $scope.simditor.focus();
        $http.get("/team/myColleagues.do?depId=" + department.id).success(function (data) {

            $scope.members = data.members;
            $scope.departments = data.departments;
        });
    };
    var atMemberIdList = new Array();
    $scope.at = function (member) {
        $scope.simditor.focus();

        if (atMemberIdList.indexOf(member.id) <= 0) {
            atMemberIdList.push(member.id);
            if ($scope.currDiary.atList == null) {
                $scope.currDiary.atList = new Array();
            }
            $scope.currDiary.atList.push(member);
        }
    };


    $http.get("/dailyLog/detail.do?date=" + localStorage.diaryCurrentDate)
        .success(function (response) {
            $scope.currDiary = response.dailyLog;
            if ($scope.currDiary == null) {
                $scope.currDiary = {};
                $scope.currDiary.date = localStorage.diaryCurrentDate;
                $scope.currDiary.title = "工作日志";
            }
        });

    $scope.autoFinish = function () {
        $scope.currDiary.content = "<h4>Keep：今日完成了哪些工作</h4><p><br></p><h4>Problem：遇到了什么问题？</h4><p><br></p><h4>Try：准备尝试哪些措施</h4><p><br></p><h4>Plan：明日的任务</h4><p><br></p>";
    };
    $scope.addDiary = function () {
        var diary = {};
        diary.title = $scope.currDiary.title;
        diary.content = $scope.currDiary.content;
        if (localStorage.diaryCurrentDate == undefined) {
            localStorage.diaryCurrentDate = new Date().format("yyyy-MM-dd");
        }
        diary.date = localStorage.diaryCurrentDate;
        var atIds = "";
        if ($scope.currDiary.atList != null) {
            for (var i = 0; i < $scope.currDiary.atList.length; i++) {
                atIds += $scope.currDiary.atList[i].id;
                atIds += ",";
            }
        }
        if (atIds.length > 0) {
            atIds = atIds.substring(0, atIds.length - 1);
        }
        diary.at = atIds;

        var uploads = "";
        if (files != null) {
            for (var j = 0; j < files.length; j++) {
                uploads += files[j].file_path;
                uploads += ",,";
                uploads += files[j].file_name;
                uploads += ",,,";
            }
        }
        if (uploads.length > 0) {
            uploads = uploads.substring(0, uploads.length - 3);
        }
        diary.uploads = uploads;
        $http.post("/dailyLog/add.do", diary)
            .success(function (data) {
                location.href = "/log/index.html";
            })
    };
    $scope.updateDiary = function () {
        var diary = {};
        diary.id = $scope.currDiary.id;
        diary.title = $scope.currDiary.title;
        diary.content = $scope.currDiary.content;
        if (localStorage.diaryCurrentDate == undefined) {
            localStorage.diaryCurrentDate = new Date().format("yyyy-MM-dd");
        }
        diary.date = localStorage.diaryCurrentDate;

        $http.post("/dailyLog/update.do", diary)
            .success(function (data) {
                location.href = "/log/index.html";
            })
    };

});


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
            });
    };
    $scope.refreshMessageList = function () {
        $http.get("/message/show.do")
            .success(function (response) {
                $scope.messageList = response.messageList;
            });
    };
    $scope.refreshMessageList();
    setInterval(function () {
        $scope.refreshMessageList();
    }, 30000);
});