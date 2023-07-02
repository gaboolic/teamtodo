app.controller("todoController", function ($scope, $http) {
    var vm = this;

    $scope.isAddBoardShow = false;
    $scope.isAddCardShow = false;
    $scope.isBtnShow = true;
    $scope.isEditCardShow = false;
    $scope.isAddTaskShow = false;
    $scope.isListShow = false;
    $scope.isEditTaskShow = false;
    $scope.isEditCard = false;
    $scope.isNewCardShow = true;
    $scope.isNewCardInputShow = false;
    $scope.isSetUpShow = false;
    $scope.isListShow = false;
    $scope.authText = "所有人可见（公共看板）";
    $scope.authNum = 0;
    $scope.isfuzeListShow = false;
    $scope.isTimeShow = false;
    $scope.isCanyuShow = false;
    $scope.startEndDay = [new Date().format("yyyy-MM-dd"), new Date().format("yyyy-MM-dd")];
    $http.get("/board/all.do")//看板分类列表
        .success(function (response) {
            $scope.boardList = response.boardList;
            if ($scope.boardList.length > 0) {
                $scope.changeBoard($scope.boardList[0]);
            }
        });
    $scope.showList1 = function () {//负责人下拉菜单
        $scope.isfuzeListShow = !$scope.isfuzeListShow;
        $scope.isCanyuShow = false;
    };
    $scope.showList2 = function () {//参与人下拉菜单
        $scope.isCanyuShow = !$scope.isCanyuShow;
        $scope.isfuzeListShow = false;
        if ($scope.isCanyuShow) {
            $scope.initOrg();
        }
    };
    $scope.showList3 = function () {//参与人下拉菜单
        $scope.isTimeShow = !$scope.isTimeShow;
    };
    $scope.showList3Enter = function () {//参与人下拉菜单
        $scope.isTimeShow = !$scope.isTimeShow;
        $scope.taskDetail.task.startTime = $scope.startEndDay[0];
        $scope.taskDetail.task.endTime = $scope.startEndDay[1];
    };
    $scope.showList3Cancel = function(){
        $scope.isTimeShow = !$scope.isTimeShow;
        $scope.taskDetail.task.startTime = null;
        $scope.taskDetail.task.endTime = null;
        $scope.startEndDay[0] = "";
        $scope.startEndDay[1] = "";
    };
    $scope.setBoard = function () {//设置看板
        $scope.isSetUpShow = true;
        $scope.boardName1 = $scope.boardName;

    };
    $scope.option = function () {//下拉菜单选择

        $scope.isListShow = !$scope.isListShow;
    };
    $scope.shutDownSet = function () {//关闭设置看板
        $scope.isSetUpShow = false;
    };

    $scope.changeAuth = function (num) {//选择要设置的权限
        $scope.authNum = num;
        if (num == 0) {
            $scope.authText = "所有人可见（公共看板）";
        }
        else {
            $scope.authText = "仅自己可见（私人看板）";
        }
        $scope.isListShow = false;
    };
    $scope.deleteBoard = function () {//删除看板
        $http.post("/board/delete.do?id=" + $scope.boardId)
            .success(function (data) {
                for (var i = 0; i < $scope.boardList.length; i++) {
                    var item = $scope.boardList[i];
                    if (item.id == $scope.boardId) {
                        $scope.boardList.splice(i, 1);
                    }
                }
                $scope.isSetUpShow = false;
                $scope.changeBoard($scope.boardList[0]);
            })
    };
    $scope.saveChange = function () {//保存修改后的看板
//            $scope.isAddBoardShow = false;
        var board = {};
        board.name = $scope.boardName1;
        board.auth = $scope.authNum;
        board.id = $scope.boardId;
        $http.post("/board/update.do", board)
            .success(function (response) {
                $scope.isSetUpShow = false;
                $scope.board.name = board.name;
                $scope.board.auth = board.auth;

            });
    };
    $scope.changeBoard = function (board) {//切换看板返回相对应分类的看板的卡片
        $scope.boardId = board.id;
        $scope.boardName = board.name;
        $scope.boardAuth = board.auth;

        $scope.board = board;
        $http.get("/board/card/all.do?boardId=" + board.id)//看板分类列表
            .success(function (response) {
                $scope.cardList = null;
                $scope.cardList = response.cardList;
            });
    };

    $scope.addBoard = function () {//添加看板
        $scope.isAddBoardShow = true;

    };
    $scope.shutDown = function () {
        $scope.isAddBoardShow = false;
    };

    //新建看板时 选择公开私有
    $scope.showAddBoardAuth = function () {
        $scope.isAddBoardAuthShow = !$scope.isAddBoardAuthShow;
    };
    $scope.auth = 0;
    $scope.addBoardAuth = "所有人可见（公开看板）";
    $scope.addBoardAuthPrivate = function () {
        $scope.auth = -1;
        $scope.addBoardAuth = "仅自己可见（私有看板）";
        $scope.isAddBoardAuthShow = false;
    };
    $scope.addBoardAuthPublic = function () {
        $scope.auth = 0;
        $scope.addBoardAuth = "所有人可见（公开看板）";
        $scope.isAddBoardAuthShow = false;
    };
    $scope.saveBoard = function () {//保存看板
        $scope.isAddBoardShow = false;
        var board = {};
        board.name = $scope.boardName;
        board.auth = $scope.auth;
        $http.post("/board/add.do", board)//
            .success(function (response) {
                board.id = response.id;
                $scope.boardList.push(board);
            });
    };

    $scope.addCard = function () {//添加卡片
        $scope.isBtnShow = false;
        $scope.isAddCardShow = true;
        $scope.isNewCardShow = false;
        $scope.isNewCardInputShow = true;
    };
    $scope.saveCard = function () {//保存新加的卡片
        $scope.isBtnShow = true;
        $scope.isAddCardShow = false;
        $scope.isNewCardShow = true;
        $scope.isNewCardInputShow = false;
        var card = {};
        card.name = $scope.newCardName;
        card.boardId = $scope.boardId;
        $http.post("/board/card/add.do", card)
            .success(function (response) {
                card.id = response.id;
                $scope.cardList.push(card);
            });
    };
    $scope.cancelCard = function () {//取消新建卡片
        $scope.isBtnShow = true;
        $scope.isAddCardShow = false;
        $scope.isNewCardShow = true;
        $scope.isNewCardInputShow = false;
        $scope.newCardName = null;
    };
    $scope.addTask = function (card) {//添加任务
        $scope.cardId = card.id;
        $scope.isAddTaskShow = true;

    };
    $scope.saveTask = function (card) {//保存新加的任务
        console.log("taskCOntent:" + card.taskContent);
        var task = {};
        task.title = card.taskContent;
        task.cardId = $scope.cardId;
        task.type = 2;


        $http.post("/task/add.do", task)
            .success(function (response) {
                task.id = response.id;
                if (card.taskList == undefined) {
                    card.taskList = [];
                }
                card.taskList.unshift(task);

                $scope.isAddTaskShow = false;
            });
    };
    $scope.editList = function (card) {//显示编辑列表
        $scope.cardId = card.id;
        $scope.isListShow = !$scope.isListShow;


    };
    $scope.test = function () {
        alert("test")
    };
    $scope.editCard = function (card) {//编辑卡片
        $scope.isEditCardShow = true;
        $scope.isListShow = false;
        $scope.cardName = card.name;
        console.log(card.name)
        $scope.cardId = card.id;
        $scope.currentCard = card;
    };
    $scope.saveCardEdit = function () {//保存被编辑的卡片
        console.log("scope.cardname " + $scope.cardName);
        $scope.isEditCardShow = false;
        var card = {};
        card.name = $scope.cardName;
        card.id = $scope.cardId;
        $http.post("/board/card/update.do", card)
            .success(function (data) {
                $scope.currentCard.name = $scope.cardName;

            })
    };
    $scope.cancelCardEdit = function () {//取消编辑的卡片
        $scope.isEditCardShow = false;
    };
    $scope.cancelTask = function () {//取消编辑的卡片
        $scope.isAddTaskShow = false;
    };
    $scope.editTask = function (task, card) {//弹出任务框
        $scope.isEditTaskShow = true;
        $scope.currentTask = task;
        $scope.currentTaskList = card.taskList;
        $http.get("/task/detail.do?id=" + task.id)
            .success(function (response) {
                $scope.taskDetail = response;
            })
    };
    $scope.deleteEditTask = function () {
        $scope.isEditTaskShow = false;
        var deleteTask = {};
        deleteTask.id = $scope.taskDetail.task.id;
        $http.get("/task/delete.do?id=" + deleteTask.id)
            .success(function () {
                for (var i = 0; i < $scope.currentTaskList.length; i++) {
                    if ($scope.currentTaskList[i].id == $scope.currentTask.id) {
                        console.log("delete" + $scope.currentTaskList[i].title)
                        $scope.currentTaskList.splice(i, 1);
                    }
                }
            });
    };
    $scope.closeEditTask = function () {//关闭弹出任务框
        $scope.isEditTaskShow = false;
        var taskDetail = $scope.taskDetail;
        var updateTask = {};
        updateTask.id = taskDetail.task.id;
        updateTask.title = $scope.currentTask.title;
        updateTask.content = $scope.currentTask.content;
        updateTask.ownerId = taskDetail.owner.id;
        var joinIds = "";
        var joinTeamIds = "";
        for (var i = 0; i < taskDetail.joinList.length; i++) {
            if (taskDetail.joinList[i].type == 1) {
                joinIds += taskDetail.joinList[i].id;
                joinIds += ",";
            } else {
                joinTeamIds += taskDetail.joinList[i].id;
                joinTeamIds += ",";
            }
        }
        if (joinIds.length > 0) {
            joinIds = joinIds.substring(0, joinIds.length - 1);
        }
        updateTask.joinIds = joinIds;
        if (joinTeamIds.length > 0) {
            joinTeamIds = joinTeamIds.substring(0, joinTeamIds.length - 1);
        }
        updateTask.joinTeamIds = joinTeamIds;
        updateTask.startTime = $scope.startEndDay[0];
        updateTask.endTime = $scope.startEndDay[1];
        $http.post("/task/update.do", updateTask)
            .success(function () {
            });
        $scope.isCanyuShow = false;
        $scope.isfuzeListShow = false;
    };
    $scope.deleteCard = function (card) {//删除的卡片
        $http.post("/board/card/delete.do?id=" + card.id)
            .success(function (data) {
                for (var i = 0; i < $scope.cardList.length; i++) {
                    var item = $scope.cardList[i];
                    if (item.id == card.id) {
                        $scope.cardList.splice(i, 1);
                    }
                }
            })
    };


    $scope.initOrg = function () {
        $http.get("/team/myColleagues.do").success(function (data) {
            $scope.departments = data.departments;
            $scope.members = data.members;
            if ($scope.taskDetail == null)
                return;
            for (var i = 0; i < $scope.members.length; i++) {
                if ($scope.taskDetail.joinList != null) {
                    for (var j = 0; j < $scope.taskDetail.joinList.length; j++) {
                        if ($scope.members[i].id == $scope.taskDetail.joinList[j].id) {
                            $scope.members[i].on = true;
                        }

                    }
                }
                if ($scope.taskDetail.owner != null) {
                    if ($scope.members[i].id == $scope.taskDetail.owner.id) {
                        $scope.members[i].ownerOn = true;
                    }
                }
            }
        });
    };
    $scope.initOrg();
    $scope.refreshDepartment = function (department) {
        $http.get("/team/myColleagues.do?depId=" + department.id).success(function (data) {

            $scope.members = data.members;
            $scope.departments = data.departments;

            if ($scope.taskDetail == null)
                return;
            for (var i = 0; i < $scope.members.length; i++) {
                if ($scope.taskDetail.joinList != null) {
                    for (var j = 0; j < $scope.taskDetail.joinList.length; j++) {
                        if ($scope.members[i].id == $scope.taskDetail.joinList[j].id) {
                            $scope.members[i].on = true;
                        }

                    }
                }
                if ($scope.taskDetail.owner != null) {
                    if ($scope.members[i].id == $scope.taskDetail.owner.id) {
                        $scope.members[i].ownerOn = true;
                    }
                }
            }
        });
    };

    $scope.clearJoin = function () {
        $scope.taskDetail.joinList = [];
        for (var i = 0; i < $scope.departments.length; i++) {
            $scope.departments[i].on = false;
        }
        for (var j = 0; j < $scope.members.length; j++) {
            $scope.members[j].on = false;
        }
    };
    $scope.selectAll = function () {
        for (var i = 0; i < $scope.departments.length; i++) {
            $scope.departments[i].on = true;
            $scope.departments[i].type = 0;
            $scope.taskDetail.joinList.push($scope.departments[i]);
        }
        for (var j = 0; j < $scope.members.length; j++) {
            $scope.members[j].on = true;
            $scope.members[j].type = 1;
            $scope.taskDetail.joinList.push($scope.members[j]);
        }

    };
    $scope.join = function (mem) {
        mem.on = !mem.on;
        if (mem.on) {
            mem.type = 1;
            $scope.taskDetail.joinList.push(mem);
        } else {
            for (var j = 0; j < $scope.taskDetail.joinList.length; j++) {
                if ($scope.taskDetail.joinList[j].type == 1) {
                    if (mem.id == $scope.taskDetail.joinList[j].id) {
                        $scope.taskDetail.joinList.splice(j, 1);
                    }
                }
            }
        }
    };

    $scope.joinDep = function (dep, $event) {
        $event.stopPropagation();
        dep.on = !dep.on;
        if (dep.on) {
            dep.type = 0;
            $scope.taskDetail.joinList.push(dep);
        } else {
            for (var j = 0; j < $scope.taskDetail.joinList.length; j++) {
                if ($scope.taskDetail.joinList[j].type != 1) {
                    if (dep.id == $scope.taskDetail.joinList[j].id) {
                        $scope.taskDetail.joinList.splice(j, 1);
                    }
                }
            }
        }
    };
    $scope.clearOwner = function () {
        for (var i = 0; i < $scope.members.length; i++) {
            $scope.members[i].ownerOn = false;
        }
        $scope.taskDetail.owner = null;
    };
    $scope.owner = function (mem) {
        for (var i = 0; i < $scope.members.length; i++) {
            $scope.members[i].ownerOn = false;
        }
        mem.ownerOn = !mem.ownerOn;
        if (mem.ownerOn) {
            $scope.taskDetail.owner = mem;
        } else {

        }
    };

    $scope.currentDate = new Date();
    $scope.currentMonth = $scope.currentDate.getMonth() + 1;
    $scope.currentMonthShow = ($scope.currentDate.getMonth() + 1) + "月";
    $scope.currentYear = $scope.currentDate.getFullYear();
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

    $scope.yearList = [2014, 2015, 2016, 2017, 2018];
    $scope.monthList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    $scope.showYearList = function () {
        $scope.isSelectYearShow = !$scope.isSelectYearShow;
    };
    $scope.showMonthList = function () {
        $scope.isSelectMonthShow = !$scope.isSelectMonthShow;
    };

    $scope.selectYear = function (year) {
        $scope.currentYear = year;
        $scope.isSelectYearShow = false;

        $scope.changeDate();
    };
    $scope.selectPrevMonth = function () {
        $scope.currentMonth -= 1;
        $scope.selectMonth($scope.currentMonth);
    };
    $scope.selectNextMonth = function (month) {
        $scope.currentMonth += 1;
        $scope.selectMonth($scope.currentMonth);
    };
    $scope.selectMonth = function (month) {
        $scope.currentMonth = month;
        if ($scope.currentMonth < 10) {
            $scope.currentMonth = "0" + $scope.currentMonth;
        }
        $scope.currentMonthShow = month + "月";
        $scope.isSelectMonthShow = false;

        $scope.changeDate();
    };

    $scope.changeDate = function () {
        var d = new Date();
        d.setYear($scope.currentYear);
        d.setMonth($scope.currentMonth-1);

        $scope.currentDate = d;
        console.log( $scope.currentDate)
        $scope.cal = createDateCal();
//        var today = d;
//        var last = new Date(today.getFullYear(), today.getMonth() + 1, 0);//获取当前月最后一天时间
//        console.log(last)
//        $scope.dayList = [];
//        for (var i = 1; i <= last.getDate(); i++) {
//            $scope.dayList.push(i);
//        }
//        console.log($scope.dayList)
    };

    $scope.isSelectDay = function (day) {
        day = day.format("yyyy-MM-dd");
        if ($scope.startEndDay[0] <= day && day <= $scope.startEndDay[1]) {
            return true;
        }
        return false;
    };

    $scope.selectDay = function (day) {
        day = day.format("yyyy-MM-dd");
        if($scope.startEndDay[0] == day) {
            $scope.startEndDay[0] = "";
            $scope.startEndDay[1] = "";
            return;
        }
        if($scope.startEndDay[0] == ""){
            $scope.startEndDay[0] = day;
        }
        if($scope.startEndDay[1] == ""){
            $scope.startEndDay[1] = day;
        }

        if ($scope.startEndDay.length == 0) {
            $scope.startEndDay[0] = day;
            $scope.startEndDay[1] = day;
        } else if ($scope.startEndDay.length == 1) {
            if ($scope.startEndDay[0] > day) {
                var temp = $scope.startEndDay[0];
                $scope.startEndDay[0] = day;
                $scope.startEndDay[1] = temp;
            } else {
                $scope.startEndDay[1] = day;
            }
        } else if ($scope.startEndDay.length == 2) {
            if (day < $scope.startEndDay[0]) {
                $scope.startEndDay[0] = day;
            } else if (day > $scope.startEndDay[0] && day < $scope.startEndDay[1]) {
                $scope.startEndDay[1] = day;
            } else if (day > $scope.startEndDay[1]) {
                $scope.startEndDay[1] = day;
            }
        }

        $scope.taskDetail.task.startTime = $scope.startEndDay[0];
        $scope.taskDetail.task.endTime = $scope.startEndDay[1];
    };

    $scope.toggleCurrentTaskStatus = function () {
        console.log("toggleCurrentTaskStatus")
        if ($scope.currentTask.status == 0) {
            $scope.currentTask.status = 1;
        } else {
            $scope.currentTask.status = 0;
        }
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
        console.log(task.status)
    };


    $scope.submitReply = function () {
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

            });
    };
    $scope.cancelReply = function () {
        $scope.replyContent = "";
    };

    //拖拽
    $scope.onDropCompleteCard = function (task, event, cd) {
        var updateTask = {};
        updateTask.id = task.id;
        updateTask.cardId = cd.id;
        $http.post("/task/update.do", updateTask)
            .success(function () {
                if (task == null)
                    return;
                if (task.cardId == cd.id) {
                    return;
                }
                for (var i = 0; i < $scope.cardList.length; i++) {
                    var card = $scope.cardList[i];
                    var index = card.taskList.indexOf(task);
                    if (index > -1) {
                        card.taskList.splice(index, 1);
                        cd.taskList.unshift(task);
                    }
                }
            });
    }



    //通知查看逻辑
    var hrefArr = window.location.href.split("?");
    if(hrefArr != null && hrefArr.length==2){
        var showId = hrefArr[1];
        console.log(showId);




        $http.get("/task/detail.do?id=" + showId)//收纳箱
            .success(function (response) {
                $scope.taskDetail = response;

                $scope.isEditTaskShow = true;
                $scope.currentTask = $scope.taskDetail.task;
            });
    }
});
