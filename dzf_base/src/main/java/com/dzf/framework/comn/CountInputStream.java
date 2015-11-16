/*
 * User: �ι��� 
 *
 * Date: 2006-4-1
 * Time: 13:55:15
 */
package com.dzf.framework.comn;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author �ι���
 *
 * Date: 2006-4-1
 * Time: 13:55:39
 */
public class CountInputStream extends FilterInputStream {

    long count;


    public CountInputStream(InputStream input) {
        super(input);
    }

    public int read() throws IOException {
        int value = super.read();
        count++;
        return value;
    }
    
    public int read(byte[] b, int off, int len) throws IOException {
        int readLen = super.read(b, off, len);
        count += readLen;
        return readLen;
    }

    public long getCount() {
        return count;
    }

}