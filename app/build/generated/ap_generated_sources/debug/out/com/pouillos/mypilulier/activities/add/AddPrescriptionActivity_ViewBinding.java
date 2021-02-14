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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddPrescriptionActivity_ViewBinding implements Unbinder {
  private AddPrescriptionActivity target;

  private View view7f0800ab;

  @UiThread
  public AddPrescriptionActivity_ViewBinding(AddPrescriptionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddPrescriptionActivity_ViewBinding(final AddPrescriptionActivity target, View source) {
    this.target = target;

    View view;
    target.selectedMedicament = Utils.findRequiredViewAsType(source, R.id.textMedicament, "field 'selectedMedicament'", AutoCompleteTextView.class);
    target.listMedicament = Utils.findRequiredViewAsType(source, R.id.layoutMedicament, "field 'listMedicament'", TextInputLayout.class);
    target.layoutQuantite = Utils.findRequiredViewAsType(source, R.id.layoutQuantite, "field 'layoutQuantite'", TextInputLayout.class);
    target.textQuantite = Utils.findRequiredViewAsType(source, R.id.textQuantite, "field 'textQuantite'", TextInputEditText.class);
    target.layoutDose = Utils.findRequiredViewAsType(source, R.id.layoutDose, "field 'layoutDose'", TextInputLayout.class);
    target.textDose = Utils.findRequiredViewAsType(source, R.id.textDose, "field 'textDose'", TextInputEditText.class);
    target.layoutDate = Utils.findRequiredViewAsType(source, R.id.layoutDate, "field 'layoutDate'", TextInputLayout.class);
    target.textDate = Utils.findRequiredViewAsType(source, R.id.textDate, "field 'textDate'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800ab = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSaveClick();
      }
    });
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddPrescriptionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedMedicament = null;
    target.listMedicament = null;
    target.layoutQuantite = null;
    target.textQuantite = null;
    target.layoutDose = null;
    target.textDose = null;
    target.layoutDate = null;
    target.textDate = null;
    target.fabSave = null;
    target.progressBar = null;

    view7f0800ab.setOnClickListener(null);
    view7f0800ab = null;
  }
}
