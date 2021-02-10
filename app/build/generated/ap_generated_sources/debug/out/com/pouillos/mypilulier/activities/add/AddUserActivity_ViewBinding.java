// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities.add;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddUserActivity_ViewBinding implements Unbinder {
  private AddUserActivity target;

  private View view7f0800c6;

  private View view7f0800bb;

  private View view7f0800bf;

  private View view7f0800be;

  @UiThread
  public AddUserActivity_ViewBinding(AddUserActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddUserActivity_ViewBinding(final AddUserActivity target, View source) {
    this.target = target;

    View view;
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
    target.selectedDepartement = Utils.findRequiredViewAsType(source, R.id.selectDepartement, "field 'selectedDepartement'", AutoCompleteTextView.class);
    target.listDepartement = Utils.findRequiredViewAsType(source, R.id.listDepartement, "field 'listDepartement'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800c6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabCancel, "field 'fabCancel' and method 'setfabCancelClick'");
    target.fabCancel = Utils.castView(view, R.id.fabCancel, "field 'fabCancel'", FloatingActionButton.class);
    view7f0800bb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setfabCancelClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabEdit, "field 'fabEdit' and method 'setFabEditClick'");
    target.fabEdit = Utils.castView(view, R.id.fabEdit, "field 'fabEdit'", FloatingActionButton.class);
    view7f0800bf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setFabEditClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabDelete, "field 'fabDelete' and method 'setfabDeleteClick'");
    target.fabDelete = Utils.castView(view, R.id.fabDelete, "field 'fabDelete'", FloatingActionButton.class);
    view7f0800be = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setfabDeleteClick();
      }
    });
    target.textName = Utils.findRequiredViewAsType(source, R.id.textName, "field 'textName'", TextInputEditText.class);
    target.layoutName = Utils.findRequiredViewAsType(source, R.id.layoutName, "field 'layoutName'", TextInputLayout.class);
    target.chipMan = Utils.findRequiredViewAsType(source, R.id.chipMan, "field 'chipMan'", Chip.class);
    target.chipWoman = Utils.findRequiredViewAsType(source, R.id.chipWoman, "field 'chipWoman'", Chip.class);
    target.chipSexe = Utils.findRequiredViewAsType(source, R.id.chipSexe, "field 'chipSexe'", ChipGroup.class);
    target.textBirthday = Utils.findRequiredViewAsType(source, R.id.textBirthday, "field 'textBirthday'", TextInputEditText.class);
    target.layoutBirthday = Utils.findRequiredViewAsType(source, R.id.layoutBirthday, "field 'layoutBirthday'", TextInputLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddUserActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
    target.selectedDepartement = null;
    target.listDepartement = null;
    target.fabSave = null;
    target.fabCancel = null;
    target.fabEdit = null;
    target.fabDelete = null;
    target.textName = null;
    target.layoutName = null;
    target.chipMan = null;
    target.chipWoman = null;
    target.chipSexe = null;
    target.textBirthday = null;
    target.layoutBirthday = null;

    view7f0800c6.setOnClickListener(null);
    view7f0800c6 = null;
    view7f0800bb.setOnClickListener(null);
    view7f0800bb = null;
    view7f0800bf.setOnClickListener(null);
    view7f0800bf = null;
    view7f0800be.setOnClickListener(null);
    view7f0800be = null;
  }
}
