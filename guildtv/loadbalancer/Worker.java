package guildtv.loadbalancer;

import com.wowza.util.HTTPUtils;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.server.IServer;

public class Worker extends Thread {
	private IServer server;
	private String beatScript;
	private String beatSecret;

	public Worker(IServer server) {
		this.server = server;
		beatScript = server.getProperties().getPropertyStr("beatScript");
		beatSecret = server.getProperties().getPropertyStr("beatSecret");

		// send start to website
		String query = "action=start";
		query += "&secret=" + beatSecret;

		try {
			// get key validity from the web server
			byte[] resp = HTTPUtils.HTTPRequestToByteArray(beatScript, "POST", query, null);
			String response = new String(resp, "UTF-8");
			
			WMSLoggerFactory.getLogger(null).warn(response);

			// check if key was valid
			if (response.indexOf("false") >= 0) {
				//stop server
				server.stopVHosts();
				WMSLoggerFactory.getLogger(null).warn("I was sent away :'(");
			}

		} catch (Exception e) {
			WMSLoggerFactory.getLogger(null).warn("Failed to send startup message");
		}
	}

	public void run() {
		while (true) {
			try {
				// Server s = ((Server) server).getInstance();
				double rate = server.getIoPerformanceCounter().getMessagesOutBytesRate();
				long conn = server.getConnectionCounter().getCurrent();
				rate = rate / (1024 * 1024 / 10);// mbit

				// send heartbeat
				new Thread(new SendHeartbeat(beatScript, rate, conn)).start();

				Thread.sleep(10000);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public void end() {
		this.interrupt();

		// send terminate to website
		String query = "action=stop";
		query += "&secret=" + beatSecret;

		try {
			// get key validity from the web server
			HTTPUtils.HTTPRequestToByteArray(beatScript, "POST", query, null);

		} catch (Exception e) {
			WMSLoggerFactory.getLogger(null).warn("Failed to send shutdown message");
		}
	}
}
