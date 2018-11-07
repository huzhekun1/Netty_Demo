package com.chat.common.dao.write;

import com.chat.common.entity.Player;
import com.chat.common.entity.PlayerExample;
import org.apache.ibatis.annotations.Param;

public interface PlayerMapper {

    int deleteByExample(PlayerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Player record);

    int insertSelective(Player record);

    int updateByExampleSelective(@Param("record") Player record, @Param("example") PlayerExample example);

    int updateByExample(@Param("record") Player record, @Param("example") PlayerExample example);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);
}