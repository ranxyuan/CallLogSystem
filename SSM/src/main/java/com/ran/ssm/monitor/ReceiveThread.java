package com.ran.ssm.monitor;

import com.ran.ssm.domain.HeartBeat;
import com.ran.ssm.util.PropertiesUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/16.
 */
public class ReceiveThread extends Thread{
    //IP地址和最后一次收到心跳时间
    public Map<String,HeartBeat> map = new HashMap<String, HeartBeat>();

    DatagramSocket sock ;
    public ReceiveThread(){
        try {
            sock = new DatagramSocket(PropertiesUtil.getInt("heartbeat.udp.receive.port"));
            this.setDaemon(true);
            System.out.println("心跳接受开始了!!");
      }
      catch (Exception e){
        e.printStackTrace();
      }
    }

    public void run() {
        byte[] buf = new byte[1] ;
        DatagramPacket pack = new DatagramPacket(buf,1);
        while(true){
            try {
                sock.receive(pack);
                int flag = buf[0] ;
                InetSocketAddress sockAddr  = (InetSocketAddress)pack.getSocketAddress();
                String ip = sockAddr.getAddress().getHostAddress();
                map.put(ip,new HeartBeat(ip,flag,System.currentTimeMillis()));
                System.out.println("收到心跳 : " + ip + "," + flag + "," + System.currentTimeMillis() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
