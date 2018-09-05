package com.ricardocenteno.octostalker.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ricardocenteno.octostalker.R;
import com.ricardocenteno.octostalker.databinding.CardviewUserBinding;
import com.ricardocenteno.octostalker.model.User;
import com.ricardocenteno.octostalker.viewmodel.ItemUserViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.ViewHolder> implements Filterable {
    private List<User> objects;
    private List<User> objectsFiltered;
    private List<User> objectKeep;

    public UserRVAdapter() {
        this.objects = Collections.emptyList();
        this.objectsFiltered = objects;
    }

    public void setUsers(List<User> users) {
        this.objects = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardviewUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cardview_user, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRVAdapter.ViewHolder holder, int position) {
        holder.bindUser(objects.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (objectKeep == null)
                    objectKeep = objects;
                if (!charString.isEmpty()) {
                    List<User> filteredList = new ArrayList<>();
                    for (User row : objectKeep) {
                        if (row.getLogin().toLowerCase().contains(charString.toLowerCase()) || row.getLogin().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    objectsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = objectsFiltered;
                filterResults.count = objectsFiltered.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                String charString = charSequence.toString();
                if (charString.isEmpty()){
                    setUsers(objectKeep);
                    objectKeep = null;
                }else {
                    objectsFiltered = (ArrayList<User>) filterResults.values;
                    setUsers(objectsFiltered);
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardviewUserBinding binding;

        private ViewHolder(CardviewUserBinding cardviewUserBinding) {
            super(cardviewUserBinding.getRoot());
            this.binding = cardviewUserBinding;
        }

        void bindUser(User user) {
            if (binding.getUserViewModel() == null) {
                binding.setUserViewModel(
                        new ItemUserViewModel(user, itemView.getContext()));
            } else {
                binding.getUserViewModel().setUser(user);
            }
        }
    }
}
