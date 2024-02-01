package top.doe.netty.demo1

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

/**
 *
 * @author zengwang
 * @create 2024-01-17 10:47
 * @desc:
 */
class NettyClient1 {

  def connect(host: String, port: Int): Unit = {
    val eventLoopGroup = new NioEventLoopGroup()
    // Netty客户端辅助启动工具
    val bootstrap = new Bootstrap()
    bootstrap.group(eventLoopGroup)
      // 配置客户端IO模型，客户端用的是不带Server关键字的类哈
      .channel(classOf[NioSocketChannel])
      .handler(new ChannelInitializer[SocketChannel] {
        override def initChannel(ch: SocketChannel): Unit = {
          ch.pipeline().addLast(new ClientHandler1)
        }
      })
    // 发送连接操作
    bootstrap.connect(host, port)
  }
}

object NettyClient1 {
  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8888
    val client1 = new NettyClient1
    client1.connect(host, port)
  }
}