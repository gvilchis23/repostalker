package com.example.octostalker.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.octostalker.R;
import com.example.octostalker.databinding.CardviewUserBinding;
import com.example.octostalker.model.User;
import com.example.octostalker.viewmodel.UserItemViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable {
    private List<User> dataList;
    private List<User> filterData;
    private List<User> keep;

    public UserAdapter() {
        this.dataList = Collections.emptyList();
        this.filterData = dataList;
    }


    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardviewUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cardview_user, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.bindUser(dataList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (keep == null)
                    keep = dataList;
                if (!charString.isEmpty()) {
                    List<User> filteredList = new ArrayList<>();
                    for (User row : keep) {
                        if (row.getLogin().toLowerCase().contains(charString.toLowerCase()) || row.getLogin().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    filterData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterData;
                filterResults.count = filterData.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                String charString = charSequence.toString();
                if (charString.isEmpty()){
                    setUsers(keep);
                    keep = null;
                }else {
                    filterData = (ArrayList<User>) filterResults.values;
                    setUsers(filterData);
                }
            }
        };
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setUsers(List<User> users) {
        this.dataList = users;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardviewUserBinding binding;

        private ViewHolder(CardviewUserBinding cardviewUserBinding) {
            super(cardviewUserBinding.getRoot());
            this.binding = cardviewUserBinding;
        }

        void bindUser(User user) {
            if (binding.getUserItemViewModel() == null) {
                binding.setUserItemViewModel(
                        new UserItemViewModel(user, itemView.getContext()));
            } else {
                binding.getUserItemViewModel().setUser(user);
            }
        }
    }
}
