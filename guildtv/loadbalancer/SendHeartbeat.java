package guildtv.loadbalancer;

import com.wowza.util.HTTPUtils;
import com.wowza.wms.logging.WMSLoggerFactory;

public class SendHeartbeat implements Runnable {
	private double rate;
	private long connections;
	private String script;

	public SendHeartbeat(String script, double rate, long conn) {
		this.rate = rate;
		this.connections = conn;
		this.script = script;
	}

	@Override
	public void run() {
		WMSLoggerFactory.getLogger(null).info("heartbeat");
		String query = "rate=" + rate;
		query += "&count=" + connections;
		query += "&action=beat";

		try {
			// get key validity from the web server
			HTTPUtils.HTTPRequestToByteArray(script, "POST", query, null);

		} catch (Exception e) {
			WMSLoggerFactory.getLogger(null).warn("Failed to send heartbeat");
		}
	}
}
