package com.soham.todo.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.soham.todo.roomModules.ScheduledEntity;
import com.soham.todo.databinding.ScheduledRecyclerItemBinding;

public class ScheduledRecyclerViewAdapter  extends ListAdapter<ScheduledEntity, ScheduledRecyclerViewHolder> {

    public ScheduledRecyclerViewAdapter(
            @NonNull DiffUtil.ItemCallback<ScheduledEntity> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public ScheduledRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ScheduledRecyclerViewHolder viewHolder = new ScheduledRecyclerViewHolder(
                ScheduledRecyclerItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                ));
        return viewHolder;
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ScheduledRecyclerViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    public static class ScheduledTaskDiff extends DiffUtil.ItemCallback<ScheduledEntity>{
        @Override
        public boolean areItemsTheSame(@NonNull ScheduledEntity oldItem, @NonNull ScheduledEntity newItem) {
            return oldItem.id == newItem.id ;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ScheduledEntity oldItem, @NonNull ScheduledEntity newItem) {
            return false;
        }
    }
}
