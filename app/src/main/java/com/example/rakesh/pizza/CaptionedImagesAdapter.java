package com.example.rakesh.pizza;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rakesh on 26-02-2017.
 */

class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    private String[] captions;
    private int[] imageIds;
    private Listener listener;
    interface Listener{             //interface to respond on click
        void onClick(int position);
    }
    CaptionedImagesAdapter(String[] captions, int[] imageIds){
        this.captions = captions;
        this.imageIds = imageIds;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        ViewHolder(CardView v) {           //viewHolder constructor holds the type of view it contains (in this case: CardView)
            super(v);
            cardView=v;
        }
    }
    void setListener(Listener listener){  //activity and fragment use this method to register as a listener
        this.listener=listener;
    }
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());   //need to define inflater in adapter before use
        CardView cv=(CardView) inflater.inflate(R.layout.card,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.imageView);
        Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView)cardView.findViewById(R.id.textView);
        textView.setText(captions[position]);                           //Display the caption in the TextView.
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(holder.getAdapterPosition());  //onClick() method will be called inside Listener interface
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return captions.length;                //The length of the captions array equals the number of data items in the recycler view

    }


}
