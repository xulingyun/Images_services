$(document).ready(function () {
    //获取音频工具 
    var audio = document.getElementById("myMusic10"); 
    var audio10 = document.getElementById("myMusic10");
    var control;
    var timeTask;
    var id;
    var idStr;
    
//    $("#MainControl10").click(function () {
//    	if($(this).attr('class')=='MainControl'){
//	        $(this).removeClass("MainControl").addClass("StopControl");
//	        if (audio10.src == "") {
//	            var Defaultsong = $(".Single .SongName").eq(0).attr("KV");
//	            $(".MusicBox .ProcessControl .SongName").text(Defaultsong);
//	            $(".Single .SongName").eq(0).css("color", "#7A8093");
//	            audio10.src = "/weixinImage/music/10.mp3";
//	        }
//	        audio10.play();
//	        timeTask = null;
//	        TimeSpan(10);
//    	}else{
//            $(this).removeClass("StopControl").addClass("MainControl");
//            audio10.pause();
//    	}
//    });
    
    $(".MainControl").click(function () {
    	idStr = $(this).attr('id');
    	id = idStr.substring(11);
    	control = $(this).attr('class');
    	if(control=="MainControl"){
    		handleButton();
    		$(this).removeClass("MainControl").addClass("StopControl");
	    	handleStart(id);
    	}else{
    	    $(this).removeClass("StopControl").addClass("MainControl");
    	    handleStop(id);
    	}
    });
    
    $(".StopControl").click(function () {
	    $(this).removeClass("StopControl").addClass("MainControl");
	    idStr = $(this).attr('id');
	    id = idStr.substring(11);
	    handleStop(id);
    });
    
    
    /*
    之前一直考虑进度条的原理，这边进度条的做法启发自腾讯一款播放器的做法，采用两个2px高度的div，重叠，
    上面那个与下面那个div的颜色不一样，用于区分进度,顶层div的width是根据播放的长度来调整的，width越长，说明播放越长，
    知道上层的div完全覆盖下面的div，达到长度的一致，说明播放完毕。我们的播放进度条和音量进度条都是这样做的
    */

    // 音频进度条事件（进度增加）
//    $(".Process").click(function (e) {
//        //播放进度条的基准参数
//        var Process = $(".Process").offset();
//        var ProcessStart = Process.left;
//        var ProcessLength = $(".Process").width();
//
//
//        var CurrentProces = e.clientX - ProcessStart;
//        DurationProcessRange(CurrentProces / ProcessLength);
//        $(".ProcessYet").css("width", CurrentProces);
//    });

    //音频进度条事件（进度减少）
//    $(".ProcessYet").click(function (e) {
//        //播放进度条的基准参数
//        var Process = $(".Process").offset();
//        var ProcessStart = Process.left;
//        var ProcessLength = $(".Process").width();
//
//        var CurrentProces = e.clientX - ProcessStart;
//        DurationProcessRange(CurrentProces / ProcessLength);
//        $(".ProcessYet").css("width", CurrentProces);
//    });
    
    $(".Process").click(function (e) {
    	var idStrProcess = $(this).attr('id');
    	var idProcess = idStrProcess.substring(7);
    	var x = e.clientX;
    	handleProcess(idProcess,x);
    });
    
    $(".ProcessYet").click(function (e) {
    	var idStrProcessYet = $(this).attr('id');
    	var idProcessYet = idStrProcessYet.substring(10);
    	var x = e.clientX;
    	handleProcessYet(idProcessYet,x);
    });
    
    

    //监听媒体文件结束的事件（ended），这边一首歌曲结束就读取下一首歌曲，实现播放上的循环播放
    audio10.addEventListener('ended', function () {
        $(".MusicList .List .Single .SongName").each(function () {
            if ($(this).css("color") == "rgb(122, 128, 147)") {
                var IsBottom = $(this).parent(".Single").next(".Single").length == 0 ? true : false;  //检查是否是最尾端的歌曲
                var NextSong;
                if (IsBottom) {
                    NextSong = $(".Single").first().children(".SongName").attr("KV");
                    $(".Single").first().children(".SongName").css("color", "#7A8093");
                }
                else {
                    NextSong = $(this).parent(".Single").next(".Single").children(".SongName").attr("KV");
                    $(this).parent(".Single").next(".Single").children(".SongName").css("color", "#7A8093");
                }

                audio10.src = "../Media/" + NextSong + ".mp3";
                $(".MusicBox .ProcessControl .SongName").text(NextSong);
                $(this).css("color", "#fff");
                audio10.play();
                return false; //代表break
            }
        });
    }, false);


});

function handleProcess(idProcess,x){
	 var Process = $("#Process"+idProcess).offset();
     var ProcessStart = Process.left;
     var ProcessLength = $("#Process"+idProcess).width();
     var CurrentProces = x - ProcessStart;
     DurationProcessRange(CurrentProces / ProcessLength,idProcess);
     $("#ProcessYet"+idProcess).css("width", CurrentProces);
}

function handleProcessYet(idProcessYet,x) {
    var Process = $("#Process"+idProcessYet).offset();
    var ProcessStart = Process.left;
    var ProcessLength = $("#Process"+idProcessYet).width();
    var CurrentProces = x - ProcessStart;
    DurationProcessRange(CurrentProces / ProcessLength,idProcessYet);
    $("#ProcessYet"+idProcessYet).css("width", CurrentProces);
}

function handleStart(id){
    var audio10 = document.getElementById("myMusic"+id);
	if (audio10.src == "") {
        var Defaultsong = $(".Single .SongName").eq(0).attr("KV");
        $(".MusicBox .ProcessControl .SongName").text(Defaultsong);
        $(".Single .SongName").eq(0).css("color", "#7A8093");
        audio10.src = "/weixinImage/music/"+id+".mp3";
    }
	audio10.play();
    timeTask = null;
    TimeSpan(id);
}

function handleStop(id){
    var audio10 = document.getElementById("myMusic"+id);
    audio10.pause();
}

function handleButton(){
	var temp0;
	var temp1;
	var temp;
	var audio10;
	var c;
	for(var i=3;i<33;i++){
		temp0 = Math.floor(i/3);
		temp1 = i%3;
		temp = temp0 + "" + temp1;
	    audio10 = document.getElementById("myMusic"+temp);
	    if(audio10.src!=""){
	    	audio10.pause();
	    }
		c = $("#MainControl"+temp).attr('class');
		if(c=="StopControl"){
			$("#MainControl"+temp).removeClass("StopControl").addClass("MainControl");
		}
	}
}

//播放进度条的转变事件
function DurationProcessRange(rangeVal,idProcessYet) {
    var audio10 = document.getElementById("myMusic"+idProcessYet);
    audio10.currentTime = rangeVal * audio10.duration;
    //audio10.play();
}

//时间进度处理程序
function TimeSpan(index) {
    var audio10 = document.getElementById("myMusic"+index);
    var ProcessYet = 0;
    timeTask = setInterval(function () {
        var ProcessYet = (audio10.currentTime / audio10.duration) * 180;
        $("#ProcessYet"+index).css("width", ProcessYet);
        var totalT = audio10.duration;
        var currentT = audio10.currentTime;
        if(!isNaN(totalT)&&!isNaN(currentT)){
        	var currentTime = timeDispose(currentT);
        	var timeAll = timeDispose(totalT);
        	$("#SongTime"+index).html(currentTime + "&nbsp;|&nbsp;" + timeAll);
        }
    }, 1000);
}

//时间处理，因为时间是以为单位算的，所以这边执行格式处理一下
function timeDispose(number) {
    var minute = parseInt(number / 60);
    var second = parseInt(number % 60);
    minute = minute >= 10 ? minute : "0" + minute;
    second = second >= 10 ? second : "0" + second;
    return minute + ":" + second;
}

//当前歌曲的总时间
function TimeAll(index) {
    var audio10 = document.getElementById("myMusic"+index);
    return audio10.duration;
}