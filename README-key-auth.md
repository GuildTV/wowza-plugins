RTMPKeyAuthentication
===========================
This is a simple plugin for wowza, to provide a simple yet effective method of authentication for RTMP streams.
It works by the viewer passes a key to wowza, which makes a web request to verify its validity. It works best when the keys are one time use only.

To use the plugin, it must be built using the Wowza IDE, and installed, then Wowza needs to be configured to use it.
Instead of compiling, the jar file can just be placed into the [install dir]/lib/ folder.

Once the server is setup for streaming, and it has been verified to stream successfully, we can configure it for this plugin.

In the Application.xml config file for our stream, we start by adding this module to the end of the Modules list.
<pre>
    &lt;Module&gt;
        &lt;Name&gt;RTMPKeyAuthentication&lt;/Name&gt;
        &lt;Description&gt;RTMP Authentication with keys&lt;/Description&gt;
        &lt;Class&gt;guildtv.RTMPKeyAuthentication&lt;/Class&gt;
    &lt;/Module&gt;
</pre>

Next we add this Property to the Properties list, but change the value to be the location of the script which will validate the keys.
<pre>
    &lt;Property&gt;
        &lt;Name&gt;authScript&lt;/Name&gt;
        &lt;Value&gt;http://live.guildtv.co.uk/auth.php&lt;/Value&gt;
    &lt;/Property&gt;
</pre>

Using the example auth.php file, create a script that will return true or false depending on the validity of the key and associated ip address.

Use the watch.php as a sample script to generate and store keys.

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
