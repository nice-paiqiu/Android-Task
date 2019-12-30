package cn.edu.ldu;

import cn.edu.ldu.util.Message;
import cn.edu.ldu.util.Translate;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


public class ClientUI extends javax.swing.JFrame {
    private DatagramSocket clientSocket; 
    private Message msg; 
    private byte[] data=new byte[8096]; 

    public ClientUI() {
        initComponents();
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height)/2;
        this.setLocation(x, y);
        filePanel.setVisible(false);
    }
    public ClientUI(DatagramSocket socket,Message msg) {
        this(); 
        clientSocket=socket; 
        this.msg=msg;
        Thread recvThread=new ReceiveMessage(clientSocket,this);
        recvThread.start();    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileMenu = new javax.swing.JPopupMenu();
        uploadFile = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        downloadFile = new javax.swing.JMenuItem();
        leftScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        leftScrollPane2 = new javax.swing.JScrollPane();
        txtInput = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        rightScrollPane = new javax.swing.JScrollPane();
        userList = new javax.swing.JList<>();
        jToolBar1 = new javax.swing.JToolBar();
        btnPhone = new javax.swing.JButton();
        btnVideo = new javax.swing.JButton();
        btnFile = new javax.swing.JButton();
        btnRemote = new javax.swing.JButton();
        filePanel = new javax.swing.JPanel();
        fileLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        progressLabel = new javax.swing.JLabel();

        uploadFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cn/edu/ldu/images/upload.png"))); // NOI18N
        uploadFile.setText("上传文件");
        uploadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadFileActionPerformed(evt);
            }
        });
        fileMenu.add(uploadFile);
        fileMenu.add(jSeparator1);

        downloadFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cn/edu/ldu/images/download.png"))); // NOI18N
        downloadFile.setText("下载文件");
        fileMenu.add(downloadFile);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        leftScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "会话消息窗口", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 14))); // NOI18N

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("宋体", 1, 16)); // NOI18N
        txtArea.setLineWrap(true);
        txtArea.setRows(5);
        leftScrollPane1.setViewportView(txtArea);

        leftScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "发言窗口", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 14))); // NOI18N

        txtInput.setColumns(20);
        txtInput.setFont(new java.awt.Font("宋体", 1, 16)); // NOI18N
        txtInput.setLineWrap(true);
        txtInput.setRows(5);
        leftScrollPane2.setViewportView(txtInput);

        btnSend.setBackground(new java.awt.Color(153, 204, 255));
        btnSend.setText("发  送");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        rightScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "在线用户", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 14))); // NOI18N

        userList.setBackground(new java.awt.Color(255, 204, 255));
        userList.setFont(new java.awt.Font("宋体", 1, 14)); // NOI18N
        rightScrollPane.setViewportView(userList);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnPhone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cn/edu/ldu/images/phone.png"))); // NOI18N
        btnPhone.setText("语音聊天");
        btnPhone.setToolTipText("语音聊天");
        btnPhone.setFocusable(false);
        btnPhone.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPhone.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnPhone);

        btnVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cn/edu/ldu/images/video.png"))); // NOI18N
        btnVideo.setText("视频聊天");
        btnVideo.setToolTipText("视频聊天");
        btnVideo.setFocusable(false);
        btnVideo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVideo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnVideo);

        btnFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cn/edu/ldu/images/file.png"))); // NOI18N
        btnFile.setText("文件传输");
        btnFile.setToolTipText("文件传输");
        btnFile.setFocusable(false);
        btnFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnFileMousePressed(evt);
            }
        });
        jToolBar1.add(btnFile);

        btnRemote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cn/edu/ldu/images/remote.png"))); // NOI18N
        btnRemote.setText("远程桌面");
        btnRemote.setToolTipText("远程桌面");
        btnRemote.setFocusable(false);
        btnRemote.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemote.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnRemote);

        fileLabel.setText("文件：");

        progressBar.setStringPainted(true);

        javax.swing.GroupLayout filePanelLayout = new javax.swing.GroupLayout(filePanel);
        filePanel.setLayout(filePanelLayout);
        filePanelLayout.setHorizontalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filePanelLayout.createSequentialGroup()
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(filePanelLayout.createSequentialGroup()
                .addComponent(fileLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        filePanelLayout.setVerticalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, filePanelLayout.createSequentialGroup()
                .addComponent(fileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(leftScrollPane1)
                            .addComponent(leftScrollPane2))
                        .addGap(4, 4, 4))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(filePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSend))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rightScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(leftScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(leftScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        try {
            msg.setText(txtInput.getText());
            msg.setType("M_MSG"); 
            data=Translate.ObjectToByte(msg);
            DatagramPacket packet=new DatagramPacket(data,data.length,msg.getToAddr(),msg.getToPort());
            clientSocket.send(packet); 
            txtInput.setText(""); 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
        }       
    }//GEN-LAST:event_btnSendActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            msg.setType("M_QUIT"); 
            msg.setText(null);
            data=Translate.ObjectToByte(msg); 
            DatagramPacket packet=new DatagramPacket(data,data.length,msg.getToAddr(),msg.getToPort());       
            clientSocket.send(packet); 
        } catch (IOException ex) { }
        clientSocket.close(); 
    }//GEN-LAST:event_formWindowClosing

    private void btnFileMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFileMousePressed
         fileMenu.show(btnFile, evt.getX()-35, evt.getY()+40);
    }//GEN-LAST:event_btnFileMousePressed

    private void uploadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadFileActionPerformed

        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setDialogTitle("选择上传文件");
        fileChooser.setApproveButtonText("选择");
        int choice=fileChooser.showOpenDialog(this); 
        if (choice==JFileChooser.APPROVE_OPTION) {           
            File file=fileChooser.getSelectedFile();
            SwingWorker<List<String>,String> sender=new FileSender(file,msg,this);
            sender.addPropertyChangeListener(new PropertyChangeListener() {
         public  void propertyChange(PropertyChangeEvent evt) {
             if ("progress".equals(evt.getPropertyName())) {
                 progressBar.setValue((Integer)evt.getNewValue());
             }
         }
     });
         filePanel.setVisible(true);
         fileLabel.setText("文件："+file.getName());
         sender.execute(); 
         
        }
    }//GEN-LAST:event_uploadFileActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFile;
    private javax.swing.JButton btnPhone;
    private javax.swing.JButton btnRemote;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnVideo;
    private javax.swing.JMenuItem downloadFile;
    public javax.swing.JLabel fileLabel;
    private javax.swing.JPopupMenu fileMenu;
    public javax.swing.JPanel filePanel;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JScrollPane leftScrollPane1;
    private javax.swing.JScrollPane leftScrollPane2;
    public javax.swing.JProgressBar progressBar;
    public javax.swing.JLabel progressLabel;
    private javax.swing.JScrollPane rightScrollPane;
    public javax.swing.JTextArea txtArea;
    private javax.swing.JTextArea txtInput;
    private javax.swing.JMenuItem uploadFile;
    public javax.swing.JList<String> userList;
    // End of variables declaration//GEN-END:variables
}
