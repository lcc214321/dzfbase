package com.dzf.framework.comn;

import java.io.IOException;
import java.io.OutputStream;

import com.dzf.framework.rsa.DES;

/**
 * 
 * @author �ι���
 *
 * Date: 2006-4-1
 * Time: 18:12:23
 * 
 * 100 0 ��ʾһ��������
 */
public class FastDESOutputStream extends OutputStream  {
    private DES des = null;

    private OutputStream out;

    private byte p[] = new byte[8];

    private int loc = 0;

    private boolean finished = false;

    public FastDESOutputStream(OutputStream outSet, DES desSet) {
        des = desSet;
        out = outSet;
    }

    public void close() throws IOException {
        finish();
        out.close();
    }

    public void flush() throws IOException {
        if (loc == 8) {
            writeOUT();
        }
        out.flush();
    }

    public void finish() throws IOException {
        if (!finished) {
            if (loc < 8) {
                p[loc++] = NetStreamConstants.ENDEDCODE;
                while (loc < 8) {
                    p[loc++] = 0;
                }
            }

            flush();
            finished = true;
        }

    }

    public void write(int b) throws java.io.IOException {
        checkFinished();
        if (loc == 8) {
            writeOUT();
        }

        if (b == NetStreamConstants.ENDEDCODE) {
            p[loc++] = (byte) (b & 0xff);
            if (loc == 8)
                writeOUT();
            p[loc++] = (byte) (b & 0xff);
        } else {
            p[loc++] = (byte) (b & 0xff);
        }

    }

    private void writeOUT() throws IOException {
        long l = des.encrypt(des.bytes2long(p));
        des.long2bytes(l, p);
        out.write(p);
        loc = 0;
    }

    public boolean finished() {
        return finished;
    }

    private void checkFinished() throws IOException {
        if (finished)
            throw new IOException("DES Output finished");

    }

}
