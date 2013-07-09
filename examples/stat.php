<?php

mysql_connect("localhost","wowza_stats","pHHtaen8mLyhHzdt") or die(mysql_error());
mysql_select_db("wowza_stats") or die(mysql_error());

$action = @$_POST['action'];
$shibId = addslashes(@$_POST['shibId']);
$ip = addslashes(@$_POST['ip']);
$wowzaId =addslashes(@$_POST['wowzaId']);

if($action == "connect"){
	if($shibId == "" || $shibId == "null" ||  $ip == "" || $ip == "null" || $wowzaId == "" || $wowzaId == "null")
		die();

	mysql_query("INSERT INTO `stats` (`wowzaId`, `shibId`, `ip`, `server`, `connectTime`) VALUES ('".$wowzaId."','".$shibId."','".$ip."','".$_SERVER['REMOTE_ADDR']."',".time().")");
	
} else if ($action == "disconnect") {
	if($ip == "" || $ip == "null" || $wowzaId == "" || $wowzaId == "null" || $uri == "" || $uri == "null")
		die();

	mysql_query("UPDATE `stats` SET `disconnectTime`=".time()." WHERE `wowzaId`='".$wowzaId."' AND `ip`='".$ip."' AND `server`='".$_SERVER['REMOTE_ADDR']."' AND `disconnectTime`=-1");
	
}
