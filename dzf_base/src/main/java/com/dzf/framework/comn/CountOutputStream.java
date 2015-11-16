/*
 * User: �ι��� 
 *
 * Date: 2006-4-1
 * Time: 15:40:06
 */
package com.dzf.framework.comn;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * @author �ι���
 *
 * Date: 2006-4-1
 * Time: 17:13:30
 */
public class CountOutputStream extends FilterOutputStream {

    long count;

    public CountOutputStream(OutputStream output) {
        super(output);
       
    }

    public void write(int b) throws IOException {
        count++;
        super.write(b);
    }

    public long getCount() {
        return count;
    }

    public void write(byte[] b, int offset, int len) throws IOException {
        count += len;
        out.write(b, offset, len);
    }


    
}