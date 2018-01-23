package com.xulc.algorithmstudy.model;

import com.inuker.bluetooth.library.search.SearchResult;

/**
 * Date：2018/1/23
 * Desc：
 * Created by xuliangchun.
 */

public class StudyBluetoothModel {
    private SearchResult searchResult;
    private boolean isBonded;

    public StudyBluetoothModel(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public StudyBluetoothModel(SearchResult searchResult, boolean isBonded) {
        this.searchResult = searchResult;
        this.isBonded = isBonded;
    }

    public SearchResult getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public boolean isBonded() {
        return isBonded;
    }

    public void setBonded(boolean bonded) {
        isBonded = bonded;
    }

    public String getName(){
        if (searchResult.getName().equals("NULL")){
            return searchResult.getAddress();
        }else {
            return searchResult.getName();
        }
    }
}
