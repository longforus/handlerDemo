package com.longforus.handlerdemo

/**
 * @describe
 * @author  longforus
 * @date 2019-04-26  22:35
 */
class Looper private constructor(quitAllowed:Boolean){

    val queue = MessageQueue(quitAllowed)
    val thread = Thread.currentThread()


    fun loop(){
        val me = sThreadLocal.get()
        while (true){
            val next = me.queue.next()
            next?.target?.handlerMessage(next)
        }
    }

    companion object {
        val sThreadLocal = ThreadLocal<Looper>()
        fun prepareMainLooper(){
            prepare(false)
        }

        fun prepare(quitAllowed: Boolean = true) {
            if (sThreadLocal.get() != null) {
                throw IllegalStateException("already created")
            }
            sThreadLocal.set(Looper(quitAllowed))
        }

        fun myLooper(): Looper {
           return sThreadLocal.get()
        }
    }
}