function iconHandler(data,col){
	return data.icon?('<i class="larry-icon '+data.icon+'"></i> '+data.icon):'';
}

var _status = [
	'<span style="color:red">禁用</span>',
	'<span style="color:green">启用</span>',
]
function statusHandler(data,col){
	return '<div style="text-align: center;">'+(data.status?_status[data.status]:'<span style="color:red">未知</span>')+'</div>';
}

function loadTree(data){
	var colums = [
		{headerText: "",hidden:true},
		{headerText:'资源名',dataField: "name",headerAlign:'center',width: 300},
		{headerText:'排序',dataField: "sort",headerAlign:'center'},
		{headerText:'资源类型',dataField: "type",headerAlign:'center'},
		{headerText:'资源值',dataField: "value",headerAlign:'center'},
		{headerText:'资源CODE',dataField: "code",headerAlign:'center'},
		{headerText:'状态',dataField: "status",headerAlign:'center',handler: "statusHandler"},
		{headerText:'图标',dataField: "icon",headerAlign:'center',handler: "iconHandler",width: 300},
		{headerText:'备注',dataField: "note",headerAlign:'center'},
//		{headerText:'操作',dataField: "id",headerAlign:'center', handler: "customLook"},
	],
	config = {
		id: "tg1",
		skin:'layui-table table m-table table-striped m-table--head-bg-brand',
		renderTo: "resources-box",//id选择器
		headerHeight: "30",
		dataAlign: "left",
		indentation: "20",
		folderOpenIcon: "/plugins/treegrid/images/folderOpen.png",
		folderCloseIcon: "/plugins/treegrid/images/folderClose.png",
		defaultLeafIcon: "/plugins/treegrid/images/defaultLeaf.png",
		hoverRowBackground: "false",
		folderColumnIndex: "1",
		itemClick: "itemClick",
		columns: colums,
		data: data
	},
	treeGrid = new TreeGrid(config);
	treeGrid.show()
}
function itemClick(a,b){
	console.log(a);
	console.log(b);
}

$(function(){
	$.ajax({
		url: root +'permission/list',
		success:function(data){
			loadTree(data.data);
		}
	})
})

