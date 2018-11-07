package com.chat.server.player.handler.impl;

import com.chat.common.core.constants.ResultCodeEnum;
import com.chat.common.core.exception.ErrorCodeException;
import com.chat.common.core.model.Result;
import com.chat.common.core.session.Session;
import com.chat.common.module.player.request.LoginRequest;
import com.chat.common.module.player.request.RegisterRequest;
import com.chat.common.module.player.response.PlayerResponse;
import com.chat.common.service.PlayerService;
import com.chat.server.player.handler.PlayerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author hzk
 * @date 2018/10/25
 */
@Component
public class PlayerHandlerImpl implements PlayerHandler{

    @Autowired
    private PlayerService playerService;

    @Override
    public Result<PlayerResponse> registerAndLogin(Session session, byte[] data) {
        PlayerResponse playerResponse = null;
        try {
            //反序列化
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.readFromBytes(data);

            ///参数校验
            if(StringUtils.isEmpty(registerRequest.getPlayerName()) || StringUtils.isEmpty(registerRequest.getPassword())){
                return Result.error(ResultCodeEnum.PARAM_ERROR.getResultCode());
            }

            //执行业务
            playerResponse = playerService.registerAndLogin(session, registerRequest.getPlayerName(), registerRequest.getPassword());
        }catch (ErrorCodeException e){
            return Result.error(e.getErrorCode());
        }catch (Exception e){
            return Result.error(ResultCodeEnum.UNKNOWN_EXCEPTION.getResultCode());
        }

        return Result.success(playerResponse);
    }

    @Override
    public Result<PlayerResponse> login(Session session, byte[] data) {
        PlayerResponse playerResponse = null;
        try {
            //反序列化
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.readFromBytes(data);

            ///参数校验
            if(StringUtils.isEmpty(loginRequest.getPlayerName()) || StringUtils.isEmpty(loginRequest.getPassword())){
                return Result.error(ResultCodeEnum.PARAM_ERROR.getResultCode());
            }

            //执行业务
            playerResponse = playerService.login(session, loginRequest.getPlayerName(), loginRequest.getPassword());
        }catch (ErrorCodeException e){
            return Result.error(e.getErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(ResultCodeEnum.UNKNOWN_EXCEPTION.getResultCode());
        }

        return Result.success(playerResponse);
    }
}
