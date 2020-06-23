package com.ran.callloggen.udp;

import com.ran.callloggen.PropertiesUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 工具类
 */
public class HeartBeatThread extends Thread{

    private DatagramSocket sock ;
    public HeartBeatThread(){
        try {
            //
            sock = new DatagramSocket(PropertiesUtil.getInt("heartbeat.udp.send.port"));
            //守护线程
            this.setDaemon(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        byte[] bytes = new byte[1] ;
        bytes[0] = (byte)PropertiesUtil.getInt("heartbeat.udp.send.flag") ;
        DatagramPacket pack = new DatagramPacket(bytes,1);
        String bcAddr = PropertiesUtil.getString("heartbeat.udp.send.bcaddr");
        int bcPort = PropertiesUtil.getInt("heartbeat.udp.send.bcport");
        int sleep = PropertiesUtil.getInt("heartbeat.udp.send.sleep.ms");
        pack.setSocketAddress(new InetSocketAddress(bcAddr,bcPort));
        while(true){
            try {
                sock.send(pack);
                System.out.println("发送了一次心跳");
                Thread.sleep(sleep);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
