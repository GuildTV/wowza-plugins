<?php

mysql_connect("localhost","wowza","pHHtaen8mLyhHzdt") or die(mysql_error());
mysql_select_db("wowza") or die(mysql_error());

$shibId = addslashes(@$_POST['shibId']);
$ip = addslashes(@$_POST['ip']);
$wowzaId =addslashes(@$_POST['wowzaId']);
$uri = addslashes(@$_POST['uri']);
$referer =addslashes(@$_POST['referer']);
$location = addslashes(@$_POST['location']);
$error = addslashes(@$_POST['error']);

$i = preg_match("#rtmp://(.*).guildtv.co.uk/live/(.*)#", $uri, $matches);
if($i != 0)
	$uri = $matches[1]."/".$matches[2];

	
mysql_query("INSERT INTO `log` (`wowzaId`, `shibId`, `ip`, `server`, `referer`, `location`, `error`) VALUES ('".$wowzaId."','".$shibId."','".$ip."','".$uri."','".$referer."','".$location."','".$error."')");

