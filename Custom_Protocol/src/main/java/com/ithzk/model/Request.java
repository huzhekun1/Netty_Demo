package com.ithzk.model;

/**
 * 请求对象
 * @author hzk
 * @date 2018/9/29
 */
public class Request {

    /**
     * 请求模块
     */
    private short module;

    /**
     * 请求命令号
     */
    private short cmd;

    /**
     * 数据部分
     */
    private byte[] data;

    public Request(short module, short cmd, byte[] data) {
        this.module = module;
        this.cmd = cmd;
        this.data = data;
    }

    public short getModule() {
        return module;
    }

    public void setModule(short module) {
        this.module = module;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getDataLength(){
        if(null == data){
            return 0;
        }
        return data.length;
    }

}
