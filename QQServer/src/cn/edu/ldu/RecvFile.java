package cn.edu.ldu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.DigestOutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLSocket;
import javax.swing.SwingWorker;
import javax.xml.bind.DatatypeConverter;


public class RecvFile extends SwingWorker<Integer,Object> {
    private final SSLSocket fileSocket; 
    private ServerUI parentUI;
    private KeyStore tks; 
    private KeyStore ks; 
    private static final int BUFSIZE=8096;
    public RecvFile(SSLSocket fileSocket,ServerUI parentUI,KeyStore tks,KeyStore ks) { 
        this.fileSocket=fileSocket;
        this.parentUI=parentUI;
        this.tks=tks;
        this.ks=ks;        
    }
    @Override
    protected Integer doInBackground() throws Exception {        
        String SERVER_KEY_STORE_PASSWORD = "123456"; 
        PrivateKey privateKey=(PrivateKey)ks.getKey("server",SERVER_KEY_STORE_PASSWORD.toCharArray());
        PublicKey publicKey=(PublicKey)tks.getCertificate("client").getPublicKey();
        DataInputStream in=new DataInputStream(
                           new BufferedInputStream(
                           fileSocket.getInputStream()));        
        String filename=in.readUTF(); 
        int fileLen=(int)in.readLong(); 
        parentUI.txtArea.append("1.收到文件名："+filename+"文件长度："+fileLen+"字节\n\n");
        File file=new File("./upload/"+filename);       
        BufferedOutputStream fout=new BufferedOutputStream(
                                  new FileOutputStream(file));
        MessageDigest sha256=MessageDigest.getInstance("SHA-256");
        DigestOutputStream out=new DigestOutputStream(fout,sha256); 
        byte[] buffer=new byte[BUFSIZE]; 
        int numRead=0; 
        int numFinished=0;
        while (numFinished<fileLen && (numRead=in.read(buffer))!=-1) { 
            out.write(buffer,0,numRead);
            numFinished+=numRead; 
        }
        parentUI.txtArea.append("2.接收文件内容结束！\n\n");
        int size=in.readInt();
        byte[] signature=new byte[size];
        int i=in.read(signature);
         parentUI.txtArea.append("3.收到加密的数字签名："+DatatypeConverter.printHexBinary(signature)+"\n\n");

        byte[] encryptKey=new byte[128];
        i=in.read(encryptKey);
        parentUI.txtArea.append("4.收到加密的密钥："+DatatypeConverter.printHexBinary(encryptKey)+"\n\n");  
 
        Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey); 
        byte[] decryptKey=cipher.doFinal(encryptKey);
        parentUI.txtArea.append("密钥解密："+DatatypeConverter.printHexBinary(decryptKey)+"\n\n");
        SecretKey secretKey = new SecretKeySpec(decryptKey, "AES");
        Cipher cipher2=Cipher.getInstance("AES");
        cipher2.init(Cipher.DECRYPT_MODE,secretKey);
        byte[] decryptSign=cipher2.doFinal(signature);
        parentUI.txtArea.append("签名解密："+DatatypeConverter.printHexBinary(decryptSign)+"\n\n");
        byte[] sourceDigest=new byte[32]; 
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        sourceDigest=cipher.doFinal(decryptSign); 
        parentUI.txtArea.append("去掉签名后的摘要："+DatatypeConverter.printHexBinary(sourceDigest)+"\n\n");
        byte[] computedDigest=new byte[32];     
        computedDigest=out.getMessageDigest().digest();
        parentUI.txtArea.append("服务器根据收到的文件重新计算的摘要："+DatatypeConverter.printHexBinary(computedDigest)+"\n\n");
        PrintWriter pw=new PrintWriter(fileSocket.getOutputStream(),true);
        if (Arrays.equals(sourceDigest,computedDigest)) {
            pw.println("M_DONE"); 
            parentUI.txtArea.append("5."+filename+"  接收成功！\n\n");
        }else {
            pw.println("M_LOST"); 
            parentUI.txtArea.append("5."+filename+"  接收失败！\n\n");
        }
        in.close();
        out.close();
        fout.close();
        pw.close();
        fileSocket.close();
        return 100;
    } 
}
