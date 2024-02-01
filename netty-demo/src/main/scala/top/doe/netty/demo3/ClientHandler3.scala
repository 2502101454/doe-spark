package top.doe.netty.demo3

import io.netty.buffer.{ByteBuf, Unpooled}
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
 *
 * @author zengwang
 * @create 2024-01-17 10:57
 * @desc:
 */
class ClientHandler3 extends ChannelInboundHandlerAdapter{
  // 一旦和服务端建立连接完成，channelActive方法被调用
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    println("ClientHandler的channelActive方法被调用！【已经跟服务端连接上了】")
    // case class \ object 默认实现了序列化
    // 向服务端发送case class实例
    ctx.writeAndFlush(RequestMsg("hello"))
  }

  // 服务端返回消息后，channelRead方法被调用，客户端读取服务端的返回
  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    msg match {
      case ResponseMsg(msg) =>
        println("ClientHandler的channelRead方法被调用！[服务端返回的消息为]:" + msg)
    }
  }
}
