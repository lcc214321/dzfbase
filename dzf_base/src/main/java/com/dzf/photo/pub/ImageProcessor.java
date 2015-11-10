package com.dzf.photo.pub;

import com.dzf.pub.BusinessException;


/**
 * 图片处理接口
 *
 */
public interface ImageProcessor {
	/**
	 * 处理图片
	 */
	public void ProcessImage(ImageObject image) throws BusinessException;
	
}
