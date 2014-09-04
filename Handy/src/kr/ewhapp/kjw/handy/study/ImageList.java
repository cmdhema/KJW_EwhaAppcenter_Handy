package kr.ewhapp.kjw.handy.study;

import java.util.ArrayList;

public class ImageList {

	private static ImageList instance;
	private ArrayList<String> imagePathList;
	
	private ImageList() {
		imagePathList = new ArrayList<String>();
	}
	
	public static ImageList getInstance() {
		if ( instance == null )
			instance = new ImageList();
		
		return instance;
	}

	public ArrayList<String> getImagePathList() {
		return imagePathList;
	}
	
	
}
