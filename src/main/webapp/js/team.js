app.controller("teamController", function ($scope, $http, $sce) {
    $scope.showHotDailyLog = function () {
        $scope.showDiaryList = true;
        $scope.listType = 1;
        $http.get("/team/showHotDailyLog.do")
            .success(function (response) {
                $scope.diaryList = response.dailyLogList;
            });
    };

    $scope.showAtMeDailyLog = function () {
        $scope.showDiaryList = true;
        $scope.listType = 2;
        $http.get("/team/showAtMeDailyLog.do")
            .success(function (response) {
                $scope.diaryList = response.dailyLogList;
            });
    };
    $scope.showAtMeDailyLog();

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
        for (var i = 0; i < $scope.diaryList.length; i++) {
            $scope.diaryList[i].on = false;
        }
        diary.on = true;

        $scope.diary = null;
        $scope.peopleList = null;
        $http.get("/dailyLog/detail.do?id=" + diary.id)
            .success(function (response) {
                $scope.diary = response.dailyLog;
                $scope.diary.content = $sce.trustAsHtml($scope.diary.content);
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
    $scope.at = function (user) {
        for (var i = 0; i < $scope.members.length; i++) {
            $scope.members[i].on = false;
        }
        user.on = true;

        $scope.isShowOtherDiaryDetail = false;

        $scope.currentVisitUser = user;
        var yearMonth = $scope.currentYear + "-" + $scope.currentMonth;
        console.log(yearMonth)
        $http.get("/team/showOtherDailyLog.do?" +
            "userId=" + user.id +
            "&yearMonth=" + yearMonth)
            .success(function (response) {

                $scope.otherDailyLogMap = response.dailyLogMap;
                $scope.otherDailyLogCount = 0;
                for (var k in $scope.otherDailyLogMap) {
                    $scope.otherDailyLogCount++;
                }
            });
    };
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
                $scope.diary.content = $sce.trustAsHtml($scope.diary.content);
                $scope.replyList = response.replyList;
                $scope.fileList = response.fileList;
                $scope.peopleList = response.visitList;
            });
    };
    $scope.replyOther = function (reply) {
        $scope.replyContent = "回复 " + reply.name + ":";
        document.getElementById("replyTextarea").focus();
    };
    $scope.cancelReply = function () {
        $scope.replyContent = null;
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
                $scope.replyList.unshift(reply);

                $scope.replyContent = null;
            });
        $scope.replyContent = null;
    };

    $scope.otherReplyOther = function (reply) {
        $scope.otherReplyContent = "回复 " + reply.name + ":";
        document.getElementById("otherReplyTextarea").focus();

    };
    $scope.cancelOtherReply = function () {
        $scope.otherReplyContent = null;
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
                $scope.replyList.unshift(reply);

                $scope.otherReplyContent = null;
            });
        $scope.otherReplyContent = null;
    };


    //通知查看逻辑
    var hrefArr = window.location.href.split("?");
    if (hrefArr != null && hrefArr.length == 2) {
        var showId = hrefArr[1];
        console.log(showId);


        $http.get("/dailyLog/detail.do?id=" + showId)
            .success(function (response) {
                $scope.diary = response.dailyLog;
                $scope.diary.content = $sce.trustAsHtml($scope.diary.content);
                $scope.replyList = response.replyList;
                $scope.fileList = response.fileList;
                $scope.peopleList = response.visitList;

                for (var i = 0; i < $scope.diaryList.length; i++) {
                    if ($scope.diaryList[i].id == $scope.diary.id) {
                        $scope.diaryList[i].on = true;
                    }
                }
                $scope.diary.on = true;
            });

    }
});
