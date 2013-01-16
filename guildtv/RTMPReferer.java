package guildtv;

import com.wowza.wms.amf.*;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.*;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerSession;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;
import com.wowza.wms.rtp.model.RTPSession;

public class RTMPReferer extends ModuleBase {

	private String address = "";
	private String pageLoc = "";

	// load server address from config
	public void onAppStart(IApplicationInstance appInstance) {
		address = appInstance.getProperties().getPropertyStr("referer");
		pageLoc = appInstance.getProperties().getPropertyStr("pageURL");
	}

	// process any rtmp clients
	public void play(IClient client, RequestFunction function, AMFDataList params) {
		getLogger().info("Overriding Play");

		if (client.getReferrer().equals(address) && client.getUri().equals(pageLoc)) {
			// accept client
			ModuleCore.play(client, function, params);
			return;
		} else {
			// reject client
			sendClientOnStatusError(client, "NetStream.Play.Failed",
					"You are not accessing the stream from a valid source");
			getLogger()
					.info("Rejected Client Key From " + client.getIp() + " (Referrer: " + client.getReferrer() + ")");
			client.rejectConnection();
			return;
		}
	}

	// drop any http clients
	public void onHTTPSessionCreate(IHTTPStreamerSession httpSession) {
		httpSession.rejectSession();
		getLogger().info("HTTP Client Rejected");
	}

	// drop any rtp clients
	public void onRTPSessionCreate(RTPSession rtpSession) {
		rtpSession.rejectSession();
		getLogger().info("RTSP Client Rejected");
	}
}