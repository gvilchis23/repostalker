package com.example.octostalker.view.fragments;
import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.octostalker.R;
import com.example.octostalker.model.User;
import com.example.octostalker.view.adapter.UserAdapter;
import com.example.octostalker.viewmodel.UsersViewModel;
import java.util.Observable;
import java.util.Observer;
import com.example.octostalker.databinding.UsersFragmentBinding;

public class UsersFragment extends Fragment implements Observer {
    public static final String TAG = "UsersFragment";
    private static final String ARG_PARAM_USER = "paramUser";
    private UsersViewModel mViewModel;
    private UserAdapter adapter;
    private UsersFragmentBinding usersFragmentBinding;
    private SearchView searchView;
    private User userSelected;


    public static UsersFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USER,user);
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments()!=null)
            this.userSelected = (User) getArguments().getSerializable(ARG_PARAM_USER);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDataBinding();
        setupListPeopleView(usersFragmentBinding.userRecycler);
        setupObserver(mViewModel);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(adapter !=null && query!=null)
                adapter.getFilter().filter(query);
                return false;
            }
        });

    }

    private void initDataBinding() {
        usersFragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.users_fragment);
        mViewModel = new UsersViewModel(getContext(),userSelected);
        usersFragmentBinding.setUserViewModel(mViewModel);
    }

    private void setupListPeopleView(RecyclerView usersRecycler) {
        UserAdapter adapter = new UserAdapter();
        usersRecycler.setAdapter(adapter);
        usersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.reset();
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof UsersViewModel) {
            adapter = (UserAdapter) usersFragmentBinding.userRecycler.getAdapter();
            mViewModel = (UsersViewModel) observable;
            adapter.setUsers(mViewModel.getUsers());
        }
    }
}
