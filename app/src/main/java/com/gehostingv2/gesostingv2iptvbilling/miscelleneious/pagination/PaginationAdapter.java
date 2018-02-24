//package com.gehostingv2.gesostingv2iptvbilling.miscelleneious.pagination;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//
//import com.gehostingv2.gesostingv2iptvbilling.R;
//import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
//import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamCategoryIdDBModel;
//import com.gehostingv2.gesostingv2iptvbilling.view.activity.VoDListViewCatActivity;
//import com.gehostingv2.gesostingv2iptvbilling.view.activity.VodActivityNewFlow;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    // flag for footer ProgressBar (i.e. last item of list)
//    private boolean isLoadingAdded = false;
//    private List<LiveStreamCategoryIdDBModel> moviesListl;
//    private Context context;
//    private static final int ITEM = 0;
//    private static final int LOADING = 1;
//    RecyclerView.ViewHolder holder;
//    MovieVH movieVH;
//    FrameLayout frameLayout;
//
//    public PaginationAdapter(List<LiveStreamCategoryIdDBModel> movieList,
//                             Context context) {
//        this.moviesListl = movieList;
//        this.context = context;
////        this.frameLayout = frameLayout;
//    }
//
//    @Override
//    public int getItemCount() {
//        return moviesListl == null ? 0 : moviesListl.size();
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder viewHolder = null;
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//
//        switch (viewType) {
//            case ITEM:
//                viewHolder = getViewHolder(parent, inflater);
//                break;
//            case LOADING:
//                View v2 = inflater.inflate(context.getResources().getLayout(R.layout.layout_vod_new_flow_list_item), parent, false);
//                viewHolder = new LoadingVH(v2);
//                break;
//        }
//        return viewHolder;
//    }
//
//    @NonNull
//    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
//        RecyclerView.ViewHolder viewHolder;
//        View v1 = inflater.inflate(context.getResources().getLayout(R.layout.layout_vod_new_flow_list_item), parent, false);
//        viewHolder = new MovieVH(v1);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//
//        LiveStreamCategoryIdDBModel movieData = moviesListl.get(position);
//        switch (getItemViewType(position)) {
//            case ITEM:
//                final MovieVH movieVH = (MovieVH) holder;
//                String categoryName = "";
//                String categoryId = "";
//                LiveStreamCategoryIdDBModel data = moviesListl.get(position);
//                categoryName = data.getLiveStreamCategoryName();
//                categoryId = data.getLiveStreamCategoryID();
//                final Bundle bundle = new Bundle();
//                bundle.putString(AppConst.CATEGORY_ID, categoryId);
//                bundle.putString(AppConst.CATEGORY_NAME, categoryName);
//                if (categoryName != null && !categoryName.equals("") && !categoryName.isEmpty())
//                    movieVH.tvMovieCategoryName.setText(categoryName);
//
//
//                final String finalCategoryId = categoryId;
//                final String finalCategoryName = categoryName;
//                movieVH.rlOuter.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        VodActivityNewFlow vodActivityNewFlow = new VodActivityNewFlow();
//                        vodActivityNewFlow.progressBar(movieVH.pbPagingLoader);
//
//                        if (movieVH != null && movieVH.pbPagingLoader != null) {
//                            movieVH.pbPagingLoader.getIndeterminateDrawable()
//                                    .setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
//                            movieVH.pbPagingLoader.setVisibility(View.VISIBLE);
//
//                        }
//                        Intent intent = new Intent(context, VoDListViewCatActivity.class);
//                        intent.putExtra(AppConst.CATEGORY_ID, finalCategoryId);
//                        intent.putExtra(AppConst.CATEGORY_NAME, finalCategoryName);
//                        context.startActivity(intent);
//                    }
//                });
//
//
//                break;
//            case LOADING:
//
////                Do nothing
//                break;
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return (position == moviesListl.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
//    }
//
//
//    public void add(LiveStreamCategoryIdDBModel mc) {
//        moviesListl.add(mc);
//        notifyItemInserted(moviesListl.size() - 1);
//    }
//
//    public void addAll(List<LiveStreamCategoryIdDBModel> mcList) {
//        for (LiveStreamCategoryIdDBModel mc : mcList) {
//            add(mc);
//        }
//    }
//
//    public void remove(LiveStreamCategoryIdDBModel city) {
//        int position = moviesListl.indexOf(city);
//        if (position > -1) {
//            moviesListl.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
//
//    public void clear() {
//        isLoadingAdded = false;
//        while (getItemCount() > 0) {
//            remove(getItem(0));
//        }
//    }
//
//    public boolean isEmpty() {
//        return getItemCount() == 0;
//    }
//
//    public void addLoadingFooter() {
//        isLoadingAdded = true;
//        add(new LiveStreamCategoryIdDBModel());
//    }
//
//    public void removeLoadingFooter() {
//        isLoadingAdded = false;
//
//        int position = moviesListl.size() - 1;
//        LiveStreamCategoryIdDBModel item = getItem(position);
//
//        if (item != null) {
//            moviesListl.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
//
//    public LiveStreamCategoryIdDBModel getItem(int position) {
//        return moviesListl.get(position);
//    }
//
//    public void showProgressLoader() {
//        movieVH.pbPagingLoader.setVisibility(View.VISIBLE);
//    }
//
//    public void setVisibiltygone(ProgressBar pbPagingLoader) {
//
//        if (pbPagingLoader!= null)
//            pbPagingLoader.setVisibility(View.GONE);
//
//    }
//
//
//    protected class MovieVH extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.tv_movie_category_name)
//        TextView tvMovieCategoryName;
//        @BindView(R.id.pb_paging_loader)
//        ProgressBar pbPagingLoader;
//        @BindView(R.id.rl_outer)
//        RelativeLayout rlOuter;
//
//
//        public MovieVH(View itemView) {
//
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            setIsRecyclable(false);
//        }
//    }
//
//    protected class LoadingVH extends RecyclerView.ViewHolder {
//
////        @BindView(R.id.tv_movie_category_name)
////        TextView tvMovieCategoryName;
////        @BindView(R.id.pb_paging_loader)
////        ProgressBar pbPagingLoader;
////        @BindView(R.id.rl_outer)
////        RelativeLayout rlOuter;
//
//        public LoadingVH(View itemView) {
//            super(itemView);
//        }
//    }
//}
