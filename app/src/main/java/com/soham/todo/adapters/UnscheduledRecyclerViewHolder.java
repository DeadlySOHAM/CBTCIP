package com.soham.todo.adapters;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soham.todo.roomModules.UnscheduledEntity;
import com.soham.todo.databinding.UnscheduledRecyclerItemBinding;


public class UnscheduledRecyclerViewHolder extends RecyclerView.ViewHolder {

    private UnscheduledRecyclerItemBinding binding;
    public UnscheduledEntity task;

    public UnscheduledRecyclerViewHolder(@NonNull UnscheduledRecyclerItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NonNull UnscheduledEntity task) {
        this.task = task;
        binding.setTask(task);
    }

}
