<?php

mysql_connect("localhost","wowza","pHHtaen8mLyhHzdt") or die(mysql_error());
mysql_select_db("wowza") or die(mysql_error());

$shibId = addslashes(@$_POST['shibId']);
$ip = addslashes(@$_POST['ip']);
$wowzaId =addslashes(@$_POST['wowzaId']);
$error = addslashes(@$_POST['error']);

mysql_query("INSERT INTO `log` (`wowzaId`, `shibId`, `ip`, `server`, `referer`, `location`, `error`) VALUES ('".$wowzaId."','".$shibId."','".$ip."','".$_SERVER['REMOTE_ADDR']."','".$error."')");

