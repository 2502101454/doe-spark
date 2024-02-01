package top.doe.netty.demo3

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.serialization.{ClassResolvers, ObjectDecoder, ObjectEncoder}

/**
 *
 * @author zengwang
 * @create 2024-01-17 10:16
 * @desc:
 */
class NettyServer3 {
  def bind(host: String, port: Int): Unit = {
    // 线程池：处理客户端连接请求
    val bossGroup = new NioEventLoopGroup()
    // 线程池：处理每条连接的数据读写
    val workerGroup = new NioEventLoopGroup()
    // Netty NIO服务端的辅助启动工具，降低开发复杂度
    val bootstrap = new ServerBootstrap()
    // 将两个线程池串一起准备干活
    bootstrap.group(bossGroup, workerGroup)
      // 配置服务端的IO模型 为NIO，服务端用的是NioServerXXX
      .channel(classOf[NioServerSocketChannel])
      // 绑定数据处理逻辑
      .childHandler(new ChannelInitializer[SocketChannel] {
        override def initChannel(ch: SocketChannel): Unit = {
          // 处理输入的顺序：decoder--> handler3
          // 处理输出的顺序: handler3 (ctx.write) --> encoder
          ch.pipeline().addLast("encoder", new ObjectEncoder) // 实现ChannelOutboundHandler
          ch.pipeline().addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass.getClassLoader))) // 实现ChannelInboundHandler
          ch.pipeline().addLast("handler", new ServerHandler3) // 实现ChannelInboundHandler
        }
      })
    // 绑定端口地址
    bootstrap.bind(host, port)
  }
}

object NettyServer3 {
  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8888
    val server = new NettyServer3
    server.bind(host, port)
  }
}