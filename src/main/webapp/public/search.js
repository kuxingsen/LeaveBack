// 查询接口
$("#deanId").on('input',function() {
    //判断长度， 长度小于6不发请求
    // console.log($(this).val());
    var length = $(this).val().length;  //计算出输入长度
    var brother = $(this).nextAll();  //找到所有兄弟节点
    if(length<6) {
        brother.eq(0).show();
        brother.eq(1).hide();
    } else {
        $.ajax({
            method: 'get',
            url: '/getDean',
            data: { deanId: $(this).val()},
            success: function(res) {
                if(res.code == 0) {   //正常返回，查找成功
                    brother.eq(0).hide();
                    brother.eq(1).text(res.deanName).show();
                } else {     //错误返回， 没找到该人员
                    brother.eq(0).show();
                    brother.eq(1).hide();
                }
            }
        }); 
    }
});
$("#teacherId").on('input',function() {
    //判断长度， 长度小于6不发请求
    // console.log($(this).val());
    var length = $(this).val().length;  //计算出输入长度
    var brother = $(this).nextAll();  //找到所有兄弟节点
    if(length<6) {
        brother.eq(0).show();
        brother.eq(1).hide();
    } else {
        $.ajax({
            method: 'get',
            url: '/getDean',
            data: { deanId: $(this).val()},
            success: function(res) {
                if(res.code == 0) {   //正常返回，查找成功
                    brother.eq(0).hide();
                    brother.eq(1).text(res.deanName).show();
                } else {     //错误返回， 没找到该人员
                    brother.eq(0).show();
                    brother.eq(1).hide();
                }
            }
        }); 
    }
});
$("#monitorId").on('input',function() {
    //判断长度， 长度小于6不发请求
    // console.log($(this).val());
    var length = $(this).val().length;  //计算出输入长度
    var brother = $(this).nextAll();  //找到所有兄弟节点
    if(length<6) {
        brother.eq(0).show();
        brother.eq(1).hide();
    } else {
        $.ajax({
            method: 'get',
            url: '/getDean',
            data: { deanId: $(this).val()},
            success: function(res) {
                if(res.code == 0) {   //正常返回，查找成功
                    brother.eq(0).hide();
                    brother.eq(1).text(res.deanName).show();
                } else {     //错误返回， 没找到该人员
                    brother.eq(0).show();
                    brother.eq(1).hide();
                }
            }
        }); 
    }
});