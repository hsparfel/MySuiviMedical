package com.pouillos.mypilulier.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.mypilulier.R;

import com.pouillos.mypilulier.entities.Prise;
import com.pouillos.mypilulier.recycler.holder.RecyclerViewHolderPrise;

import java.util.List;

public class RecyclerAdapterPrise extends RecyclerView.Adapter<RecyclerViewHolderPrise> {

        private List<Prise> listPrise;

    public interface Listener {
        void onClickPriseButton(int position);
    }

    private final Listener callback;

    public RecyclerAdapterPrise(List<Prise> listPrise, Listener callback) {
        this.listPrise = listPrise;
        this.callback = callback;
    }

        @Override
        public RecyclerViewHolderPrise onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_list_prise, parent, false);

            return new RecyclerViewHolderPrise(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolderPrise viewHolder, int position) {
            viewHolder.updateWithPrise(this.listPrise.get(position),this.callback);
        }

        @Override
        public int getItemCount() {
            return this.listPrise.size();
        }

    public Prise getPrise(int position){
        return this.listPrise.get(position);
    }
}
