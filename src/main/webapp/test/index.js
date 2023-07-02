var canvas = document.getElementById("jump");
var ctx = canvas.getContext("2d");
window.addEventListener('keydown', keyDown, true);
window.addEventListener('keyup', keyUp, true);
var jump = document.getElementById("jump");
jump.addEventListener('click', myClick, true);
var time=setInterval(function () {
    clear();
    paint();
    speed();
    //accelerationSpeed();//加速度
    touchCheck();
    createWood();
    death();
}, 100);
var x = 240;//初始位置
var y = 340;//初始位置
var vx = 0;//左右速度
var vy = -20;//向上下速度
var a = 1;//加速度

var zero = {};
zero.x = 50;
zero.y = 300;
zero.len = 120;
var one = {};
one.x = 200;
one.y = 590;
one.len = 120;
var two = {};
two.x = 80;
two.y = 400;
two.len = 120;
var three = {};
three.x = 150;
three.y = 200;
three.len = 120;
var four = {};
four.x = 250;
four.y = 150;
four.len = 120;
var five = {};
five.x = 300;
five.y = 350;
five.len = 120;
var six = {};
six.x = 350;
six.y = 480;
six.len = 120;
var boards = [];
for(var i=0;i<8;i++){//初始化数组
    var board={};
    board.len=parseInt(70*Math.random())+50;
    board.y=parseInt(600*Math.random());
    board.x=parseInt((500-board.len)*Math.random());
    boards[i]=board;
}
function createWood() {//随机生成木板
    for(var i=0; i<boards.length;i++){
        if(boards[i].y>600){
            var boardy=0;
            var boardx=0;
            do{
                boardy=parseInt(200*Math.random());
                boardx=parseInt((500-boards[i].len)*Math.random());
            }while(judgeTogether(boardx,boardy));
            boards[i].len=parseInt(10*Math.random())+50;
            boards[i].x=boardx;
            boards[i].y=boardy;
        }
    }
}
function judgeTogether(x,y){//判断是否密集
    for(var j=0;j<boards.length;j++){
        if(Math.abs(y-boards[j].y)<50 && Math.abs(x-boards[j].x)<150){
            return true;
        }
    }
    return false;
}
function death(){//游戏死了
    if(y>600){
        alert("sisile");
        clearInterval(time);
    }
}
function paint() {
    ctx.beginPath();
    for (var i = 0; i < boards.length; i++) {
//        boards[i].y++;
        ctx.moveTo(boards[i].x, boards[i].y);
        ctx.lineTo(boards[i].x + boards[i].len, boards[i].y);
    }
    ctx.rect(x, y, 40, 40);
    ctx.stroke();
}
function clear() {
    ctx.clearRect(0, 0, 500, 600);
}
function speed() {
    if (vy < 0 && y<250) {//判断小球下落且在屏幕上方木板下滑否则小球向上跳
        for (var i = 0; i < boards.length; i++) {
            boards[i].y -= vy;
        }
    }
    else{
        y = y + vy;
    }
    x = x + vx;
}
function accelerationSpeed() {//加速度
    vy = vy + a;
}
function touchCheck() {//碰撞检测
    if (x <= 0) {//左边碰撞
        vx = 0;
    }
    if ((x + 40) >= 500) {//右边碰撞
        vx = 0;
    }
    for (var i = 0; i < boards.length; i++) {
        if ((x + 40) > boards[i].x && x < (boards[i].x + boards[i].len)) {//木板上
            if (boards[i].y - (y + 40) <= vy && boards[i].y - (y + 40) > 0) {
                vy = -20;
            }
        }
    }
}
function myClick(e) {
//    console.log(e)
    if(e.offsetX<200) {
        left();
    } else {
        right();
    }
}
function keyDown(e) {//按下键盘
    var keyID = e.keyCode ? e.keyCode : e.which;
    if (keyID == 37) {
        left();
    }
    else if (keyID == 39) {
        right();
    }
//    alert("按键 " +keyID+ " 被按下");
}
function keyUp(e) {//松开键盘
    var keyID = e.keyCode ? e.keyCode : e.which;
    if (keyID == 37) {
        endLeft();
    }
    else if (keyID == 39) {
        endRight();
    }
//    alert("按键 " + " 被按下");
}
function left() {
    if (x > 0) {
        vx = -10;
    }
}
function right() {
    if ((x + 40) < 500) {
        vx = 10;
    }
}
function endLeft() {
    if (vx < 0) {
        vx = 0;
    }
}
function endRight() {
    if (vx > 0) {
        vx = 0;
    }
}
//var c=document.getElementById("jump");
//var ctx=c.getContext("2d");
//
//ctx.beginPath();
//ctx.lineWidth="5";
//ctx.strokeStyle="red";  // 绿色路径
//ctx.moveTo(0,75);
//ctx.lineTo(250,75);
//ctx.stroke();  // 进行绘制
//
//ctx.beginPath();
//ctx.strokeStyle="blue";  // 紫色路径
//ctx.moveTo(50,0);
//ctx.lineTo(150,130);
//ctx.stroke();  // 进行绘制