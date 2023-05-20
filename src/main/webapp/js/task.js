app = angular.module("app", ['ngDraggable', 'ngAnimate'], function ($httpProvider) {
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
}).controller("todoController", function ($scope, $http) {
    $scope.isTaskShow = false;
    $scope.isEditShow = false;
    $scope.isDiscussShow = true;
    $scope.isDeleteShow = false;
    $scope.isDownShow = false;
    $scope.isAuthShow = false;
    $scope.currentDate = new Date();//日历
    fillTask();
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
    $scope.isDoneTask = function (task) {
        return task != null && task.status != null && task.status == 1;
    };
    $scope.doneTask = function (task) {
        task.status = 1;
        task.date = new Date().format("yyyy-MM-dd");
        $http.post("/task/update.do", task)
            .success(function (data) {

            })
    };
    $scope.undoneTask = function (task) {
        task.status = 0;
        $http.post("/task/update.do", task)
            .success(function (data) {

            })
    };
    $scope.toggleTaskStatus = function (task, $event) {
        $event.stopPropagation();
        if (task.status == 0) {
            $scope.doneTask(task);
            task.status = 1;
        } else {
            $scope.undoneTask(task);
            task.status = 0;
        }
        console.log(task)
    };
    $scope.discussClick = function () {//切换讨论与详情选项卡
        $scope.isDiscussShow = true;
    };
    $scope.detailClick = function () {//切换讨论与详情选项卡
        $scope.isDiscussShow = false;
    };
    $scope.cancelReply = function () {
        $scope.replyContent = null;
    };
    $scope.transfer = function () {
        var reply = {};
        reply.taskId = $scope.id;
        reply.content = $scope.replyContent;

        $http.post("/task/reply.do", reply)
            .success(function (data) {
                reply.createTime = new Date().format("yyyy-MM-dd HH:mm:ss");
                reply.name = data.name;
                reply.headImage = data.headImage;
                $scope.taskDetail.replyList.push(reply);
                $scope.replyContent = null;

            })
    };
    $scope.authPublic = function () {
        $scope.editAuth = 0;
        $scope.isAuthShow = false;
    };
    $scope.authPrivate = function () {
        $scope.editAuth = -1;
        $scope.isAuthShow = false;
    };
    $scope.editTask = function (task) {//编辑任务及收纳箱
        $scope.isTaskShow = false;
        $scope.isEditShow = true;
        $scope.editTitle = task.title;
        $scope.editContent = task.content;
        $scope.id = task.id;
        $scope.currentEditTask = task;
        $http.get("/task/detail.do?id=" + task.id)//收纳箱
            .success(function (response) {
                $scope.taskDetail = response;
                $scope.initOrgEdit();
            });
    };
    $scope.saveEdit = function () {//保存编辑任务及收纳箱
        $scope.isEditShow = false;
        var taskDetail = $scope.taskDetail;

        var task = {};
        task.id = $scope.id;
        task.title = $scope.editTitle;
        task.content = $scope.editContent;
        task.auth = $scope.editAuth;

        /* 修改参与人 */
        if (taskDetail.joinListEdit != null && taskDetail.joinListEdit.length != 0) {
            var joinIds = "";
            for (var i = 0; i < taskDetail.joinListEdit.length; i++) {
                joinIds += taskDetail.joinListEdit[i].id;
                joinIds += ",";
            }
            if (joinIds.length > 0) {
                joinIds = joinIds.substring(0, joinIds.length - 1);
            }
            task.joinIds = joinIds;
        }
        $http.post("/task/update.do", task)
            .success(function (data) {
                $scope.currentEditTask.title = $scope.editTitle;
                $scope.currentEditTask.content = $scope.editContent;
                $scope.currentEditTask = null;
            });
        $scope.isEditTeamListShow = false;
    };
    $scope.deleteBtnShow = function () {//删除按钮显示
        $scope.isDeleteShow = !$scope.isDeleteShow;
    };
    $scope.downShow = function () {//下发按钮显示
        $scope.isDownShow = !$scope.isDownShow;
    };
    $scope.authShow = function () {//公开秘密按钮显示
        $scope.isAuthShow = !$scope.isAuthShow;
    };
    $scope.deleteTask = function () {//删除任务
        $http.post("/task/delete.do?id=" + $scope.id)
            .success(function (data) {
                for (var i = 0; i < $scope.acceptList.length; i++) {
                    var item = $scope.acceptList[i];
                    if (item.id == $scope.id) {
                        $scope.acceptList.splice(i, 1);
                    }
                }
                for (var i = 0; i < $scope.taskList.length; i++) {
                    var item = $scope.taskList[i];
                    if (item.id == $scope.id) {
                        $scope.taskList.splice(i, 1);
                    }
                }
                $scope.isEditShow = false;
                $scope.isDeleteShow = false;
            })
    };
    $scope.addTask = function (level) {//添加任务
        $scope.isTaskShow = true;
        $scope.isEditShow = false;
        $scope.level = level;

    };
    $scope.isAddTaskDownShow = false;
    $scope.isAddTaskAuthShow = false;
    $scope.addTaskAuth = 0;
    $scope.showAddTaskDown = function () {
//        $scope.isAddTaskDownShow = !$scope.isAddTaskDownShow;
        $scope.isAddTeamListShow = !$scope.isAddTeamListShow;
    };
    $scope.editDownShow = function () {
        $scope.isEditTeamListShow = !$scope.isEditTeamListShow;

    };
    $scope.showAddTaskAuth = function () {
        $scope.isAddTaskAuthShow = !$scope.isAddTaskAuthShow;
    };
    $scope.addTaskPublic = function () {
        $scope.addTaskAuth = 0;
        $scope.isAddTaskAuthShow = false;
    };
    $scope.addTaskPrivate = function () {
        $scope.addTaskAuth = -1;
        $scope.isAddTaskAuthShow = false;
    };
    $scope.save = function () {//保存了
        $scope.isTaskShow = false;

        var task = {};
        task.type = 1;
        task.level = $scope.level;
        task.date = $scope.currentDate.format("yyyy-MM-dd");
        task.title = $scope.title;
        task.content = $scope.content;
        task.auth = $scope.addTaskAuth;

        if ($scope.joinList != null) {
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
        console.log(task)
        $http.post("/task/add.do", task)
            .success(function (data) {
                task.id = data.id;
                task.headImage = data.headImage;
                task.username = data.username;
                $scope.taskList.push(task);
                $scope.title = null;
                $scope.content = null;
            })
    };
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
                task.headImage = data.headImage;
                $scope.acceptList.push(task);
                $scope.acceptTaskTitle = null;
            })
    };
    $scope.cancel = function (level) {
        $scope.isTaskShow = false;
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
    };
    $scope.calRight = function () {//向右一月
        $scope.currentDate.setDate(1);
        $scope.currentDate.setMonth($scope.currentDate.getMonth() + 1);
        $scope.cal = createDateCal();
    };
    $scope.changeDate = function (date) {//更改当前日期
        $scope.currentDate = date;
        fillTask();
        $scope.cal = createDateCal();
    };
    $scope.isCurrentDate = function (date) {
        return $scope.currentDate.format("yyyy-MM-dd") == date.format("yyyy-MM-dd");
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
    function fillTask() {
        var date = new Date();
        date.setTime($scope.currentDate.getTime());
        $http.get("/task/show.do?type=1&date=" + date.format("yyyy-MM-dd"))
            .success(function (response) {
                $scope.taskList = response.taskList;
            });
        $http.get("/task/showStar.do?yearMonth=" + date.format("yyyy-MM"))
            .success(function (response) {
                $scope.starList = response.dateList;
            });
    }


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


    $scope.initOrgEdit = function () {
        $http.get("/team/myColleagues.do").success(function (data) {
            $scope.membersEdit = data.members;
            $scope.departmentsEdit = data.departments;
            if ($scope.taskDetail == null)
                return;

            for (var i = 0; i < $scope.membersEdit.length; i++) {
                if ($scope.taskDetail.joinList != null) {
                    console.log("!-nul")
                    for (var j = 0; j < $scope.taskDetail.joinList.length; j++) {
                        if ($scope.membersEdit[i].id == $scope.taskDetail.joinList[j].id) {
                            $scope.membersEdit[i].on = true;
                        }
                    }
                }
            }
        });
    };
    $scope.initOrgEdit();
    $scope.refreshDepartmentEdit = function (department) {
        $http.get("/team/myColleagues.do?depId=" + department.id).success(function (data) {

            $scope.membersEdit = data.members;
            $scope.departmentsEdit = data.departments;
        });
    };
    $scope.joinEdit = function (mem) {
        if ($scope.taskDetail.joinListEdit == null) {
            $scope.taskDetail.joinListEdit = [];
        }
        mem.onEdit = !mem.onEdit;
        if (mem.onEdit) {
            if ($scope.taskDetail.joinListEdit.indexOf($scope.membersEdit[j]) == -1) {
                $scope.taskDetail.joinListEdit.push(mem);
            }
        } else {
            for (var j = 0; j < $scope.taskDetail.joinListEdit.length; j++) {
                if (mem.id == $scope.taskDetail.joinListEdit[j].id) {
                    $scope.taskDetail.joinListEdit.splice(j, 1);
                }
            }
        }
    };
    $scope.clearJoinEdit = function () {
        $scope.taskDetail.joinListEdit = [];
        for (var j = 0; j < $scope.membersEdit.length; j++) {
            $scope.membersEdit[j].onEdit = false;
        }
    };
    $scope.selectAllEdit = function () {
        if ($scope.taskDetail.joinListEdit == null) {
            $scope.taskDetail.joinListEdit = [];
        }
        for (var j = 0; j < $scope.membersEdit.length; j++) {
            if ($scope.taskDetail.joinListEdit.indexOf($scope.membersEdit[j]) == -1) {
                $scope.membersEdit[j].on = true;
                $scope.taskDetail.joinListEdit.push($scope.membersEdit[j]);
            }
        }

    };

    //拖拽
    $scope.onDropCompleteTodo = function (task, level, evt) {
        if (task == null)
            return;
        if (task.type == 0) {
            //如果是收纳箱移到todo
            var index = $scope.acceptList.indexOf(task);
            if (index > -1) {
                $scope.acceptList.splice(index, 1);
                $scope.taskList.push(task);
            }
        }
        task.type = 1;
        task.level = level;
        task.date = $scope.currentDate.format("yyyy-MM-dd");

        var req = {};
        req.id = task.id;
        req.type = task.type;
        req.level = task.level;
        req.date = task.date;

        $http.post("/task/update.do", req)
            .success(function () {
            })
    };
    $scope.onDropCompleteAccept = function (task, evt) {
        if (task.type == 1) {
            //如果是todo 移到收纳箱
            var index = $scope.taskList.indexOf(task);
            if (index > -1) {
                $scope.taskList.splice(index, 1);
                $scope.acceptList.push(task);
            }
        }
        task.type = 0;
        var req = {};
        req.id = task.id;
        req.type = task.type;
        req.level = task.level;
        $http.post("/task/update.do", req)
            .success(function () {
            })
    };



    //通知查看逻辑
    var hrefArr = window.location.href.split("?");
    if(hrefArr != null && hrefArr.length==2){
        var showId = hrefArr[1];
        console.log(showId);

        $http.get("/task/detail.do?id=" + showId)//收纳箱
            .success(function (response) {
                $scope.taskDetail = response;
                $scope.initOrgEdit();

                $scope.isTaskShow = false;
                $scope.isEditShow = true;
                $scope.editTitle = $scope.taskDetail.task.title;
                $scope.editContent = $scope.taskDetail.task.content;
                $scope.id = showId;
            });
    }
});
