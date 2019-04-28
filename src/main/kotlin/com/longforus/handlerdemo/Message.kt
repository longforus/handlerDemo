package com.longforus.handlerdemo

/**
 * @describe
 * @author  longforus
 * @date 2019-04-26  22:36
 */
class Message (val what:Int) {
    var obj:Any? = null
    var target:Handler? = null
    var next:Message? = null

}