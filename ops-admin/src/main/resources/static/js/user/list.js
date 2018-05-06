$(function(){
	var status = [
		'<span style="color:#FF5722">禁用</span>',
		'<span style="color:#009688">启用</span>',
	]
	var cols = [[
		/*{title:'序号',align:params.center,field:'rn',width:60}, */
		{title:'用户名',align:params.center,width:'14%',field:'userName',}, 
		{title:'昵称',align:params.center,width:'14%',field:'nickName'}, 
		{title:'角色',align:params.center,width:'14%',field:'roleName'}, 
		{title:'创建时间',align:params.center,width:'14%',field:'insertTime'}, 
		{title:'操作时间',align:params.center,width:'14%',field:'updateTime'}, 
		{title:'状态',align:params.center,width:'10%',field:'status',templet:function(data){
			if(data.status) return status[data.status];
			else return status[0];
		}}, 
		{title:'操作',align:params.center,width:'20%',templet:function(data){
			return '<a class="layui-btn layui-btn-xs"><i class="icon larry-icon larry-buchongiconsvg15"></i>分配角色</a> '
			+'<a class="layui-btn layui-btn-xs layui-btn-normal"><i class="layui-icon"></i>修改</a>'
			+'<a class="layui-btn layui-btn-xs layui-btn-danger"><i class="layui-icon"></i>刪除</a>';
		}},
	]];
	load.initLoadData('/user/list',cols);
})