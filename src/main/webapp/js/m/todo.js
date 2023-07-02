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
            templateUrl: 'accept.html',
            controller: 'acceptController'
        })
        .when('/accept', {
            templateUrl: 'accept.html',
            controller: 'acceptController'
        })
        .when('/todo', {
            templateUrl: 'todo.html',
            controller: 'todoController'
        })
        .when('/todo/:date', {
            templateUrl: 'todo.html',
            controller: 'todoController'
        })
        .when('/add', {
            templateUrl: 'add.html',
            controller: 'addController'
        })
        .when('/add/:date', {
            templateUrl: 'add.html',
            controller: 'addController'
        })
        .when('/edit', {
            templateUrl: 'edit.html',
            controller: 'editController'
        })
        .when('/edit/:id', {
            templateUrl: 'edit.html',
            controller: 'editController'
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
app.filter('levelFilter', function () {//过滤0123
    return function (list, level) {
        if (list == undefined) {
            return;
        }
        var arr = [];
        for (var i = 0; i < list.length; i++) {
            if (list[i].level == level) {
                arr.push(list[i]);
            }
        }
        return arr;
    }
});
app.controller('acceptController', function ($scope, $http) {

    $scope.pageClass = 'accept';


    $http.get("/task/show.do?type=0")//收纳箱
        .success(function (response) {
            $scope.acceptList = response.taskList;
        });
    $scope.saveAcceptTask = function () {//保存添加的收纳箱收纳箱任务
        console.log(":::::" + $scope.acceptTaskTitle);
        var task = {};
        task.type = 0;
        task.title = $scope.acceptTaskTitle;
        $http.post("/task/add.do", task)
            .success(function (data) {
                var task = {};
                task.title = $scope.acceptTaskTitle;
                task.id = data.id;
                task.username = data.username;
                task.headImage = data.headImage;
                $scope.acceptList.push(task);
                $scope.acceptTaskTitle = null;
            })
    };
    $scope.editTask = function (item) {
        localStorage.isReturnAccept = "1";
        location.href = "/todo/m/index.html#/edit/"+item.id;
    }
});

app.controller('todoController', function ($scope, $http, $routeParams) {
    $scope.pageClass = 'todo';
    var date = $routeParams.date;
    if (date == null) {
        date = new Date().format("yyyy-MM-dd");
    }
    $scope.isTaskShow = false;
    $scope.isEditShow = false;
    $scope.isDiscussShow = true;
    $scope.isDeleteShow = false;
    $scope.isDownShow = false;
    $scope.isAuthShow = false;
    $scope.isListOneShow = false;
    $scope.isListTwoShow = false;
    $scope.isListThreeShow = false;
    $scope.isListFourShow = false;
    $scope.currentDate = date;//
    function fillTask() {
//        var date = new Date();
//        date.setTime($scope.currentDate.getTime());
//        console.log(date)
        //$http.get("/task/show.do?type=1&date=" + date.format("yyyy-MM-dd"))
        $http.get("/task/show.do?type=1&date=" + date)
            .success(function (response) {
                $scope.taskList = response.taskList;
            });
    }

    fillTask();
    $scope.showListOne = function () {
        $scope.isListOneShow = !$scope.isListOneShow;
    };
    $scope.showListTwo = function () {
        $scope.isListTwoShow = !$scope.isListTwoShow;
    };
    $scope.showListThree = function () {
        $scope.isListThreeShow = !$scope.isListThreeShow;
    };
    $scope.showListFour = function () {
        $scope.isListFourShow = !$scope.isListFourShow;
    };

    $scope.createNewTask = function () {
        location.href = "/todo/m/index.html#/add/" + date;
    };

    $scope.editTask = function (item) {
        localStorage.isReturnAccept = "0";
        location.href = "/todo/m/index.html#/edit/"+item.id;
    }
});

app.controller('addController', function ($scope, $http, $routeParams) {
    $scope.pageClass = 'add';
    var date = $routeParams.date;
    $scope.isSelectShow = false;
    $scope.toggleTaskAuth = function () {
        $scope.isTaskPublic = !$scope.isTaskPublic;
        $scope.addTaskAuth = $scope.isTaskPublic ? "0" : "-1";
    };
    $scope.toggleTaskAuth();
    $scope.selectTaskType = function () {
        $scope.isSelectShow = true;
    };
    $scope.goIssued = function () {
        $scope.isIssuedShow = true;
    };
    $scope.returnAdd = function () {
        $scope.isSelectShow = false;
        $scope.isIssuedShow = false;
    };
    $scope.returnAddEnter = function () {
        $scope.isSelectShow = false;
        $scope.isIssuedShow = false;
    };
    $scope.selectLevel = function (level) {
        $scope.taskLevel = level;
        switch (level) {
            case 0:
                $scope.taskLevelText = "不重要不紧急";
                break;
            case 1:
                $scope.taskLevelText = "紧急但不重要";
                break;
            case 2:
                $scope.taskLevelText = "重要但不紧急";
                break;
            case 3:
                $scope.taskLevelText = "重要且紧急";
                break;
        }
        $scope.isSelectShow = false;
        $scope.isIssuedShow = false;
    };
    $scope.selectLevel(3);

    $scope.addTask = function () {
        var task = {};
        task.type = 1;
        task.level = $scope.taskLevel;
        task.date = date;
        task.title = $scope.title;
        task.content = $scope.content;
        task.auth = $scope.addTaskAuth;

        /* 修改参与人 */
        if ($scope.joinList != null && $scope.joinList.length != 0) {
            var joinIds = "";
            for (var i = 0; i < $scope.joinList.length; i++) {
                joinIds += $scope.joinList[i].id;
                joinIds += ",";
            }
            if (joinIds.length > 0) {
                joinIds = joinIds.substring(0, joinIds.length - 1);
            }
            task.joinIds = joinIds;
        }
        $http.post("/task/add.do", task)
            .success(function (data) {
                task.id = data.id;
                task.headImage = data.headImage;
                task.username = data.username;

                location.href = "/todo/m/index.html#/todo/" + date;
            })
    };
    $scope.returnTodoList = function () {
        location.href = "/todo/m/index.html#/todo/" + date;
    };


    $scope.initOrg = function () {
        $scope.isSelectAll = false;
        $http.get("/team/myColleagues.do").success(function (data) {
            $scope.members = data.members;
            $scope.departments = data.departments;

            for (var i = 0; i < $scope.members.length; i++) {
                if ($scope.joinList != null) {
                    for (var j = 0; j < $scope.joinList.length; j++) {
                        if ($scope.members[i].id == $scope.joinList[j].id) {
                            $scope.members[i].on = true;
                        }

                    }
                }
            }
        });
    };
    $scope.initOrg();
    $scope.refreshDepartment = function (department) {
        $scope.isSelectAll = false;
        $http.get("/team/myColleagues.do?depId=" + department.id).success(function (data) {

            $scope.members = data.members;
            $scope.departments = data.departments;

            for (var i = 0; i < $scope.members.length; i++) {
                if ($scope.joinList != null) {
                    for (var j = 0; j < $scope.joinList.length; j++) {
                        if ($scope.members[i].id == $scope.joinList[j].id) {
                            $scope.members[i].on = true;
                        }

                    }
                }
            }
        });
    };
    $scope.join = function (mem) {
        mem.on = !mem.on;
        if (mem.on) {
            if ($scope.joinList == null) {
                $scope.joinList = [];
            }
            $scope.joinList.push(mem);
        } else {
            for (var j = 0; j < $scope.joinList.length; j++) {
                if (mem.id == $scope.joinList[j].id) {
                    $scope.joinList.splice(j, 1);
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
        if ($scope.joinList == null) {
            $scope.joinList = [];
        }
        for (var j = 0; j < $scope.members.length; j++) {
            if ($scope.joinList.indexOf($scope.members[j]) == -1) {
                $scope.members[j].on = true;
                $scope.joinList.push($scope.members[j]);
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

app.controller('editController', function ($scope, $http, $routeParams) {
    $scope.pageClass = 'edit';
    $scope.isDeleteShow = false;
    $scope.isReplyListShow = true;
    var id = $routeParams.id;
    $http.get("/task/detail.do?id=" + id)//收纳箱
        .success(function (response) {
            $scope.taskDetail = response;
            $scope.isTaskPublic = $scope.taskDetail.task.auth == "0";
            $scope.joinList = $scope.taskDetail.joinList;
            $scope.initOrg();

        });

    $scope.showReplyList = function () {
        $scope.isReplyListShow = true;
    };
    $scope.showDetail = function () {
        $scope.isReplyListShow = false;
    };

    $scope.isSelectShow = false;
    $scope.toggleTaskAuth = function () {
        $scope.isTaskPublic = !$scope.isTaskPublic;
        $scope.addTaskAuth = $scope.isTaskPublic ? "0" : "-1";
    };
    $scope.selectTaskType = function () {
        $scope.isSelectShow = true;
    };
    $scope.goIssued = function () {
        $scope.isIssuedShow = true;
    };

    $scope.selectLevel = function (level) {
        $scope.taskDetail.task.level = level;
        switch (level) {
            case 0:
                $scope.taskLevelText = "不重要不紧急";
                break;
            case 1:
                $scope.taskLevelText = "紧急但不重要";
                break;
            case 2:
                $scope.taskLevelText = "重要但不紧急";
                break;
            case 3:
                $scope.taskLevelText = "重要且紧急";
                break;
        }
        $scope.isSelectShow = false;
        $scope.isIssuedShow = false;
    };
    $scope.saveEdit = function () {//保存编辑任务及收纳箱

        var task = $scope.taskDetail.task;
        task.auth = $scope.addTaskAuth;
        if(task.level != null) {
            task.type = 1;
        }

        /* 修改参与人 */
        if ($scope.joinList != null && $scope.joinList.length != 0) {
            var joinIds = "";
            for (var i = 0; i < $scope.joinList.length; i++) {
                joinIds += $scope.joinList[i].id;
                joinIds += ",";
            }
            if (joinIds.length > 0) {
                joinIds = joinIds.substring(0, joinIds.length - 1);
            }
            task.joinIds = joinIds;
        } else {
            task.joinIds = "";
        }
        $http.post("/task/update.do", task)
            .success(function (data) {
                $scope.returnTaskList();
            })
    };

    $scope.showDelete = function () {
        $scope.isDeleteShow = true;
    };
    $scope.cancel = function () {
        $scope.isDeleteShow = false;
    };
    $scope.deleteTask = function () {
        $http.post("/task/delete.do?id=" + $scope.taskDetail.task.id)
            .success(function (data) {
                $scope.returnTaskList();
            })
    };

    $scope.returnTaskList = function () {
        if (localStorage.isReturnAccept == "1") {
            location.href = "/todo/m/index.html#/accept";
            return;
        }
        if ($scope.taskDetail.task.type == 1) {
            location.href = "/todo/m/index.html#/todo";
        } else {
            location.href = "/todo/m/index.html#/accept";
        }
    };

    $scope.transfer = function () {
        var reply = {};
        reply.taskId = $scope.taskDetail.task.id;
        reply.content = $scope.replyContent;

        $http.post("/task/reply.do", reply)
            .success(function (data) {
                reply.createTime = new Date().format("yyyy-MM-dd HH:mm:ss");
                reply.name = data.name;
                reply.headImage = data.headImage;
                $scope.taskDetail.replyList.unshift(reply);
                $scope.replyContent = null;

            })
    };


    $scope.initOrg = function () {
        $scope.isSelectAll = false;
        $http.get("/team/myColleagues.do").success(function (data) {
            $scope.members = data.members;
            $scope.departments = data.departments;


            for (var i = 0; i < $scope.members.length; i++) {
                if ($scope.joinList != null) {
                    for (var j = 0; j < $scope.joinList.length; j++) {
                        if ($scope.members[i].id == $scope.joinList[j].id) {
                            $scope.members[i].on = true;
                        }

                    }
                }
            }
        });
    };
    $scope.refreshDepartment = function (department) {
        $scope.isSelectAll = false;
        $http.get("/team/myColleagues.do?depId=" + department.id).success(function (data) {

            $scope.members = data.members;
            $scope.departments = data.departments;

            for (var i = 0; i < $scope.members.length; i++) {
                if ($scope.joinList != null) {
                    for (var j = 0; j < $scope.joinList.length; j++) {
                        if ($scope.members[i].id == $scope.joinList[j].id) {
                            $scope.members[i].on = true;
                        }

                    }
                }
            }
        });
    };
    $scope.join = function (mem) {
        mem.on = !mem.on;
        if (mem.on) {
            if ($scope.joinList == null) {
                $scope.joinList = [];
            }
            $scope.joinList.push(mem);
        } else {
            for (var j = 0; j < $scope.joinList.length; j++) {
                if (mem.id == $scope.joinList[j].id) {
                    $scope.joinList.splice(j, 1);
                    $scope.taskDetail.joinList.splice(j, 1);
                }
            }
        }
    };
    $scope.returnEdit = function () {
        $scope.isSelectShow = false;
        $scope.isIssuedShow = false;
    };
    $scope.returnEditEnter = function () {
        $scope.isSelectShow = false;
        $scope.isIssuedShow = false;
        $scope.taskDetail.joinList = $scope.joinList;
    };
    $scope.clearJoin = function () {
        $scope.joinList = [];
        $scope.taskDetail.joinList = [];
        for (var j = 0; j < $scope.members.length; j++) {
            $scope.members[j].on = false;
        }
    };
    $scope.selectAll = function () {
        if ($scope.joinList == null) {
            $scope.joinList = [];
        }
        for (var j = 0; j < $scope.members.length; j++) {
            if ($scope.joinList.indexOf($scope.members[j]) == -1) {
                $scope.members[j].on = true;
                $scope.joinList.push($scope.members[j]);
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
//        $scope.taskDetail.joinList = $scope.joinList;
    }
});


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

    $scope.returnTodo = function () {
        location.href = "/todo/m/index.html#/todo/" + $scope.currentDate.format("yyyy-MM-dd");
    };
    $scope.returnTodoCancel = function () {
        location.href = "/todo/m/index.html#/todo/" + new Date().format("yyyy-MM-dd");
    };

    $scope.returnToday = function () {
        $scope.selectDay(new Date());
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
    $scope.isNextMonth = function (date) {
        if (date.getMonth() != $scope.currentDate.getMonth()) {
            return true;
        }
        return false;
    };

    $scope.cal = createDateCal();
    function createDateCal() {
        var startDate = new Date();
        startDate.setYear($scope.currentDate.getFullYear());
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
        $http.get("/task/showStar.do?yearMonth=" + $scope.currentDate.format("yyyy-MM"))
            .success(function (response) {
                $scope.starList = response.dateList;
            });
    };
    $scope.calRight = function () {//向右一月
        $scope.currentDate.setDate(1);
        $scope.currentDate.setMonth($scope.currentDate.getMonth() + 1);
        $scope.cal = createDateCal();
        $http.get("/task/showStar.do?yearMonth=" + $scope.currentDate.format("yyyy-MM"))
            .success(function (response) {
                $scope.starList = response.dateList;
            });
    };
    $http.get("/task/showStar.do?yearMonth=" + $scope.currentDate.format("yyyy-MM"))
        .success(function (response) {
            $scope.starList = response.dateList;
        });
});