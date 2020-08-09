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

import com.forte.lang.Language;
import com.simplerobot.modules.utils.KQCode;
import com.simplerobot.modules.utils.KQCodeUtils;
import love.forte.simbot.component.ding.messages.*;

/**
 * @author ForteScarlet <ForteScarlet@163.com>
 * 2020/8/8
 */
public class Test1 {
    public static void main(String[] args) {
        Language.init();

        // 钉钉 text类型的消息
        // [CQ:at,qq=123,all,456,789]
        // 或者
        // [CQ:at,code=123,all,456,789]
        DingText dingText = new DingText("123");

        // 钉钉 at类型的消息
        // 参数1 at的人
        // 参数2 是否at全体, 默认为false
        DingAt dingAt = new DingAt(new String[]{"1111", "2222", "3333"});
        // 只at全体的ding at
        DingAt atAll = DingAt.getAtAll();
        // 不at任何人的空at
        DingAt emptyAt = DingAt.getEmpty();


        // 钉钉 markdown类型的消息
        // [CQ:ding-markdown,title=标题,text=正文]
        DingMarkdown markdown = new DingMarkdown("标题", "正文");

        // 可以通过DingMarkDownBuilder构造markdown
        DingMarkdownBuilder builder = new DingMarkdownBuilder("标题");
        DingMarkdown markdown2 = builder
                .h1("标题1")
                .h2("标题2")
                .h3("标题3")
                .h4("标题4")
                .orderedList("有序1", "有序2", "有序3")
                .build();

        System.out.println(markdown2.getText());


        // 钉钉 link类型
        // 参数：
        /*
        title
        text
        messageUrl
        picUrl 默认为null
         */
        // [CQ:ding-link,title=标题,text=正文,messageUrl=链接]
        DingLink link = new DingLink("标题", "正文", "文本链接");

        // 钉钉 独立跳转链接
        // [CQ:ding-actionCard,title=标题.text=正文,btnOrientation=0,btnTitle=t1&#44;t2,btnUrl=url1&#44;url2]
        // 其中，&#44;是逗号的转移。一般不用管，当成btnTitle=1,2就行
        DingAutonomyActionCard dingAutonomyActionCard = new DingAutonomyActionCard("title", "text", "1", new DingAutonomyActionCardButtons[]{new DingAutonomyActionCardButtons("标题", "链接")});
        // 钉钉 整体跳转链接
        // [CQ:ding-actionCard,title=标题.text=正文,btnOrientation=0,btnTitle=t1,btnUrl=url1]
        DingWholeActionCard dingWholeActionCard = new DingWholeActionCard("title", "text", "1", "button title", "button url");


        // 钉钉 FeedCard 暂时不支持CQ码解析
        DingFeedCard feedCard = new DingFeedCard(new DingFeedCardLink[]{new DingFeedCardLink("标题", "图片链接", "跳转链接")});

    }
}
