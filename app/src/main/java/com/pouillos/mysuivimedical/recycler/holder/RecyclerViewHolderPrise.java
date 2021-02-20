package com.pouillos.mysuivimedical.recycler.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.mysuivimedical.R;

import com.pouillos.mysuivimedical.entities.Prise;
import com.pouillos.mysuivimedical.interfaces.BasicUtils;
import com.pouillos.mysuivimedical.recycler.adapter.RecyclerAdapterPrise;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolderPrise extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.detail)
    TextView detail;

    private WeakReference<RecyclerAdapterPrise.Listener> callbackWeakRef;

    public RecyclerViewHolderPrise(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithPrise(Prise prise, RecyclerAdapterPrise.Listener callback){
        String string = "";
        if (BasicUtils.isInteger(prise.getQteDose())) {
            string += Math.round(prise.getQteDose());
        } else {
            string += prise.getQteDose();
        }
        string +=" "+prise.getDose().getName() + " - ";
        if (prise.getMedicament().getDenomination().length()>17) {
            string += prise.getMedicament().getDenomination().substring(0,16)+"...";
        } else {
            string += prise.getMedicament().getDenomination();
        }
        if (prise.getEffectue()) {
            string += " - effectué";
        }
        this.detail.setText(string);
        this.detail.setOnClickListener(this);
        this.callbackWeakRef = new WeakReference<RecyclerAdapterPrise.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        RecyclerAdapterPrise.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickPriseButton(getAdapterPosition());
    }
}
