package com.dzf.photo.pub;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.dzf.pub.BusinessException;


/**
 * 图片压缩处理器
 * @author Administrator
 *
 */
public class ImageCompressProcessor implements ImageProcessor {
	private int width;
	private int height;
	
	public ImageCompressProcessor(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void ProcessImage(ImageObject image) throws BusinessException{
		if(image.getDecodeOption().InJustDecodeBounds)
			throw new BusinessException("对象ImageObject的decodeOption.InJustDecodeBounds为true，不允许处理图片");
		
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();  
		Image scaleImg = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		g.drawImage(scaleImg,  0, 0, width, height, null);  
	    g.dispose();  
	    image.getImage().flush();
	    image.setImage(buffImg);
	}

}
