package com.cp.jo.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.cp.jo.model.ModelProtection;

import java.util.List;

public class DiffUtilProtection extends DiffUtil.Callback {

    List<ModelProtection> oldList;
    List<ModelProtection> newList;

    public DiffUtilProtection(List<ModelProtection> oldList, List<ModelProtection> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
