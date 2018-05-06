/**
 * @name : larryMS主入口
 * @author larry
 * @QQ: 313492783
 * @site : www.larryms.com
 * @Last Modified time: 2017-12-15 09:00:00
 */
layui
	.extend({
		larryms: 'lib/larryms',
		larryMenu: 'lib/larryMenu',
	})
	.define(
		['jquery', 'larryms', 'larryMenu', 'layer'],
		function(exports) {
			"use strict";
			var $ = layui.$,
				larryms = layui.larryms,
				larryMenu = layui
				.larryMenu(),
				device = layui.device();

			var Admin = function() {
				this.config = {
					icon: 'larry',
					url: '//at.alicdn.com/t/font_477590_wtu39l8gjfjyk3xr.css',
					online: true
				}, this.screen = function() {
					var width = $(window).width();
					if (width >= 1200) {
						return 4;
					} else if (width >= 992) {
						return 3;
					} else if (width >= 768) {
						return 2;
					} else {
						return 1;
					}
				}
			};

			var call = {
				// 扩展面板larry-panel
				panel: function() {
					$('.larry-panel .tools')
						.off('click')
						.on(
							'click',
							function() {
								if ($(this).hasClass(
										'larry-unfold1')) {
									$(this)
										.addClass(
											'larry-fold9')
										.removeClass(
											'larry-unfold1');
									$(this)
										.parent(
											'.larry-panel-header')
										.siblings(
											'.larry-panel-body')
										.slideToggle();
								} else {
									$(this)
										.addClass(
											'larry-unfold1')
										.removeClass(
											'larry-fold9');
									$(this)
										.parent(
											'.larry-panel-header')
										.siblings(
											'.larry-panel-body')
										.slideToggle();
								}
							});
					$('.larry-panel .close').off('click').on(
						'click',
						function() {
							$(this).parents('.larry-panel')
								.parent().fadeOut();
						});
				},
				// 子页面新增tab选项卡到父级窗口
				addTab: function(data) {
					// 1、判断子页面是否在iframe框架内或者是否拥有父级ajaxLoad节点：即不支持页面独立窗口打开的新增
					if (window.top !== window.self) {
						console.log(top.tab);
						// console.log(top.Tabs);
						top.tab.tabAdd(data);
					} else {
						window.location.href = data.href;
					}

				},
				// 页面右键菜单
				RightMenu: function(larryMenuData) {
					larryMenu.ContentMenu(larryMenuData, {
						name: 'body'
					}, $('body'));
					if (top == self) {
						$('#larry_tab_content').mouseenter(function() {
							larryMenu.remove();
						});
					} else {
						$('#larry_tab_content', parent.document)
							.mouseout(function() {
								larryMenu.remove();
							});
					}
				}

			};
			Admin.prototype.init = function() {
				var that = this,
					_config = that.config;

				larryms.fontset({
					icon: _config.icon,
					url: _config.url,
					online: _config.online
				});
				layui.config({
					base: layui.cache.base + 'lib/'
				});
				// 加载特定模块
				if (layui.cache.page) {
					layui.cache.page = layui.cache.page.split(',');
					if ($.inArray('larry', layui.cache.page) === -1) {
						var extend = {};
						layui.cache.mods = layui.cache.mods === undefined ? 'larryms' : layui.cache.mods;
						layui.cache.path = layui.cache.path === undefined ? (layui.cache.mods + '/') : layui.cache.path;
						for (var i = 0; i < layui.cache.page.length; i++) {
							extend[layui.cache.page[i]] = layui.cache.path + layui.cache.page[i];
						}
						layui.extend(extend);
						layui.use(layui.cache.page);
					}
				}
				// 页面右键定义
				if (layui.cache.rightMenu !== false && layui.cache.rightMenu !== 'custom') {
					// 默认右键菜单
					call
						.RightMenu([
							[{
								text: "刷新当前页",
								func: function() {
									if (top == self) {
										document.location
											.reload();
									} else {
										$(
												'.layui-tab-content .layui-tab-item',
												parent.document)
											.each(
												function() {
													if ($(
															this)
														.hasClass(
															'layui-show')) {
														$(
																this)
															.children(
																'iframe')
															.attr(
																'src',
																$(
																	this)
																.children(
																	'iframe')
																.attr(
																	'src'));
													}
												});
									}
								}
							}, {
								text: "重载主框架",
								func: function() {
									top.document.location
										.reload();
								}
							}, {
								text: "设置系统主题",
								func: function() {
									if (top.document
										.getElementById('larryTheme') !== null) {
										top.document
											.getElementById(
												'larryTheme')
											.click();
									} else {
										larryms
											.error(
												'当前页面不支持主题设置或请登陆系统后设置系统主题',
												larryms.tit[0]);
									}
								}
							}, {
								text: "选项卡常用操作",
								data: [
									[{
										text: "定位当前选项卡",
										func: function() {
											if (top.document
												.getElementById('tabCtrD') !== null) {
												top.document
													.getElementById(
														'tabCtrD')
													.click();
											} else {
												larryms
													.error(
														'请先登陆系统，此处无选项卡操作',
														larryms.tit[0]);
											}
										}
									}, {
										text: "关闭当前选项卡",
										func: function() {
											if (top.document
												.getElementById('tabCtrA') !== null) {
												top.document
													.getElementById(
														'tabCtrA')
													.click();
											} else {
												larryms
													.error(
														'请先登陆系统，此处无选项卡操作',
														larryms.tit[0]);
											}
										}
									}, {
										text: "关闭其他选项卡",
										func: function() {
											if (top.document
												.getElementById('tabCtrB') !== null) {
												top.document
													.getElementById(
														'tabCtrB')
													.click();
											} else {
												larryms
													.error(
														'请先登陆系统，此处无选项卡操作',
														larryms.tit[0]);
											}
										}
									}, {
										text: "关闭全部选项卡",
										func: function() {
											if (top.document
												.getElementById('tabCtrC') !== null) {
												top.document
													.getElementById(
														'tabCtrC')
													.click();
											} else {
												larryms
													.error(
														'请先登陆系统，此处无选项卡操作',
														larryms.tit[0]);
											}
										}
									}]
								]
							}, {
								text: "清除缓存",
								func: function() {
									top.document
										.getElementById(
											'clearCached')
										.click();
								}
							}],
							[{
								text: "访问larryMS官网",
								func: function() {
									top.window
										.open('https://www.larryms.com');
								}
							}]
						]);
				} else if (layui.cache.rightMenu === false) {
					larryMenu.remove();
					larryMenu = null;
				}

			};
			//larry-panel
			Admin.prototype.panel = function() {
				call.panel();
			};

			Admin.prototype.render = Admin.prototype.init;
			var larry = new Admin();
			larry.render();

			exports('larry', larry);
		});

//通用工具类
var ajaxSetup = $.ajaxSetup({
	complete:function(XMLHttpRequest, textStatus){
		//非成功处理
		if(XMLHttpRequest.responseJSON && (XMLHttpRequest.responseJSON.success==false)){
			alert(XMLHttpRequest.responseJSON.message);
		}
	},
	beforeSend:function(xhr){
	},
	type:'POST',
	dataType: 'json'
})

//格式化金额 保留后2位小数
function formatCurrency(num) {
	if (num == undefined || num == '') {
		return 0.00;
	}
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num))
		num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num * 100 + 0.50000000001);
	cents = num % 100;
	num = Math.floor(num / 100).toString();
	if (cents < 10)
		cents = "0" + cents;
	for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
	return (((sign) ? '' : '-') + num + '.' + cents);
}

//POST提交
function post(URL, PARAMS) {
	var temp = document.createElement("form");
	temp.action = URL;
	temp.method = "post";
	temp.style.display = "none";
	for (var x in PARAMS) {
		var opt = document.createElement("textarea");
		opt.name = x;
		opt.value = PARAMS[x];
		temp.appendChild(opt);
	}
	document.body.appendChild(temp);
	temp.submit();
	return temp;
}

//AJAX 同步 
function sync(url, PARAMS,success) {
	return ajax_util(url, PARAMS, false,success);
}

//AJAX 异步
function async(url, PARAMS,success) {
	return ajax_util(url, PARAMS, true,success);
}

//AJAX工具JS
function ajax_util(url, PARAMS, is_a_s_ync,success) {
	var _data;
	$.ajax({
		cache: false,
		url: url,
		data: PARAMS,
		async: is_a_s_ync,
		error: function(request) {
			alert("Connection error");
		},
		success: function(data) {
			_data = data;
			typeof success =='function' && success(data)
		},
		error: function() {
			alert("Ajax请求数据失败!");
		}
	});
	return _data;
}

//时间格式 包含 时分秒
function formatterDate(date) {
	var datetime = date.getFullYear() + "-" // "年"
		+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1)) + "-" // "月"
		+ (date.getDate() < 10 ? "0" + date.getDate() : date
			.getDate());
	return datetime;
}

//时间转换JS 年月日
function getDateTime(data) {
	if (data == undefined) {
		return "";
	}
	var d = new Date(data); //根据时间戳生成的时间对象
	var date = (d.getFullYear()) + "-" + (d.getMonth() + 1) + "-" + (d.getDate());
	return date;
}

//具体时间转换 精确到秒
function getDateTimeHours(data) {
	if (data == undefined) {
		return "";
	}
	var d = new Date(data); //根据时间戳生成的时间对象
	var date = (d.getFullYear()) + "-" + (d.getMonth() + 1) + "-" + (d.getDate()) + " " + (d.getHours()) + ":" + (d.getMinutes()) + ":" + (d.getSeconds());
	return date;
}

//具体时间 精确到分
function getDateTimeMinute(data) {
	if (data == undefined) {
		return "";
	}
	var d = new Date(data); //根据时间戳生成的时间对象
	var date = (d.getFullYear()) + "-" + (d.getMonth() + 1) + "-" + (d.getDate()) + " " + (d.getHours()) + ":" + (d.getMinutes());
	return date;
}

//计算天数差的函数，通用  
function DateDiff(sDate1, sDate2) { //sDate1和sDate2是2006-12-18格式  
	var aDate, oDate1, oDate2, iDays
	aDate = sDate1.split("-")
	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]) //转换为12-18-2006格式  
	aDate = sDate2.split("-")
	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) //把相差的毫秒数转换为天数  
	return iDays
}

//时间格式 包含 时分秒当天最初时间 秒统一为 00:00:00
function formatterDateStart(date) {
	var datetime = date.getFullYear() + "-" // "年"
		+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1)) + "-" // "月"
		+ (date.getDate() < 10 ? "0" + date.getDate() : date
			.getDate()) + " " + "00:00:00";
	return datetime;
}

//获取当前时间 前N天的时间
function getBeforeDate(n) {
	var n = n;
	var d = new Date();
	var year = d.getFullYear();
	var mon = d.getMonth() + 1;
	var day = d.getDate();
	if (day <= n) {
		if (mon > 1) {
			mon = mon - 1;
		} else {
			year = year - 1;
			mon = 12;
		}
	}
	d.setDate(d.getDate() - n);
	year = d.getFullYear();
	mon = d.getMonth() + 1;
	day = d.getDate();
	s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
	return s;
}