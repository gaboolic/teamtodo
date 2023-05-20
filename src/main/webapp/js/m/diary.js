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
            templateUrl: 'myDiary.html',
            controller: 'myDiaryController'
        })
        .when('/myDiary', {
            templateUrl: 'myDiary.html',
            controller: 'myDiaryController'
        })
        .when('/myDiary/:date', {
            templateUrl: 'myDiary.html',
            controller: 'myDiaryController'
        })
        .when('/create', {
            templateUrl: 'create.html',
            controller: 'createController'
        })
        .when('/create/:date', {
            templateUrl: 'create.html',
            controller: 'createController'
        })
        .when('/edit', {
            templateUrl: 'edit.html',
            controller: 'editController'
        })
        .when('/edit/:date', {
            templateUrl: 'edit.html',
            controller: 'editController'
        })
        .when('/myDiaryDetail', {
            templateUrl: 'myDiaryDetail.html',
            controller: 'myDiaryDetailController'
        })
        .when('/myDiaryDetail/:date', {
            templateUrl: 'myDiaryDetail.html',
            controller: 'myDiaryDetailController'
        })
        .when('/auth', {
            templateUrl: 'auth.html',
            controller: 'authController'
        })
        .when('/authCustom', {
            templateUrl: 'authCustom.html',
            controller: 'authCustomController'
        })
        .when('/visitList', {
            templateUrl: 'visitList.html',
            controller: 'visitListController'
        })
        .when('/replyList', {
            templateUrl: 'replyList.html',
            controller: 'replyListController'
        })
        .when('/calendar', {
            templateUrl: 'calendar.html',
            controller: 'calendarController'
        })
        .when('/calendar/:date', {
            templateUrl: 'calendar.html',
            controller: 'calendarController'
        })

});
app.controller('myDiaryController', function ($scope, $http, $sce, $routeParams) {
    $scope.pageClass = 'myDiary';
    init($scope, $http);
    var date = $routeParams.date;
    if (date == null) {
        date = new Date().format("yyyy-MM-dd")
    }

    $http.get("/dailyLog/canCreate.do")
        .success(function (response) {
            $scope.isCanCreate = response.isCanCreate;

        });

    $http.get("/dailyLog/list.do")//获取自定义权限列表
        .success(function (response) {
            $scope.diaryList = angular.forEach(angular.fromJson(response.dailyLogList), function (diary) {
                diary.content = $sce.trustAsHtml(diary.content);
            });
        });
    $scope.createDiary = function () {
        if ($scope.isCanCreate == 1) {
            location.href = "/log/m/index.html#/create/" + date;
        }
    };
    $scope.isGray = function () {
        return $scope.isCanCreate != 1;
    };
});
app.controller('createController', function ($scope, $http, $routeParams) {
    $scope.pageClass = 'create';
    init($scope, $http);

    $scope.addDiary = function () {
        var diary = {};
        diary.title = $scope.diary.title;
        diary.content = $scope.diary.content;
        var date = $routeParams.date;
        diary.date = date;
        var atIds = "";
//        if ($scope.currDiary.atList != null) {
//            for (var i = 0; i < $scope.currDiary.atList.length; i++) {
//                atIds += $scope.currDiary.atList[i].id;
//                atIds += ",";
//            }
//        }
//        if (atIds.length > 0) {
//            atIds = atIds.substring(0, atIds.length - 1);
//        }
//        diary.at = atIds;
//
//        var uploads = "";
//        if (files != null) {
//            for (var j = 0; j < files.length; j++) {
//                uploads += files[j].file_path;
//                uploads += ",,";
//                uploads += files[j].file_name;
//                uploads += ",,,";
//            }
//        }
//        if (uploads.length > 0) {
//            uploads = uploads.substring(0, uploads.length - 3);
//        }
//        diary.uploads = uploads;
        $http.post("/dailyLog/add.do", diary)
            .success(function (data) {
                location.href = "/log/m/index.html#/myDiary";
            })
    };

});
app.controller('editController', function ($scope, $http, $routeParams, $sce) {
    $scope.pageClass = 'edit';
    init($scope, $http);
    var date = $routeParams.date;

    $http.get("/dailyLog/detail.do?date=" + date)
        .success(function (response) {
            $scope.diary = response.dailyLog;
            if (response.dailyLog != null) {
                $scope.diaryContent = $sce.trustAsHtml(response.dailyLog.content);
            }
            $scope.peopleList = response.visitList;
            $scope.fileList = response.fileList;
        });

    $scope.returnDetail = function () {
        location.href = "/log/m/index.html#/myDiaryDetail/" + date;
    };
    $scope.updateDiary = function () {
        var diary = {};
        diary.id = $scope.diary.id;
        diary.title = $scope.diary.title;
        diary.content = $scope.diary.content;
        $http.post("/dailyLog/update.do", diary)
            .success(function (data) {
                location.href = "/log/m/index.html#/myDiaryDetail/" + date;
            })
    };
    $scope.deleteDiary = function () {//删除日志
        $http.get("/dailyLog/delete.do?id=" + $scope.diary.id)//
            .success(function (response) {
                $scope.diary = null;
                location.href = "/log/m/index.html#/myDiary";
            });
    };
});
app.controller('myDiaryDetailController', function ($scope, $http, $routeParams, $sce) {
    $scope.pageClass = 'myDiaryDetail';
    init($scope, $http);
    var date = $routeParams.date;
    $http.get("/dailyLog/detail.do?date=" + date)
        .success(function (response) {
            $scope.diary = response.dailyLog;
            if (response.dailyLog != null) {
                $scope.diaryContent = $sce.trustAsHtml(response.dailyLog.content);
            }
            $scope.replyList = response.replyList;
            $scope.fileList = response.fileList;
            $scope.peopleList = response.visitList;
        });
    $scope.returnEdit = function () {
        location.href = "/log/m/index.html#/edit/" + date;
    };
    $scope.createDiary = function () {
        if (date <= new Date().format("yyyy-MM-dd"))
            location.href = "/log/m/index.html#/create/" + date;
    };

    $scope.isShowNewAdd = function () {//判断是新建还是补写

        return date <= new Date().format("yyyy-MM-dd");

    };

    $scope.goVisitList = function () {
        $scope.isDetailShow = false;
        $scope.isVisitShow = true;
    };
    $scope.goMainDiaryDetail = function () {
        $scope.isDetailShow = true;
        $scope.isVisitShow = false;
        $scope.isReplyShow = false;
    };
    $scope.goMainDiaryDetail();
    $scope.goReplyList = function () {
        $scope.isDetailShow = false;
        $scope.isReplyShow = true;
    };

    $scope.submitOtherReply = function () {
        var reply = {};
        reply.id = $scope.diary.id;
        reply.content = $scope.otherReplyContent;

        $http.post("/dailyLog/reply.do", reply)
            .success(function (data) {
                reply.createTime = new Date().format("yyyy-MM-dd HH:mm:ss");
                reply.name = data.name;
                reply.headImage = data.headImage;

                $scope.replyList.push(reply);

                $scope.replyContent = null;
            })
    }
});
app.controller('authController', function ($scope, $http) {
    $scope.pageClass = 'auth';
    init($scope, $http);
    $scope.isTaskPublic = true;
    $scope.isAuthPageShow = true;
    $scope.isIssuedShow = false;

    $scope.returnCustom = function () {
        $scope.isAuthPageShow = false;
        $scope.isIssuedShow = false;
        $scope.isAuthListShow = true;
    };
    $scope.returnAuth = function () {
        $scope.isAuthPageShow = true;
        $scope.isIssuedShow = false;
        $scope.isAuthListShow = false;
    };
    $scope.returnAuthSet = function () {
        $scope.isAuthPageShow = false;
        $scope.isIssuedShow = true;
        $scope.isAuthListShow = false;
    };

    $scope.returnAuthEnter = function () {
        $scope.isAuthPageShow = false;
        $scope.isIssuedShow = false;
        $scope.isAuthListShow = true;

        var auth = "";
        for (var i = 0; i < $scope.authUserList.length; i++) {
            auth += $scope.authUserList[i].id;
            auth += ",";
        }
        if (auth.length > 0) {
            auth = auth.substr(0, auth.length - 1);
        }
        $http.get("/dailyLog/changeAuth.do?auth=" + auth)
            .success(function () {

            })
    };

    $scope.toggleTaskAuthPrivate = function () {
        $scope.isTaskPrivate = !$scope.isTaskPrivate;
        $scope.isTaskPublic = false;
    };
    $scope.toggleTaskAuthPublic = function () {
        $scope.isTaskPublic = !$scope.isTaskPublic;
        $scope.isTaskPrivate = false;
    };
    $http.get("/dailyLog/getAuth.do")//获取默认权限
        .success(function (response) {

            var auth = response.auth;
            console.log(auth);
            if (auth == 0) {
                $scope.isTaskPublic = true;
                $scope.isTaskPrivate = false;
                console.log("==0");

            } else if (auth == -1) {
                $scope.isTaskPrivate = true;
                $scope.isTaskPublic = false;
                console.log("==-1");

            } else {
                $scope.isTaskPrivate = false;
                $scope.isTaskPublic = false;
                $scope.authUserList = response.authUsers;
                console.log("else");

            }
        });
    $scope.saveAuth = function () {
        var auth = 0;
        if ($scope.isTaskPublic) {
            auth = 0;
        } else {
            auth = -1;
        }

        $http.get("/dailyLog/changeAuth.do?auth=" + auth)
            .success(function () {
                console.log("保存权限成功")
                location.href = "/log/m/index.html#/myDiary";
            });
    };


    $scope.initOrg = function () {
        $http.get("/team/myColleagues.do").success(function (data) {
            $scope.members = data.members;
            $scope.departments = data.departments;
        });
    };
    $scope.initOrg();
    $scope.refreshDepartment = function (department) {
        $http.get("/team/myColleagues.do?depId=" + department.id).success(function (data) {

            $scope.members = data.members;
            $scope.departments = data.departments;
        });
    };
    $scope.join = function (mem) {
        mem.on = !mem.on;
        if (mem.on) {
            if ($scope.authUserList == null) {
                $scope.authUserList = [];
            }
            $scope.authUserList.push(mem);
        } else {
            for (var j = 0; j < $scope.joauthUserListnList.length; j++) {
                if (mem.id == $scope.authUserList[j].id) {
                    $scope.authUserList.splice(j, 1);
                }
            }
        }
    };
    $scope.clearJoin = function () {
        $scope.joinList = [];
        for (var j = 0; j < $scope.members.length; j++) {
            $scope.members[j].on = false;
        }
    };
    $scope.selectAll = function () {
        if ($scope.authUserList == null) {
            $scope.authUserList = [];
        }
        for (var j = 0; j < $scope.members.length; j++) {
            if ($scope.authUserList.indexOf($scope.members[j]) == -1) {
                $scope.members[j].on = true;
                $scope.authUserList.push($scope.members[j]);
            }
        }
    };


    $scope.toggleSelectAll = function () {
        $scope.isSelectAll = !$scope.isSelectAll;
        if ($scope.isSelectAll) {
            $scope.selectAll();
        } else {
            $scope.clearJoin();
        }
    }
});
app.controller('authCustomController', function ($scope, $http) {
    $scope.pageClass = 'authCustom';
    init($scope, $http);


});
app.controller('visitListController', function ($scope, $http) {
    $scope.pageClass = 'visitList';
    init($scope, $http);


});
app.controller('replyListController', function ($scope, $http) {
    $scope.pageClass = 'replyList';
    init($scope, $http);


});


function init($scope, $http) {

}


app.controller('calendarController', function ($scope, $http, $routeParams) {
    $scope.pageClass = 'calendar';
    var date = $routeParams.date;
    if (date == null) {
        date = new Date().format("yyyy-MM-dd");
    }
    var arr = date.split("-");
    var d = new Date();
    d.setYear(arr[0]);
    d.setMonth(arr[1] - 1);
    d.setDate(arr[2]);
    console.log(d.format("yyyy-MM-dd"));
    $scope.currentDate = d;

    $scope.returnList = function () {
        location.href = "/log/m/index.html#/myDiary/";
    };
    $scope.returnTodo = function () {
        location.href = "/log/m/index.html#/myDiaryDetail/" + $scope.currentDate.format("yyyy-MM-dd");
    };
    $scope.newCreate = function () {
        location.href = "/log/m/index.html#/create/" + $scope.currentDate.format("yyyy-MM-dd");
    };

    $scope.selectDay = function (date) {
        $scope.currentDate = date;
        $scope.cal = createDateCal();
    };
    $scope.isToday = function (date) {
        return date.format("yyyy-MM-dd") == $scope.currentDate.format("yyyy-MM-dd");
    };
    $scope.isSunday = function (date) {
        return date.getDay() == 0 || date.getDay() == 6;
    };
    $scope.isStar = function (date) {
        if ($scope.starList == null) {
            return false;
        }

        for (var i = 0; i < $scope.starList.length; i++) {
            if ($scope.starList[i] == date.format("yyyy-MM-dd")) {
                return true;
            }
        }
        return false;
    };
    $scope.isShowCreate = function () {
        if ($scope.isStar($scope.currentDate)) {
            return false;
        }
        if ($scope.currentDate.getTime() > new Date().getTime()) {
            return false;
        }
        return true;
    };
    $scope.isNextMonth = function (date) {
        if (date.getMonth() != $scope.currentDate.getMonth()) {
            return true;
        }
        return false;
    };

    $scope.cal = createDateCal();
    function createDateCal() {
        var startDate = new Date();
        startDate.setYear($scope.currentDate.getYear() + 1900);
        startDate.setMonth($scope.currentDate.getMonth());
        startDate.setDate(1);
        startDate.setDate(startDate.getDate() - startDate.getDay());

        var cal = [
            [],
            [],
            [],
            [],
            [],
            []
        ];
        for (var i = 0; i < 42; i++) {
            var d = new Date();
            d.setTime(startDate.getTime());
            cal[Math.floor(i / 7)][i % 7] = d;
            startDate.setDate(startDate.getDate() + 1);
        }
        return cal;
    }

    $scope.calLeft = function () {//向左一月
        $scope.currentDate.setDate(1);
        $scope.currentDate.setMonth($scope.currentDate.getMonth() - 1);
        $scope.cal = createDateCal();
        $http.get("/dailyLog/showStar.do?yearMonth=" + $scope.currentDate.format("yyyy-MM"))
            .success(function (response) {
                $scope.starList = response.dateList;
            });
    };
    $scope.calRight = function () {//向右一月
        $scope.currentDate.setDate(1);
        $scope.currentDate.setMonth($scope.currentDate.getMonth() + 1);
        $scope.cal = createDateCal();
        $http.get("/dailyLog/showStar.do?yearMonth=" + $scope.currentDate.format("yyyy-MM"))
            .success(function (response) {
                $scope.starList = response.dateList;
            });
    };
    $http.get("/dailyLog/showStar.do?yearMonth=" + $scope.currentDate.format("yyyy-MM"))
        .success(function (response) {
            $scope.starList = response.dateList;
        });
});