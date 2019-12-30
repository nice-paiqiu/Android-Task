package cn.edu.ldu;

import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.security.KeyStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


public class ServerUI extends javax.swing.JFrame {

    public ServerUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtHostName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtHostPort = new javax.swing.JTextField();
        btnStart = new javax.swing.JButton();
        midPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("20170407430343叶晖");

        topPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "启动服务器", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("宋体", 1, 14)); // NOI18N
        jLabel1.setText("主机名：");

        txtHostName.setFont(new java.awt.Font("宋体", 1, 14)); // NOI18N
        txtHostName.setText("127.0.0.1");

        jLabel2.setFont(new java.awt.Font("宋体", 1, 14)); // NOI18N
        jLabel2.setText("端口：");

        txtHostPort.setFont(new java.awt.Font("宋体", 1, 14)); // NOI18N
        txtHostPort.setText("50000");

        btnStart.setFont(new java.awt.Font("宋体", 1, 14)); // NOI18N
        btnStart.setText("启  动");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHostName, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHostPort, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addGap(49, 49, 49)
                .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtHostName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtHostPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStart))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        midPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "聊天室大厅", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 14))); // NOI18N

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("宋体", 1, 16)); // NOI18N
        txtArea.setLineWrap(true);
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        javax.swing.GroupLayout midPanelLayout = new javax.swing.GroupLayout(midPanel);
        midPanel.setLayout(midPanelLayout);
        midPanelLayout.setHorizontalGroup(
            midPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        midPanelLayout.setVerticalGroup(
            midPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, midPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(midPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(midPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        try {
            String hostName=txtHostName.getText();
            int hostPort=Integer.parseInt(txtHostPort.getText());
            DatagramSocket serverSocket=new DatagramSocket(hostPort);
            txtArea.append("服务器开始侦听...\n");
            Thread recvThread=new ReceiveMessage(serverSocket,this);
            recvThread.start();
            new Thread(new Runnable() {
                public void run() {
                try {
                InputStream key =ServerUI.class.getResourceAsStream("/cn/edu/ldu/keystore/server.keystore");
                InputStream tkey =ServerUI.class.getResourceAsStream("/cn/edu/ldu/keystore/tserver.keystore");
                String SERVER_KEY_STORE_PASSWORD = "123456"; 
                String SERVER_TRUST_KEY_STORE_PASSWORD = "123456";

                SSLContext ctx = SSLContext.getInstance("SSL");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore tks = KeyStore.getInstance("JKS");
                ks.load(key, SERVER_KEY_STORE_PASSWORD.toCharArray());
                tks.load(tkey, SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());
                kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
                tmf.init(tks);
                ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                SSLServerSocket sslListenSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(hostPort);                
                int processors=Runtime.getRuntime().availableProcessors();
                ExecutorService fixedPool=Executors.newFixedThreadPool(processors*2);
                while (true) { 
                    SSLSocket fileSocket=(SSLSocket)sslListenSocket.accept();
                    SwingWorker<Integer,Object> recver=new RecvFile(fileSocket,ServerUI.this,tks,ks); 
                    fixedPool.execute(recver); 
                }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                }            
                }
            }).start();                        
        } catch (NumberFormatException | SocketException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
        }
        btnStart.setEnabled(false);
    }//GEN-LAST:event_btnStartActionPerformed


    public static void main(String args[]) {
  
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
   
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel midPanel;
    private javax.swing.JPanel topPanel;
    public javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtHostName;
    private javax.swing.JTextField txtHostPort;
    // End of variables declaration//GEN-END:variables
}
