/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  component-ding
 * File     DingSecretUtil.java
 * Date  2020/8/7 下午9:36
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */
package love.forte.simbot.component.ding.utils

import org.apache.commons.codec.binary.Base64
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 *
 * @author ForteScarlet <ForteScarlet></ForteScarlet>@163.com>
 * 2020/8/7
 */
object DingSecretUtil {
    /**
     * 验证方法之二，加签
     * <br></br>
     * 加签后，把 timestamp和第一步得到的签名值拼接到URL中。 <br></br>
     * 例如: `https://oapi.dingtalk.com/robot/send?access_token=XXXXXX&timestamp=XXX&sign=XXX`
     *
     * @see [https://ding-doc.dingtalk.com/doc./serverapi3/iydd5h](https://ding-doc.dingtalk.com/doc./serverapi3/iydd5h)
     *
     * @param timestamp 当前时间戳，单位是毫秒，与请求调用时间误差不能超过1小时
     * @param secret 密钥，机器人安全设置页面，加签一栏下面显示的SEC开头的字符串
     * @return sign
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class, InvalidKeyException::class)
    fun secret(timestamp: Long, secret: String): String {
        val stringToSign = "$timestamp\n$secret"
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256"))
        val signData = mac.doFinal(stringToSign.toByteArray(StandardCharsets.UTF_8))
        return URLEncoder.encode(String(Base64.encodeBase64(signData)), "UTF-8")
    }
}