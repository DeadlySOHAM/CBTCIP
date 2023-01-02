package com.soham.todo.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soham.todo.roomModules.ScheduledEntity;
import com.soham.todo.databinding.ScheduledRecyclerItemBinding;

public class ScheduledRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ScheduledRecyclerItemBinding binding;
    public ScheduledEntity task;

    public ScheduledRecyclerViewHolder(
            ScheduledRecyclerItemBinding binding
            ) {
        super(binding.getRoot());
        this.binding = binding;
    }


    public void bind(@NonNull ScheduledEntity task) {
        this.task = task;
        binding.setTask(task);
    }

}
