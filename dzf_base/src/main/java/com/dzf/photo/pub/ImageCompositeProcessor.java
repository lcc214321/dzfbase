package com.dzf.photo.pub;

import java.util.ArrayList;

import com.dzf.pub.BusinessException;


public class ImageCompositeProcessor implements ImageProcessor {
	private ArrayList<ImageProcessor> processors = new ArrayList<ImageProcessor>();
	
	public void add(ImageProcessor processor){
		processors.add(processor);
	}
	
	public void remove(ImageProcessor processor){
		if(processors.contains(processor))
			processors.remove(processor);
	}
	
	public void clear(){
		processors.clear();
	}
	
	public void ProcessImage(ImageObject image) throws BusinessException{
		for(ImageProcessor processor: processors){
			processor.ProcessImage(image);
		}
	}

}
