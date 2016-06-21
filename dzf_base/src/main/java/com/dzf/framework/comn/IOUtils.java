package com.dzf.framework.comn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.dzf.pub.DZFWarpException;
import com.dzf.pub.StringUtil;
import com.dzf.pub.SuperVO;
import com.dzf.pub.WiseRunException;
import com.dzf.pub.cache.IDzfSerializable;

public class IOUtils {

	public IOUtils() {
		// TODO Auto-generated constructor stub
	}

	public static byte[] getBytes(String[] obj) throws DZFWarpException {
		ByteArrayOutputStream bout = null;
		NetObjectOutputStream nos = null;

		try {
			bout = new ByteArrayOutputStream();
			nos = new NetObjectOutputStream(bout);

			int len = obj == null ? 0 : obj.length;
			nos.write(len);

			for (int i = 0; i < len; i++) {
				if (StringUtil.isEmptyWithTrim(obj[i])) {
					nos.writeByte(0);
					// nos.write(obj[i].getBytes());
				} else {
					nos.writeByte(obj[i].length());
					nos.write(obj[i].getBytes());
				}
			}
			nos.flush();
			bout.flush();
			return bout.toByteArray();
		} catch (Exception e) {
			throw new WiseRunException(e);
		} finally {
			if (bout != null) {
				try {
					bout.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}
			if (nos != null) {
				try {
					nos.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}

		}

	}

	public static byte[] getBytes(Object obj) throws DZFWarpException {
		ByteArrayOutputStream bout = null;
		NetObjectOutputStream nos = null;
		try {
			bout = new ByteArrayOutputStream();
			nos = new NetObjectOutputStream(bout);

			nos.writeObject(obj);
			nos.flush();
			bout.flush();
			return bout.toByteArray();
			// int len=obj==null?0:obj.length;
			// nos.write(len);
			// for(int i=0;i<len;i++){
			// nos.writeUTF(obj[i]);
			// }
			// nos.writeObject(obj);
		} catch (Exception e) {
			throw new WiseRunException(e);
		} finally {
			if (bout != null) {
				try {
					bout.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}
			if (nos != null) {
				try {
					nos.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}
		}

	}

	public static byte[] getBytes(SuperVO svo, IDzfSerializable iser) throws DZFWarpException {
		ByteArrayOutputStream bout = null;
		NetObjectOutputStream nos = null;
		try {
			bout = new ByteArrayOutputStream();
			nos = new NetObjectOutputStream(bout);

			iser.setSerializable(svo, nos);
			nos.flush();
			bout.flush();
			return bout.toByteArray();
		} catch (Exception e) {
			throw new WiseRunException(e);
		} finally {
			if (bout != null) {
				try {
					bout.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}
			if (nos != null) {
				try {
					nos.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}
		}

	}

	public static SuperVO getObject(byte[] bs, IDzfSerializable iser) throws DZFWarpException {
		ByteArrayInputStream bin = null;
		NetObjectInputStream is = null;

		try {
			bin = new ByteArrayInputStream(bs);
			is = new NetObjectInputStream(bin);
			SuperVO svo = (SuperVO) iser.getSerializable(is);
			return svo;
		} catch (Exception e) {
			throw new WiseRunException(e);
		} finally {
			if (bin != null) {
				try {
					bin.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}
		}

	}

	public static String[] getObject(byte[] bs) throws DZFWarpException {
		ByteArrayInputStream bin = null;
		NetObjectInputStream is = null;
		try {
			bin = new ByteArrayInputStream(bs);
			is = new NetObjectInputStream(bin);

			int len = is.read();
			String[] strs = new String[len];
			int len1 = 0;
			byte[] bs1 = null;
			for (int i = 0; i < len; i++) {
				len1 = is.readByte();
				if (len1 > 0) {
					bs1 = new byte[len1];
					is.read(bs1);
					strs[i] = new String(bs1);// is.readUTF();
				}
			}
			return strs;
		} catch (Exception e) {
			throw new WiseRunException(e);
		} finally {
			if (bin != null) {
				try {
					bin.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					throw new WiseRunException(e);
				}
			}
		}

	}

}
