/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  component-ding
 * File     DingComponentConfiguration.kt
 * Date  2020/8/8 下午6:49
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */

@file:Suppress("unused")

package love.forte.simbot.component.ding

import com.forte.config.ConfigurationHelper
import com.forte.qqrobot.ConfigProperties
import com.forte.qqrobot.anno.depend.Beans
import com.forte.qqrobot.anno.depend.Depend
import com.forte.qqrobot.constant.PriorityConstant
import com.forte.qqrobot.sender.HttpClientAble
import com.forte.qqrobot.sender.HttpClientHelper
import love.forte.simbot.component.ding.sceret.DefaultDingSecretCalculator
import love.forte.simbot.component.ding.sceret.DingSecretCalculator
import love.forte.simbot.component.ding.sender.*
import java.util.regex.Pattern


@Beans
class TemporaryHttpClientConfiguration {
    /**
     * 临时处理，提供一个[HttpClientAble]实例
     */
    @get:Beans(priority = PriorityConstant.SECOND_LAST)
    val defaultHttpClientAble: HttpClientAble = HttpClientHelper.getDefaultHttp()
}

/**
 * 钉钉的组件配置类
 * @author ForteScarlet <ForteScarlet@163.com>
 * 2020/8/8
 */
@Beans
class DingComponentConfiguration {


    /**
     * 签名计算器
     * @see DingSecretCalculator
     * @see DefaultDingSecretCalculator
     */
    @get:Beans
    val defaultDingSecretCalculator: DefaultDingSecretCalculator = DefaultDingSecretCalculator

    /**
     * sender builder
     */
    @get:Beans
    val dingSenderBuilder: DingSenderBuilderImpl = DingSenderBuilderImpl

    /**
     * 获取配置
     */
    @field:Depend
    lateinit var configProperties: ConfigProperties

    /**
     * http client able
     */
    @field:Depend
    lateinit var httpClientAble: HttpClientAble

    /**
     * Ding配置类
     */
    @Beans
    fun getDingConfiguration(): DingConfiguration {
        val injectAble = ConfigurationHelper.toInjectable(DingConfiguration::class.java)
        val conf = DingConfiguration()
        injectAble.inject(conf, configProperties)
        return conf
    }


    /**
     * DingSender管理器
     * @param config 配置信息
     * @param dingSecretCalculator 签名计算器
     */
    @Beans
    fun getDingSenderManager(config: DingConfiguration,
                             dingSecretCalculator: DingSecretCalculator,
                             senderBuilder: DingSenderBuilder
    ): DingSenderManager {
        val senders: MutableMap<String, DingSender> = config.dingBots.split(",").asSequence().map {
            val infoSplit = it.split(Pattern.compile(":"), 2)
            val secret: String? = if(infoSplit[0].isEmpty()) null else infoSplit[0]
            val accessToken: String = infoSplit[1]
            val dingSenderInfo = DingSenderInfo(accessToken, config.webhook, secret, dingSecretCalculator, httpClientAble, config)
            val dingSender = senderBuilder.getDingSender(dingSenderInfo)
            accessToken to dingSender
        }.toMap(mutableMapOf())

        return DingSenderManagerImpl(senders)
    }


}


