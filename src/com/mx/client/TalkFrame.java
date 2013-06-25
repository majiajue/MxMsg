package com.mx.client;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import com.mx.clent.vo.MsgUser;
import com.mx.client.msg.ShockMsg;



public class TalkFrame extends BaseFrame implements Runnable{
    
	public com.mx.clent.vo.MsgUser friend;  //聊天的对方的QQ
	public static com.mx.clent.vo.MsgUser owerUser;
	private TalkFrame qqTalkFrame;
    private boolean isFileSndOrGeting=false;
    public  static boolean isViedoing=false;
	
	 /** Creates new form QQTalkFrame */
    public TalkFrame(MsgUser friend) {
    	this.friend=friend;
    	initComponents();
        qqTalkFrame=this;
    }
    
    public void changeTile(String info){
    	this.setTitle(info);
    }

                       
    private void initComponents() {
    	changeTile("与 "+friend.getUserName()+"  交谈中...");
    	//intSubstance();
    	//Image icon=ResourcesManagement.getImage("icon/message.gif");
        //this.setIconImage(icon);
        glassBox1=new GlassBox();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        personWord_TextPane = new javax.swing.JTextPane();
        headIamge_jLabel = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        font_jButton = new javax.swing.JButton();
        face_jButton = new javax.swing.JButton();
        shock_Button = new javax.swing.JButton();
        sendImage_jButton = new javax.swing.JButton();
        fenMusic_jLabel = new javax.swing.JLabel();
        talkRecord_Button = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        sendMsg_jTextPane = new javax.swing.JTextPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        showMsg_jTextPane = new javax.swing.JTextPane();
        exit_Button = new javax.swing.JButton();
        send_Button = new javax.swing.JButton();
        setKey_Button = new javax.swing.JButton();
        jTabbedPane2 = new SnapTipTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        friendXiuXiu_jLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        talkRecord_jTextPane = new javax.swing.JTextPane();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane1 = new SnapTipTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        snedFile_Button = new javax.swing.JButton();
        voice_Button = new javax.swing.JButton();
        video_Button = new javax.swing.JButton();
        moblie_jButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        qqMusic_Button = new javax.swing.JButton();
        qqSpace_jButton = new javax.swing.JButton();
        qqGame_jButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        remoteHlep_Button = new javax.swing.JButton();
        findShare_jButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        intoBlacklist_Button = new javax.swing.JButton();
        deleteFriend_Button = new javax.swing.JButton();
        layoutSet_Button = new javax.swing.JButton();

        jPanel7.setBackground(new java.awt.Color(51, 204, 255));
        jPanel9.setBackground(new java.awt.Color(51, 204, 255));
        personWord_TextPane.setBackground(new java.awt.Color(51, 204, 255));
        personWord_TextPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        
        personWord_TextPane.setText(friend.getPersonWord());
        
        personWord_TextPane.setSelectionColor(new java.awt.Color(102, 204, 255));
        jScrollPane1.setViewportView(personWord_TextPane);

        String headNum=(String)friend.getAttribute("headImage");
        //ImageIcon headIamge=ResourcesManagement.getImageIcon("head/"+headNum+".gif",43,43);
        //headIamge_jLabel.setIcon(headIamge);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(headIamge_jLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
            .addComponent(headIamge_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jToolBar1.setBackground(new java.awt.Color(51, 204, 255));
        jToolBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        font_jButton.setBackground(new java.awt.Color(51, 204, 255));
        
        //Image fontIamge=ResourcesManagement.getImage("icon/font.gif",16,16);
       // font_jButton.setIcon(new javax.swing.ImageIcon(fontIamge));
        font_jButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                font_jButtonMouseClicked(evt);
            }
        });

        jToolBar1.add(font_jButton);

        face_jButton.setBackground(new java.awt.Color(51, 204, 255));
        //Image face_Iamge=ResourcesManagement.getImage("icon/smiley.gif",16,16);
        //face_jButton.setIcon(new javax.swing.ImageIcon(face_Iamge));
        face_jButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                face_jButtonMouseClicked(evt);
            }
        });
        jToolBar1.add(face_jButton);
        shock_Button.setBackground(new java.awt.Color(51, 204, 255));
        //Image shock_Iamge=ResourcesManagement.getImage("icon/zhengdong.jpg",16,16);
        //shock_Button.setIcon(new javax.swing.ImageIcon(shock_Iamge));
        shock_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shock_ButtonMouseClicked(evt);
            }
        });

        jToolBar1.add(shock_Button);

        sendImage_jButton.setBackground(new java.awt.Color(51, 204, 255));
        //Image sends_Iamge=ResourcesManagement.getImage("icon/import_24.gif",16,16);
       // sendImage_jButton.setIcon(new javax.swing.ImageIcon(sends_Iamge));
        sendImage_jButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendImage_jButtonMouseClicked(evt);
            }
        });

        jToolBar1.add(sendImage_jButton);
        //Image fenMusic_Iamge=ResourcesManagement.getImage("icon/mymultimedia.gif",16,16);
        //fenMusic_jLabel.setIcon(new javax.swing.ImageIcon(fenMusic_Iamge));
        jToolBar1.add(fenMusic_jLabel);

        talkRecord_Button.setBackground(new java.awt.Color(51, 204, 255));
        talkRecord_Button.setText("\u804a\u5929\u8bb0\u5f55");
        talkRecord_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                talkRecord_ButtonMouseClicked(evt);
            }
        });

        jToolBar1.add(talkRecord_Button);
        showMsg_jTextPane.setEditable(false);
        jScrollPane3.setViewportView(sendMsg_jTextPane);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(showMsg_jTextPane);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        );

        exit_Button.setText("\u5173\u95ed");
        exit_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exit_ButtonMouseClicked(evt);
            }
        });

        send_Button.setText("\u53d1\u9001");
        send_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                send_ButtonMouseClicked(evt);
            }
        });

        setKey_Button.setText("1");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(296, Short.MAX_VALUE)
                .addComponent(exit_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(send_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(setKey_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(260, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setKey_Button)
                    .addComponent(send_Button)
                    .addComponent(exit_Button)))
        );

        jPanel6.setBackground(new java.awt.Color(51, 204, 255));
        //ImageIcon friendXiuXiuIcon=ResourcesManagement.getImageIcon("xiuxiu/4.gif");
        //friendXiuXiu_jLabel.setIcon(friendXiuXiuIcon);

        //ImageIcon myXiuXiuIcon=ResourcesManagement.getImageIcon("xiuxiu/5.GIF");
        //jLabel4.setIcon(myXiuXiuIcon);
        
        
        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
            .addComponent(friendXiuXiu_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(friendXiuXiu_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
        );
        jTabbedPane2.addTab("\u4e2a\u4eba\u79c0\u79c0", jPanel6);

        jScrollPane4.setViewportView(talkRecord_jTextPane);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );
        jTabbedPane2.addTab("\u804a\u5929\u8bb0\u5f55", jPanel12);

        jPanel5.setBackground(new java.awt.Color(51, 204, 255));
        jTabbedPane1.setBackground(new java.awt.Color(51, 204, 255));
        jPanel1.setBackground(new java.awt.Color(51, 204, 255));
        snedFile_Button.setBackground(new java.awt.Color(51, 204, 255));
        //ImageIcon sendFileIcon= ResourcesManagement.getImageIcon("icon/export_24.gif");
        //snedFile_Button.setIcon(sendFileIcon);
      

        voice_Button.setBackground(new java.awt.Color(51, 204, 255));
        //ImageIcon voiceIcon= ResourcesManagement.getImageIcon("ui/microphone4.png");
        //voice_Button.setIcon(voiceIcon);
        voice_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                voice_ButtonMouseClicked(evt);
            }
        });
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(moblie_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(video_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(voice_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(snedFile_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(432, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(moblie_jButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(video_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(voice_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(snedFile_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jTabbedPane1.addTab("\u804a  \u5929", jPanel1);

        jPanel2.setBackground(new java.awt.Color(51, 204, 255));
        qqMusic_Button.setBackground(new java.awt.Color(51, 204, 255));
        //ImageIcon qqMusicIcon= ResourcesManagement.getImageIcon("ui/Music.png");
        //qqMusic_Button.setIcon(qqMusicIcon);
        
        qqMusic_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qqMusic_ButtonMouseClicked(evt);
            }
        });

        qqSpace_jButton.setBackground(new java.awt.Color(51, 204, 255));
        //ImageIcon qqSpaceIcon= ResourcesManagement.getImageIcon("ui/Home.png");
        //qqSpace_jButton.setIcon(qqSpaceIcon);
        qqSpace_jButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qqSpace_jButtonMouseClicked(evt);
            }
        });

        qqGame_jButton.setBackground(new java.awt.Color(51, 204, 255));
        //ImageIcon qqGameIcon= ResourcesManagement.getImageIcon("ui/Games.png");
       // qqGame_jButton.setIcon(qqGameIcon);
        qqGame_jButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qqGame_jButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(qqGame_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qqSpace_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qqMusic_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(475, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qqGame_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qqSpace_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qqMusic_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("\u5a31  \u4e50", jPanel2);

        jPanel3.setBackground(new java.awt.Color(51, 204, 255));
        remoteHlep_Button.setBackground(new java.awt.Color(51, 204, 255));
        remoteHlep_Button.setText("\u8fd0\u7a0b\u534f\u52a9");
        remoteHlep_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                remoteHlep_ButtonMouseClicked(evt);
            }
        });

        findShare_jButton.setBackground(new java.awt.Color(0, 204, 255));
        findShare_jButton.setText("\u67e5\u770b\u5171\u4eab");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(findShare_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remoteHlep_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(425, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(findShare_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remoteHlep_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jTabbedPane1.addTab("\u5e94  \u7528", jPanel3);

        jPanel4.setBackground(new java.awt.Color(51, 204, 255));
        intoBlacklist_Button.setBackground(new java.awt.Color(51, 204, 255));
        intoBlacklist_Button.setText("\u79fb\u5165\u9ed1\u540d\u5355");

        deleteFriend_Button.setBackground(new java.awt.Color(51, 204, 255));
        deleteFriend_Button.setText("\u5220\u9664\u597d\u53cb");

        layoutSet_Button.setBackground(new java.awt.Color(51, 204, 255));
        layoutSet_Button.setText("\u7248\u9762\u8bbe\u7f6e");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(layoutSet_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteFriend_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(intoBlacklist_Button)
                .addContainerGap(326, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(layoutSet_Button, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addComponent(deleteFriend_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(intoBlacklist_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jTabbedPane1.addTab("\u5de5  \u5177", jPanel4);

        jTabbedPane1.getAccessibleContext().setAccessibleName("tabl1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING)))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
        );
        addWindowListener(new WindowAdapter(){
	    	public void windowClosing(WindowEvent e){
	    	   	setVisible(false);
	    	}});
        
        textFontAttrib.setSize(22);
        textFontAttrib.setColor(new Color(255, 0, 0));
        textFontAttrib.setName("宋体");
        textFontAttrib.setStyle(1);
	    sendMsg_jTextPane.setCharacterAttributes(textFontAttrib.getAttrSet(), true);
	    
	    userInfoFontAttrib.setSize(14);
	    userInfoFontAttrib.setColor(new Color(0, 0, 255));
	    userInfoFontAttrib.setName("宋体");
	    doc=showMsg_jTextPane.getStyledDocument();
	    getContentPane().add(glassBox1, java.awt.BorderLayout.CENTER);
	    this.pack();
    }// </editor-fold>    
    
    
    private void insert(FontAttrib userAttrib,FontAttrib textAttrib) {
    	  try { // 插入文本
    		  doc.insertString(doc.getLength(), userAttrib.getText()+"\r\n", userAttrib.getAttrSet());
    		  doc.insertString(doc.getLength(),    "    "+textAttrib.getText()+"\r\n", textAttrib.getAttrSet());
    	  } catch (BadLocationException e) {
    	   e.printStackTrace();
    	  }
    	 }


	//选择文件还回文件的对象
    private File slecetFile(){
		javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
		fileChooser.showOpenDialog(this);
		File file = fileChooser.getSelectedFile();
		return file;
	}
    
    
    public void setVisible(boolean b){
    	if(b){
    		BaseFrame.centerWindow(this);
    		Cache cache=Cache.getInstance();
    	   	cache.addTalkFrame(friend.getUserID(), this);
    	}else{
    		showMsg_jTextPane.setText("");
    		Cache cache=Cache.getInstance();
    	   	cache.removeTalkFrame(friend.getUserID());
    	   	this.dispose();
    	}
    	super.setVisible(b);
    }
	 
    private void font_jButtonMouseClicked(java.awt.event.MouseEvent evt) {                                          

    }                                         

    private void face_jButtonMouseClicked(java.awt.event.MouseEvent evt) {                                          

    }                                         

    /**
     * 
     * @param 发送震动消息
     */
    private void shock_ButtonMouseClicked(java.awt.event.MouseEvent evt) {
    	if(friend.isOnline()){
    		String srcQQ=owerUser.getUserID();
        	String destQQ=this.friend.getUserID();
        	ShockMsg shockFrameMessage=new ShockMsg(srcQQ,destQQ);
        	//ConnectSession.getInstance().sendTextMessage(shockFrameMessage);
        	//FrameEarthquakeCenter dec = new FrameEarthquakeCenter (this);
        	//dec.startShake();
    	}else{
    		javax.swing.JOptionPane.showMessageDialog(this,"您的好友没在线或者隐身！");
    	}
    	
    }                                         

    private void sendImage_jButtonMouseClicked(java.awt.event.MouseEvent evt) {                                               
// TODO 将在此处添加您的处理代码：
    }                                              

    /**
     * 
     * @param evt
     */
    private void talkRecord_ButtonMouseClicked(java.awt.event.MouseEvent evt) {  
    	
    	
    }                                              

    private void remoteHlep_ButtonMouseClicked(java.awt.event.MouseEvent evt) {                                               
// TODO 将在此处添加您的处理代码：
    }                                              

    private void qqMusic_ButtonMouseClicked(java.awt.event.MouseEvent evt) {                                            
// TODO 将在此处添加您的处理代码：
    }                                           

    private void qqSpace_jButtonMouseClicked(java.awt.event.MouseEvent evt) {                                             
// TODO 将在此处添加您的处理代码：
    }                                            

    private void qqGame_jButtonMouseClicked(java.awt.event.MouseEvent evt) {                                            
// TODO 将在此处添加您的处理代码：
    }                                           

    /**
     * 音频请求按钮
     * @param evt
     */
    private void voice_ButtonMouseClicked(java.awt.event.MouseEvent evt) {                                          
    	System.out.println("音频请求按钮");
    
    }                                         

    /**
     * 传送文件的按钮的事件
     * @param evt
     */
                        

    /**
     * 关闭按钮的激发事件
     * @param evt
     */
    private void exit_ButtonMouseClicked(java.awt.event.MouseEvent evt) {   
    	this.setVisible(false);
    }                                        

    /**
     * 发送按钮的激发事件
     * @param evt
     */
    private void send_ButtonMouseClicked(java.awt.event.MouseEvent evt) { 
    	this.sendTalkMessage();
    }    
    
    public void sendTalkMessage(){
    	String msgStr=sendMsg_jTextPane.getText();
    	if(msgStr.length()==0){
    		javax.swing.JOptionPane.showMessageDialog(this,"聊天信息不能为空!!..");
    	}else{
    		//TalkMessage msg=new TalkMessage();
        	//msg.setDestQQ(friend.getUserID());
        	//msg.setSrcQQ(owerUser.getUserID());
        	//msg.setTalkMsg(msgStr);
        	//ConnectSession connectSession=ConnectSession.getInstance();
        	//connectSession.sendTextMessage(msg);
        	showMsg(owerUser.getUserName(),msgStr);
        	sendMsg_jTextPane.setText("");
    	}
    }
    
    
    public void showMsg(String peak,String msg){
    	SimpleDateFormat  sf   =new SimpleDateFormat("HH:mm:ss");//这里的格式自己按需要写   
		Date d=new Date();   
		String dataString   = sf.format(d);
		userInfoFontAttrib.setText(peak+"  "+dataString);
		System.out.println("textInfo:"+msg);
		textFontAttrib.setText(msg);
		this.insert(userInfoFontAttrib, textFontAttrib);
    }
   

 
    
    
 
	public void run() {
	}
	public boolean isFileSndOrGeting() {
		return isFileSndOrGeting;
	}

	public void setFileSndOrGeting(boolean isFileSndOrGeting) {
		this.isFileSndOrGeting = isFileSndOrGeting;
	}

	public JTabbedPane getJTabbedPane(){
		return jTabbedPane2;
	}
	

    private  FontAttrib userInfoFontAttrib=new FontAttrib();
    private  FontAttrib textFontAttrib=new FontAttrib();
    private  StyledDocument doc = null; // 非常重要插入文字样式就靠它了
    


    private javax.swing.JButton deleteFriend_Button;
    private javax.swing.JButton exit_Button;        //关闭的按钮
    private javax.swing.JButton face_jButton;
    private javax.swing.JLabel fenMusic_jLabel;
    private javax.swing.JButton findShare_jButton;
    private javax.swing.JButton font_jButton;
    private javax.swing.JLabel headIamge_jLabel;
    private javax.swing.JButton intoBlacklist_Button;
    private javax.swing.JLabel friendXiuXiu_jLabel;
    
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private SnapTipTabbedPane jTabbedPane1;
    private SnapTipTabbedPane jTabbedPane2;
    
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton layoutSet_Button;
    private javax.swing.JButton moblie_jButton;
    private javax.swing.JTextPane personWord_TextPane;
    private javax.swing.JButton qqGame_jButton;
    private javax.swing.JButton qqMusic_Button;
    private javax.swing.JButton qqSpace_jButton;
    private javax.swing.JButton remoteHlep_Button;
    private javax.swing.JButton sendImage_jButton;
    
    private javax.swing.JTextPane sendMsg_jTextPane;      //写入聊天信息的TextArea
    
    private javax.swing.JButton send_Button;              //发送信息的按钮
    private javax.swing.JButton setKey_Button;            //设置快捷键的按钮
    private javax.swing.JButton shock_Button;             //发送震动的按钮
    
    public javax.swing.JTextPane showMsg_jTextPane;        //显示聊天信息的TextArea
    
    private javax.swing.JButton snedFile_Button;           //传送文件的按钮
    private javax.swing.JButton talkRecord_Button;
    
    private javax.swing.JTextPane talkRecord_jTextPane;
    
    private javax.swing.JButton video_Button;               //视频请求按钮
    private javax.swing.JButton voice_Button;              // 音频请求按钮
    private GlassBox glassBox1;
    // 变量声明结束      


	public static MsgUser getOwerUser() {
		return owerUser;
	}

	public static void setOwerUser(MsgUser owerUser) {
		TalkFrame.owerUser = owerUser;
	}
   public static void main(String[] args) {
	 new TalkFrame(new MsgUser()).setVisible(true);
}
}
