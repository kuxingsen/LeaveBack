var express = require('express');
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.urlencoded({ extended: false }));
app.use('/public',express.static('public'));

app.set('view engine', 'ejs');

app.get('/getAllInfo',function(req,res) {
    console.log('here');
    res.send({total:2,rows:[{studentId:201624133135,studentName:'少年',department:'软件学院',className:'16软件1班',phone:123456,beginTime:'2017年5月1日',endTime:'2018年6月5日',status:'批准'}]});
});

app.get('/show',function(req,res) {
    res.render('show');
});

app.post('/class/add',function(req,res) {
    if(req.body.classId == '123')
        res.send({code:0});
    else 
        res.send({code:1,msg:"未知错误"});
});
app.post('/class/modify',function(req,res) {
    if(req.body.classId == '123')
        res.send({code:0});
    else 
        res.send({code:1,msg:"未知错误"});
});

app.get('/getClass',function(req,res) {
    // console.log(req.query);
    // res.send({code:1,msg: "无此用户"});
    res.send({code:0,classId: req.query.classId,className:"班级名称",deanId:'123456',deanName:'美少女',teacherId:'123321',teacherName:'最漂亮美少女',monitorId:'201624133135',monitorName:'Af'});
});

app.get('/getDean',function(req,res) {
    if(req.query.deanId == 123456)
        res.send({code:0,deanName: '胡歌'});
    else 
        res.send({code:1,deanName: '吴亦凡'});
});
app.get('/modify',function(req,res) {
    res.render('modify');
});
app.get('/', function (req, res) {
    res.render('index');
});
app.get('/add',function(req,res) {
    res.render('add');
});
app.get('/gettable',function(req,res) {
    var idx = req.query.pageIndex;
    if(idx == 1){
        res.send({total:2,rows:[{classid:1001,classname:'软件工程1班',teacherid:001,teachername:'是神',monitorid:1010,monitorname:'大神'}]});
    } else {
        res.send({total:2,rows:[{classid:1003,classname:'软件工程2班',teacherid:001,teachername:'哈哈哈',monitorid:1002,monitorname:'64'}]})
    }
});
app.delete('/class',function(req,res) {
    console.log(req.body);
    res.send("fdsf");
});
var server = app.listen(3000, function () {
  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
});