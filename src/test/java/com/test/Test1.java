/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  component-ding
 * File     Test1.java
 * Date  2020/8/8 下午8:53
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */

package com.test;

import com.alibaba.fastjson.JSON;
import com.forte.lang.Language;
import love.forte.simbot.component.ding.messages.*;
import love.forte.simbot.component.ding.utils.DingCQUtil;

/**
 * @author ForteScarlet <ForteScarlet@163.com>
 * 2020/8/8
 */
public class Test1 {
    public static void main(String[] args) {

        Language.init();

        DingText dingText = new DingText("123");
        DingText dingText2 = new DingText("123");
        DingMarkdown dingMarkdown = new DingMarkdown("ti", "m_text");

        DingText plus = dingText.plus(dingMarkdown);

        DingSpecialMessage merge = DingSpecialMessageMerger.merge(dingText, dingText2);

        DingSpecialMessageChain chain = DingCQUtil.INSTANCE.msgToDing("ding!");

        System.out.println(JSON.toJSONString(chain));

        System.out.println(merge);

    }
}
