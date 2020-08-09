/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  component-ding
 * File     DingApplication.java
 * Date  2020/8/7 下午9:36
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */
package love.forte.simbot.component.ding

import com.forte.config.Conf
import com.forte.qqrobot.BaseConfiguration


/**
 * 钉钉对应的配置类
 *
 *
 *
 */
class DingConfiguration: BaseConfiguration<DingConfiguration>() {
    /**
     * 钉钉机器人得到的webhook路径。不需要携带后面的参数，如果有则会被忽略
     * 默认为：`https://oapi.dingtalk.com/robot/send`
     */
    @Conf("ding.webhook", comment = "钉钉机器人得到的webhook路径。不需要携带后面的参数，如果有则会被忽略")
    var webhook: String = "https://oapi.dingtalk.com/robot/send"


    /**
     * 此处也提供了多bot的配置方式
     * 关于access_token 和 secret, 分比对应path和code
     * 例如：`simbot.ding.bots=secret:access_token`
     * 其中，secret可省略，则为：`simbot.ding.bots=:access_token`
     */
    @Conf("ding.bots", comment = "如同simbot.core.bots一样，此处也提供了多bot的配置方式")
    var dingBots: String = ""



}
