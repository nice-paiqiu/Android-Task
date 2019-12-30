package cn.edu.ldu;

import cn.edu.ldu.db.beans.Member;
import cn.edu.ldu.db.tables.MemberManager;
import cn.edu.ldu.util.Message;
import cn.edu.ldu.util.Translate;
import cn.edu.ldu.util.User;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiveMessage extends Thread {
    private DatagramSocket serverSocket; 
    private DatagramPacket packet; 
    private List<User> userList=new ArrayList<User>(); 
    private byte[] data=new byte[8096]; 
    private ServerUI parentUI; 
    
    public ReceiveMessage(DatagramSocket socket,ServerUI parentUI) {
        serverSocket=socket;
        this.parentUI=parentUI;
    }
    @Override
    public void run() {  
        while (true) {
            try {
            packet=new DatagramPacket(data,data.length);
            serverSocket.receive(packet);
            Message msg=(Message)Translate.ByteToObject(packet.getData());
            String userId=msg.getUserId();     
            if (msg.getType().equalsIgnoreCase("M_LOGIN")) { 
                Message backMsg=new Message();
                Member bean=new Member();
                bean.setId(Integer.parseInt(userId));
                bean.setPassword(msg.getPassword());            
                if (!MemberManager.userLogin(bean)) {
                    backMsg.setType("M_FAILURE");
                    byte[] buf=Translate.ObjectToByte(backMsg);
                    DatagramPacket backPacket=new DatagramPacket(buf,buf.length,packet.getAddress(),packet.getPort());
                    serverSocket.send(backPacket);              
                }else { 
                    backMsg.setType("M_SUCCESS");
                    byte[] buf=Translate.ObjectToByte(backMsg);
                    DatagramPacket backPacket=new DatagramPacket(buf,buf.length,packet.getAddress(),packet.getPort());
                    serverSocket.send(backPacket); 
                    
                    User user=new User();
                    user.setUserId(userId); 
                    user.setPacket(packet); 
                    userList.add(user);
                    parentUI.txtArea.append(userId+" 登录！\n");
                    for (int i=0;i<userList.size();i++) { 
                        if (!userId.equalsIgnoreCase(userList.get(i).getUserId())){
                            DatagramPacket oldPacket=userList.get(i).getPacket(); 
                            DatagramPacket newPacket=new DatagramPacket(data,data.length,oldPacket.getAddress(),oldPacket.getPort());
                            serverSocket.send(newPacket); 
                        }
                        Message other=new Message();
                        other.setUserId(userList.get(i).getUserId());
                        other.setType("M_ACK");
                        byte[] buffer=Translate.ObjectToByte(other);
                        DatagramPacket newPacket=new DatagramPacket(buffer,buffer.length,packet.getAddress(),packet.getPort());
                        serverSocket.send(newPacket);
                    }               
                }                  
            }else if (msg.getType().equalsIgnoreCase("M_MSG")) { 
                parentUI.txtArea.append(userId+" 说："+msg.getText()+"\n");
                for (int i=0;i<userList.size();i++) { 
                    DatagramPacket oldPacket=userList.get(i).getPacket();
                    DatagramPacket newPacket=new DatagramPacket(data,data.length,oldPacket.getAddress(),oldPacket.getPort()); 
                    serverSocket.send(newPacket); 
                }
            }else if (msg.getType().equalsIgnoreCase("M_QUIT")) { 
                parentUI.txtArea.append(userId+" 下线！\n");
                for(int i=0;i<userList.size();i++) {
                    if (userList.get(i).getUserId().equals(userId)) {
                        userList.remove(i);
                        break;
                    }
                }
                for (int i=0;i<userList.size();i++) {
                    DatagramPacket oldPacket=userList.get(i).getPacket();
                    DatagramPacket newPacket=new DatagramPacket(data,data.length,oldPacket.getAddress(),oldPacket.getPort());
                    serverSocket.send(newPacket);
                }
            }
            } catch (IOException | SQLException |  NumberFormatException ex) {  } 
        }
    }
}

