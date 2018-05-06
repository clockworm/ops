/**
 * @name 后台登录模块
 */
layui.define(['larry','form','larryms'],function(exports){
	"use strict";
	var $ = layui.$,
		layer = layui.layer,
        larryms = layui.larryms,
        form = layui.form;
    
	/**登录背景轮播插件*/
    function supersized() {
        $.supersized({
            // 功能
            slide_interval: 3000,
            transition: 1,
            transition_speed: 1000,
            performance: 1,
            // 大小和位置
            min_width: 0,
            min_height: 0,
            vertical_center: 1,
            horizontal_center: 1,
            fit_always: 0,
            fit_portrait: 1,
            fit_landscape: 0,
            // 组件
            slide_links: 'blank',
            slides: [{
                image: '/common/images/login/1.jpg'
            }, {
                image: '/common/images/login/2.jpg'
            }, {
                image: '/common/images/login/3.jpg'
            }]
        });
    }
    
    $('#change,#codeimage').on('click',function(){
    	$('#codeimage').attr('src','getYzm?t='+new Date().getTime());
    })
    
    larryms.plugin('jquery.supersized.min.js',supersized);
    
    $('input[name="username"],input[name="password"],input[name="code"]').on('keydown',function(e){
    	if(e.keyCode==13){
    		$(this).blur();
	    	$('#login-btn').click();
    	}
    })
    
    //模拟登录(2.08会重写构建前后端分离验证模块)
    form.on('submit(submit)', function(data) {
    	async(root+'common/login',data.field,function(data){
    		if(data.code==0&&data.data){
    			layer.msg('登录成功', {icon: 1,time: 1000});
                setTimeout(function() {
                	window.location.href=root+"common/index";
                }, 1000);
    		}else if(data.code==102){
    			layer.tips(data.message, $('#password'), {tips: [3, '#FF5722']});
    		}else{
    			layer.tips(data.message, $('#username'), {tips: [3, '#FF5722']});
    		}
    	});
    	return false;
    });
    exports('login', {}); 
});