<?php
//connect to the mysql database
mysql_connect("sql_location","sql_username","sql_password");
mysql_select_db("sql_db");

//generate a key
$key = sha1($_SERVER['REMOTE_ADDR'].time());
//insert the key into the database
mysql_query("INSERT INTO `keys` (`key`,`ip`) VALUES ('".$key."','".$_SERVER['REMOTE_ADDR']."')") or die(mysql_error());

?>
<html>
<head>
	<title>RTMPKeyAuthentication Test</title>
	<script type="text/javascript" src="/jwplayer.js" ></script>
</head>
<body>
	<p>Your key is <?php print $key; ?></p>
	<div id="myElement">Loading the player...</div>
	<script type="text/javascript">
	    jwplayer("myElement").setup({
	        file: "rtmp://127.0.0.1:1935/live/<?php print $key; ?>",
	    });
	</script>
</body>
</html>
