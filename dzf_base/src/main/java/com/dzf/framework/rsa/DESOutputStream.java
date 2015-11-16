package com.dzf.framework.rsa;

/**
 * 100 0 ��ʾһ��������
 * 100 100��ʾ 100
 */
import java.io.IOException;
import java.io.OutputStream;


public class DESOutputStream extends java.io.OutputStream
{
	private DES des = null;
	private OutputStream out;
	private byte p[] = new byte[8];
	private int loc = 0;
/**
 * DESStream ������
 * @param outSet OutputStream	�����
 * @param desSet DES 	���ܶ���
 */
public DESOutputStream(OutputStream outSet, DES desSet) {
	super();
	des = desSet;
	out = outSet;
}
/**
 * �ر�
 */
public void close() throws IOException
{
	writeOUT();
	out.close();
}
/**
 * ��������  ��ΰ ����
 * ����ʱ�䣺
 * ��ɵĹ��ܣ�????
 * �޸ļ�¼��   100 0 ��ʾһ��������
 */
public void flush() throws IOException {
	p[loc++] = 100;
	if (loc == 8) {
		writeOUT();
	}
	p[loc++] = 0;
	writeOUT();
}
/**
 * д��һ���ֽ�
 * �Ա任����ֱ�ӽ��д���
 * 100���ֵĸ�����Ե�һЩ
 * write ����ע�͡�
 */
public void write(int b) throws java.io.IOException {
	if (b == DESInputStream.ENDEDCODE) {
		p[loc++] = (byte) (b & 0xff);
		if (loc == 8)
			writeOUT();
		p[loc++] = (byte) (b & 0xff);
	}
	else
		p[loc++] = (byte) (b & 0xff);
	if (loc == 8) {
		writeOUT();
		return;
	}
}
/**
 * ��8���ֽڽ��м���д��
 */
public void writeOUT() throws IOException
{
	if (loc < 8)
		p[loc++] = DESInputStream.ENDEDCODE;
	long l = des.encrypt(des.bytes2long(p));
	des.long2bytes(l, p);
	out.write(p);
	loc = 0;
	out.flush();
}
}