/****************文件上传插件封装*****************/
!function($, window, document){
	"use strict";
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
		    hidePicker:true,
		},
        this.options = $.extend({}, this.defaults, opt)
    }
	
	FileUploader.prototype  = {
		init:function(){
			var that = this;
			that.$element.addClass('layui-hide').after(
				'<div id="layui-upload" class="layui-upload" title="点击选择文件">\
					<span class="center-ps ps"><i class="la la-picture-o" style="display: block;color: #ddd;font-size: 86px;"></i>可以尝试<g style="color:red">拖拽</g>图片或者使用<g style="color:red">截屏工具粘贴</g>(Ctrl+V)图片到本区域</span>\
					<div id="listView" class="layui-upload-list">\
					</div>\
					<div id="uploader" class="wu-example">\
						<div class="btns">\
						    <div id="picker">添加图片</div>\
						</div>\
					</div>\
				</div>\
			');
			var uploader = WebUploader.create(this.options);
			that.uploader=uploader;
			that.$uploader='#layui-upload';
			that.$listView=$('#listView');
			layui.use(['layer','element'],function(){
				that.layer = layui.layer;
				that.element = layui.element;
				that.initevents();
				that.reloadPics(that.options.picData);
			});
			
			if(that.options.hidePicker){
				$('#picker').css('display','none');
				var label = '#picker label';
				$('#layui-upload').on('click',function(e){
					if(e.target!=this) return;
					$(label).click();
				})
				that.$listView.on('click',function(e){
					if(e.target!=this) return;
					$(label).click();
				})
				$('.center-ps').on('click',function(){
					$(label).click();
				})
			}
			
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
		removeFile:function(item,file){
			//移除事件并移除dom
			item.find('.image-reload,.image-delete,.upload-img-prev-link').off().end().remove();
			//移除某一文件, 默认只会标记文件状态为已取消，如果第二个参数为 `true` 则会从 queue 中移除
			this.uploader.removeFile(file,true);
			//从已上传文件列表移除
			delete this.data.uploadedFiles[file.id];
			//是否显示中间上传提示信息
			if(this.$listView.find('.layui-upload-item').length<=0){
				$(this.$uploader).find('.center-ps').removeClass('layui-hide');
			}
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
			})
		},
		beforeFileQueued:function(){
			var that = this;
			that.uploader.on( 'beforeFileQueued', function(file) {
				//验证最大允许上传大小
				if(file.size > that.options.fileSingleSizeLimit){
					that.errorMsg('最大允许上传大小为'+ that.options.fileSingleSizeLimit/1024/1024 +'M');
					return false;
				}
				//验证所有上传数量
				var size = $('.layui-upload-item').length;
				if(size>=that.options.fileNumLimit){
					that.errorMsg('超出数量限制，最大上传数量为：<span style="color:red">'+that.options.fileNumLimit+'</span>');
					return false;
				}
				
				return typeof that.options.beforeFileQueued=='function' &&that.options.beforeFileQueued(file);
		    });
		},
		addFile:function(file){
			var that = this;
			//隐藏上传提示
			$(this.$uploader).find('.center-ps').addClass('layui-hide');
			// 添加到列表
			var dataid = file.dataid ||'';
			var $item = $(['<div id="'+ file.id +'" dataid="'+dataid+'" class="layui-upload-item upload-img-prev-link" title="'+file.name+'">'
				,'<div class="layui-upload-hover-item layui-upload-preview"></div>'
		       /* ,'<p class="layui-upload-hover-item"></p>'*/
		        ,'<div class="layui-upload-btns layui-upload-hover-item">'
		          ,'<a href="javascript:;" class="layui-btn layui-btn-mini image-reload layui-hide" title="重传"><i class="fa fa-repeat"></i></a>'
		          ,'<a href="javascript:;" class="layui-btn layui-btn-mini layui-btn-danger image-delete" title="删除"><i class="fa fa-trash"></i></a>'
		        ,'</div>'
		        ,'<div class="layui-progress layui-progress-big layui-progress-radius-fix" lay-showpercent="true" lay-filter="image'+file.id+'">'
		        ,'<span class="layui-progress-text size">'+ (file.size/1024).toFixed(1) +'kb</span>'
	        	  ,'<div class="layui-progress-bar" lay-percent="0%">'
	        	    ,'<span class="layui-progress-text progress-text">0%</span>'
	        	  ,'</div>'
	        	  ,'<div class="layui-progress-status">等待上传</div>'
	        	,'</div>'
		      ,'</div>'].join(''));
			
			// 预览
			var imgsrc;
			var reader = new FileReader();
			reader.readAsDataURL(file.source.source);  
			reader.onload = function(data){
		 	    if (this.result) {
		 	    	var result = this.result;
		 	    	var $img=$('<img style="width:233px;height:200px" src="'+result+'"></img>');
		 	    	$item.find('.layui-upload-preview').append($img);
		 	    	// layer.photos预览大图
		 	    	$img.on('click', function(){
		 	    		that.layer.photos({
		 	        	    photos: {data:[{alt: file.name,src: result,pid: file.id}]}
		 	        	    ,anim: 5 // 0-6的选择，指定弹出图片动画类型，默认随机
		 	        	});
		 	        });
		 	    }else{
		 	    	img = '<span>不能预览</span>';
		 	    }
		 	    // layui tip预览小图
		    }
			//删除
			$item.find('.image-delete').on("click", function () {
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
							var id = $this.closest('.layui-upload-item').attr('dataid');
							if(that.options.isRealTimeDelete){
								$.ajax({
									url:settings.deleteServer,
									data:{id:id},
									success:function(data){
										if(data.success){
											that.removeFile($item,file);
										}
										that.layer.msg((data&&data.message)||'未知');
									}
								})
							}else{
								that.removeFile($item,file);
							}
							that.data.deletedFileIds.push(id);
							that.layer.close(index);
							typeof that.options.fileDeleted == 'function' && that.options.fileDeleted(that.data);
						}
					)
				}else{
					that.removeFile($item,file);
				}
			});
			//重传
			$item.find('.image-reload').on("click", function () {
				that.uploader.upload(file);
			});
			that.$listView.append($item);
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
		    	var $item = $('#'+file.id);
		    	$item.find('.layui-progress-status').html('上传中...');
	    		that.element.progress('image'+file.id, percentage.toFixed(1) * 100+'%');//设置页面进度条
		    });
		},
		successFile:function(file,data){
			var $item = $('#'+file.id);
			$item.attr('dataid',data.data.id||'');
	    	if(data.success){
	    		$item.find('.layui-progress-status').html('上传成功');
	    		$item.find('.layui-progress-bar').css('background-color','#86EAA1');
	    		this.data.uploadedFiles[file.id]=data.data;//缓存已上传的文件属性：{id:'',url:''}
	    	}else{
	    		this.errorFile(file,data);
	    	}
		},
		errorFile:function(file,data){
			var $item = $('#'+file.id);
			$item.find('.layui-progress-status').html('上传失败');
			$item.find('.layui-progress-bar').css('background-color','#ff5722');
			$item.find('.image-reload').removeClass('layui-hide'); //显示重传
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