package guildtv;

import com.wowza.util.HTTPUtils;
import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.IClient;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.module.ModuleCore;
import com.wowza.wms.request.RequestFunction;

public class RTMPStats extends ModuleBase {

	private String address = "";

	// load server address from config
	public void onAppStart(IApplicationInstance appInstance) {
		address = appInstance.getProperties().getPropertyStr("statScript");
	}

	public void play(IClient client, RequestFunction function, AMFDataList params) {
		getLogger().info("Log connect");

		// get the key from the flash player
		String shibId = getParamString(params, PARAM1);
		if (shibId == null) {
			getLogger().info("No shibId for client " + client.getIp());
			return;
		}
		shibId = shibId.substring(shibId.indexOf("?") + 1);

		String query = "shibId=" + shibId;
		query += "&ip=" + client.getIp();
		query += "&wowzaId=" + client.getClientId();
		query += "&uri=" + client.getUri();
		query += "&action=connect";

		try {
			// get key validity from the web server
			HTTPUtils.HTTPRequestToByteArray(address, "POST", query, null);

		} catch (Exception e) {
			getLogger().info("Failed to log client " + client.getIp() + " (" + shibId + ")");
		}

		ModuleCore.play(client, function, params);
	}

	public void onDisconnect(IClient client) {
		getLogger().info("Log disconnect");

		String query = "ip=" + client.getIp();
		query += "&wowzaId=" + client.getClientId();
		query += "&uri=" + client.getUri();
		query += "&action=disconnect";

		try {
			// get key validity from the web server
			HTTPUtils.HTTPRequestToByteArray(address, "POST", query, null);

		} catch (Exception e) {
			getLogger().info("Failed to log client " + client.getIp() + " (" + client.getClientId() + ")");
		}
	}
}
