package top.doe.netty.demo2

import io.netty.buffer.{ByteBuf, Unpooled}
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
 *
 * @author zengwang
 * @create 2024-01-17 10:57
 * @desc:
 */
class ClientHandler2 extends ChannelInboundHandlerAdapter{
  // 一旦和服务端建立连接完成，channelActive方法被调用
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    println("ClientHandler的channelActive方法被调用！【已经跟服务端连接上了】")
    val msg = "hello"
    // 封装成netty自己传输数据使用的ByteBuffer 进行发送
    ctx.writeAndFlush(Unpooled.copiedBuffer(msg.getBytes("UTF-8")))
  }

  // 服务端返回消息后，channelRead方法被调用，客户端读取服务端的返回
  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    val buf: ByteBuf = msg.asInstanceOf[ByteBuf]
    val len = buf.readableBytes()
    val bytes = new Array[Byte](len)
    buf.readBytes(bytes)

    val message = new String(bytes, "UTF-8")
    println("ClientHandler的channelRead方法被调用！[服务端返回的消息为]:" + message)

  }
}
