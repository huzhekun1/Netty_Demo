package com.chat.common.dao.read;

import com.chat.common.entity.Player;
import com.chat.common.entity.PlayerExample;

import java.util.List;

public interface PlayerMapper {

    int countByExample(PlayerExample example);

    List<Player> selectByExample(PlayerExample example);

    Player selectByPrimaryKey(Long id);

}