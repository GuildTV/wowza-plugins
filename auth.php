<?php
//connect to the mysql database
mysql_connect("sql_location","sql_username","sql_password");
mysql_select_db("sql_db");

//delete entry of key against id
$q = mysql_query("DELETE FROM `keys` WHERE `key`='".addslashes(@$_POST['key'])."' AND `ip`='".addslashes(@$_POST['ip'])."'");

//report whether key was valid
if(mysql_affected_rows() > 0)
	print "true";
else
	print "false";

?>
