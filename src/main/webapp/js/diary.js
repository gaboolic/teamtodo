app.controller("diaryController", function ($scope, $http, $sce) {
    $scope.today = new Date();
    $scope.currentDate = new Date();//日历
    localStorage.diaryCurrentDate = $scope.currentDate.format("yyyy-MM-dd");//
    $scope.isCreateShow = false;
    $scope.isPersonLiseShow = false;
    $scope.isDiaryDetailShow = false;
    fillDailyLog();
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

    $scope.isShowNewCreate = function () {//判断是新建还是补写
        return $scope.diary == null && $scope.today.format("yyyy-MM-dd") == $scope.currentDate.format("yyyy-MM-dd");
    };
    $scope.isShowNewAdd = function () {//判断是新建还是补写
        var flag = $scope.diary == null && $scope.today.format("yyyy-MM-dd") != $scope.currentDate.format("yyyy-MM-dd");
        if (flag) {
            return $scope.currentDate.getTime() < new Date().getTime();
        }
        return false;
    };
    $scope.create = function () {
        $scope.isCreateShow = true;

    };
    $scope.saveDiary = function () {
        var diary = {};
        diary.title = $scope.diaryTitle;
        diary.content = $scope.diaryContent;
        diary.date = $scope.currentDate.format("yyyy-MM-dd");
        $http.post("/dailyLog/add.do", diary)
            .success(function (data) {
            })
    };
    $scope.deleteDiary = function () {//删除日志
        $http.get("/dailyLog/delete.do?id=" + $scope.diary.id)//
            .success(function (response) {
                $scope.diary = null;
            });
    };
//    $scope.joinList = [];
    $http.get("/dailyLog/getAuth.do")//获取默认权限
        .success(function (response) {
            $scope.authShow = response.auth;
            $scope.authUserList = response.authUsers;
            if (response.auth != 0 && response.auth != -1) {
//                var strArr = response.auth.split(",");
//                for(var i=0;i<strArr.length;i++) {
//                    var mem = {};
//                    mem.id = strArr[i];
//                    $scope.joinList.push(mem);
//                }

            }
        });
    $scope.changeAuthShow = function (num) {//更改权限
        $scope.authShow = num;
        if (num == 2) {
            $scope.isPersonLiseShow = !$scope.isPersonLiseShow;
        }
        else {
            $scope.isPersonLiseShow = false;
        }
    };
    $scope.saveAuth = function () {
        var auth = "";
        if ($scope.authShow == 0 || $scope.authShow == -1) {
            auth = $scope.authShow;
        } else {
            if ($scope.joinList != null) {
                var joinIds = "";
                for (var i = 0; i < $scope.joinList.length; i++) {
                    joinIds += $scope.joinList[i].id;
                    joinIds += ",";
                }
                if (joinIds.length > 0) {
                    joinIds = joinIds.substring(0, joinIds.length - 1);
                }
                auth = joinIds;
            }
        }
        $http.get("/dailyLog/changeAuth.do?auth=" + auth)
            .success(function () {
                alert("保存权限成功");
                location.href = "/log/setauth.html";
            });
    };
    $http.get("/dailyLog/list.do")//获取自定义权限列表
        .success(function (response) {
            $scope.diaryList = angular.forEach(angular.fromJson(response.dailyLogList), function (diary) {
                diary.content = $sce.trustAsHtml(diary.content);
            });

//                $scope.diaryList = response.dailyLogList;
//                for(var i=0;i< $scope.diaryList.length;i++){
//                    $sce.trustAsHtml($scope.diaryList[i].content)
//                }

        });
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
    $scope.changeDate = function (date) {//更改当前日期
        $scope.currentDate = date;
        localStorage.diaryCurrentDate = date.format("yyyy-MM-dd");//
        fillDailyLog();
        $scope.cal = createDateCal();
        $scope.isDiaryDetailShow = true;
    };
    $scope.isStarDate = function (date) {
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
    $scope.isCurrentDate = function (date) {
        return $scope.currentDate.format("yyyy-MM-dd") == date.format("yyyy-MM-dd");
    };
    function fillDailyLog() {
        $scope.diary = null;
        $scope.peopleList = null;
        $http.get("/dailyLog/detail.do?date=" + $scope.currentDate.format("yyyy-MM-dd"))
            .success(function (response) {
                $scope.diary = response.dailyLog;
                if (response.dailyLog != null) {
                    $scope.diaryContent = $sce.trustAsHtml(response.dailyLog.content);
                }
                $scope.peopleList = response.visitList;
                $scope.replyList = response.replyList;
                $scope.fileList = response.fileList;
            });
        $http.get("/dailyLog/showStar.do?yearMonth=" + $scope.currentDate.format("yyyy-MM"))
            .success(function (response) {
                $scope.starList = response.dateList;
            });
    };

    $scope.showDiaryDetail = function (diary) {
        localStorage.diaryCurrentDate = diary.date;
        var dateStr = diary.date;
        var d = new Date();
        var a = dateStr.split("-");
        d.setYear(a[0]);
        d.setMonth(a[1] - 1);
        d.setDate(a[2]);
        $scope.currentDate = d;
        $scope.cal = createDateCal();
        $scope.isDiaryDetailShow = true;
        $scope.diary = null;
        $scope.peopleList = null;
        $http.get("/dailyLog/detail.do?id=" + diary.id)
            .success(function (response) {
                $scope.diary = response.dailyLog;
                $scope.diaryContent = $sce.trustAsHtml($scope.diary.content);
                $scope.peopleList = response.visitList;
                $scope.fileList = response.fileList;
                $scope.replyList = response.replyList;
            });
    };

    $scope.initOrg = function () {
        $http.get("/team/myColleagues.do").success(function (data) {
            $scope.members = data.members;
            $scope.departments = data.departments;
        });
    };
    $scope.initOrg();
    $scope.refreshDepartment = function (department, $event) {
        $http.get("/team/myColleagues.do?depId=" + department.id).success(function (data) {

            $scope.members = data.members;
            $scope.departments = data.departments;
        });
        $event.stopPropagation();
    };
    $scope.join = function (mem, $event) {
        mem.on = !mem.on;
        if (mem.on) {
            if ($scope.joinList == null) {
                $scope.joinList = [];
            }
            if ($scope.joinList.indexOf(mem) == -1) {
                $scope.joinList.push(mem);
            }
        } else {
            for (var j = 0; j < $scope.joinList.length; j++) {
                if (mem.id == $scope.joinList[j].id) {
                    $scope.joinList.splice(j, 1);
                }
            }
        }
        $event.stopPropagation();
    };
    $scope.clearJoin = function ($event) {
        for (var j = 0; j < $scope.members.length; j++) {
            $scope.members[j].on = false;
            $scope.joinList = [];
        }
        $event.stopPropagation();
    };
    $scope.selectAll = function ($event) {
        if ($scope.joinList == null) {
            $scope.joinList = [];
        }
        for (var j = 0; j < $scope.members.length; j++) {
            if ($scope.joinList.indexOf($scope.members[j]) == -1) {
                $scope.members[j].on = true;
                $scope.joinList.push($scope.members[j]);
            }
        }
        $event.stopPropagation();
    };

    //通知查看逻辑
    var hrefArr = window.location.href.split("?");
    if (hrefArr != null && hrefArr.length == 2) {
        var showId = hrefArr[1];
        console.log(showId);


        $scope.diary = null;
        $scope.peopleList = null;
        $http.get("/dailyLog/detail.do?id=" + showId)
            .success(function (response) {
                $scope.diary = response.dailyLog;
                $scope.diaryContent = $sce.trustAsHtml($scope.diary.content);
                $scope.peopleList = response.visitList;
                $scope.fileList = response.fileList;
                $scope.replyList = response.replyList;

                $scope.isDiaryDetailShow = true;
                localStorage.diaryCurrentDate = $scope.diary.date;
                var dateStr = $scope.diary.date;
                var d = new Date();
                var a = dateStr.split("-");
                d.setYear(a[0]);
                d.setMonth(a[1] - 1);
                d.setDate(a[2]);
                $scope.currentDate = d;
                $scope.cal = createDateCal();
            });
    }
});