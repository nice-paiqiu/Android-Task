package cn.edu.ldu.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Translate {
 
    public static byte[] ObjectToByte(Object obj) {
       byte[] buffer=null;
        try {
            ByteArrayOutputStream bo=new ByteArrayOutputStream(); 
            ObjectOutputStream oo=new ObjectOutputStream(bo); 
            oo.writeObject(obj); 
            buffer=bo.toByteArray(); 
        }catch(IOException ex) {}
        return buffer;
    } 
   
    public static Object ByteToObject(byte[] buffer) {
        Object obj=null;
        try {
            ByteArrayInputStream bi=new ByteArrayInputStream(buffer); 
            ObjectInputStream oi=new ObjectInputStream(bi); 
            obj=oi.readObject();
        }catch(IOException | ClassNotFoundException ex) { }
        return obj;
    } 
}
