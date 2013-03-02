<?php

mysql_connect("localhost","wowza_stats","pHHtaen8mLyhHzdt") or die(mysql_error());
mysql_select_db("wowza_stats") or die(mysql_error());

$startTime = 1358200000;
$interval = 30;

$time = $startTime;
print "[";
while($time+$interval <= time()) {
	$userIds = array();

	$q = mysql_query("SELECT * FROM `stats` WHERE (`disconnectTime`=-1 OR `disconnectTime`>=".$time.") AND `connectTime`<=".($time+$interval));

	//$num = mysql_num_rows($q);
	//if($num<1) $num  = 0;
	$num = 0;

	while($data = mysql_fetch_array($q)) {
		if(!isset($userIds[$data['shibId']])){
			$userIds[$data['shibId']] = 1;
			$num++;
		}
	}

	if($time+2*$interval>=time())
		print "[".($time*1000).",".$num."]\n"; 
	else
		print "[".($time*1000).",".$num."],\n"; 

	
	$time+=$interval;
}

print "]";




?>
