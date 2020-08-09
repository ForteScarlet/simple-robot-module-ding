/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  component-ding
 * File     specialMessageCanAt.kt
 * Date  2020/8/8 上午12:02
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */

package love.forte.simbot.component.ding.messages

/*
    此文件定义那些可以与at类型相结合的消息类型
    text
    markdown
 */


/**
 * 钉钉的At类型的消息。
 * 似乎只有在text和markdown类型的时候可以使用at
 * ```
 *  "at": {
 *      "atMobiles": [
 *      "150XXXXXXXX"
 *      ],
 *      "isAtAll": false
 *  }
```
 */
data class DingAt(val atMobiles: Array<String>, val isAtAll: Boolean = false): BaseNormalDingSpecialMessage<DingAt>("at") {

    companion object DingAtAll {
        @JvmStatic
        val atAll = DingAt(arrayOf(), true)
        @JvmStatic
        val empty = DingAt(arrayOf())
    }


    /**
     * 一共就俩参数, 要么是`atMobiles`, 要么是`isAtAll`，其他都是null
     */
    override operator fun get(key: String): Any? {
        return when (key) {
            "atMobiles" -> atMobiles
            "isAtAll" -> isAtAll
            else -> null
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DingAt

        if (!atMobiles.contentEquals(other.atMobiles)) return false
        if (isAtAll != other.isAtAll) return false

        return true
    }

    override fun hashCode(): Int {
        var result = atMobiles.contentHashCode()
        result = 31 * result + isAtAll.hashCode()
        return result
    }

    /**
     * 合并两个[DingAt]
     */
    override fun doPlus(other: DingAt): DingAt {
        val atAll = isAtAll || other.isAtAll
        val atMobiles = atMobiles.plus(other.atMobiles).distinct()
        return DingAt(atMobiles.toTypedArray(), atAll)
    }

    /**
     * compare by [isAtAll]
     */
    override fun compareTo(other: DingSpecialMessage): Int {
        return if(other is DingAt){
            isAtAll.compareTo(other.isAtAll)
        }else -1
    }
}



/**
 * 钉钉的Text类型的消息
 * 不应该与markdown类型[DingMarkdown]同时存在
 * `msgtype = text`
 * 可以存在at，也可以为null
 */
data class DingText(val content: String): BaseNormalDingSpecialMessage<DingText>("text") {

    /**
     * 只能获取到content的值
     */
    override operator fun get(key: String): Any? {
        return if(key == "content") content else null
    }

    /**
     * 合并两个[DingText]
     */
    override fun doPlus(other: DingText): DingText = DingText(this.content + other.content)

    /**
     * compare by [content]
     */
    override fun compareTo(other: DingSpecialMessage): Int {
        return if(other is DingText){
            return this.content.compareTo(other.content)
        }else 1
    }
}


fun String.toDingText(): DingText = DingText(this)


/**
 * markdown语法, 且不应该与text类型[DingText]同时存在
 * ```
 * {
    "msgtype": "markdown",
    "markdown": {
    "title":"杭州天气",
    "text": "#### 杭州天气 @150XXXXXXXX \n> 9度，西北风1级，空气良89，相对温度73%\n> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n> ###### 10点20分发布 [天气](https://www.dingtalk.com) \n"
    },
    "at": {
    "atMobiles": [
    "150XXXXXXXX"
    ],
    "isAtAll": false
    }
}
 * ```
 *
 * 目前钉钉的markdown语法只支持一部分子集
 */
data class DingMarkdown(val title: String, val text: String): BaseNormalDingSpecialMessage<DingMarkdown>("markdown") {
    override fun get(key: String): Any? {
        return when(key) {
            "title" -> title
            "text" -> text
            else -> null
        }
    }

    /**
     * 会对title和text进行合并。text直接会有一个换行符(\n)
     */
    override fun doPlus(other: DingMarkdown): DingMarkdown = DingMarkdown(this.title + other.title, this.text + "\n" + other.text)

    /**
     * 根据标题排序
     */
    override fun compareTo(other: DingSpecialMessage): Int {
        return if(other is DingMarkdown){
            this.title.compareTo(other.title)
        }else  this compareWith other
    }

}
