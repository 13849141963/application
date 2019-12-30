package com.zy.cn;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @ClassName SignUtil
 * @Description 签名的生成规则
 * @Author 狗蛋
 * @Date 2019/12/30 下午6:57
 * @Version 1.0
 */
public class SignUtil {

    /****
     * 获取生成的sign值：服务端的sign值获取方式
     * @param accessSecret 访问秘钥的key
     * @param params
     * @return sign值
     */
    public static String getSign(String accessSecret, String methodType, Map<String, String> params) throws Exception{

        // 判断参数不能为空

        // 1.对参数map进行排序
        Map<String, String> sortMap = sortMap(params);
        System.out.println("排序后的map对象："+sortMap);
        // 2.将map转换成字符拼接串
        String joinString = joinString(sortMap);
        System.out.println("map的拼接串："+joinString);
        // 3.添加待生成sign的规则 按POP的签名规则拼接成最终的待签名串
        String staySign = addJoinRules(methodType, joinString);
        System.out.println("待签名的拼接串："+staySign);
        // 4.签名采用HmacSHA1算法 + Base64，编码采用UTF-8。
        String sign = generateSign(accessSecret + "&", staySign);
        System.out.println("编码前的sign值:"+sign);
        // 签名最后也要做特殊URL编码
        return specialUrlEncode(sign);
    }


    /****
     * 根据参数Key排序（顺序）
     * @param paras
     * @return
     */
    public static Map<String, String> sortMap(Map<String, String> paras){
        java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
        sortParas.putAll(paras);
        return sortParas;
    }

    /****
     * 将map转换成字符串的拼接串
     * 例如：AccessKeyId=testId&Action=SendSms&Format=XML&OutId=123&PhoneNumbers=15300000001
     *      &RegionId=cn-hangzhou&SignName=%E9%98%BF%E9%87%8C%E4%BA%91%E7%9F%AD%E4%BF%A1%E6%B5%8B%E8%AF%95%E4%B8%93%E7%94%A8
     *      &SignatureMethod=HMAC-SHA1
     *      &SignatureNonce=45e25e9b-0a6f-4070-8c85-2956eda1b466
     *      &SignatureVersion=1.0
     *      &TemplateCode=SMS_71390007
     *      &TemplateParam=%7B%22customer%22%3A%22test%22%7D
     *      &Timestamp=2017-07-12T02%3A42%3A19Z
     *      &Version=2017-05-25
     * @param sortParas
     * @return
     */
    public static String joinString(Map<String, String> sortParas) throws Exception{
        java.util.Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(sortParas.get(key)));
        }
        // 去除第一个多余的&符号
        return sortQueryStringTmp.substring(1);
        //return sortQueryStringTmp
    }


    /****
     * 添加待生成sign的规则 按POP的签名规则拼接成最终的待签名串。规则如下：
     * HTTPMethod + “&” + specialUrlEncode(“/”) + ”&” + specialUrlEncode(sortedQueryString)
     * @param methodType 请求接口的类型
     * @param joinString map的拼接串
     * @return
     * @throws Exception
     */
    public static String addJoinRules(String methodType, String joinString)throws Exception {
        // 按POP的签名规则拼接成最终的待签名串
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append(methodType).append("&");
        stringToSign.append(specialUrlEncode("/")).append("&");
        stringToSign.append(specialUrlEncode(joinString));
        return stringToSign.toString();
    }


    /****
     *  签名 签名采用HmacSHA1算法 + Base64，编码采用UTF-8。参考代码如下：
     * @param accessSecret 访问秘钥的密码
     * @param stringToSign 待签名的字符串
     * @return
     */
    public static String generateSign(String accessSecret, String stringToSign)throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new sun.misc.BASE64Encoder().encode(signData);
    }


    /***
     * 首先介绍下面会用到的特殊URL编码这个是POP特殊的一种规则，
     * 即在一般的URLEncode后再增加三种字符替换：加号 （+）替换成 %20、星号 （*）
     * 替换成 %2A、 %7E 替换回波浪号 （~）参考代码如下：
     * @param value
     * @return
     */
    public static String specialUrlEncode(String value) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(value, "UTF-8")
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~");
    }

    // 客户端的生成方式
    public static void main(String[] args) throws Exception{
        // 模拟方式
        // 秘钥key
        String accessKeyId = "e-wallet";
        // 秘钥密码
        String accessSecret = "123456";
        // api接口的请求类型
        String methodType = "GET";

        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
        // 创建需要请求的参数map对象
        java.util.Map<String, String> paras = new java.util.HashMap<String, String>();
        // 1. 系统参数
        paras.put("SignatureMethod", "HMAC-SHA1");
        paras.put("SignatureNonce", java.util.UUID.randomUUID().toString());
        paras.put("AccessKeyId", accessKeyId);
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", df.format(new java.util.Date()));
        // 2.业务参数
        paras.put("Action", "SendSms");
        paras.put("Version", "2017-05-25");
        paras.put("id", "98");
        paras.put("username", "zhangsan");

        // 3. 去除签名关键字Key
        if (paras.containsKey("Signature")) paras.remove("Signature");

        // 4. 参数KEY排序
        java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
        sortParas.putAll(paras);
        // 5. 构造待签名的字符串
        java.util.Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
        }
        String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
        System.out.println("sortedQueryString:"+sortedQueryString);
        String signature = getSign(accessSecret, methodType ,paras);
        System.out.println("编码后的sign值："+signature);

        // 客户端的拼接方式
        // http://dysmsapi.aliyuncs.com/?Signature=q5Yv1HqwhFMCpDt%2BenCxIV2A%2Fo4%3D
        // &AccessKeyId=e-wallet
        // &SignatureMethod=HMAC-SHA1
        // &SignatureNonce=6bd8f29b-2386-4297-b076-6ba2f68dc8b7
        // &SignatureVersion=1.0
        // &Timestamp=2019-12-30T12%3A06%3A50Z
        // &id=98
        // &username=zhangsan
        System.out.println("http://dysmsapi.aliyuncs.com/?Signature="+signature + sortQueryStringTmp);
        /****
         * sortedQueryString:AccessKeyId=e-wallet&SignatureMethod=HMAC-SHA1&SignatureNonce=6bd8f29b-2386-4297-b076-6ba2f68dc8b7&SignatureVersion=1.0&Timestamp=2019-12-30T12%3A06%3A50Z&id=98&username=zhangsan
         * 排序后的map对象：{AccessKeyId=e-wallet, SignatureMethod=HMAC-SHA1, SignatureNonce=6bd8f29b-2386-4297-b076-6ba2f68dc8b7, SignatureVersion=1.0, Timestamp=2019-12-30T12:06:50Z, id=98, username=zhangsan}
         * map的拼接串：AccessKeyId=e-wallet&SignatureMethod=HMAC-SHA1&SignatureNonce=6bd8f29b-2386-4297-b076-6ba2f68dc8b7&SignatureVersion=1.0&Timestamp=2019-12-30T12%3A06%3A50Z&id=98&username=zhangsan
         * 待签名的拼接串：GET&%2F&AccessKeyId%3De-wallet%26SignatureMethod%3DHMAC-SHA1%26SignatureNonce%3D6bd8f29b-2386-4297-b076-6ba2f68dc8b7%26SignatureVersion%3D1.0%26Timestamp%3D2019-12-30T12%253A06%253A50Z%26id%3D98%26username%3Dzhangsan
         * 编码前的sign值:q5Yv1HqwhFMCpDt+enCxIV2A/o4=
         * 编码后的sign值：q5Yv1HqwhFMCpDt%2BenCxIV2A%2Fo4%3D
         * http://dysmsapi.aliyuncs.com/?Signature=q5Yv1HqwhFMCpDt%2BenCxIV2A%2Fo4%3D&AccessKeyId=e-wallet&SignatureMethod=HMAC-SHA1&SignatureNonce=6bd8f29b-2386-4297-b076-6ba2f68dc8b7&SignatureVersion=1.0&Timestamp=2019-12-30T12%3A06%3A50Z&id=98&username=zhangsan
         */
        /***参数说明：
         * Signature 请求签名，即最终生成的签名结果值。 string 必传参数
         * AccessKeyId 访问密钥 ID。AccessKey 用于调用 API。  string 必传参数
         * Action API 的名称   string 必传参数
         * SignatureMethod 签名方式。取值范围：HMAC-SHA1 string 必传参数
         * SignatureNonce 签名唯一随机数。用于防止网络重放攻击，建议您每一次请求都使用不同的随机数。JAVA语言建议用：java.util.UUID.randomUUID()生成。
         * SignatureVersion 签名算法版本。取值范围：1.0。
         * Timestamp 请求的时间戳。按照ISO8601 标准表示，并需要使用UTC时间，格式为yyyy-MM-ddTHH:mm:ssZ。示例：2018-01-01T12:00:00Z 表示北京时间 2018 年 01 月 01 日 20 点 00 分 00 秒。
         * Version API 的版本号，格式为 YYYY-MM-DD。取值范围：2017-05-25。
         *
         * 注意 ： SignatureNonce 这个字段可以保存在数据库中避免网络重复攻击
         */
    }


}
