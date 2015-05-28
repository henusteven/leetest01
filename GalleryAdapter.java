package com.kissoft.kcym.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kissoft.kcym.R;
import com.kissoft.kcym.model.VideoInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GalleryAdapter extends
		RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
	private static final String TAG = GalleryAdapter.class.getSimpleName();
	private OnItemClickLitener mOnItemClickLitener;
	private LayoutInflater mInflater;
	private List<VideoInfo> mDatas;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	public GalleryAdapter(Context context, List<VideoInfo> data) {
		Log.e("TAG", "context:" + context + ", data:" + data);
		mInflater = LayoutInflater.from(context);
		mDatas = data;
	}

	public interface OnItemClickLitener {
		void onItemClick(View view, int position);
	}

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View arg0) {
			super(arg0);
		}

		ImageView mImg;
		TextView mTxt;
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = mInflater.inflate(R.layout.activity_index_gallery_item,
				viewGroup, false);
		ViewHolder viewHolder = new ViewHolder(view);

		viewHolder.mImg = (ImageView) view
				.findViewById(R.id.id_index_gallery_item_image);
		viewHolder.mTxt = (TextView) view
				.findViewById(R.id.id_index_gallery_item_text);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
		imageLoader.displayImage(mDatas.get(i).image.get(0).url, viewHolder.mImg);

		viewHolder.mTxt.setText(mDatas.get(i).name);

		if (mOnItemClickLitener != null) {
			viewHolder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
				}
			});

		}

	}

}
