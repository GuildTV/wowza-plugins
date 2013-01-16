<?php

mysql_connect("localhost","wowza_stats","pHHtaen8mLyhHzdt") or die(mysql_error());
mysql_select_db("wowza_stats") or die(mysql_error());

$userIds = array();

$q = mysql_query("SELECT * FROM `stats` WHERE `disconnectTime`>=".time());
//$num = mysql_num_rows($q);
//if($num < 1)
//$num = 0;

$num = 0;

	while($data = mysql_fetch_array($q)) {
		if(!isset($userIds[$data['shibId']])){
			$userIds[$data['shibId']] = 1;
			$num++;
		}
	}

?>
[<?php print time()*1000; ?>, <?php print $num; ?>]

