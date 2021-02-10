// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities.afficher;

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
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AfficherRdvAnalyseActivity_ViewBinding implements Unbinder {
  private AfficherRdvAnalyseActivity target;

  private View view7f0800be;

  private View view7f0800bf;

  private View view7f0800c6;

  private View view7f0800bb;

  private View view7f0800b8;

  private View view7f0800c2;

  @UiThread
  public AfficherRdvAnalyseActivity_ViewBinding(AfficherRdvAnalyseActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AfficherRdvAnalyseActivity_ViewBinding(final AfficherRdvAnalyseActivity target,
      View source) {
    this.target = target;

    View view;
    target.selectedRdv = Utils.findRequiredViewAsType(source, R.id.selectRdv, "field 'selectedRdv'", AutoCompleteTextView.class);
    target.listRdv = Utils.findRequiredViewAsType(source, R.id.listRdv, "field 'listRdv'", TextInputLayout.class);
    target.layoutAnalyse = Utils.findRequiredViewAsType(source, R.id.layoutAnalyse, "field 'layoutAnalyse'", TextInputLayout.class);
    target.textAnalyse = Utils.findRequiredViewAsType(source, R.id.textAnalyse, "field 'textAnalyse'", TextInputEditText.class);
    target.layoutDate = Utils.findRequiredViewAsType(source, R.id.layoutDate, "field 'layoutDate'", TextInputLayout.class);
    target.textDate = Utils.findRequiredViewAsType(source, R.id.textDate, "field 'textDate'", TextInputEditText.class);
    target.layoutHeure = Utils.findRequiredViewAsType(source, R.id.layoutHeure, "field 'layoutHeure'", TextInputLayout.class);
    target.textHeure = Utils.findRequiredViewAsType(source, R.id.textHeure, "field 'textHeure'", TextInputEditText.class);
    target.layoutNote = Utils.findRequiredViewAsType(source, R.id.layoutNote, "field 'layoutNote'", TextInputLayout.class);
    target.textNote = Utils.findRequiredViewAsType(source, R.id.textNote, "field 'textNote'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.fabDelete, "field 'fabDelete' and method 'fabDeleteClick'");
    target.fabDelete = Utils.castView(view, R.id.fabDelete, "field 'fabDelete'", FloatingActionButton.class);
    view7f0800be = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabDeleteClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabEdit, "field 'fabEdit' and method 'fabEditClick'");
    target.fabEdit = Utils.castView(view, R.id.fabEdit, "field 'fabEdit'", FloatingActionButton.class);
    view7f0800bf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabEditClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800c6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabCancel, "field 'fabCancel' and method 'fabCancelClick'");
    target.fabCancel = Utils.castView(view, R.id.fabCancel, "field 'fabCancel'", FloatingActionButton.class);
    view7f0800bb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabCancelClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabAdd, "field 'fabAdd' and method 'fabAddClick'");
    target.fabAdd = Utils.castView(view, R.id.fabAdd, "field 'fabAdd'", FloatingActionButton.class);
    view7f0800b8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabAddClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabPhoto, "field 'fabPhoto' and method 'fabPhotoClick'");
    target.fabPhoto = Utils.castView(view, R.id.fabPhoto, "field 'fabPhoto'", FloatingActionButton.class);
    view7f0800c2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabPhotoClick();
      }
    });
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.activity_main_toolbar, "field 'toolbar'", Toolbar.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AfficherRdvAnalyseActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedRdv = null;
    target.listRdv = null;
    target.layoutAnalyse = null;
    target.textAnalyse = null;
    target.layoutDate = null;
    target.textDate = null;
    target.layoutHeure = null;
    target.textHeure = null;
    target.layoutNote = null;
    target.textNote = null;
    target.fabDelete = null;
    target.fabEdit = null;
    target.fabSave = null;
    target.fabCancel = null;
    target.fabAdd = null;
    target.fabPhoto = null;
    target.toolbar = null;
    target.progressBar = null;

    view7f0800be.setOnClickListener(null);
    view7f0800be = null;
    view7f0800bf.setOnClickListener(null);
    view7f0800bf = null;
    view7f0800c6.setOnClickListener(null);
    view7f0800c6 = null;
    view7f0800bb.setOnClickListener(null);
    view7f0800bb = null;
    view7f0800b8.setOnClickListener(null);
    view7f0800b8 = null;
    view7f0800c2.setOnClickListener(null);
    view7f0800c2 = null;
  }
}
