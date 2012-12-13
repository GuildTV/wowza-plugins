wowza-RTMPKeyAuthentication
===========================
This is a simple plugin for wowza, to provide a simple yet effective method of authentication for RTMP streams.
It works by the viewer passes a key to wowza, which makes a web request to verify its validity. It works best when the keys are one time use only.

To use the plugin, it must be built using the Wowza IDE, and installed, then Wowza needs to be configured to use it.

Once the server is setup for streaming, and it has been verified to stream successfully, we can configure it for this plugin.

In the Application.xml config file for our stream, we start by adding this module to the end of the Modules list.
    <Module>
        <Name>RTMPKeyAuthentication</Name>
        <Description>RTMP Authentication with keys</Description>
        <Class>guildtv.RTMPKeyAuthentication</Class>
    </Module>

Next we add this Property to the Properties list, but change the value to be the location of the script which will validate the keys.
    <Property>
        <Name>authScript</Name>
        <Value>http://live.guildtv.co.uk/auth.php</Value>
    </Property>

Using the sample auth.php file, create a script that will return true or false depending on the validity of the key and associated ip address.

Use the watch.php as a sample script to generate and store keys.

Finally we need to create a table in a database to store the keys.
    CREATE TABLE `keys` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `key` varchar(50) NOT NULL,
      `ip` varchar(15) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `key` (`key`)
    );
