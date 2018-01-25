package com.xulc.algorithmstudy.model;

import com.inuker.bluetooth.library.search.SearchResult;

/**
 * Date：2018/1/23
 * Desc：
 * Created by xuliangchun.
 */

public class StudyBluetoothModel {
    private SearchResult searchResult;
    private int bondState;   // bondState = Constants.BOND_NONE, BOND_BONDING, BOND_BONDED

    public StudyBluetoothModel(SearchResult searchResult, int bondState) {
        this.searchResult = searchResult;
        this.bondState = bondState;
    }

    public SearchResult getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }



    public String getName(){
        return searchResult.getName();
    }

    public int getBondState() {
        return bondState;
    }

    public void setBondState(int bondState) {
        this.bondState = bondState;
    }
}
