package com.longforus.handlerdemo

import kotlin.concurrent.thread

/**
 * @describe 模仿android handler 机制 实现子线程发送消息给主线程
 * @author  longforus
 * @date 2019-04-26  22:35
 */

fun main() {
    Looper.prepareMainLooper()
    val h = object :Handler(){
        override fun handlerMessage(msg: Message) {
            println("${Thread.currentThread().name}   ${msg.what}   ${msg.obj}")
        }
    }
    for (i in 0..50) {
        thread {
//            Thread.sleep(Random.nextLong(500))
            val msg = Message(i)
            msg.obj = System.currentTimeMillis()
            h.post(msg)
            println("${Thread.currentThread().name} i=$i")
        }
    }
    Looper.myLooper().loop()
}