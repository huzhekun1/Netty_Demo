package com.chat.common.core.serializable;

import org.jboss.netty.buffer.ChannelBuffer;

import java.nio.charset.Charset;
import java.util.*;

/**
 * 自定义序列化
 * @author hzk
 * @date 2018/9/26
 */
public abstract class Serializable {

    public static final Charset CHARSET = Charset.forName("UTF-8");

    protected ChannelBuffer writeBuffer;

    protected ChannelBuffer readBuffer;

    /**
     * 反序列化具体实现
     */
    protected abstract void read();

    /**
     * 序列化具体实现
     */
    protected abstract void write();

    /**
     * 从bytes数组读取数据
     * @param bytes
     * @return
     */
    public Serializable readFromBytes(byte[] bytes){
        readBuffer = BufferFactory.getBuffer(bytes);
        read();
        readBuffer.clear();
        return this;
    }

    /**
     * 从channelBuffer读取数据
     * @param channelBuffer
     */
    public void readFromBuffer(ChannelBuffer channelBuffer){
        this.readBuffer = channelBuffer;
        read();
    }

    /**
     * 写入到本地channelBuffer
     * @return
     */
    public ChannelBuffer writeToLocalBuffer(){
        this.writeBuffer = BufferFactory.getBuffer();
        write();
        return writeBuffer;
    }

    /**
     * 写入到目标channelBuffer
     * @param channelBuffer
     * @return
     */
    public ChannelBuffer writeToTargetBuffer(ChannelBuffer channelBuffer){
        this.writeBuffer = channelBuffer;
        write();
        return writeBuffer;
    }

    public Serializable writeByte(Byte value){
        writeBuffer.writeByte(value);
        return this;
    }

    public Serializable writeInt(int value){
        writeBuffer.writeInt(value);
        return this;
    }

    public Serializable writeShort(short value){
        writeBuffer.writeShort(value);
        return this;
    }

    public Serializable writeLong(long value){
        writeBuffer.writeLong(value);
        return this;
    }

    public Serializable writeFloat(float value){
        writeBuffer.writeFloat(value);
        return this;
    }

    public Serializable writeDouble(double value){
        writeBuffer.writeDouble(value);
        return this;
    }

    public Serializable writeString(String value){
        if(null == value || value.isEmpty()){
            writeShort((short)0);
            return this;
        }
        byte[] bytes = value.getBytes(CHARSET);
        short size = (short) bytes.length;
        writeBuffer.writeShort(size);
        writeBuffer.writeBytes(bytes);
        return this;
    }

    public Serializable writeObject(Object object){
        if(null == object){
            writeByte((byte)0);
        }else{
            if(object instanceof Integer){
                writeInt((int)object);
            }else if(object instanceof Short){
                writeShort((short)object);
            }else if(object instanceof Byte){
                writeByte((byte)object);
            }else if(object instanceof Long){
                writeLong((long)object);
            }else if(object instanceof Float){
                writeFloat((float)object);
            }else if(object instanceof Double){
                writeDouble((double)object);
            }else if(object instanceof String){
                writeString((String) object);
            }else if(object instanceof Serializable){
                writeByte((byte)1);
                Serializable serializable = (Serializable) object;
                serializable.writeToTargetBuffer(writeBuffer);
            }else{
                throw new RuntimeException("不可序列化类型:[%s]"+object.getClass());
            }
        }
        return this;
    }

    public <T> Serializable writeList(List<T> list){
        if(isEmpty(list)){
            writeBuffer.writeShort((short)0);
            return this;
        }
        writeBuffer.writeShort((short)list.size());
        for(T t:list){
            writeObject(t);
        }
        return this;
    }

    public <K,V> Serializable writeMap(Map<K,V> map){
        if(isEmpty(map)){
            writeBuffer.writeShort((short)0);
            return this;
        }
        writeBuffer.writeShort((short)map.size());
        for (Map.Entry<K,V> entry:map.entrySet()) {
            writeObject(entry.getKey());
            writeObject(entry.getValue());
        }
        return this;
    }


    /**
     * 返回byte数组
     * @return
     */
    public byte[] getBytes(){
        writeToLocalBuffer();
        byte[] bytes = null;
        if(writeBuffer.writerIndex() == 0){
            bytes = new byte[0];
        }else{
            bytes = new byte[writeBuffer.writerIndex()];
            writeBuffer.readBytes(bytes);
        }
        writeBuffer.clear();
        return bytes;
    }

    public byte readByte(){
        return readBuffer.readByte();
    }

    public short readShort(){
        return readBuffer.readShort();
    }

    public int readInt(){
        return readBuffer.readInt();
    }

    public long readLong(){
        return readBuffer.readLong();
    }

    public float readFloat(){
        return readBuffer.readFloat();
    }

    public double readDouble(){
        return readBuffer.readDouble();
    }

    public String readString(){
        short size = readBuffer.readShort();
        if(size <= 0){
            return "";
        }
        byte[] bytes = new byte[size];
        readBuffer.readBytes(bytes);
        return new String(bytes,CHARSET);
    }

    public <K> K readObject(Class<K> clz){
        Object k = null;
        if(clz == int.class || clz == Integer.class){
            k = readInt();
        }else if(clz == byte.class || clz == Byte.class){
            k = readByte();
        }else if(clz == short.class || clz == Short.class){
            k = readShort();
        }else if(clz == long.class || clz == Long.class){
            k = readLong();
        }else if(clz == float.class || clz == Float.class){
            k = readFloat();
        }else if(clz == double.class || clz == Double.class){
            k = readDouble();
        }else if(clz == String.class){
            k = readString();
        }else if(Serializable.class.isAssignableFrom(clz)){
            try {
                byte hasObject = readBuffer.readByte();
                if(hasObject == 1){
                    Serializable temp = (Serializable) clz.newInstance();
                    temp.readFromBuffer(readBuffer);
                    k = temp;
                }else{
                    k = null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            throw new RuntimeException(String.format("不支持类型:[%s]",clz));
        }
        return (K)k;
    }

    public <T> List<T> readList(Class<T> clz){
        ArrayList<T> list = new ArrayList<>();
        short size = readBuffer.readShort();
        for(int i=0;i<size;i++){
            list.add(readObject(clz));
        }
        return list;
    }

    public <K,V> Map<K,V> readMap(Class<K> keyClz,Class<V> valueClz){
        HashMap<K, V> map = new HashMap<>();
        short size = readBuffer.readShort();
        for (int i =0;i<size;i++){
            K key = readObject(keyClz);
            V value = readObject(valueClz);
            map.put(key,value);
        }
        return map;
    }

    private <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.isEmpty();
    }
    public <K,V> boolean isEmpty(Map<K,V> c) {
        return c == null || c.isEmpty();
    }
}
