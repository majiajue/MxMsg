package com.mx.client.netty;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;

import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.SConfig;
import com.mx.client.webtools.XmlUtil;

public class NettyClient implements Runnable {
	// 用来查看是否接受到重复信息
	private URI uri;

	public NettyClient(String url) throws URISyntaxException {
		this.uri = new URI(url);
	}

	/**
	 * 返回和服务器的时间差，用来修正时间值
	 * 
	 * @return
	 */
	public long getTimeOffset2Server() {
		long timeoffset = 0;
		try {
			long time1 = System.currentTimeMillis();
			String result = XmlUtil.instance().parseXmltoString(
					ConnectionUtils.getInstance().getRequest(
							"/gettime/" + SConfig.getInstance().getSessionKey() + "/call.xml"), "UTF-8", "time");
			System.out.println("同步时间==" + result);
			if (result != null) {
				long servertime = Long.valueOf(result);
				long time2 = System.currentTimeMillis();
				long currenttime = time2 - (time2 - time1) / 2;// 消除网络延时的误差。
				timeoffset = currenttime - servertime;
				// LOG.v("timeOffset", timeoffset + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// LOG.e("pangolin", "cannot get conrrect time");
		}
		return timeoffset;
	}

	public long updateTime() {
		int requestTime = 0;
		long newTimeOffset = getTimeOffset2Server();
		do {
			if (newTimeOffset != 0) {
				NettyStatus.TiemOffset = newTimeOffset;
				break;
			} else {
				newTimeOffset = getTimeOffset2Server();
			}
			if (++requestTime > 3) {
				break;
			}
		} while (true);
		return newTimeOffset;
	}

	private synchronized void comet() {
		System.out.println("进来了-----");
		try {
			this.uri = new URI("https://www.han2011.com/" + "getmessage/"
					+ SConfig.getInstance().getProfile().getSession() + "/call.xml");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
		String host = uri.getHost() == null ? "localhost" : uri.getHost();
		int port = uri.getPort();
		if (port == -1) {
			if ("http".equalsIgnoreCase(scheme)) {
				port = 80;
			} else if ("https".equalsIgnoreCase(scheme)) {
				port = 443;
			}
		}

		if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
			System.err.println("Only HTTP(S) is supported.");
			return;
		}

		boolean ssl = "https".equalsIgnoreCase(scheme);

		// Configure the client.
		ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		// Set up the event pipeline factory.
		bootstrap.setPipelineFactory(new NettyPipelineFactory(ssl));

		// Start the connection attempt.
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

		// Wait until the connection attempt succeeds or fails.
		Channel channel = future.awaitUninterruptibly().getChannel();
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
			bootstrap.releaseExternalResources();
			return;
		}

		// Prepare the HTTP request.
		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
		request.setHeader(HttpHeaders.Names.HOST, host);
		request.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
		request.setHeader(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);

		// Set some example cookies.
		// CookieEncoder httpCookieEncoder = new CookieEncoder(false);
		// httpCookieEncoder.addCookie("my-cookie", "foo");
		// httpCookieEncoder.addCookie("another-cookie", "bar");
		// request.setHeader(HttpHeaders.Names.COOKIE,
		// httpCookieEncoder.encode());

		// Send the HTTP request.
		channel.write(request);
		NettyStatus.isRettyRunning = true;
		// Log.v("netty", "start block waiting");
		// Wait for the server to close the connection.
		channel.getCloseFuture().awaitUninterruptibly();
		// Log.v("netty", "start block waiting end");
		// Shut down executor threads to exit.

		bootstrap.releaseExternalResources();
	}

	@Override
	public void run() {
		updateTime();

		AncodeFactory.getInstance().setOnLine();

		while (true) {
			System.out.println("线路线程" + String.valueOf(updateTime() == 0));
			if (AncodeFactory.isLogin == false) {
				// Log.v("mixun", "isLogin false");
				try {
					setRunning(false);
					AncodeFactory.getInstance().setOffLine();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			if (updateTime() == 0) {
				try {
					if (AncodeFactory.getInstance().isNetworkAvailiable() == false) {
						NettyStatus.retry++;
						if (NettyStatus.retry < 6) { // 如果网络不在，每隔20秒尝试
							Thread.sleep(NettyStatus.s20);
						}
					} else {
						if (NettyStatus.retry < 6) { // 如果网络在，每隔10秒尝试
							Thread.sleep(NettyStatus.s10);
						}
					}
					if (NettyStatus.retry > 6) {
						AncodeFactory.getInstance().setOffLine();
						NettyStatus.retry++;
						Thread.sleep(NettyStatus.s40);
					}
				} catch (InterruptedException e) {
				}
			} else {
				System.out.println("线程进行中-------");
				AncodeFactory.getInstance().setOnLine();
				NettyStatus.retry = 0;
				comet(); // 如果成功进入一个长连，程序会阻塞在comet里面

				try {
					AncodeFactory.getInstance().setOffLine();
					Thread.sleep(NettyStatus.s20);
				} catch (InterruptedException e) {
				}
			}
		}// endwhile
	}

	public synchronized void setRunning(boolean running) {
		NettyStatus.mRunning = running;
	}
}
