RTMPReferer
===========================
This is a simple plugin for wowza, which restricts access by checking the referring webpage of the stream.

To use the plugin, it must be built using the Wowza IDE, and installed, then Wowza needs to be configured to use it.
Instead of compiling, the jar file can just be placed into the [install dir]/lib/ folder.

Once the server is setup for streaming, and it has been verified to stream successfully, we can configure it for this plugin.

In the Application.xml config file for our stream, we start by adding this module to the end of the Modules list.
<pre>
    &lt;Module&gt;
        &lt;Name&gt;RTMPReferer&lt;/Name&gt;
        &lt;Description&gt;RTMP Authentication with referer&lt;/Description&gt;
        &lt;Class&gt;guildtv.RTMPReferer&lt;/Class&gt;
    &lt;/Module&gt;
</pre>

Next we add this Property to the Properties list, but change the values to correspond to the player and refering page.
<pre>
    &lt;Property&gt;
        &lt;Name&gt;referer&lt;/Name&gt;
        &lt;Value&gt;https://live.guildtv.co.uk/jwplayer/player.swf&lt;/Value&gt;
    &lt;/Property&gt;
    &lt;Property&gt;
        &lt;Name&gt;pageURL&lt;/Name&gt;
        &lt;Value&gt;https://live.guildtv.co.uk/watch.php&lt;/Value&gt;
    &lt;/Property&gt;
</pre>
