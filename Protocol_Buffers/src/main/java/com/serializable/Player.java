package com.serializable;

import java.io.Serializable;
import java.util.List;

/**
 * java序列化对象
 * @author hzk
 * @date 2018/9/20
 */
public class Player implements Serializable{

    private static final long serialVersionUID = -1602899989398586421L;

    private Integer playerId;
    private String name;
    private Integer age;
    private List<Integer> skills;

    public Player(Integer playerId, String name, Integer age,List<Integer> skills) {
        this.playerId = playerId;
        this.name = name;
        this.age = age;
        this.skills = skills;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }
}
