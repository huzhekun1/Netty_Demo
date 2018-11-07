package com.customSerializable.entity;

import com.customSerializable.core.AbstractSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hzk
 * @date 2018/9/27
 */
public class Player extends AbstractSerializable {

    private Long playerId;
    private Integer age;
    private List<Integer> skills = new ArrayList<>();
    private Resource resource = new Resource();

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
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

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    protected void read() {
        playerId = readLong();
        age = readInt();
        skills = readList(Integer.class);
        resource = readObject(Resource.class);
    }

    @Override
    protected void write() {
        writeLong(playerId);
        writeInt(age);
        writeList(skills);
        writeObject(resource);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", age=" + age +
                ", skills=" + skills +
                ", resource=" + resource +
                '}';
    }
}
