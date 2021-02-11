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

  private View view7f08006d;

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
    target.text_nb_medicament = Utils.findRequiredViewAsType(source, R.id.text_nb_medicament, "field 'text_nb_medicament'", TextView.class);
    target.listPrescription = Utils.findRequiredViewAsType(source, R.id.list_prescription, "field 'listPrescription'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.button_list_prescription, "field 'buttonListPrescription' and method 'buttonListPrescriptionClick'");
    target.buttonListPrescription = Utils.castView(view, R.id.button_list_prescription, "field 'buttonListPrescription'", MaterialButton.class);
    view7f08006d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.buttonListPrescriptionClick();
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
    target.text_nb_medicament = null;
    target.listPrescription = null;
    target.buttonListPrescription = null;

    view7f08006d.setOnClickListener(null);
    view7f08006d = null;
  }
}
