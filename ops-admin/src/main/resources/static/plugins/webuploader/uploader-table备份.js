/****************文件上传插件封装*****************/
!function($, window, document){
	
	var FileUploader = function(ele, opt) {
        this.$element = ele,
        this.defaults = {
		    // 上传文件接口
		    server: '',
		    // 删除文件接口
		    deleteServer: '',
		    //md5秒传验证接口
		    md5Server:'',
		    // 选择文件入口
		    pick: '#picker',
		    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		    resize: false,
		    // 是否允许在文件传输时提前把下一个文件准备好。默认true
	        // 对于一个文件的准备工作比较耗时，比如图片压缩，md5序列化。
	        // 如果能提前在当前文件传输期处理，可以节省总体耗时。
		    prepareNextFile:true,
		    // 允许上传文件类型 默认图片
		    accept: {
			   	title: 'Images',
			   	extensions: 'gif,jpg,jpeg,bmp,png',
			   	mimeTypes: 'image/png,image/jpeg,image/gif,image/bmp'
		   	},
		   	//验证文件总数量, 超出则不允许加入队列。
		   	fileNumLimit:10,
		   	//去重， 根据文件名字、文件大小和最后修改时间来生成hash Key
		   	//true为可重复   ，false为不可重复
		   	duplicate:false,
		   	//允许上传的单个文件最大大小（10M）
		   	fileSingleSizeLimit:10*1024*1024,
		   	//指定Drag And Drop拖拽的容器，如果不指定，则不启动。
		   	dnd:'.layui-upload',
		   	//是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开。
		   	disableGlobalDnd:true,
		   	//粘贴截图
		   	paste: document.body,
		   	//上传并发数。允许同时最大上传进程数。
		   	threads: 3,
		    //是否实时删除文件，如果实时删除则请求删除文件ajax接口
		    //如果不是实时删除,则记录删除文件,默认true
		    isRealTimeDelete: true,
		    //是否自定义添加文件错误事件
		    eventAddFileErrorCustom:false,
		    //用于回显图片的图片链接数组
		    addFileError:function(){},
		    fileDeleted:function(){},
		    beforeFileQueued:function(){},
		    fileQueued:function(){},
		    uploadProgress:function(){},
		    uploadSuccess:function(){},
		    uploadError:function(){},
		    uploadFinished:function(){},
		    //图片回显数据，id和url对象组成的数组
		    picData:[],//[{id:'uuid1',url:'picurl1'},{id:'uuid2',url:'picurl2'}]
		    picServerPrefix:'',//图片回显调用的路径前缀
		},
        this.options = $.extend({}, this.defaults, opt)
    }
	
	FileUploader.prototype  = {
		init:function(){
			var that = this;
			that.$element.addClass('layui-hide').after('\
				<div class="layui-upload">\
					<div class="layui-upload-list">\
						<table class="layui-table" lay-size="sm" lay-skin="row">\
							<thead>\
								<tr>\
								<th>文件名</th>\
								<th>大　小</th>\
								<th>状　态</th>\
								<th>进　度</th>\
								<th>操　作</th>\
								</tr>\
							</thead>\
							<tbody id="listView"></tbody>\
						</table>\
					</div>\
					<div id="uploader" class="wu-example">\
						<div class="btns">\
						    <div id="picker">添加图片</div>\
						    <span class="center-ps ps">可以尝试<g style="color:red">拖拽</g>图片或者使用<g style="color:red">截屏工具粘贴</g>(Ctrl+V)图片到本区域</span>\
						</div>\
					</div>\
				</div>\
					');
			var uploader = WebUploader.create(this.options);
			that.uploader=uploader;
			that.$listView=$('#listView');
			layui.use(['layer','element'],function(){
				that.layer = layui.layer;
				that.element = layui.element;
				that.initevents();
				that.reloadPics(that.options.picData);
			})
			return that;
		},
		upload:function(){
			this.uploader.upload();
		},
		data:{
			uploadedFiles:{},//记录已上传的文件
			deletedFileIds:[],//记录删除的已上传的文件id
		},
		errorMsg:function(content){
			return this.layer.msg(content, {icon: 2 ,shift: 6 ,time:2000});
		},
		removeFile:function($tr,file){
			//移除事件并移除dom
			$tr.find('.image-reload,.image-delete,.upload-img-prev-link').off().end().remove();
			//移除某一文件, 默认只会标记文件状态为已取消，如果第二个参数为 `true` 则会从 queue 中移除
			this.uploader.removeFile(file,true);
			//从已上传文件列表移除
			delete this.data.uploadedFiles[file.id];
		},
		initevents:function(){
			this.ready();			//上传插件就绪事件
			this.addFileError();	//添加文件出错
			this.beforeFileQueued();//验证最大允许上传大小
			this.fileQueued();		//当有文件被添加进队列的时候
			this.uploadProgress();	//文件上传过程中创建进度条实时显示
			this.uploadSuccess();	//文件上传成功
			this.uploadError();	//文件上传失败
			this.uploadFinished();	//文件全部上传成功
		},
		ready:function(){
			this.uploader.on('ready',function(){
				console.log('ready');
			})
		},
		//添加文件出错
		addFileError:function(){
			var that = this;
			//1.在设置了`fileNumLimit`且尝试给`uploader`添加的文件数量超出这个值时派送。
			//2.在设置了`Q_EXCEED_SIZE_LIMIT`且尝试给`uploader`添加的文件总大小超出这个值时派送。
			//3.当文件类型不满足时触发。。
			that.uploader.on('error', function(type,arg1,arg2){
				if(that.options.eventAddFileErrorCustom){
					typeof that.options.addFileError=='function' &&that.options.addFileError(type,arg1,arg2);
				}else{
					if(type==='Q_TYPE_DENIED'){//文件类型不符
						that.errorMsg('文件类型不符，请选择正确的图片:<span style="color:red">'+arg1.name+'</span>');
					}else if (type==='Q_EXCEED_NUM_LIMIT') {//超出数量限制
						that.errorMsg('超出数量限制，最大上传数量为：<span style="color:red">'+arg1+'</span>');
					}else if(type==='F_DUPLICATE'){
						that.errorMsg('这个文件已经被选择了:<span style="color:red">'+arg1.name+'</span>');
					}
				}
				console.log('error');
			})
		},
		//验证最大允许上传大小
		beforeFileQueued:function(){
			var that = this;
			that.uploader.on( 'beforeFileQueued', function(file) {
				if(file.size > that.options.fileSingleSizeLimit){
					that.errorMsg('最大允许上传大小为'+ that.options.fileSingleSizeLimit/1024/1024 +'M');
					return false;
				}
				return typeof that.options.beforeFileQueued=='function' &&that.options.beforeFileQueued(file);
		    });
		},
		addFile:function(file){
			var that = this;
			// 添加到列表
			var dataid = file.dataid ||'';
			var $tr = $(['<tr id="'+ file.id +'" dataid="'+dataid+'">'
		        ,'<td class="name"><a title="'+file.name+'" class="upload-img-prev-link" href="javascript:;">'+ file.name +'</a></td>'
		        ,'<td>'+ (file.size/1024).toFixed(1) +'kb</td>'
		        ,'<td>等待上传</td>'
		        ,'<td>'
		        	,'<div class="layui-progress layui-progress-big layui-progress-radius-fix" lay-showpercent="true" lay-filter="image'+file.id+'">'
		        	  ,'<div class="layui-progress-bar" lay-percent="0%">'
		        	    ,'<span class="layui-progress-text">0%</span>'
		        	  ,'</div>'
		        	,'</div>'
		        ,'</td>'
		        ,'<td>'
		          ,'<a href="javascript:;" class="layui-btn layui-btn-mini image-reload layui-hide">重传</a>'
		          ,'<a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger image-delete">删除</a>'
		        ,'</td>'
		      ,'</tr>'].join(''));
			
			// 预览
			var imgsrc;
			var reader = new FileReader();
			reader.readAsDataURL(file.source.source);  
			reader.onload = function(data){
		 	    if (this.result) {
		 	    	var result = this.result;
		 	    	var img='<img style="max-width:320px;max-height:500px" src="'+result+'"></img>'
		 	    	// layer.photos预览大图
		 	    	$tr.find('.upload-img-prev-link').on('click', function(){
		 	    		that.layer.photos({
		 	        	    photos: {data:[{alt: file.name,src: result,pid: file.id}]}
		 	        	    ,anim: 5 // 0-6的选择，指定弹出图片动画类型，默认随机
		 	        	});
		 	        });
		 	    }else{
		 	    	img = '<span>不能预览</span>';
		 	    }
		 	    
		 	    // layui tip预览小图
	 	        var thisTipIndex = 0;
	 	        $tr.find('.upload-img-prev-link').on('mouseover', function(){
	 	        	thisTipIndex = that.layer.tips(img, $(this),{
	 	        		tips: [2, 'rgba(35,38,46,0.5)'],
	 	        		maxWidth: 350,
	 	        		time: 5000,
	 	        	});
	 	        }).on('mouseleave', function(){
	 	        	that.layer.close(thisTipIndex);
	 	        });
		    }
			//删除
			$tr.find('.image-delete').on("click", function () {
				var $this = $(this);
				if(that.data.uploadedFiles[file.id]){
					var index = that.layer.alert(
						'该文件已上传，确认删除附件吗？', 
						{
							skin: '',
							title: '提示信息',
							closeBtn:1
						},
						function(){
							//判断是否实时删除文件，如果实时删除则请求删除文件ajax接口
							//如果不是实时删除,则记录删除文件的id
							var id = $this.closest('tr').attr('dataid');
							if(that.options.isRealTimeDelete){
								$.ajax({
									url:settings.deleteServer,
									data:{id:id},
									success:function(data){
										if(data.success){
											that.removeFile($tr,file);
										}
										that.layer.msg((data&&data.message)||'未知');
									}
								})
							}else{
								that.removeFile($tr,file);
							}
							that.data.deletedFileIds.push(id);
							that.layer.close(index);
							typeof that.options.fileDeleted == 'function' && that.options.fileDeleted(that.data);
						}
					)
				}else{
					that.removeFile($tr,file);
				}
			});
			//重传
			$tr.find('.image-reload').on("click", function () {
				that.uploader.upload(file);
			});
			that.$listView.append($tr);
		},
		//当有文件被添加进队列的时候执行
		fileQueued:function(){
			var that = this;
			that.uploader.on( 'fileQueued', function(file) {
				typeof that.options.fileQueued=='function' &&that.options.fileQueued(file);
				that.addFile(file);
			});
		},
		// 文件上传过程中创建进度条实时显示
		uploadProgress:function(){
			var that = this;
			that.uploader.on('uploadProgress', function (file, percentage) {
				typeof that.options.uploadProgress=='function' &&that.options.uploadProgress(file, percentage.toFixed(1) * 100);
		    	var tds = $('#'+file.id).children();
		    	tds.eq(2).html('<span style="color: #5FB878;">上传中...</span>');
		    		that.element.progress('image'+file.id, percentage.toFixed(1) * 100+'%');//设置页面进度条
		    });
		},
		successFile:function(file,data){
			var tds = $('#'+file.id).attr('dataid',data.data.id||'');
			var tds = $('#'+file.id).children();
	    	if(data.success){
	    		tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
	    		tds.eq(3).find('.layui-progress-bar').css('background-color','#86EAA1');
	    		this.data.uploadedFiles[file.id]=data.data;//缓存已上传的文件属性：{id:'',url:''}
	    	}else{
	    		this.errorFile(file,data);
	    	}
		},
		errorFile:function(file,data){
			var tds = $('#'+file.id).children();
			tds.eq(2).html('<span style="color: #ff5722;">上传失败</span>');
			tds.eq(3).find('.layui-progress-bar').css('background-color','#ff5722');
			tds.eq(4).find('.image-reload').removeClass('layui-hide'); //显示重传
			this.errorMsg(file.id+'上传失败：'+data.message)
		},
		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
		uploadSuccess:function(){
			var that = this;
			that.uploader.on('uploadSuccess', function (file, response) {
				typeof that.options.uploadSuccess=='function' &&that.options.uploadSuccess(file, response);
		    	that.successFile(file, response);
		    });
		},
		// 文件上传失败，显示上传出错。
		uploadError:function(){
			var that = this;
			that.uploader.on('uploadError', function (file) {
				typeof that.options.uploadError=='function' &&that.options.uploadError(file);
				that.error(file);
			});
		},
		//全部文件上传完成事件
		uploadFinished:function(){
			var that = this;
			that.uploader.on('uploadFinished', function () {
				var length = $('#listView').children().length;
				if(Object.keys(that.data.uploadedFiles).length == length){//总上传数=已上传数
					typeof that.options.uploadFinished=='function' &&that.options.uploadFinished(that.data);
				}
			});
		},
		/**
		 * 需要编辑的图片列表
		 * picList = ['图片url','图片url','图片url','图片url' ]
		 */
		reloadPics:function(picData){
			var that = this;
			var getFileBlob = function (url, cb) {
			    var xhr = new XMLHttpRequest();
			    var _true = 
			    xhr.open("GET", (url.startsWith('http://') || url.startsWith('https://'))?url:that.options.picServerPrefix + url);
			    xhr.responseType = "blob";
			    xhr.addEventListener('load', function() {
			    	cb(xhr.response);
			    });
			    xhr.send();
			};
			var blobToFile = function (blob, name) {
				blob.lastModifiedDate = new Date();
				blob.name = name;
				return blob;
			};

			var getFileObject = function(filePathOrUrl, cb) {
				getFileBlob(filePathOrUrl, function (blob) {
					cb(blobToFile(blob, filePathOrUrl.substring(filePathOrUrl.lastIndexOf('/')+1,filePathOrUrl.length)));
				});
			};
			
			$.each(picData, function(index,item){
				getFileObject(item.url, function (fileObject) {
					var wuFile = new WebUploader.Lib.File(WebUploader.guid('rt_'),fileObject);
					var file = new WebUploader.File(wuFile);
					file.statusText='complete';
					file.dataid = item.id;
					that.addFile(file);
					var data = {success:true, data:item};
					that.successFile(file,data);
					that.element.progress('image'+file.id, '100%');//设置页面进度条
				})
			});
		}
	}
	
	$.fn.uploader = function(options){
        var fileUploader = new FileUploader(this, options);
        return fileUploader.init();
	}
}(jQuery,window, document)