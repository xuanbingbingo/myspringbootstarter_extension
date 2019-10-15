package com.shuiyou.myspringboot.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 标题: 获取用户 IP 地址
 *
 * <p>功能描述:
 *
 * <p>版权: 税友软件集团股份限公司
 *
 * <p>创建时间: 2018年11月23日,下午4:22:02
 *
 * <p>作者：liuxp
 *
 * <p>修改历史记录： ====================================================================<br>
 */
public class IPAddressUtil {

    /**
     * 获取本地IP地址
     *
     * <p><b>功能描述和使用场景:</b>
     *
     * <p><b>实现流程:</b> <br>
     *
     * @return String
     */
    public static String getLocalIpAddress() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration<?> ifaces = NetworkInterface.getNetworkInterfaces();
                    ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration<?> inetAddrs = iface.getInetAddresses();
                        inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) { // 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr.getHostAddress();
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress.getHostAddress();
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {

        }
        return "127.0.0.1";
    }
}
