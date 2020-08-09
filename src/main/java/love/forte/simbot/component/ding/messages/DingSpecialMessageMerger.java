/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  component-ding
 * File     DingSpecialMessageMergeUtil.java
 * Date  2020/8/7 下午11:56
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */

package love.forte.simbot.component.ding.messages;

/**
 * {@link DingSpecialMessage}的合并工具类
 * 此工具类仅供{@link DingSpecialMessageChain}使用。
 * @author ForteScarlet <ForteScarlet@163.com>
 * 2020/8/7
 */
public class DingSpecialMessageMerger {
    /**
     * 对两个{@link DingSpecialMessage}进行合并
     * 需要保证其类型相同。
     * 由于kotlin对于泛型的管控较为严格，因此通过java来进行类型不安全的合并
     * @param first  first msg
     * @param second second msg
     * @return 合并结果
     */
    public static DingSpecialMessage merge(DingSpecialMessage first, DingSpecialMessage second){
        return first.plus(second);
    }
}
