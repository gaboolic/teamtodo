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
            templateUrl: 'home.html',
            controller: 'homeController'
        })
        .when('/otherDiary', {
            templateUrl: 'otherDiary.html',
            controller: 'otherDiaryController'
        })
        .when('/otherDiaryList', {
            templateUrl: 'otherDiaryList.html',
            controller: 'otherDiaryListController'
        })
        .when('/otherDiaryList/:id/:name', {
            templateUrl: 'otherDiaryList.html',
            controller: 'otherDiaryListController'
        })
        .when('/otherDiaryDetail', {///other/：id
            templateUrl: 'otherDiaryDetail.html',
            controller: 'otherDiaryDetailController'
        })
        .when('/otherDiaryDetail/:id', {///other/：id
            templateUrl: 'otherDiaryDetail.html',
            controller: 'otherDiaryDetailController'
        })
        .when('/visitList', {
            templateUrl: 'visitList.html',
            controller: 'visitListController'
        })
        .when('/replyList', {
            templateUrl: 'replyList.html',
            controller: 'replyListController'
        })

});
app.controller('homeController', function ($scope, $http) {
    $scope.pageClass = 'home';
    init($scope, $http);


});
app.controller('otherDiaryController', function ($scope, $http) {
    $scope.pageClass = 'otherDiary';
    init($scope, $http);
});
app.controller('otherDiaryListController', function ($scope, $http, $routeParams,$sce) {
    $scope.pageClass = 'otherDiaryList';
    init($scope, $http);

    $scope.username=$routeParams.name;
    var id = $routeParams.id;

    $scope.currentDate = new Date();

    $scope.refreshYearMonth = function(){
        $scope.currentYear = $scope.currentDate.getYear() + 1900;
        $scope.currentMonth = $scope.currentDate.getMonth() + 1;
        if ($scope.currentMonth < 10) {
            $scope.currentMonth = "0" + $scope.currentMonth;
        }

        var prevDate = new Date();
        prevDate.setTime($scope.currentDate.getTime());
        prevDate.setMonth(prevDate.getMonth()-1);
        console.log("prevDate:"+prevDate.format("yyyy-MM-dd"));
        $scope.prevYear = prevDate.getYear() + 1900;
        $scope.prevMonth = prevDate.getMonth() + 1;
        if ($scope.prevMonth < 10) {
            $scope.prevMonth = "0" + $scope.prevMonth;
        }

        var nextDate = new Date();
        nextDate.setTime($scope.currentDate.getTime());
        nextDate.setMonth(nextDate.getMonth()+1);
        console.log("nextDate:"+nextDate.format("yyyy-MM-dd"));

        $scope.nextYear = nextDate.getYear() + 1900;
        $scope.nextMonth = nextDate.getMonth() + 1;
        if ($scope.nextMonth < 10) {
            $scope.nextMonth = "0" + $scope.nextMonth;
        }

        var yearMonth = $scope.currentYear + "-" + $scope.currentMonth;
        console.log(yearMonth)
        $http.get("/team/showOtherDailyLogList.do?" +
            "userId=" + id +
            "&yearMonth=" + yearMonth)
            .success(function (response) {
                $scope.otherDailyLogList = angular.forEach(angular.fromJson(response.dailyLogList), function (diary) {
                    diary.content = $sce.trustAsHtml(diary.content);
                });
                //$scope.otherDailyLogList = response.dailyLogList;
            });
    };
    $scope.refreshYearMonth();

    $scope.prev = function(){
        $scope.currentDate.setMonth($scope.currentDate.getMonth()-1);
        $scope.refreshYearMonth();
    };
    $scope.next = function(){
        $scope.currentDate.setMonth($scope.currentDate.getMonth()+1);
        $scope.refreshYearMonth();
    };
});
app.controller('otherDiaryDetailController', function ($scope, $http, $routeParams,$sce) {

    $scope.pageClass = 'otherDiaryDetail';
    init($scope, $http);

    var id = $routeParams.id;
    $scope.diary = null;
    $scope.peopleList = null;
    $http.get("/dailyLog/detail.do?id=" + id)
        .success(function (response) {
            $scope.diary = response.dailyLog;
            $scope.diary.contentHtml = $sce.trustAsHtml($scope.diary.content);
            $scope.replyList = response.replyList;
            $scope.fileList = response.fileList;
            $scope.visitList = response.visitList;
        });
    $scope.isDetailShow = true;
    $scope.isReplyShow = false;
    $scope.isVisitShow = false;

    $scope.goVisitList = function(){
        $scope.isDetailShow = false;
        $scope.isVisitShow = true;
    };
    $scope.goMainDiaryDetail = function(){
        $scope.isDetailShow = true;
        $scope.isVisitShow = false;
        $scope.isReplyShow = false;
    };
    $scope.goReplyList = function(){
        $scope.isDetailShow = false;
        $scope.isReplyShow = true;
    };
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
    $scope.showHotDailyLog = function () {
        $scope.showDiaryList = true;
        $scope.listType = 1;
        $http.get("/team/showHotDailyLog.do")
            .success(function (response) {
                $scope.diaryList = response.dailyLogList;
            });
    };
    $scope.showHotDailyLog();

    $scope.showAtMeDailyLog = function () {
        $scope.showDiaryList = true;
        $scope.listType = 2;
        $http.get("/team/showAtMeDailyLog.do")
            .success(function (response) {
                $scope.diaryList = response.dailyLogList;
            });
    };
    $scope.showOtherDailyLog = function () {
        $scope.showDiaryList = false;
    };
    $scope.returnDiaryList = function () {
        $scope.showDiaryList = true;
        $scope.isShowOtherDiaryDetail = false;
        $scope.showOtherDailyLog();
        $scope.diary = null;
    };
    $scope.showDiaryDetail = function (diary) {
        $scope.diary = null;
        $scope.peopleList = null;
        $http.get("/dailyLog/detail.do?id=" + diary.id)
            .success(function (response) {
                $scope.diary = response.dailyLog;
                $scope.replyList = response.replyList;
                $scope.fileList = response.fileList;
                $scope.peopleList = response.visitList;
            });
    };
    $scope.initOrg = function () {
        $http.get("/team/myAuthColleagues.do").success(function (data) {
            $scope.members = data.members;
            $scope.departments = data.departments;
        });
    };
    $scope.initOrg();
    $scope.refreshDepartment = function (department) {
        $http.get("/team/myAuthColleagues.do?depId=" + department.id).success(function (data) {

            $scope.members = data.members;
            $scope.departments = data.departments;
        });
    };
    $scope.currentYear = new Date().getYear() + 1900;
    $scope.currentMonth = new Date().getMonth() + 1;
    if ($scope.currentMonth < 10) {
        $scope.currentMonth = "0" + $scope.currentMonth;
    }
    $scope.yearList = [2014, 2015, 2016, 2017];
    $scope.monthList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

    $scope.showSelectYear = function () {
        $scope.isSelectYearShow = !$scope.isSelectYearShow;
    };
    $scope.showSelectMonth = function () {
        $scope.isSelectMonthShow = !$scope.isSelectMonthShow;
    };
    $scope.selectYear = function (year) {
        $scope.currentYear = year;
        $scope.isSelectYearShow = false;

        $scope.changeDate();
        $scope.at($scope.currentVisitUser);
    };
    $scope.selectMonth = function (month) {
        $scope.currentMonth = month;
        if ($scope.currentMonth < 10) {
            $scope.currentMonth = "0" + $scope.currentMonth;
        }
        $scope.isSelectMonthShow = false;

        $scope.changeDate();
        $scope.at($scope.currentVisitUser);
    };
    $scope.changeDate = function () {
        var d = new Date();
        d.setYear($scope.currentYear);
        d.setMonth($scope.currentMonth);

        var today = new Date();
        var last = new Date(today.getFullYear(), today.getMonth() + 1, 0);//获取当前月最后一天时间
        $scope.dayList = [];
        for (var i = 1; i <= last.getDate(); i++) {
            $scope.dayList.push(i);
        }
    };
    $scope.changeDate();

    $scope.showOtherDiaryDetail = function (diary) {
        if (diary == null) {
            return;
        }
        $scope.isShowOtherDiaryDetail = true;
        $scope.diary = null;
        $scope.peopleList = null;
        $http.get("/dailyLog/detail.do?id=" + diary.id)
            .success(function (response) {
                $scope.diary = response.dailyLog;
                $scope.replyList = response.replyList;
                $scope.fileList = response.fileList;
                $scope.peopleList = response.visitList;
            });
    };

    $scope.submitReply = function () {
        var reply = {};
        reply.id = $scope.diary.id;
        reply.content = $scope.replyContent;

        $http.post("/dailyLog/reply.do", reply)
            .success(function (data) {
                reply.createTime = new Date().format("yyyy-MM-dd HH:mm:ss");
                reply.name = data.name;
                reply.headImage = data.headImage;
                $scope.replyList.push(reply);

                $scope.replyContent = null;
            })
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
    };
}
