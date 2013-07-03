package com.mx.client.netty;

import static org.jboss.netty.channel.Channels.pipeline;

import javax.net.ssl.SSLEngine;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.http.HttpClientCodec;
import org.jboss.netty.handler.codec.http.HttpContentDecompressor;
import org.jboss.netty.handler.ssl.SslHandler;

public class NettyPipelineFactory implements ChannelPipelineFactory {

	private final boolean ssl;

	public NettyPipelineFactory(boolean ssl) {
		this.ssl = ssl;
	}

	public ChannelPipeline getPipeline() throws Exception {
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = pipeline();

		// Enable HTTPS if necessary.
		if (ssl) {
			SSLEngine engine = NettySslContextFactory.getClientContext().createSSLEngine();
			engine.setUseClientMode(true);

			pipeline.addLast("ssl", new SslHandler(engine));
		}

		pipeline.addLast("codec", new HttpClientCodec());

		// Remove the following line if you don't want automatic content
		// decompression.
		pipeline.addLast("inflater", new HttpContentDecompressor());

		// Uncomment the following line if you don't want to handle HttpChunks.
		// pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));

		pipeline.addLast("handler", new NettyClientHandler());
		return pipeline;
	}
}