package top.doe.netty.demo2

import io.netty.buffer.{ByteBuf, Unpooled}
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
 *
 * @author zengwang
 * @create 2024-01-17 10:36
 * @desc:
 */
class ServerHandler2 extends ChannelInboundHandlerAdapter{
  // 有客户端和服务端建立连接后调用
  override def channelActive(channelHandlerContext: ChannelHandlerContext): Unit = {
    println("ServerHandler的channelActivate方法被调用【一个客户端连接上了】")
  }

  override def channelInactive(channelHandlerContext: ChannelHandlerContext): Unit = {
    println("ServerHandler的channelInactivate方法被调用【一个客户端断开连接了】")
  }

  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    // 将接收来的Netty ByteBuffer 拆包，解析为字符串
    val buf: ByteBuf = msg.asInstanceOf[ByteBuf]
    val len = buf.readableBytes()
    val bytes = new Array[Byte](len)
    buf.readBytes(bytes)

    val message = new String(bytes, "UTF-8")
    println("ServerHandler的channelRead方法被调用【收到客户端发送的消息内容为】：" + message)

    // 给客户端也发消息返回
    val back = "你好"
    ctx.writeAndFlush(Unpooled.copiedBuffer(back.getBytes("UTF-8")))


  }
}
