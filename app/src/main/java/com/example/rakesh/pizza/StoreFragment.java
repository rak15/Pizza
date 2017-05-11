package com.example.rakesh.pizza;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends ListFragment {


    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayAdapter<String> adapter=new ArrayAdapter<>(inflater.getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.stores));
        setListAdapter(adapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
