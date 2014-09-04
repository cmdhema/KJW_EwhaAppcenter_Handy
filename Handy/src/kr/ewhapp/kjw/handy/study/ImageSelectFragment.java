package kr.ewhapp.kjw.handy.study;

import java.io.File;

import kr.ewhapp.handy.R;
import kr.ewhapp.kjw.handy.study.ScoreCheckDialog.OnDialogClickCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@EFragment(R.layout.layout_imagecheck)
public class ImageSelectFragment extends Fragment {

	final int PICK_FROM_CAMERA = 0;
	final int PICK_FROM_ALBUM = 1;

	@ViewById(R.id.photo_container)
	RelativeLayout containerLayout;
	@ViewById(R.id.photo_iv)
	ImageView photoIv;
	
	@FragmentArg
	String uri;
	@FragmentArg
	String flag;
	@FragmentArg
	String subject;
	@FragmentArg
	String study;

	String photoPath;

	ImageSelectDialog dialog;

	private Uri fileUri;

	@AfterViews
	void init() {
		photoPath = getImagePath(Uri.parse(uri));
		photoIv.setImageURI(Uri.fromFile(new File(photoPath)));

		dialog = new ImageSelectDialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setOnDialogClickListener(listener);
	}

	@Click(R.id.photo_container)
	void showDialog() {
		dialog.show();
	}

	OnDialogClickCallback listener = new OnDialogClickCallback() {

		@Override
		public void onDialogClick(View view, Object ids) {
			ImageSelectDialog.ImageViewId id = (ImageSelectDialog.ImageViewId) ids;

			if (view.getId() == id.continueBtnId) {
				ImageList.getInstance().getImagePathList().add(getImagePath(fileUri));
				if (flag.equals("Camera"))
					callCameraIntent();
				else if (flag.equals("Album"))
					callAlbumIntent();
			} else if (view.getId() == id.againBtnId) {
				ImageList.getInstance().getImagePathList().remove(getImagePath(fileUri));
				if (flag.equals("Camera"))
					callCameraIntent();
				else if (flag.equals("Album"))
					callAlbumIntent();
			} else if (view.getId() == id.resultBtnId) {
				ImageList.getInstance().getImagePathList().add(getImagePath(fileUri));
				ResultFragment fragment = new ResultFragment_();
				Bundle bundle = new Bundle();
				bundle.putString("subject", subject);
				bundle.putString("study", study);
				fragment.setArguments(bundle);
				getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
				
			}
			dialog.dismiss();
		}
	};

	private void callCameraIntent() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}

	private void callAlbumIntent() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}

	/**
	 * 선택된 uri의 사진 Path를 가져온다. uri 가 null 경우 마지막에 저장된 사진을 가져온다.
	 * 
	 * @param uri
	 * @return
	 */
	private String getImagePath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		if (uri == null) {
			uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		}

		Cursor mCursor = getActivity().getContentResolver().query(uri, projection, null, null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
		if (mCursor == null || mCursor.getCount() < 1) {
			return null; // no cursor or no record
		}
		int column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		mCursor.moveToFirst();

		String path = mCursor.getString(column_index);

		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
		}

		return path;
	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != getActivity().RESULT_OK)
			return;

		switch (requestCode) {
		case PICK_FROM_ALBUM:
			fileUri = data.getData();
		case PICK_FROM_CAMERA:
			if (fileUri != null) {
				
				ImageSelectFragment fragment = new ImageSelectFragment_();
				Bundle bundle = new Bundle();
				bundle.putString("uri", fileUri.toString());
				bundle.putString("subject", subject);
				bundle.putString("study", study);
				bundle.putString("flag", flag);
				fragment.setArguments(bundle);
				getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
				
				break;
			}

		}
	}

}
