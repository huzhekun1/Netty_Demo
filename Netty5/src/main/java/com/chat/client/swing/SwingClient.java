package com.chat.client.swing;

import com.chat.client.boot.Client;
import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Request;
import com.chat.common.module.chat.request.PrivateChatRequest;
import com.chat.common.module.chat.request.PublicChatRequest;
import com.chat.common.module.player.request.LoginRequest;
import com.chat.common.module.player.request.RegisterRequest;
import com.chat.common.module.player.response.PlayerResponse;
import com.chat.common.core.constants.SwingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * swing客户端
 * @author hzk
 * @date 2018/10/25
 */
@Component
public class SwingClient extends JFrame implements ActionListener{

    @Autowired
    private Client client;

    /**
     * 用户信息
     */
    private PlayerResponse playerResponse;

    /**
     * 用户名
     */
    private JTextField playerName;

    /**
     * 密码
     */
    private JTextField password;

    /**
     * 登录按钮
     */
    private JButton loginButton;

    /**
     * 注册按钮
     */
    private JButton registerButton;

    /**
     * 聊天内容
     */
    private JTextArea chatContent;

    /**
     * 发送消息
     */
    private JTextField message;

    /**
     * 目标用户
     */
    private JTextField targetPlayer;

    /**
     * 发送按钮
     */
    private JButton sendButton;

    /**
     * 操作提示
     */
    private JLabel hints;

    public SwingClient(){
        getContentPane().setLayout(null);
        //登录部分
        JLabel playerNameLab = new JLabel("用户名");
        playerNameLab.setFont(new Font("宋体", Font.PLAIN,12));
        playerNameLab.setBounds(76,40,54,15);
        getContentPane().add(playerNameLab);

        playerName = new JTextField();
        playerName.setBounds(139,37,154,21);
        getContentPane().add(playerName);
        playerName.setColumns(10);

        JLabel passwordLab = new JLabel("密  码");
        passwordLab.setFont(new Font("宋体", Font.PLAIN, 12));
        passwordLab.setBounds(76, 71, 54, 15);
        getContentPane().add(passwordLab);

        password = new JTextField();
        password.setColumns(10);
        password.setBounds(139, 68, 154, 21);
        getContentPane().add(password);

        //登录
        loginButton = new JButton("登录");
        loginButton.setFont(new Font("宋体", Font.PLAIN, 12));
        loginButton.setActionCommand(SwingConstants.AbstractButtonCommand.LOGIN);
        loginButton.addActionListener(this);
        loginButton.setBounds(315, 37, 93, 23);
        getContentPane().add(loginButton);

        //注册
        registerButton = new JButton("注册");
        registerButton.setFont(new Font("宋体", Font.PLAIN, 12));
        registerButton.setActionCommand(SwingConstants.AbstractButtonCommand.REGISTER);
        registerButton.addActionListener(this);
        registerButton.setBounds(315, 67, 93, 23);
        getContentPane().add(registerButton);

        //聊天内容框
        chatContent = new JTextArea();
        chatContent.setLineWrap(true);

        JScrollPane scrollBar = new JScrollPane(chatContent);
        scrollBar.setBounds(76, 96, 93, 403);
        scrollBar.setSize(336, 300);
        getContentPane().add(scrollBar);


        //发送消息部分
        JLabel targetLab = new JLabel("私聊用户");
        targetLab.setFont(new Font("宋体", Font.PLAIN, 12));
        targetLab.setBounds(76, 436, 63, 24);
        getContentPane().add(targetLab);

        targetPlayer = new JTextField();
        targetPlayer.setBounds(139, 438, 133, 21);
        getContentPane().add(targetPlayer);
        targetPlayer.setColumns(10);

        JLabel messageLab = new JLabel("消息");
        messageLab.setFont(new Font("宋体", Font.PLAIN, 12));
        messageLab.setBounds(76, 411, 54, 15);
        getContentPane().add(messageLab);

        message = new JTextField();
        message.setBounds(139, 408, 222, 21);
        getContentPane().add(message);
        message.setColumns(10);

        sendButton = new JButton("发送");
        sendButton.setFont(new Font("宋体", Font.PLAIN, 12));
        sendButton.setBounds(382, 407, 67, 23);
        sendButton.setActionCommand(SwingConstants.AbstractButtonCommand.SEND);
        sendButton.addActionListener(this);
        getContentPane().add(sendButton);

        //错误提示区域
        hints = new JLabel();
        hints.setForeground(Color.red);
        hints.setFont(new Font("宋体", Font.PLAIN, 14));
        hints.setBounds(76, 488, 200, 15);
        getContentPane().add(hints);


        int weigh = 500;
        int high = 600;
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - weigh) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - high) / 2;
        this.setLocation(w, h);
        this.setTitle("Try Chat");
        this.setSize(weigh, high);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()){
            //登录
            case SwingConstants.AbstractButtonCommand.LOGIN:
                try {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setPlayerName(playerName.getText());
                    loginRequest.setPassword(password.getText());
                    //构建请求
                    Request request = Request.valueOf(Constants.AbstractModule.PLAYER,Constants.AbstractCmdPlayer.LOGIN,loginRequest.getBytes());
                    client.sendMessage(request);
                }catch (Exception e){
                    e.printStackTrace();
                    hints.setText("无法连接服务器");
                }
                break;
            //注册
            case SwingConstants.AbstractButtonCommand.REGISTER:
                try {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setPlayerName(playerName.getText());
                    registerRequest.setPassword(password.getText());
                    //构建请求
                    Request request = Request.valueOf(Constants.AbstractModule.PLAYER,Constants.AbstractCmdPlayer.REGISTER_AND_LOGIN,registerRequest.getBytes());
                    client.sendMessage(request);
                }catch (Exception e){
                    e.printStackTrace();
                    hints.setText("无法连接服务器");
                }
                break;
            //发送消息
            case SwingConstants.AbstractButtonCommand.SEND:
                try {
                    if(StringUtils.isEmpty(targetPlayer.getText()) && !StringUtils.isEmpty(message.getText())){
                        PublicChatRequest publicChatRequest = new PublicChatRequest();
                        publicChatRequest.setContent(message.getText());
                        //构建请求
                        Request request = Request.valueOf(Constants.AbstractModule.CHAT,Constants.AbstractCmdChat.PUBLIC_CHAT,publicChatRequest.getBytes());
                        client.sendMessage(request);
                    }else{
                        if(StringUtils.isEmpty(message.getText())){
                            hints.setText("发送内容不能为空！");
                            return;
                        }

                        long playerId = 0;
                        try {
                            playerId = Long.parseLong(targetPlayer.getText());
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                            hints.setText("用户ID为数字!");
                            return;
                        }

                        PrivateChatRequest privateChatRequest = new PrivateChatRequest();
                        privateChatRequest.setContent(message.getText());
                        privateChatRequest.setTargetPlayerId(playerId);
                        //构建请求
                        Request request = Request.valueOf(Constants.AbstractModule.CHAT,Constants.AbstractCmdChat.PRIVATE_CHAT,privateChatRequest.getBytes());
                        client.sendMessage(request);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    hints.setText("无法连接服务器");
                }
            default:
                break;
        }
    }

    @Override
    protected void processWindowStateEvent(WindowEvent e) {
        if(WindowEvent.WINDOW_CLOSING == e.getID()){
            client.shutdown();
        }
        super.processWindowStateEvent(e);
    }

    public PlayerResponse getPlayerResponse() {
        return playerResponse;
    }

    public void setPlayerResponse(PlayerResponse playerResponse) {
        this.playerResponse = playerResponse;
    }

    public JTextArea getChatContent() {
        return chatContent;
    }

    public JLabel getHints() {
        return hints;
    }

}
