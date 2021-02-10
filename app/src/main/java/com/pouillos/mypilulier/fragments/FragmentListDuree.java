package com.pouillos.mypilulier.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pouillos.mypilulier.R;
import com.pouillos.mypilulier.activities.add.AddPrescriptionActivity;
import com.pouillos.mypilulier.enumeration.Duree;


public class FragmentListDuree extends Fragment {

    Duree duree;

    AddPrescriptionActivity addPrescriptionActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_duree, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddPrescriptionActivity) {
            this.addPrescriptionActivity = (AddPrescriptionActivity) context;
        }
    }

    public void sendResponse() {
        this.addPrescriptionActivity.duree = duree;
    }
}
