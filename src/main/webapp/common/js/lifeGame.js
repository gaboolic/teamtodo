var xOffset = 20;
var yOffset = 20;
var gridSize = 40;
var size = 10;
var board = new Array(
    [1, 1, 0, 0, 0, 0, 0, 0, 0, 0],
    [1, 1, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 1, 0, 0, 0, 0],
    [0, 0, 0, 0, 1, 0, 1, 0, 0, 0],
    [0, 0, 0, 0, 1, 1, 1, 0, 0, 0],
    [0, 0, 0, 1, 1, 1, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);
var c = document.getElementById("myCanvas");
var cxt = c.getContext("2d");
cxt.fillStyle = "#FF0000";
paint();
<!-- 主绘制函数 -->
function paint() {
    cxt.fillStyle = "#eeeeee";
    cxt.fillRect(0, 0, 500, 500);
    cxt.fillStyle = "#00ff00";
    for (var i = 0; i <= size; i++) {
        cxt.moveTo(xOffset, yOffset + i * gridSize);
        cxt.lineTo(xOffset + gridSize * size, yOffset + i * gridSize);
    }
    for (var i = 0; i <= size; i++) {
        cxt.moveTo(xOffset + i * gridSize, yOffset);
        cxt.lineTo(xOffset + i * gridSize, yOffset + gridSize * size);
    }
    cxt.stroke();
    for (var x = 0; x < board.length; x++) {
        for (var y = 0; y < board[x].length; y++) {
            //alert(x+" "+y+" "+board[x][y]);
            if (board[x][y] == 1) {
                cxt.fillRect(xOffset + x * gridSize, yOffset + y * gridSize, gridSize - 1, gridSize - 1);
            }
        }
    }
}
<!--我是上帝！鼠标点击，反转细胞存活情况 -->
function mouseClick(event) {
    var x = parseInt((event.layerX - xOffset) / gridSize);
    var y = parseInt((event.layerY - yOffset) / gridSize);
    board[x][y] = !board[x][y];
    paint();
}
<!--生死判定 -->
function reverse() {
    var copy = board;
    for (var i = 0; i < board.length; i++) {
        for (var j = 0; j < board[i].length; j++) {
            var count = aroundCount(i, j);
            if (count == 3) {
                copy[i][j] = 1;
            } else if (count == 2) {
            } else {
                copy[i][j] = 0;
            }
        }
    }
    board = copy;
}
<!--不算该细胞，周围八个格子细胞存活数 -->
function aroundCount(x, y) {
    var count = 0;
    for (var i = x - 1; i <= x + 1; i++) {
        for (var j = y - 1; j <= y + 1; j++) {
            if (i < 0 || i >= board.length || j < 0 || j >= board.length) {
                continue;
            }
            if (board[i][j]) {
                count++;
            }
        }
    }
    if (board[x][y]) {
        count--;
    }
    return count;
}
<!--start -->
function start() {
    setTimeout(function () {
        reverse();
        paint();
        setTimeout(arguments.callee, 1000);
    }, 1000);
}
start();
