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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mypilulier.R;
import com.shawnlin.numberpicker.NumberPicker;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddRappelActivity_ViewBinding implements Unbinder {
  private AddRappelActivity target;

  private View view7f0800bd;

  @UiThread
  public AddRappelActivity_ViewBinding(AddRappelActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddRappelActivity_ViewBinding(final AddRappelActivity target, View source) {
    this.target = target;

    View view;
    target.selectedDose = Utils.findRequiredViewAsType(source, R.id.selectionDose, "field 'selectedDose'", AutoCompleteTextView.class);
    target.listDose = Utils.findRequiredViewAsType(source, R.id.listDose, "field 'listDose'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800bd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSaveClick();
      }
    });
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
    target.numberPickerHeure = Utils.findRequiredViewAsType(source, R.id.numberPickerHeure, "field 'numberPickerHeure'", NumberPicker.class);
    target.numberPickerMinute = Utils.findRequiredViewAsType(source, R.id.numberPickerMinute, "field 'numberPickerMinute'", NumberPicker.class);
    target.numberPickerQuantiteDose = Utils.findRequiredViewAsType(source, R.id.numberPickerQuantiteDose, "field 'numberPickerQuantiteDose'", NumberPicker.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddRappelActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedDose = null;
    target.listDose = null;
    target.fabSave = null;
    target.progressBar = null;
    target.numberPickerHeure = null;
    target.numberPickerMinute = null;
    target.numberPickerQuantiteDose = null;

    view7f0800bd.setOnClickListener(null);
    view7f0800bd = null;
  }
}
