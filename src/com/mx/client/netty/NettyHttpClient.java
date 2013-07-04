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

public class NettyHttpClient {
	private ClientBootstrap mBootstrap;
	private String mScheme = "https";
	private String mHost = "www.han2011.com";
	private int mPort = 443;

	public NettyHttpClient() {
		// Configure the client.
		mBootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));
		// Set up the event pipeline factory.
		boolean ssl = "https".equalsIgnoreCase(mScheme);
		mBootstrap.setPipelineFactory(new NettyPipelineFactory(ssl));

	}

	public void connect(String url) throws URISyntaxException {
		URI uri = new URI(url);
		// String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
		// String host = uri.getHost() == null ? "localhost" : uri.getHost();
		// int port = uri.getPort();
		// if (port == -1) {
		// if ("http".equalsIgnoreCase(scheme)) {
		// port = 80;
		// } else if ("https".equalsIgnoreCase(scheme)) {
		// port = 443;
		// }
		// }

		// if (!"http".equalsIgnoreCase(scheme) &&
		// !"https".equalsIgnoreCase(scheme)) {
		// System.err.println("Only HTTP(S) is supported.");
		// return;
		// }

		// Start the connection attempt.
		ChannelFuture future = mBootstrap.connect(new InetSocketAddress(mHost, mPort));

		// Wait until the connection attempt succeeds or fails.
		Channel channel = future.awaitUninterruptibly().getChannel();
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
			mBootstrap.releaseExternalResources();
			return;
		}

		// Prepare the HTTP request.
		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
		request.setHeader(HttpHeaders.Names.HOST, mHost);
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
		mBootstrap.shutdown();
		// bootstrap.releaseExternalResources();

	}
}
