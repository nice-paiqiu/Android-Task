package cn.edu.ldu.util;

import java.net.DatagramPacket;


public class User {
    private String userId=null;
    private DatagramPacket packet=null; 
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public DatagramPacket getPacket() {
        return packet;
    }
    public void setPacket(DatagramPacket packet) {
        this.packet = packet;
    }   
}
