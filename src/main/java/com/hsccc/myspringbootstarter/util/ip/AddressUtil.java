package com.hsccc.myspringbootstarter.util.ip;

import com.alibaba.fastjson.JSONObject;
import com.hsccc.myspringbootstarter.core.common.Constant;
import com.hsccc.myspringbootstarter.util.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
public class AddressUtil {
    private static final Logger log = LoggerFactory.getLogger(AddressUtil.class);

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        // 内网不查询
        if (IpUtil.internalIp(ip)) {
            return "内网IP";
        }
        // TODO: 自定义配置，工具类整理, 移除fastjson
        if (false) {
            try {
                String rspStr = HttpUtil.sendGet(IP_URL, "ip=" + ip + "&json=true", Constant.GBK);
                if (!StringUtils.hasText(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSONObject.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            } catch (Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return UNKNOWN;
    }
}
