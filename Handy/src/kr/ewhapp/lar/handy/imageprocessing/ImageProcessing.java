package kr.ewhapp.lar.handy.imageprocessing;

import java.util.ArrayList;

import kr.ewhapp.kjw.handy.study.ImageList;
import kr.ewhapp.kjw.handy.study.StudyModel;
import android.os.AsyncTask;

public class ImageProcessing extends AsyncTask<Void, Void, StudyModel>{

	StudyModel updateModel;
	ImageProcessingListener listener;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		updateModel = new StudyModel();
	}

	@Override
	protected StudyModel doInBackground(Void... params) {
		
		imageProcessing(ImageList.getInstance().getImagePathList());
		
		//TODO
		updateModel.rightCount = 10;
		updateModel.totalCount = 15;
		
		return updateModel;
	}

	@Override
	protected void onPostExecute(StudyModel result) {
		super.onPostExecute(result);

		listener.onImageProcessingSuccess(result);
	}
	
	private void imageProcessing(ArrayList<String> list) {
		//TODO
	}
	
	
	public interface ImageProcessingListener {
		public void onImageProcessingSuccess(StudyModel data);
		public void onError();
	}

	public void setImageProcessListener(ImageProcessingListener listener){
		this.listener =  listener;
	}
	
}
