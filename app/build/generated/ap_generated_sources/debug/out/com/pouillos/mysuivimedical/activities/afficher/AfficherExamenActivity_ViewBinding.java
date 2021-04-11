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

public class AfficherExamenActivity_ViewBinding implements Unbinder {
  private AfficherExamenActivity target;

  private View view7f0800b7;

  private View view7f0800b8;

  private View view7f0800bb;

  private View view7f0800b5;

  private View view7f0800b4;

  @UiThread
  public AfficherExamenActivity_ViewBinding(AfficherExamenActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AfficherExamenActivity_ViewBinding(final AfficherExamenActivity target, View source) {
    this.target = target;

    View view;
    target.selectedExamen = Utils.findRequiredViewAsType(source, R.id.selectExamen, "field 'selectedExamen'", AutoCompleteTextView.class);
    target.listExamen = Utils.findRequiredViewAsType(source, R.id.listExamen, "field 'listExamen'", TextInputLayout.class);
    target.layoutName = Utils.findRequiredViewAsType(source, R.id.layoutName, "field 'layoutName'", TextInputLayout.class);
    target.textName = Utils.findRequiredViewAsType(source, R.id.textName, "field 'textName'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.fabDelete, "field 'fabDelete' and method 'fabDeleteClick'");
    target.fabDelete = Utils.castView(view, R.id.fabDelete, "field 'fabDelete'", FloatingActionButton.class);
    view7f0800b7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabDeleteClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabEdit, "field 'fabEdit' and method 'fabEditClick'");
    target.fabEdit = Utils.castView(view, R.id.fabEdit, "field 'fabEdit'", FloatingActionButton.class);
    view7f0800b8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabEditClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800bb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabCancel, "field 'fabCancel' and method 'fabCancelClick'");
    target.fabCancel = Utils.castView(view, R.id.fabCancel, "field 'fabCancel'", FloatingActionButton.class);
    view7f0800b5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabCancelClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabAdd, "field 'fabAdd' and method 'fabAddClick'");
    target.fabAdd = Utils.castView(view, R.id.fabAdd, "field 'fabAdd'", FloatingActionButton.class);
    view7f0800b4 = view;
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
    AfficherExamenActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedExamen = null;
    target.listExamen = null;
    target.layoutName = null;
    target.textName = null;
    target.fabDelete = null;
    target.fabEdit = null;
    target.fabSave = null;
    target.fabCancel = null;
    target.fabAdd = null;
    target.toolbar = null;
    target.progressBar = null;

    view7f0800b7.setOnClickListener(null);
    view7f0800b7 = null;
    view7f0800b8.setOnClickListener(null);
    view7f0800b8 = null;
    view7f0800bb.setOnClickListener(null);
    view7f0800bb = null;
    view7f0800b5.setOnClickListener(null);
    view7f0800b5 = null;
    view7f0800b4.setOnClickListener(null);
    view7f0800b4 = null;
  }
}
