package com.rasset.wflaunch.ui.adapter;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * @deprecated 버려야됨 이건...
 */
public abstract class ReloadScrollListner implements OnScrollListener {

	// The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 3;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 1;

    public ReloadScrollListner() {
    }

    public void reset() {
        currentPage = 0;
        previousTotalItemCount = 0;
    }
    
    public void setVisibleThreshold(int nVisibleThreshold) {
        visibleThreshold = nVisibleThreshold;
    }

    public ReloadScrollListner(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public ReloadScrollListner(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    @Override
    public void onScroll(AbsListView view,int firstVisibleItem,int visibleItemCount,int totalItemCount) {
//        Logger.d(this, "====================================================");
//        for(int i = firstVisibleRow; i < lastVisibleRow; i++){
            // 아이템이 3개면 가운데 있는 녀석이 보이는 거고
            // 아이템이 2개면 처음 있는 놈이 없어질 놈이다.
            
            
            //Write your code here(allocation/deallocation/store in array etc.)
//            if(mImageViewListener != null) {
//                mImageViewListener.onCallback(0);
//            }
//            Logger.d(this, i + "=" + view.getItemAtPosition(i));
//        }
        
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) { this.loading = true; } 
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        if (!loading
                && totalItemCount > visibleThreshold + visibleItemCount
                && (totalItemCount - visibleThreshold)<=(firstVisibleItem + visibleItemCount)) {
            onLoadMore(currentPage + 1, totalItemCount);
            loading = true;
        }
    }

    public abstract void onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

}
