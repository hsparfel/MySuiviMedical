// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.button.MaterialButton;
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AccueilActivity_ViewBinding implements Unbinder {
  private AccueilActivity target;

  private View view7f080063;

  private View view7f080065;

  private View view7f080064;

  private View view7f080066;

  @UiThread
  public AccueilActivity_ViewBinding(AccueilActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AccueilActivity_ViewBinding(final AccueilActivity target, View source) {
    this.target = target;

    View view;
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
    target.textView = Utils.findRequiredViewAsType(source, R.id.textView, "field 'textView'", TextView.class);
    target.listPrescription = Utils.findRequiredViewAsType(source, R.id.list_prescription, "field 'listPrescription'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.button_list_prescription, "field 'buttonListPrescription' and method 'buttonListPrescriptionClick'");
    target.buttonListPrescription = Utils.castView(view, R.id.button_list_prescription, "field 'buttonListPrescription'", MaterialButton.class);
    view7f080063 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.buttonListPrescriptionClick();
      }
    });
    target.listRdvContact = Utils.findRequiredViewAsType(source, R.id.list_rdv_contact, "field 'listRdvContact'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.button_list_rdv_contact, "field 'buttonListRdvContact' and method 'buttonListRdvContactClick'");
    target.buttonListRdvContact = Utils.castView(view, R.id.button_list_rdv_contact, "field 'buttonListRdvContact'", MaterialButton.class);
    view7f080065 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.buttonListRdvContactClick();
      }
    });
    target.listRdvAnalyse = Utils.findRequiredViewAsType(source, R.id.list_rdv_analyse, "field 'listRdvAnalyse'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.button_list_rdv_analyse, "field 'buttonListRdvAnalyse' and method 'buttonListRdvAnalyseClick'");
    target.buttonListRdvAnalyse = Utils.castView(view, R.id.button_list_rdv_analyse, "field 'buttonListRdvAnalyse'", MaterialButton.class);
    view7f080064 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.buttonListRdvAnalyseClick();
      }
    });
    target.listRdvExamen = Utils.findRequiredViewAsType(source, R.id.list_rdv_examen, "field 'listRdvExamen'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.button_list_rdv_examen, "field 'buttonListRdvExamen' and method 'buttonListRdvExamenClick'");
    target.buttonListRdvExamen = Utils.castView(view, R.id.button_list_rdv_examen, "field 'buttonListRdvExamen'", MaterialButton.class);
    view7f080066 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.buttonListRdvExamenClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AccueilActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
    target.textView = null;
    target.listPrescription = null;
    target.buttonListPrescription = null;
    target.listRdvContact = null;
    target.buttonListRdvContact = null;
    target.listRdvAnalyse = null;
    target.buttonListRdvAnalyse = null;
    target.listRdvExamen = null;
    target.buttonListRdvExamen = null;

    view7f080063.setOnClickListener(null);
    view7f080063 = null;
    view7f080065.setOnClickListener(null);
    view7f080065 = null;
    view7f080064.setOnClickListener(null);
    view7f080064 = null;
    view7f080066.setOnClickListener(null);
    view7f080066 = null;
  }
}
