package com.soham.todo.adapters;

import android.view.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;

import com.soham.todo.roomModules.UnscheduledEntity;
import com.soham.todo.databinding.UnscheduledRecyclerItemBinding;

public class UnscheduledRecyclerViewAdapter extends ListAdapter<UnscheduledEntity, UnscheduledRecyclerViewHolder> {


    public UnscheduledRecyclerViewAdapter(
            @NonNull DiffUtil.ItemCallback<UnscheduledEntity> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public UnscheduledRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UnscheduledRecyclerViewHolder viewHolder = new UnscheduledRecyclerViewHolder(
                UnscheduledRecyclerItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                ));
        return viewHolder;
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull UnscheduledRecyclerViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    public static class UnscheduledTaskDiff extends DiffUtil.ItemCallback<UnscheduledEntity>{
        @Override
        public boolean areItemsTheSame(@NonNull UnscheduledEntity oldItem, @NonNull UnscheduledEntity newItem) {
            return oldItem.id == newItem.id ;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UnscheduledEntity oldItem, @NonNull UnscheduledEntity newItem) {
            return false;
        }
    }
}
