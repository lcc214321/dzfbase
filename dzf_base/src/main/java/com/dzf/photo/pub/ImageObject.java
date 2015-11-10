package com.dzf.photo.pub;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.util.Iterator;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.dzf.pub.BusinessException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;



public class ImageObject implements Externalizable{

	
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		//OutputStreamWriter osw = null;
		in.readLong();
		try {
			ImageObject ioj= decodeStream(in, new DecodeOption(), name);
			this.image=ioj.image;
			this.name=ioj.name;
			this.path=ioj.path;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		
		
		try {
			writeToStream(out, 1.0f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private DecodeOption decodeOption;
	private Image image;
	private String name;
	private String path;
	private String contentType;
	private int[] pixels;
	
	private int width ;
	private int height;
	
	private ImageObject(DecodeOption decodeOption){ 
		width = 0;
		height = 0;
		pixels = null;
		this.decodeOption = decodeOption;
		if(this.decodeOption == null)
			this.decodeOption = new DecodeOption();
	}
	public static ImageObject decode(Image im, String fileExt) {
		//this.decodeOption=new DecodeOption();
		ImageObject imageObj = new ImageObject(new DecodeOption());
		String contentType1 = "image/jpeg";
		if(fileExt.toLowerCase().endsWith("png"))
			contentType1 = "image/png"; 
		imageObj.setContentType(contentType1);
		imageObj.setImage(im);

		return imageObj;
	}
	private void internalDecode(Object o, String contentType) throws Exception{
		this.contentType = contentType;
		Iterator<ImageReader> iter = ImageIO.getImageReadersByMIMEType(contentType);
		ImageReader reader = iter.next();
		ImageInputStream imageStream = ImageIO.createImageInputStream(o);
		reader.setInput(imageStream, true);

		if(decodeOption.InJustDecodeBounds){
			// 赋值宽度和高度，效率比ImageIO.read()快很多
			setWidth(reader.getWidth(0));
			setHeight(reader.getHeight(0));
			setImage(null);
		} else {
			ImageReadParam readParam = reader.getDefaultReadParam();
			// 设置采样密度
			readParam.setSourceSubsampling(decodeOption.InSampleSize, decodeOption.InSampleSize, 0, 0);
			Image img = reader.read(0, readParam);
			setImage(img);
		} 
		// ImageIcon类在读取图片时，使用了缓存技术，同一张图片，第一次读取会有点慢，但是下次读取就会很快
//		Image image = new ImageIcon(path).getImage();
//		Image image =Toolkit.getDefaultToolkit().getImage(path);
//		setImage(reader.read(0));
		imageStream.close();
		reader.dispose();		
	}
	
	private void decode(InputStream inputStream, String fileExt) throws Exception{
		String contentType = "image/jpeg";
		if(fileExt.toLowerCase().endsWith("png"))
			contentType = "image/png"; 
		internalDecode(inputStream, contentType);
	}
	private void decode(ObjectInput inputStream, String fileExt) throws Exception{
		String contentType = "image/jpeg";
		if(fileExt.toLowerCase().endsWith("png"))
			contentType = "image/png"; 
		internalDecode(inputStream, contentType);
	}
	 private void decode(String path) throws Exception{
		File file = new File(path);
        String contentType = new MimetypesFileTypeMap().getContentType(path);  
		if (path.toLowerCase().endsWith(".png")) {  
		    contentType = "image/png";  
		}  
		internalDecode(file, contentType);
		
		setPath(path);
		setName(file.getName());
	}

	/**
	 * 根据文件路径获取图片对象
	 * @param path 图片文件路径
	 * @param decodeOption 解码选项
	 * @return
	 * @throws Exception
	 */
	public static ImageObject decodeFile(String path, DecodeOption decodeOption) throws Exception{
		ImageObject imageObj = new ImageObject(decodeOption);
		imageObj.decode(path);
		return imageObj;
	}
	
	/**
	 * 根据文件路径获取图片对象
	 * @param path 图片文件路径
	 * @return
	 * @throws Exception
	 */
	public static ImageObject decodeFile(String path) throws Exception{
		return decodeFile(path, new DecodeOption());
	}
	
	/**
	 * 根据输入流获取图片对象
	 * @param inputStream
	 * @param decodeOption 解码选项
	 * @return
	 * @throws Exception
	 */
	public static ImageObject decodeStream(InputStream inputStream, DecodeOption decodeOption, String fileExt) throws Exception{
		ImageObject imageObj = new ImageObject(decodeOption);
		imageObj.decode(inputStream, fileExt);
		return imageObj;
	}
	public static ImageObject decodeStream(ObjectInput inputStream, DecodeOption decodeOption, String fileExt) throws Exception{
		ImageObject imageObj = new ImageObject(decodeOption);
		imageObj.decode(inputStream, fileExt);
		return imageObj;
	}
	public static ImageObject decodeStream(InputStream inputStream, String fileExt) throws Exception{
		return decodeStream(inputStream, new DecodeOption(), fileExt);
	}
	
	public BufferedImage getBufferedImage(){
		if(image == null) return null;
		return (BufferedImage) image;
	}
	
	public ImageObject clone(){
		try {
			InputStream inputStream = AsInputStream(1.0f);
			ImageObject cloneObj = new ImageObject(decodeOption);
			cloneObj.internalDecode(inputStream, contentType);
			cloneObj.setName(getName());
			cloneObj.setPath(getPath());
			return cloneObj;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 把当前图片保存为jpg格式，并写入输出流
	 * @param outputStream 输出流
	 * @param quality 压缩比例，取值为0-1,1.0为无损压缩
	 * @throws Exception
	 */
	public void writeToJPEG(OutputStream outputStream, float quality) throws Exception {
		if(decodeOption.InJustDecodeBounds)
			throw new BusinessException("decodeOption.InJustDecodeBounds为true，不允许输出流");
		
		BufferedImage bufferImg = getBufferedImage();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
		JPEGEncodeParam encodeParam = encoder.getDefaultJPEGEncodeParam(bufferImg);
		//setQuality(float quality, boolean forceBaseline), quality取值在 1.0 到 0.0 之间,1.0为无损压缩
		encodeParam.setQuality(quality, false);
		encoder.encode(bufferImg, encodeParam);
	}
	
	/**
	 * 输出到输入流中
	 * @param quality
	 * @return
	 * @throws Exception
	 */
	public InputStream AsInputStream(final float quality) throws Exception{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		writeToStream(outputStream, quality);
		
		return new ByteArrayInputStream(outputStream.toByteArray());
	}
	
	/**
	 * 把当前图片写入输出流
	 * @param outputStream 输出流
	 * @param quality 压缩比例，取值为0-1,1.0为无损压缩
	 * @throws Exception
	 */
	public void writeToStream(OutputStream outputStream, float quality) throws Exception{
		if(decodeOption.InJustDecodeBounds)
			throw new BusinessException("decodeOption.InJustDecodeBounds为true，不允许输出流");
		
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByMIMEType(contentType);
		ImageWriter writer = iter.next();
		ImageOutputStream imageStream = ImageIO.createImageOutputStream(outputStream);
		writer.setOutput(imageStream);
		
		if(writer instanceof JPEGImageWriter){
			ImageWriteParam writeParam = writer.getDefaultWriteParam();
			// 设置压缩模式
			writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			// 将压缩质量设置为 0 和 1 之间的某个值
			writeParam.setCompressionQuality(quality);
			writer.write(null, new IIOImage(getBufferedImage(), null, null), writeParam);
		} else {

			writer.write(null, new IIOImage(getBufferedImage(), null, null), null);
		}
		
		imageStream.flush();
		imageStream.close();
		writer.dispose();
	}
	public void writeToStream(ObjectOutput outputStream, float quality) throws Exception{
		if(decodeOption.InJustDecodeBounds)
			throw new BusinessException("decodeOption.InJustDecodeBounds为true，不允许输出流");
		
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByMIMEType(contentType);
		ImageWriter writer = iter.next();
		ImageOutputStream imageStream = ImageIO.createImageOutputStream(outputStream);
		writer.setOutput(imageStream);
		outputStream.writeLong(1);
		if(writer instanceof JPEGImageWriter){
			ImageWriteParam writeParam = writer.getDefaultWriteParam();
			// 设置压缩模式
			writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			// 将压缩质量设置为 0 和 1 之间的某个值
			writeParam.setCompressionQuality(quality);
			writer.write(null, new IIOImage(getBufferedImage(), null, null), writeParam);
		} else {

			writer.write(null, new IIOImage(getBufferedImage(), null, null), null);
		}
		
		imageStream.flush();
		imageStream.close();
		writer.dispose();
	}
	/**
	 * 把当前图片写入文件
	 * @param file 输出文件
	 * @param quality 压缩比例，取值为0-1,1.0为无损压缩
	 * @throws Exception
	 */
	public void writeToFile(File file, float quality) throws Exception{
		FileOutputStream outputStream = new FileOutputStream(file);
		try {
			writeToStream(outputStream, quality);
		} finally {
			outputStream.flush();
			outputStream.close();
		}
	}
	
	/**
	 * 获取图像像素
	 * @return
	 */
	public int[] getPixels(){
		if(image == null) return null;
		if(pixels == null){
			pixels = new int[getWidth()*getHeight()];
			PixelGrabber pg = new PixelGrabber(image, 0, 0, getWidth(), getHeight(), pixels, 0, getWidth());
			try {
				pg.grabPixels();
			} catch (InterruptedException e){};		
		}
		return pixels;
	}
	
	public DecodeOption getDecodeOption(){
		return decodeOption;
	}
	
	public Image getImage() {
		return image;
	}
	
	void setImage(Image image){
		this.image = image;
		pixels = null;
		if(image != null){
			setWidth(image.getWidth(null));
			setHeight(image.getHeight(null));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getWidth() {
		return width;
	}
	
	void setWidth(int width){
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	
	void setHeight(int height){
		this.height = height;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setDecodeOption(DecodeOption decodeOption) {
		this.decodeOption = decodeOption;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	
}
