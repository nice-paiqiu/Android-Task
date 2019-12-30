package cn.edu.ldu;

import cn.edu.ldu.util.Message;
import cn.edu.ldu.util.Translate;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.URL;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;


public class ReceiveMessage extends Thread{
    private DatagramSocket clientSocket; 
    private ClientUI parentUI; 
    private byte[] data=new byte[8096]; 
    private DefaultListModel listModel=new DefaultListModel(); 
    public ReceiveMessage(DatagramSocket socket,ClientUI parentUI) {
        clientSocket=socket; 
        this.parentUI=parentUI; 
    }   
    @Override
    public void run() {
        while (true) { 
          try {
            DatagramPacket packet=new DatagramPacket(data,data.length);
            clientSocket.receive(packet); 
            Message msg=(Message)Translate.ByteToObject(data);
            String userId=msg.getUserId(); 
            if (msg.getType().equalsIgnoreCase("M_LOGIN")) { 
                playSound("/cn/edu/ldu/sound/fadeIn.wav");
                parentUI.txtArea.append(userId+" 昂首挺胸进入聊天室...\n");
                listModel.add(listModel.getSize(), userId);
                parentUI.userList.setModel(listModel);
            }else if (msg.getType().equalsIgnoreCase("M_ACK")) { 
                listModel.add(listModel.getSize(), userId);
                parentUI.userList.setModel(listModel);
            }else if (msg.getType().equalsIgnoreCase("M_MSG")) { 
                playSound("/cn/edu/ldu/sound/msg.wav");
                parentUI.txtArea.append(userId+" 说："+msg.getText()+"\n");
            }else if (msg.getType().equalsIgnoreCase("M_QUIT")) { 
                playSound("/cn/edu/ldu/sound/leave.wav");
                parentUI.txtArea.append(userId+" 大步流星离开聊天室...\n");
                listModel.remove(listModel.indexOf(userId));
                parentUI.userList.setModel(listModel);
            }
          }catch (Exception ex) {
              JOptionPane.showMessageDialog(null, ex.getMessage(),"错误提示",JOptionPane.ERROR_MESSAGE);
          }
        } 
    }
    private void playSound(String filename) {
        URL url = AudioClip.class.getResource(filename);
        AudioClip sound;
        sound = Applet.newAudioClip(url);
        sound.play();
    }
}
