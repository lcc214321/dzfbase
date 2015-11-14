package com.dzf.framework.rsa;

/**
 * ���ߣ���ΰ
 * ����DES�㷨��������ݽ��м���
 */
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;



public class DESInputStream extends java.io.InputStream
{
	/** DES ���ܶ��� */
	private DES des = null;
	/** һ�ζ������ */
	private byte[] p = new byte[8];
	/** InputStream�ֽ����Ķ��� */
	private InputStream in;
	/** ��ʼ��ʱ��Ҫ���¶��� */
	private int loc = 9;
	/** ��β��� */
	public static final byte  ENDEDCODE =(byte)100;
	/** �ڱ任������ֽں� */
	private boolean changMeaning=false;
	/** ��8 ֱ�ӿ��� */
	private boolean inBlock=false;
/**
 * ����desSet�������м���
 *
 * 
 */
public DESInputStream(InputStream inSet,DES desSet)
{
	super();
	des=desSet;
	in=inSet;
}
/**
 * �رձ���
 */
public void close()throws IOException
{
	super.close();
	in.close();
}
/**
 * ����������Э������ֽ�
 * read ����ע�͡�
 */
public int read() throws java.io.IOException {
	while (true) {
		if (loc >= 8) {
			//////////////////////
			//
			//	�����ֽڣ����ܣ�
			///////////////////////
			int len = in.read(p);
			if (len == -1)
				throw new EOFException("�ļ�ĩβ");
			for (; len < 8; len++)
				p[len] = (byte) in.read();
			long s = des.bytes2long(p);
			long l = des.decrypt(s);
			des.long2bytes(l, p);
			loc = 0;
		}
		//////////////////////
		//
		//	�ж��Ƿ��Ǳ任������ַ�100��
		//	�����0��ʾ���������Ҫ�׳��쳣����
		//  read(byte[],int,int)����
		///////////////////////
		if (changMeaning) {
			changMeaning = false;
			if (p[loc] == 0) {
				loc = 8;
				if (inBlock) {
					throw new IOException("END OF THIS BLOCK");
				}
				else
					continue;
			}
			break;
		}
		else
			if (p[loc] == ENDEDCODE) {
				changMeaning = true;
				loc++;
				continue;
			}
			else
				break;
	}
	byte pb = p[loc++];
	int pi = pb & 0xff;
	return pi;
}
public int read(byte b[]) throws IOException
{
	return read(b, 0, b.length);
}
/**
 *
 *	@see java.io.InputStream
 *
 */
public int read(byte b[], int off, int len) throws IOException {
	if (len <= 0) {
		return 0;
	}
	int c = read();
	if (c == -1) {
		return -1;
	}
	inBlock = true;
	b[off] = (byte) c;
	int i = 1;
	try {
		for (; i < len; i++) {
			c = read();
			if (c == -1) {
				break;
			}
			if (b != null) {
				b[off + i] = (byte) c;
			}
		}
	}
	catch (IOException ee) {
	}
	inBlock = false;
	return i;
}
}