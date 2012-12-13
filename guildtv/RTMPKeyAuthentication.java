package guildtv;

import com.wowza.util.HTTPUtils;
import com.wowza.wms.amf.*;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.*;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerSession;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;
import com.wowza.wms.rtp.model.RTPSession;

public class RTMPKeyAuthentication extends ModuleBase {

	private String address = "";

	//load server address from config
	public void onAppStart(IApplicationInstance appInstance) {
		address = appInstance.getProperties().getPropertyStr("authScript");
	}

	//process any rtmp clients
	public void play(IClient client, RequestFunction function, AMFDataList params) {
		getLogger().info("Overriding Play");

		//get the key from the flash player
		String key = getParamString(params, PARAM1);
		key = key.substring(key.indexOf("?") + 1);

		try {
			//get key validity from the web server
			byte[] resp = HTTPUtils.HTTPRequestToByteArray(address, "POST", "key=" + key + "&ip=" + client.getIp(),
					null);
			String response = new String(resp, "UTF-8");
			
			//check if key was valid
			if (response.indexOf("true") >= 0) {
				//accept client
				ModuleCore.play(client, function, params);
				return;
			} else {
				//reject client
				sendClientOnStatusError(client, "NetStream.Play.Failed", "Your key was rejected");
				getLogger().info("Rejected Client Key From " + client.getIp());
				return;
			}
		} catch (Exception e) {
			sendClientOnStatusError(client, "NetStream.Play.Failed", "The Server had an error");
			getLogger().info("Errored with client " + client.getIp());
			return;
		}
	}

	//drop any http clients
	public void onHTTPSessionCreate(IHTTPStreamerSession httpSession) {
		httpSession.rejectSession();
		getLogger().info("HTTP Client Rejected");
	}

	//drop any rtp clients
	public void onRTPSessionCreate(RTPSession rtpSession) {
		rtpSession.rejectSession();
		getLogger().info("RTSP Client Rejected");
	}
}