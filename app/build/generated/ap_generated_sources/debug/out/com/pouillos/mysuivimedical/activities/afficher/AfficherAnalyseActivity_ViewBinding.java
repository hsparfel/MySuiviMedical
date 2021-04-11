// Generated code from Butter Knife. Do not modify!
package com.pouillos.mysuivimedical.activities.afficher;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AfficherAnalyseActivity_ViewBinding implements Unbinder {
  private AfficherAnalyseActivity target;

  private View view7f0800b6;

  private View view7f0800b7;

  private View view7f0800ba;

  private View view7f0800b4;

  private View view7f0800b3;

  @UiThread
  public AfficherAnalyseActivity_ViewBinding(AfficherAnalyseActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AfficherAnalyseActivity_ViewBinding(final AfficherAnalyseActivity target, View source) {
    this.target = target;

    View view;
    target.selectedAnalyse = Utils.findRequiredViewAsType(source, R.id.selectAnalyse, "field 'selectedAnalyse'", AutoCompleteTextView.class);
    target.listAnalyse = Utils.findRequiredViewAsType(source, R.id.listAnalyse, "field 'listAnalyse'", TextInputLayout.class);
    target.layoutName = Utils.findRequiredViewAsType(source, R.id.layoutName, "field 'layoutName'", TextInputLayout.class);
    target.textName = Utils.findRequiredViewAsType(source, R.id.textName, "field 'textName'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.fabDelete, "field 'fabDelete' and method 'fabDeleteClick'");
    target.fabDelete = Utils.castView(view, R.id.fabDelete, "field 'fabDelete'", FloatingActionButton.class);
    view7f0800b6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabDeleteClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabEdit, "field 'fabEdit' and method 'fabEditClick'");
    target.fabEdit = Utils.castView(view, R.id.fabEdit, "field 'fabEdit'", FloatingActionButton.class);
    view7f0800b7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabEditClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800ba = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabCancel, "field 'fabCancel' and method 'fabCancelClick'");
    target.fabCancel = Utils.castView(view, R.id.fabCancel, "field 'fabCancel'", FloatingActionButton.class);
    view7f0800b4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabCancelClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabAdd, "field 'fabAdd' and method 'fabAddClick'");
    target.fabAdd = Utils.castView(view, R.id.fabAdd, "field 'fabAdd'", FloatingActionButton.class);
    view7f0800b3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabAddClick();
      }
    });
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.activity_main_toolbar, "field 'toolbar'", Toolbar.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AfficherAnalyseActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedAnalyse = null;
    target.listAnalyse = null;
    target.layoutName = null;
    target.textName = null;
    target.fabDelete = null;
    target.fabEdit = null;
    target.fabSave = null;
    target.fabCancel = null;
    target.fabAdd = null;
    target.toolbar = null;
    target.progressBar = null;

    view7f0800b6.setOnClickListener(null);
    view7f0800b6 = null;
    view7f0800b7.setOnClickListener(null);
    view7f0800b7 = null;
    view7f0800ba.setOnClickListener(null);
    view7f0800ba = null;
    view7f0800b4.setOnClickListener(null);
    view7f0800b4 = null;
    view7f0800b3.setOnClickListener(null);
    view7f0800b3 = null;
  }
}
