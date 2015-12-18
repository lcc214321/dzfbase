package com.dzf.pub.cache;

import java.io.IOException;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;


public interface IDzfSerializable<T> {
void setSerializable(T svo, NetObjectOutputStream nos)throws IOException;
T getSerializable(NetObjectInputStream nos)throws IOException;
}
