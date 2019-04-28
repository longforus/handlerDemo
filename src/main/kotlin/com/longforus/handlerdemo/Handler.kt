package com.longforus.handlerdemo

/**
 * @describe
 * @author  longforus
 * @date 2019-04-26  22:35
 */
open class Handler {
    val looper: Looper = Looper.myLooper()
    val messageQueue: MessageQueue= looper.queue


    open fun handlerMessage(msg:Message){}

    fun post(msg: Message) {
        msg.target = this
        messageQueue.enqueue(msg)
    }
}