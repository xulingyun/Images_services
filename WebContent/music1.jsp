<%@ page language="java" import="java.util.*,java.net.URL,java.sql.*"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />
<title>胎教音乐</title>
<script src="./js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="./js/Ben.Audio.1.0.0.js" type="text/javascript"></script>
<script src="./js/log.js" type="text/javascript"></script>
<link type="text/css" href="./js/Ben.Audio.1.0.1.css" rel="Stylesheet" />
<script src="./jquery.mobile-1.4.4/jquery.mobile-1.4.4.min.js"></script>
<link rel="stylesheet"
	href="./jquery.mobile-1.4.4/jquery.mobile-1.4.4.1.css" />
<style type="text/css">
img {
	width: 100%;
}
</style>
</head>
<body>
	<img src="./images/music.png" />
	<div data-role="content">
		<div data-role="collapsible-set">
			<div data-role="collapsible" data-collapsed="false" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>1</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic10"></audio>
					<div id="MainControl10" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName10" class="SongName" style="color: #000">学划船</div>
						<div id="SongTime10" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process10" class="Process"></div>
						<div id="ProcessYet10" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic11"></audio>
					<div id="MainControl11" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName11" class="SongName" style="color: #000">雨滴</div>
						<div id="SongTime11" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process11" class="Process"></div>
						<div id="ProcessYet11" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic12"></audio>
					<div id="MainControl12" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName12" class="SongName" style="color: #000">做游戏</div>
						<div id="SongTime12" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process12" class="Process"></div>
						<div id="ProcessYet12" class="ProcessYet"></div>
					</div>
				</div>
			</div>
			
			<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>2</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic20"></audio>
					<div id="MainControl20" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName20" class="SongName" style="color: #000">Huldra-Mi</div>
						<div id="SongTime20" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process20" class="Process"></div>
						<div id="ProcessYet20" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic21"></audio>
					<div id="MainControl21" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName21" class="SongName" style="color: #000">Olav Bergsland</div>
						<div id="SongTime21" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process21" class="Process"></div>
						<div id="ProcessYet21" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic22"></audio>
					<div id="MainControl22" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName22" class="SongName" style="color: #000">Trebakken</div>
						<div id="SongTime22" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process22" class="Process"></div>
						<div id="ProcessYet22" class="ProcessYet"></div>
					</div>
				</div>
			</div>
			
			
			<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>3</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic30"></audio>
					<div id="MainControl30" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName30" class="SongName" style="color: #000">花的圆舞曲</div>
						<div id="SongTime30" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process30" class="Process"></div>
						<div id="ProcessYet30" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic31"></audio>
					<div id="MainControl31" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName31" class="SongName" style="color: #000">美丽的彩虹</div>
						<div id="SongTime31" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process31" class="Process"></div>
						<div id="ProcessYet31" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic32"></audio>
					<div id="MainControl32" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName32" class="SongName" style="color: #000">梦幻曲</div>
						<div id="SongTime32" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process32" class="Process"></div>
						<div id="ProcessYet32" class="ProcessYet"></div>
					</div>
				</div>
			</div>
			
			
			<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>4</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic40"></audio>
					<div id="MainControl40" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName40" class="SongName" style="color: #000">怪话</div>
						<div id="SongTime40" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process40" class="Process"></div>
						<div id="ProcessYet40" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic41"></audio>
					<div id="MainControl41" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName41" class="SongName" style="color: #000">陌生的国家和人民</div>
						<div id="SongTime41" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process41" class="Process"></div>
						<div id="ProcessYet41" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic42"></audio>
					<div id="MainControl42" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName42" class="SongName" style="color: #000">捉迷藏</div>
						<div id="SongTime42" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process42" class="Process"></div>
						<div id="ProcessYet42" class="ProcessYet"></div>
					</div>
				</div>
			</div>
			
			
			<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>5</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic50"></audio>
					<div id="MainControl50" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName50" class="SongName" style="color: #000">会跳舞的洋娃娃</div>
						<div id="SongTime50" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process50" class="Process"></div>
						<div id="ProcessYet50" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic51"></audio>
					<div id="MainControl51" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName51" class="SongName" style="color: #000">快乐的小画家</div>
						<div id="SongTime51" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process51" class="Process"></div>
						<div id="ProcessYet51" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic52"></audio>
					<div id="MainControl52" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName52" class="SongName" style="color: #000">小小卡车司机</div>
						<div id="SongTime52" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process52" class="Process"></div>
						<div id="ProcessYet52" class="ProcessYet"></div>
					</div>
				</div>
			</div>
			
			
			<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>6</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic60"></audio>
					<div id="MainControl60" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName60" class="SongName" style="color: #000">Codename Dragonfly</div>
						<div id="SongTime60" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process60" class="Process"></div>
						<div id="ProcessYet60" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic61"></audio>
					<div id="MainControl61" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName61" class="SongName" style="color: #000">Seek You</div>
						<div id="SongTime61" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process61" class="Process"></div>
						<div id="ProcessYet61" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic62"></audio>
					<div id="MainControl62" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName62" class="SongName" style="color: #000">Take Me Higher</div>
						<div id="SongTime62" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process62" class="Process"></div>
						<div id="ProcessYet62" class="ProcessYet"></div>
					</div>
				</div>
			</div>
			
			
			<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>7</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic70"></audio>
					<div id="MainControl70" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName70" class="SongName" style="color: #000">03</div>
						<div id="SongTime70" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process70" class="Process"></div>
						<div id="ProcessYet70" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic71"></audio>
					<div id="MainControl71" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName71" class="SongName" style="color: #000">Dinner for two</div>
						<div id="SongTime71" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process71" class="Process"></div>
						<div id="ProcessYet71" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic72"></audio>
					<div id="MainControl72" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName72" class="SongName" style="color: #000">曲目</div>
						<div id="SongTime72" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process72" class="Process"></div>
						<div id="ProcessYet72" class="ProcessYet"></div>
					</div>
				</div>
			</div>
			
			
			<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>8</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic80"></audio>
					<div id="MainControl80" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName80" class="SongName" style="color: #000">糖果仙子之舞</div>
						<div id="SongTime80" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process80" class="Process"></div>
						<div id="ProcessYet80" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic81"></audio>
					<div id="MainControl81" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName81" class="SongName" style="color: #000">王子与糖果仙子的舞蹈</div>
						<div id="SongTime81" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process81" class="Process"></div>
						<div id="ProcessYet81" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic82"></audio>
					<div id="MainControl82" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName82" class="SongName" style="color: #000">雪精灵的华尔兹</div>
						<div id="SongTime82" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process82" class="Process"></div>
						<div id="ProcessYet82" class="ProcessYet"></div>
					</div>
				</div>
			</div>
			
			
			<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>9</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic90"></audio>
					<div id="MainControl90" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName90" class="SongName" style="color: #000">Ave Maria</div>
						<div id="SongTime90" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process90" class="Process"></div>
						<div id="ProcessYet90" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic91"></audio>
					<div id="MainControl91" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName91" class="SongName" style="color: #000">Moditwa Dziewicy</div>
						<div id="SongTime91" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process91" class="Process"></div>
						<div id="ProcessYet91" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic92"></audio>
					<div id="MainControl92" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName92" class="SongName" style="color: #000">Songs Of Spring</div>
						<div id="SongTime92" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process92" class="Process"></div>
						<div id="ProcessYet92" class="ProcessYet"></div>
					</div>
				</div>
			</div>
			
			
			<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-iconpos="right" data-collapsed-icon="music-down" data-expanded-icon="music-up">
				<h3 align="center">第&nbsp;<i>10</i>&nbsp;个月</h3>
				<div class="MusicBox">
					<audio id="myMusic100"></audio>
					<div id="MainControl100" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName100" class="SongName" style="color: #000">Dawn</div>
						<div id="SongTime100" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process100" class="Process"></div>
						<div id="ProcessYet100" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic101"></audio>
					<div id="MainControl101" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName101" class="SongName" style="color: #000">Eine Kiene Nachtmusik</div>
						<div id="SongTime101" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process101" class="Process"></div>
						<div id="ProcessYet101" class="ProcessYet"></div>
					</div>
				</div>
				<div class="MusicBox">
					<audio id="myMusic102"></audio>
					<div id="MainControl102" class="MainControl"></div>
					<div class="ProcessControl">
						<div id="SongName102" class="SongName" style="color: #000">Menuet</div>
						<div id="SongTime102" class="SongTime" style="color: #000">00:00&nbsp;|&nbsp;00:00</div>
						<div id="Process102" class="Process"></div>
						<div id="ProcessYet102" class="ProcessYet"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
