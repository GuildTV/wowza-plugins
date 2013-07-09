package guildtv.loadbalancer;

import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.server.*;

public class Balancer implements IServerNotify2 {

	private Worker beat;

	/*
	 * Called after the Server is initialized. At this point, the server is running, all VHosts have been initialized
	 * and the Server is ready to accept connections.
	 */
	public void onServerInit(IServer server) {
		beat = new Worker(server);
		beat.start();
		WMSLoggerFactory.getLogger(null).info("started my load balancer :)");
	}

	/*
	 * Called when the Server starts to shut down.
	 */
	public void onServerShutdownStart(IServer server) {
		beat.end();
		WMSLoggerFactory.getLogger(null).info("stopped my load balancer :(");
	}

	@Override
	public void onServerCreate(IServer server) {
	}

	@Override
	public void onServerShutdownComplete(IServer server) {
	}

	@Override
	public void onServerConfigLoaded(IServer server) {
	}
}