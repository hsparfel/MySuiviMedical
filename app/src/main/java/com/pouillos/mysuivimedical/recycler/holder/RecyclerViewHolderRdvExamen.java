package com.pouillos.mysuivimedical.recycler.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.entities.RdvExamen;
import com.pouillos.mysuivimedical.recycler.adapter.RecyclerAdapterRdvExamen;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolderRdvExamen extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.detail)
    TextView detail;

    private WeakReference<RecyclerAdapterRdvExamen.Listener> callbackWeakRef;

    public RecyclerViewHolderRdvExamen(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //ButterKnife.bind(this);
    }

    public void updateWithRdvExamen(RdvExamen rdvExamen, RecyclerAdapterRdvExamen.Listener callback){
        this.detail.setText(rdvExamen.toString());
        this.detail.setOnClickListener(this);
        //4 - Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<RecyclerAdapterRdvExamen.Listener>(callback);
    }


    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        RecyclerAdapterRdvExamen.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickRdvExamenButton(getAdapterPosition());
    }
}
