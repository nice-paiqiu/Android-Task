package cn.edu.ldu;
import cn.edu.ldu.security.Cryptography;
import cn.edu.ldu.util.Message;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.DigestInputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.SwingWorker;
import javax.xml.bind.DatatypeConverter;


public class FileSender extends SwingWorker<List<String>,String>{
    private File file; 
    private Message msg;
    private ClientUI parentUI; 
    private SSLSocket fileSocket; 
    private static final int BUFSIZE=8096; 
    private int progress=0; 
    private String lastResults=null; 

    public FileSender(File file,Message msg,ClientUI parentUI) {
        this.file=file;
        this.msg=msg;
        this.parentUI=parentUI;
    }
    @Override
    protected List<String> doInBackground() throws Exception {  
        InputStream key =ClientUI.class.getResourceAsStream("/cn/edu/ldu/keystore/client.keystore");
        InputStream tkey =ClientUI.class.getResourceAsStream("/cn/edu/ldu/keystore/tclient.keystore");
        String CLIENT_KEY_STORE_PASSWORD = "123456"; 
        String CLIENT_TRUST_KEY_STORE_PASSWORD = "123456";
        SSLContext ctx = SSLContext.getInstance("SSL"); 
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509"); 
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore tks = KeyStore.getInstance("JKS");
        ks.load(key, CLIENT_KEY_STORE_PASSWORD.toCharArray());
        tks.load(tkey, CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
        kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
        tmf.init(tks);
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);  
        fileSocket = (SSLSocket)ctx.getSocketFactory().createSocket(msg.getToAddr(),msg.getToPort());
        DataOutputStream out=new DataOutputStream(
                             new BufferedOutputStream(
                             fileSocket.getOutputStream()));
        PrivateKey privateKey=(PrivateKey)ks.getKey("client", CLIENT_KEY_STORE_PASSWORD.toCharArray());
        PublicKey publicKey=(PublicKey)tks.getCertificate("server").getPublicKey();        
        MessageDigest sha256=MessageDigest.getInstance("SHA-256");
        DataInputStream din=new DataInputStream(
                           new BufferedInputStream(
                           new FileInputStream(file)));
        DigestInputStream in=new DigestInputStream(din,sha256);
        
        long fileLen=file.length();  
        out.writeUTF(file.getName());
        out.writeLong(fileLen);
        out.flush();
        parentUI.txtArea.append("1.发送文件名称、文件长度成功！\n");
     
        int numRead=0; 
        int numFinished=0; 
        byte[] buffer=new byte[BUFSIZE];   
        while (numFinished<fileLen && (numRead=in.read(buffer))!=-1) { 
            out.write(buffer,0,numRead); 
            out.flush();
            numFinished+=numRead; 
            Thread.sleep(200); 
            publish(numFinished+"/"+fileLen+"bytes");
            setProgress(numFinished*100/(int)fileLen);             
        }
        in.close();
        din.close();
        parentUI.txtArea.append("2.传送文件内容成功！\n");
        byte[] fileDigest=in.getMessageDigest().digest(); 
        parentUI.txtArea.append("生成的摘要："+DatatypeConverter.printHexBinary(fileDigest)+"\n\n"); 
        Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] signature=cipher.doFinal(fileDigest);
        parentUI.txtArea.append("生成的数字签名："+DatatypeConverter.printHexBinary(signature)+"\n\n"); 
        SecretKey secretKey=Cryptography.generateNewKey();
        parentUI.txtArea.append("生成的密钥："+DatatypeConverter.printHexBinary(secretKey.getEncoded())+"\n\n"); 
        Cipher cipher2=Cipher.getInstance("AES");
        cipher2.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptSign=cipher2.doFinal(signature);
        parentUI.txtArea.append("用密钥加密后的数字签名："+DatatypeConverter.printHexBinary(encryptSign)+"\n\n");     
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptKey=cipher.doFinal(secretKey.getEncoded());
        parentUI.txtArea.append("对密钥加密："+DatatypeConverter.printHexBinary(encryptKey)+"\n\n");
        out.writeInt(encryptSign.length);
        out.flush();
        out.write(encryptSign);
        out.flush();
        parentUI.txtArea.append("3.发送加密的数字签名成功！\n");
        out.write(encryptKey);
        out.flush();
        parentUI.txtArea.append("4.发送加密的密钥成功！\n");
        BufferedReader br=new BufferedReader(
                          new InputStreamReader(
                          fileSocket.getInputStream()));
        String response=br.readLine();
        if (response.equalsIgnoreCase("M_DONE")) { 
            lastResults= "5."+ file.getName() +"  服务器成功接收！\n" ;
        }else if (response.equalsIgnoreCase("M_LOST")){ 
            lastResults= "5."+ file.getName() +"  服务器接收失败！\n" ;
        }
       
        br.close();
        out.close();
        fileSocket.close();
        return null;
    } 
    @Override
    protected void process(List<String> middleResults) {
        for (String str:middleResults) {
            parentUI.progressLabel.setText(str);
        }   
    }
    @Override
    protected void done() {
        parentUI.progressBar.setValue(parentUI.progressBar.getMaximum());
        parentUI.txtArea.append(lastResults+"\n");
        parentUI.filePanel.setVisible(false);
    }
}