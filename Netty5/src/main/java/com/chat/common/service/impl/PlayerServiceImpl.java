package com.chat.common.service.impl;

import com.chat.common.core.constants.ResultCodeEnum;
import com.chat.common.core.exception.ErrorCodeException;
import com.chat.common.core.session.Session;
import com.chat.common.core.session.SessionManager;
import com.chat.common.entity.Player;
import com.chat.common.entity.PlayerExample;
import com.chat.common.module.player.response.PlayerResponse;
import com.chat.common.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户服务
 * @author hzk
 * @date 2018/10/24
 */
@Component
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private com.chat.common.dao.read.PlayerMapper playerMapper_r;

    @Autowired
    private com.chat.common.dao.write.PlayerMapper playerMapper_w;

    @Override
    public PlayerResponse registerAndLogin(Session session, String playerName, String password) {
        PlayerExample playerExample = new PlayerExample();
        playerExample.createCriteria().andNameEqualTo(playerName);
        int exist = playerMapper_r.countByExample(playerExample);
        if(exist > 0){
            //注册用户名已存在
            throw new ErrorCodeException(ResultCodeEnum.PLAYER_EXIST.getResultCode());
        }

        Player player = new Player();
        player.setName(playerName);
        player.setPassword(password);
        player.setLevel(1);
        player.setExp(0);
        int registerFlag = playerMapper_w.insertSelective(player);
        if(registerFlag <= 0){
            //注册失败
            throw new ErrorCodeException(ResultCodeEnum.UNKNOWN_EXCEPTION.getResultCode());
        }

        return login(session,playerName,password);
    }

    @Override
    public PlayerResponse login(Session session, String playerName, String password) {
        
        //检测当前会话是否已经登录
        if(session.getAttachment() != null){
            throw new ErrorCodeException(ResultCodeEnum.ALREADY_LOGIN.getResultCode());
        }

        //检测用户是否存在
        PlayerExample playerExample = new PlayerExample();
        playerExample.createCriteria().andNameEqualTo(playerName);
        List<Player> players = playerMapper_r.selectByExample(playerExample);
        if(null == players || players.isEmpty()){
            throw new ErrorCodeException(ResultCodeEnum.PLAYER_NO_EXIST.getResultCode());
        }

        //检测账户密码匹配
        Player player = players.get(0);
        if(!password.equals(player.getPassword())){
            throw new ErrorCodeException(ResultCodeEnum.PASSWORD_ERROR.getResultCode());
        }

        //检测是否在其他地方已经登录
        boolean onlinePlayer = SessionManager.isOnlinePlayer(player.getId());
        if(onlinePlayer){
            Session oldSession = SessionManager.removeSession(player.getId());
            oldSession.removeAttachment();
            //踢下线
            oldSession.close();
        }

        //加入在线玩家会话
        if(SessionManager.putSession(player.getId(),session)){
            session.setAttachment(player);
        }else{
            throw new ErrorCodeException(ResultCodeEnum.LOGIN_FAIL.getResultCode());
        }

        //创建Response传输对象返回
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setPlayerId(player.getId());
        playerResponse.setPlayerName(player.getName());
        playerResponse.setLevel(player.getLevel());
        playerResponse.setExp(player.getExp());
        return playerResponse;
    }
}