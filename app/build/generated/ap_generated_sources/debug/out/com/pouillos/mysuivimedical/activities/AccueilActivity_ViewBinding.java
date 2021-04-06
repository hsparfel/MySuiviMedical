// Generated code from Butter Knife. Do not modify!
package com.pouillos.mysuivimedical.activities;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.pouillos.mysuivimedical.R;
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
    target.simpleProgressBar = Utils.findRequiredViewAsType(source, R.id.simpleProgressBar, "field 'simpleProgressBar'", ProgressBar.class);
    target.textView = Utils.findRequiredViewAsType(source, R.id.textView, "field 'textView'", TextView.class);
    target.listRecyclerRdvContact = Utils.findRequiredViewAsType(source, R.id.list_recycler_rdv_contact, "field 'listRecyclerRdvContact'", RecyclerView.class);
    target.listRecyclerRdvExamen = Utils.findRequiredViewAsType(source, R.id.list_recycler_rdv_examen, "field 'listRecyclerRdvExamen'", RecyclerView.class);
    target.listRecyclerRdvAnalyse = Utils.findRequiredViewAsType(source, R.id.list_recycler_rdv_analyse, "field 'listRecyclerRdvAnalyse'", RecyclerView.class);
    target.futursRdvContact = Utils.findRequiredViewAsType(source, R.id.futursRdvContact, "field 'futursRdvContact'", TextView.class);
    target.futursRdvExamen = Utils.findRequiredViewAsType(source, R.id.futursRdvExamen, "field 'futursRdvExamen'", TextView.class);
    target.futursRdvAnalyse = Utils.findRequiredViewAsType(source, R.id.futursRdvAnalyse, "field 'futursRdvAnalyse'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AccueilActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
    target.simpleProgressBar = null;
    target.textView = null;
    target.listRecyclerRdvContact = null;
    target.listRecyclerRdvExamen = null;
    target.listRecyclerRdvAnalyse = null;
    target.futursRdvContact = null;
    target.futursRdvExamen = null;
    target.futursRdvAnalyse = null;
  }
}
