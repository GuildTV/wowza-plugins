package guildtv;

import com.wowza.util.HTTPUtils;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.IClient;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerSession;
import com.wowza.wms.mediacaster.IMediaCaster;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.rtp.model.RTPSession;
import com.wowza.wms.stream.IMediaStreamNameAliasProvider2;
import com.wowza.wms.stream.livepacketizer.ILiveStreamPacketizer;

public class RTMPStatsandSecurity extends ModuleBase implements IMediaStreamNameAliasProvider2 {

	private String destination;
	private String script;
	private String logScript;

	public void onAppStart(IApplicationInstance appInstance) {

		script = appInstance.getProperties().getPropertyStr("script");
		logScript = appInstance.getProperties().getPropertyStr("logScript");
		destination = appInstance.getProperties().getPropertyStr("streamName");

		appInstance.setStreamNameAliasProvider(this);

	}

	public String resolvePlayAlias(IApplicationInstance appInstance, String shibId, IClient client) {
		getLogger().info("My Log connect");

		// get the key from the flash player

		if (shibId == null) {
			getLogger().info("No shibId for client " + client.getIp());
			String error = "ip=" + client.getIp();
			error += "&wowzaId=" + client.getClientId();
			error += "&error=noShib";
			log(error);
			return null;
		}

		String query = "shibId=" + shibId;
		query += "&ip=" + client.getIp();
		query += "&wowzaId=" + client.getClientId();
		query += "&action=connect";

		try {
			// get key validity from the web server
			byte[] resp = HTTPUtils.HTTPRequestToByteArray(script, "POST", query, null);
			String response = new String(resp, "UTF-8");

			// check if key was valid
			if (response.indexOf("true") >= 0) {
				// accept client
				return destination;
			} else {
				// reject client
				getLogger().info("Rejected Client Key From " + client.getIp());
				String error = "shibId=" + shibId;
				error += "&ip=" + client.getIp();
				error += "&wowzaId=" + client.getClientId();
				error += "&error=invalidKey";
				log(error);
				return null;
			}

		} catch (Exception e) {
			getLogger().info("Failed to log client " + client.getIp());
		}

		return null;
	}

	private void log(String query) {
		try {
			// get key validity from the web server
			HTTPUtils.HTTPRequestToByteArray(logScript, "POST", query, null);

		} catch (Exception e) {
			getLogger().info("Failed to write to log");
		}
	}

	public void onDisconnect(IClient client) {
		getLogger().info("My Log disconnect");

		String query = "ip=" + client.getIp();
		query += "&wowzaId=" + client.getClientId();
		query += "&action=disconnect";

		try {
			// get key validity from the web server
			HTTPUtils.HTTPRequestToByteArray(script, "POST", query, null);
			getLogger().info("Logged Disconnect");

		} catch (Exception e) {
			getLogger().info("Failed to log client " + client.getIp() + " (" + client.getClientId() + ")");
		}
	}

	public String resolveStreamAlias(IApplicationInstance appInstance, String name) {
		getLogger().info("Resolve Stream: " + name);
		return name;
	}

	public String resolveStreamAlias(IApplicationInstance appInstance, String name, IMediaCaster mediaCaster) {
		return resolveStreamAlias(appInstance, name);
	}

	public String resolvePlayAlias(IApplicationInstance appInstance, String name, IHTTPStreamerSession httpSession) {
		getLogger().warn("Blocked Play HTTPSession");
		return null;
	}

	public String resolvePlayAlias(IApplicationInstance appInstance, String name, RTPSession rtpSession) {
		getLogger().warn("Blocked Play RTPSession");
		return null;
	}

	public String resolvePlayAlias(IApplicationInstance appInstance, String name,
			ILiveStreamPacketizer liveStreamPacketizer) {
		getLogger().warn("Blocked Play LiveStreamPacketizer");
		return null;
	}

	public String resolvePlayAlias(IApplicationInstance appInstance, String name) {
		getLogger().warn("Blocked Play");
		return null;
	}
}
