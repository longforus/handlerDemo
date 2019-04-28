package com.longforus.handlerdemo

import java.util.concurrent.locks.ReentrantLock

/**
 * @describe 使用生产者-消费者模式的链式消息队列
 * @author  longforus
 * @date 2019-04-26  22:35
 */
class MessageQueue(val quitAllowed: Boolean) {
    var mHead: Message? = null
    var mTail: Message? = null
    val MAX_SIZE = 5
    var count = 0
    val lock = ReentrantLock()
    val notEmpty = lock.newCondition() //感觉条件的使用方法就是 当前不成立就阻塞  成立了就发signal
    val notFull = lock.newCondition()
    fun enqueue(msg: Message) {
        try {
            lock.lock()
            while (count == MAX_SIZE) {
                notFull.await()//已经满了 不满条件不成立 阻塞
            }
            if (mHead == null || mTail == null) {//头指针为空 表示整个表为空 插入了第一个item后 头尾都指向这个item
                mHead = msg
                mTail = msg
            } else {
                mTail!!.next = msg
                mTail = msg
            }
            count++
            notEmpty.signalAll()//插入了msg 不空条件成立 通知解除不空的条件阻塞
        } catch (e: InterruptedException) {
        } finally {
            lock.unlock()
        }
    }


    fun next(): Message? {
        var temp :Message?= null
        try {
            lock.lock()
            while (count == 0||mHead==null) {
                notEmpty.await()//现在空了 不空的条件不成立 阻塞
            }
            temp = mHead
            mHead = mHead!!.next
            if (mHead == null) {//头指针指向了null 说明链表已经为空了 同时也清空尾指针
                mTail = null
            }
            count--
            notFull.signalAll()//取出了msg 不满的条件成立了  通知解除不满的条件阻塞
        } catch (e: InterruptedException) {
        } finally {
            lock.unlock()
        }
        return temp
    }
}