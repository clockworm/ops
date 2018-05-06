var ajaxSetup = $.ajaxSetup({
	complete:function(XMLHttpRequest, textStatus){
		//非成功处理
		if(XMLHttpRequest.responseJSON && (XMLHttpRequest.responseJSON.data==false || XMLHttpRequest.responseJSON.code!=0)){
			alert(XMLHttpRequest.responseJSON.message);
		}
	},
	beforeSend:function(xhr){
	},
	type:'POST',
	dataType: 'json'
})

$.fn.getFormDada = function(){
	var params = $(this).serializeArray();
    var data = {};
    for( x in params ){
    	if(!data[params[x].name]){
    		data[params[x].name] = params[x].value;
    	}else{
    		data[params[x].name] += ',' + params[x].value
    	}
    }
    return data;
}

var load = {
	reload:function(searchBtn){
		if(searchBtn){
			$(searchBtn).click();
		}else{
			$('#search-btn').click();
		}
	},
	/**
	 * 页面加载完成后，立即加载数据
	 * @param table layui.table
	 * @param layer layui.layer
	 * @param url 数据接口
	 * @param cols 列表设置
	 * @param config 分页参数设置<pre>{
	 * showPage:是否分页显示，默认false,
	 * limit：每页显示条数，默认15,
	 * limits:条数选项，默认[15,30,50,100]
	 * }
	 * </pre>
	 */
	initLoadData:function(url,cols,config){
		layui.use(['table','layer'],function(){
			var layer = layui.layer;
			var table = layui.table;
			data.loadData(table,layer,url,cols,config);
			data.onSearch(table,layer,url,cols,config);
		})
	},
	/**
	 * 页面加载完成后，只加载空表格
	 * @param table layui.table
	 * @param layer layui.layer
	 * @param url 数据接口
	 * @param cols 列表设置
	 * @param config 分页参数设置<pre>{
	 * showPage:是否分页显示，默认false,
	 * limit：每页显示条数，默认15,
	 * limits:条数选项，默认[15,30,50,100]
	 * }
	 * </pre>
	 */
	initLoadEmptyTable:function(url,cols,config){
		layui.use(['table','layer'],function(){
			var layer = layui.layer;
			var table = layui.table;
			data.initLoad(table,layer,url,cols,config);
			data.onSearch(table,layer,url,cols,config);
		})
	},
};


var data = {
	done:function(tableId){
		$('#'+tableId).parent().find('table').addClass('table m-table table-striped m-table--head-bg-brand');
	},
	setting:function(config){
		return $.extend({
			showPage:true,
			limit:15,
			limits:[15,30,50,100],
			done:function(){},
			tableId:'datatable',
			searchBtnId:'search-btn',
			searchFormId:'search-form',
			status:false
		},config);
	},
	/**
	 * 初始化加载空表格
	 * @param table
	 * @param cols
	 * @param config
	 */
	initLoad:function(table,layer,url,cols,config){
		var $this = this, setting = $this.setting(config);
//		console.log(setting);
		laytable = table.render({
			elem : '#'+setting.tableId,
			data:[],
			cols : cols,
			skin : 'row', //表格风格
			page : setting.showPage,
			limit : setting.limit,
			limits : setting.limits,
			even : true,
//			size: 'sm',
			id :setting.tableId,
			method:'POST',
			done:function(data,page,currentCount){
				$this.done(setting.tableId);
				typeof setting.done === 'function' && setting.done(data, page, currentCount);
			}
		});
	},
	/**
	 * 加载数据
	 * @param table layui.table
	 * @param url 数据接口
	 * @param cols 列表设置
	 * @param config 分页参数设置<pre>{
	 * showPage:是否分页显示，默认false,
	 * limit：每页显示条数，默认15,
	 * limits:条数选项，默认[15,30,50,100],
	 * done:function 表格加载完成后执行事件
	 * }
	 * </pre>
	 */
	loadData:function(table,layer,url,cols,config){
		var $this = this, setting = $this.setting(config);
//		console.log(setting);
		laytable = table.render({
			elem : '#'+setting.tableId,
			url: url,
			cols: cols,
			skin: 'row', //表格风格
			page: setting.showPage,
			limit: setting.limit,
			limits: setting.limits,
			even: true,
			id: setting.tableId,
			method: 'POST',
			where: $this.setSearchData(setting.searchFormId),
			done:function(data,page,currentCount){
				$this.done(setting.tableId);
				typeof setting.done === 'function' && setting.done(data, page, currentCount);
			}
		});
	},
	/**
	 * 获取查询参数
	 */
	setSearchData:function(searchFormId){return $('#'+searchFormId).getFormDada()},
	/**
	 * 查询操作
	 * @param table layui.table
	 * @param layer layui.layer
	 * @param url 数据接口
	 * @param cols 列表设置
	 * @param config 分页参数设置<pre>{
	 * showPage:是否分页显示，默认false,
	 * limit：每页显示条数，默认15,
	 * limits:条数选项，默认[15,30,50,100]
	 * }
	 * </pre>
	 */
	onSearch:function(table,layer,url,cols,config){
		var $this = this, $ = layui.$, setting = $this.setting(config);
		$('body').on('click','#'+setting.searchBtnId, function() {
			layer.msg('加载中...',{time:500});
			if(!setting.status) {
				$this.loadData(table,layer,url,cols,setting);
				setting.status = true;
				return;
			}
			var reload = function() {
				table.reload(setting.tableId, {
					where : $this.setSearchData(setting.searchFormId),
					page:{curr:1}
				});
			}
			reload.call(this);
		});
	},
};

var params = {
	left:'left',
	center:'center',
	right:'right'
}

var alert = function(msg,fn){
	layui.use('layer',function(){
		var layer = layui.layer;
		var index = layer.confirm(msg,function(){
			layer.close(index);
			typeof fn === 'function' && fn(index);
		})
	})
}

var loadTree = function(selector,data,onChangedFn,type){
	$(selector).jstree({
        plugins: ["wholerow", type, "types"],//["wholerow", "checkbox", "types"],
        checkbox: {
            three_state: false
        },
        core: {
            themes : {responsive: false},
            data: data
        },
        types : {
            default : {icon : "fa fa-folder m--font-warning"},
            file : {icon : "fa fa-file  m--font-warning"}
        },
    });
	$(selector).on("changed.jstree", function (e, data) {
	    if(typeof onChangedFn == 'function'){
	    	onChangedFn(data);
	    }
	});
}

