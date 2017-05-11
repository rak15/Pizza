package com.example.rakesh.pizza;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
/**
 * A simple {@link Fragment} subclass.
 */
public class PizzaMaterialFragment extends Fragment {

    public PizzaMaterialFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView=(RecyclerView)inflater.inflate(R.layout.fragment_pizza_material, container, false); //adding layout with RecyclerView in this fragment
        String[] pizzaNames=new String[Pizza.pizzas.length];
        for (int i=0;i<pizzaNames.length;i++)
            pizzaNames[i]=Pizza.pizzas[i].getName();
        int[] pizzaImages=new int[Pizza.pizzas.length];
        for (int i=0;i<pizzaNames.length;i++)
            pizzaImages[i]=Pizza.pizzas[i].getImageId();
        CaptionedImagesAdapter adapter=new CaptionedImagesAdapter(pizzaNames,pizzaImages);  //passing array to adapter
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter.setListener(new CaptionedImagesAdapter.Listener(){
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"CardView pressed",Toast.LENGTH_SHORT).show();
            }
        });
        return recyclerView;
    }
}
