MusicBox = function() {
	var _this = this;
	var media = document.getElementById("musicBox");
	//var media1;
	//var media12;
	//var media123;
	var mucicFiles = [ {
		name : "学划船",
		url : "/weixinImage/music/10.mp3"
	}, {
		name : "雨滴",
		url : "/weixinImage/music/11.mp3"
	}, {
		name : "做游戏",
		url : "/weixinImage/music/12.mp3"
	} ,{
		name : "Huldra-Mi",
		url : "/weixinImage/music/20.mp3"
	}, {
		name : "Olav Bergsland",
		url : "/weixinImage/music/21.mp3"
	}, {
		name : "Trebakken",
		url : "/weixinImage/music/22.mp3"
	},{
		name : "花的圆舞曲",
		url : "/weixinImage/music/30.mp3"
	}, {
		name : "美丽的彩虹",
		url : "/weixinImage/music/31.mp3"
	}, {
		name : "梦幻曲",
		url : "/weixinImage/music/32.mp3"
	}, 
	{
		name : "怪话",
		url : "/weixinImage/music/40.mp3"
	}, {
		name : "陌生的国家和人民",
		url : "/weixinImage/music/41.mp3"
	}, {
		name : "捉迷藏",
		url : "/weixinImage/music/42.mp3"
	}, {
		name : "会跳舞的洋娃娃",
		url : "/weixinImage/music/50.mp3"
	}, {
		name : "快乐的小画家",
		url : "/weixinImage/music/51.mp3"
	}, {
		name : "小小卡车司机",
		url : "/weixinImage/music/52.mp3"
	}, {
		name : "Codename Dragonfly",
		url : "/weixinImage/music/60.mp3"
	}, {
		name : "Seek You",
		url : "/weixinImage/music/61.mp3"
	}, {
		name : "Take Me Higher",
		url : "/weixinImage/music/62.mp3"
	}, {
		name : "03",
		url : "/weixinImage/music/70.mp3"
	}, {
		name : "dinner for two",
		url : "/weixinImage/music/71.mp3"
	}, {
		name : "曲目",
		url : "/weixinImage/music/72.mp3"
	}, {
		name : "糖果仙子之舞",
		url : "/weixinImage/music/80.mp3"
	}, {
		name : "王子与糖果仙子的舞蹈",
		url : "/weixinImage/music/81.mp3"
	}, {
		name : "雪精灵的华尔兹",
		url : "/weixinImage/music/82.mp3"
	},{
		name : "Ave Maria",
		url : "/weixinImage/music/90.mp3"
	}, {
		name : "Moditwa Dziewicy",
		url : "/weixinImage/music/91.mp3"
	}, {
		name : "Songs Of Spring",
		url : "/weixinImage/music/92.mp3"
	},{
		name : "Dawn",
		url : "/weixinImage/music/00.mp3"
	}, {
		name : "Eine Kiene Nachtmusik",
		url : "/weixinImage/music/01.mp3"
	}, {
		name : "Menuet",
		url : "/weixinImage/music/02.mp3"
	}];

	var index = -1;
	var playingFile = null;
	var playMode = 1;
	this.nextMusic = function() {
//		if (playMode == "1") {
//			index += 1;
//		}
//		if (index == mucicFiles.length) {
//			index = 0;
//		}
		playingFile = mucicFiles[0];
//		media.attr("src", playingFile.url);
		media.setAttribute("src",playingFile.url);
//		media.play();
		$("#ul_musicList1").children().css({
			"background-color" : "#F00",
			"border" : "solid 1px #F00",
			"color" : "#F00"
		});
		($("#ul_musicList1").children()[index]).css({
			"background-color" : "#F00",
			"border" : "solid 1px #F00",
			"color" : "#F00"
		});
	}
	this.loadStart = function() {
		("#sn_status").text("加载中……");
	}
	this.playing = function() {
		("#sn_status").text("当前正在播放：" + playingFile.name);
	}
	this.pausePaly = function() {
		("#sn_status").text("暂停："+playingFile.name);
	}
	this.loadError = function() {
//		alert("加载错误");
		("#sn_status").text("加载" + playingFile.name + "失败，可能文件不存在");
	}
	this.plays = function(){
//		media123.pause();
//		media1.pause();
//		media12.pause();
//		media123.play();
	}
	
	this.init = function() {
		var temp;
		for(var i=0;i<mucicFiles.length;i++){
			if(i%3==0){
				$("#ul_musicList"+Math.floor(i/3)).append("<li>" + mucicFiles[i].name + ":<br><audio preload='none' id=musicBox"+i+" onplay='mb.plays()' onended='mb.nextMusic()' onloadstart='mb.loadStart()' onplaying='mb.playing()' onpause='mb.pausePaly()' onerror='mb.loadError()'> </audio></li>");
				var media0 = document.getElementById("musicBox"+i);
				media0.setAttribute("src",mucicFiles[i].url);
			}else if(i%3==1){
				$("#ul_musicList"+Math.floor(i/3)).append("<li>" + mucicFiles[i].name + ":<br><audio preload='none' id=musicBox"+i+" onplay='mb.plays()' onended='mb.nextMusic()' onloadstart='mb.loadStart()' onplaying='mb.playing()' onpause='mb.pausePaly()' onerror='mb.loadError()'> </audio></li>");
				var media1 = document.getElementById("musicBox"+i);
				media1.setAttribute("src",mucicFiles[i].url);
			}else if(i%3==2){
				$("#ul_musicList"+Math.floor(i/3)).append("<li>" + mucicFiles[i].name + ":<br><audio preload='none' id=musicBox"+i+" onplay='mb.plays()' onended='mb.nextMusic()' onloadstart='mb.loadStart()' onplaying='mb.playing()' onpause='mb.pausePaly()' onerror='mb.loadError()'> </audio></li>");
				var media2 = document.getElementById("musicBox"+i);
				media2.setAttribute("src",mucicFiles[i].url);
			}
			
		}
		_this.nextMusic();
//		("#slt_playMode").change(function() {
//			playMode = ("#slt_playMode").val();
//		});
	}
	
	this.named=function(names){
		return "musicBox"+names;
	}
}