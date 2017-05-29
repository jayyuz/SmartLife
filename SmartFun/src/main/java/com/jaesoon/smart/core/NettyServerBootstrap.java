package com.jaesoon.smart.core;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jaesoon.protocol.SmPackage;
import com.jaesoon.protocol.core.req.connection.PushToUserReq;
import com.jaesoon.protocol.core.req.login.DevLoginReq;
import com.jaesoon.protocol.core.req.login.UserLoginReq;
import com.jaesoon.protocol.devtype.Switch;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

/**
 * Created by yaozb on 15-4-11.
 */
public class NettyServerBootstrap {
    private int port;
    private SocketChannel socketChannel;

    private static final int PORT = 9999;

    private static Logger logger = Logger.getLogger(NettyServerBootstrap.class);
    /**
     * 用于分配处理业务线程的线程组个数
     */
    protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2; //默认
    /**
     * 业务出现线程大小
     */
    protected static final int BIZTHREADSIZE = 4;
    /*
     * NioEventLoopGroup实际上就是个线程池,
     * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件,
     * 每一个NioEventLoop负责处理m个Channel,
     * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
     */
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

    public NettyServerBootstrap(int port) throws InterruptedException {
        this.port = port;
        bind();
    }

    protected static void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    private void bind() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                ByteBuf delimiter = Unpooled.copiedBuffer(Constant.END_FIX.getBytes());
                pipeline.addLast("frameDecoder", new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, delimiter));
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new NettyServerHandler());
            }
        });
        ChannelFuture f = bootstrap.bind(port).sync();
        if (f.isSuccess()) {
            logger.debug("server start!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NettyServerBootstrap bootstrap = new NettyServerBootstrap(PORT);
        Gson gson = new Gson();
        SmPackage smPackage = new SmPackage();
        smPackage.sequence = 1;
        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.UserMobileNO = "13249089091";
        userLoginReq.UserPassword = "123456";
        smPackage.content = new JsonParser().parse(userLoginReq.toString()).getAsJsonObject();
//        logger.debug(smPackage.toString());
        MyLogger.log(smPackage.toString());
        smPackage.sequence = 1;
        DevLoginReq devLoginReq = new DevLoginReq();
        devLoginReq.DevId = "201610010000001";
        devLoginReq.sn = "201610010000001";
        smPackage.content =
                new JsonParser().parse(devLoginReq.toString()).getAsJsonObject();
//        logger.debug(smPackage.toString());
        MyLogger.log(smPackage.toString());

        Switch iswitch = new Switch();
        iswitch.setState(false);
        PushToUserReq pushToUserReq = new PushToUserReq();
        pushToUserReq.DevId = "201610010000001";
        pushToUserReq.Content = gson.toJson(iswitch);
        smPackage.content =
                new JsonParser().parse(pushToUserReq.toString()).getAsJsonObject();
//        logger.debug(smPackage.toString());
        MyLogger.log(smPackage.toString());
    }

}