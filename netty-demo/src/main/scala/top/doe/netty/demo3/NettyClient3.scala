package top.doe.netty.demo3

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.serialization.{ClassResolvers, ObjectDecoder, ObjectEncoder}

/**
 *
 * @author zengwang
 * @create 2024-01-17 10:47
 * @desc:
 */
class NettyClient3 {

  def connect(host: String, port: Int): Unit = {
    val eventLoopGroup = new NioEventLoopGroup()
    // Netty客户端辅助启动工具
    val bootstrap = new Bootstrap()
    bootstrap.group(eventLoopGroup)
      // 配置客户端IO模型，客户端用的是不带Server关键字的类哈
      .channel(classOf[NioSocketChannel])
      .handler(new ChannelInitializer[SocketChannel] {
        override def initChannel(ch: SocketChannel): Unit = {
          ch.pipeline().addLast("encoder", new ObjectEncoder)
          ch.pipeline().addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass.getClassLoader)))
          ch.pipeline().addLast("handler", new ClientHandler3) //  实现ChannelInboundHandler
        }
      })
    // 发送连接操作
    bootstrap.connect(host, port)
  }
}

object NettyClient3 {
  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8888
    val client1 = new NettyClient3
    client1.connect(host, port)
  }
}