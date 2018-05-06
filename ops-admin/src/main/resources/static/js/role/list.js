$(function(){
	var status = [
		'<span style="color:#FF5722">禁用</span>',
		'<span style="color:#009688">启用</span>',
	]
	var cols = [[
		/*{title:'序号',align:params.center,field:'rn',width:60}, */
		{title:'角色名',align:params.center,field:'name',width:200}, 
		{title:'描述',align:params.center,field:'description',width:200}, 
		{title:'创建时间',align:params.center,field:'insertTime',width:200}, 
		{title:'操作时间',align:params.center,field:'updateTime',width:200}, 
		{title:'状态',align:params.center,width:100,field:'status',width:100,templet:function(data){
			if(data.status) return status[data.status];
			else return status[0];
		}}, 
		{title:'操作',align:params.center,width:260,templet:function(data){
			return '<a class="layui-btn layui-btn-xs"><i class="icon larry-icon larry-buchongiconsvg15"></i>分配资源</a> '
			+'<a class="layui-btn layui-btn-xs layui-btn-normal"><i class="layui-icon"></i>修改</a>'
			+'<a class="layui-btn layui-btn-xs layui-btn-danger"><i class="layui-icon"></i>刪除</a>';
		}},
	]];
	load.initLoadData('/role/list',cols);
})