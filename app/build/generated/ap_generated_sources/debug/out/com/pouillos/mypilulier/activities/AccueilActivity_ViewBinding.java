// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.button.MaterialButton;
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AccueilActivity_ViewBinding implements Unbinder {
  private AccueilActivity target;

  @UiThread
  public AccueilActivity_ViewBinding(AccueilActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AccueilActivity_ViewBinding(AccueilActivity target, View source) {
    this.target = target;

    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
    target.textView = Utils.findRequiredViewAsType(source, R.id.textView, "field 'textView'", TextView.class);
    target.text_nb_medicament = Utils.findRequiredViewAsType(source, R.id.text_nb_medicament, "field 'text_nb_medicament'", TextView.class);
    target.listPrise = Utils.findRequiredViewAsType(source, R.id.list_prise, "field 'listPrise'", RecyclerView.class);
    target.buttonListPrise = Utils.findRequiredViewAsType(source, R.id.button_list_prise, "field 'buttonListPrise'", MaterialButton.class);
    target.btnImport = Utils.findRequiredViewAsType(source, R.id.btn_import, "field 'btnImport'", Button.class);
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
    target.listPrise = null;
    target.buttonListPrise = null;
    target.btnImport = null;
  }
}
