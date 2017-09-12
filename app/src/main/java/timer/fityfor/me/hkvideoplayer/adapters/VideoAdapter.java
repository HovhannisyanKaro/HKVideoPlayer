package timer.fityfor.me.hkvideoplayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import timer.fityfor.me.hkvideoplayer.R;
import timer.fityfor.me.hkvideoplayer.controllers.DataController;
import timer.fityfor.me.hkvideoplayer.controllers.ViewController;
import timer.fityfor.me.hkvideoplayer.entities.MyList;
import timer.fityfor.me.hkvideoplayer.entities.MyVideo;
import timer.fityfor.me.hkvideoplayer.interfaces.RecyclerViewOnItemClicked;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private MyList<MyVideo> videos;
    private RecyclerViewOnItemClicked recyclerViewOnItemClicked;

    public VideoAdapter(RecyclerViewOnItemClicked recyclerViewOnItemClicked) {
        context = ViewController.getViewController().getContext();
        inflater = LayoutInflater.from(context);
        this.recyclerViewOnItemClicked = recyclerViewOnItemClicked;
        setData();
    }

    public void setData() {
        videos = DataController.getInstance().getVideosData();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_videos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String currVideUrl = videos.get(position).getVideoUrl();
        holder.tvTitle.setText(currVideUrl);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewOnItemClicked.onItemClicked(view, getAdapterPosition());
                }
            });
        }
    }
}
