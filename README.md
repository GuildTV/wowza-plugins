wowza-plugins
===========================
This is a plugin to provide security for a rtmp stream, aswell as to provide accurate logs of viewers.

To use the plugin, it must be built using the Wowza IDE, and installed, then Wowza needs to be configured to use it.
Instead of compiling, the jar file can just be placed into the [install dir]/lib/ folder.

Once the server is setup for streaming, and it has been verified to stream successfully, we can configure it for this plugin.

In the Application.xml config file for our stream, we start by adding this module to the end of the Modules list.
<pre>
    &lt;Module&gt;
        &lt;Name&gt;RTMPStatsandSecurity&lt;/Name&gt;
        &lt;Description&gt;RTMP Statistics and Security&lt;/Description&gt;
        &lt;Class&gt;guildtv.RTMPStatsandSecurity&lt;/Class&gt;
    &lt;/Module&gt;
</pre>

Next we add these Properties to the Properties list, but change the values to suit your setup.
<pre>
    &lt;Property&gt;
        &lt;Name&gt;script&lt;/Name&gt;
        &lt;Value&gt;http://live.guildtv.co.uk/stats/stat.php&lt;/Value&gt;
    &lt;/Property&gt;
    &lt;Property&gt;
        &lt;Name&gt;logScript&lt;/Name&gt;
        &lt;Value&gt;http://live.guildtv.co.uk/stats/log.php&lt;/Value&gt;
    &lt;/Property&gt;
    &lt;Property&gt;
        &lt;Name&gt;streamName&lt;/Name&gt;
        &lt;Value&gt;myStream&lt;/Value&gt;
    &lt;/Property&gt;
    &lt;Property&gt;
        &lt;Name&gt;referer&lt;/Name&gt;
        &lt;Value&gt;https://live.guildtv.co.uk&lt;/Value&gt;
    &lt;/Property&gt;
    &lt;Property&gt;
        &lt;Name&gt;pageURL&lt;/Name&gt;
        &lt;Value&gt;https://live.guildtv.co.uk/watch.php&lt;/Value&gt;
    &lt;/Property&gt;
</pre>

Using the example file, create some pages to store and display the data being returned by wowza.

Using the example stats.php file, create a script that will return true or false depending on the validity of the key and associated ip address.

Use the watch.php as a sample script to generate and store keys.

You viewers must be sent to rtmp://live.guildtv.co.uk:1935/live/&lt;unique key&gt;. They will then be sent to the stream defined in the config file.

Finally we need to create a table in a database to store the keys.
<pre>
    CREATE TABLE `keys` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `key` varchar(50) NOT NULL,
      `ip` varchar(15) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `key` (`key`)
    );
</pre>

You may want to think about controlling access to the statScript and the stats viewing, to protect the integrity of the data and the privacy of your users.

