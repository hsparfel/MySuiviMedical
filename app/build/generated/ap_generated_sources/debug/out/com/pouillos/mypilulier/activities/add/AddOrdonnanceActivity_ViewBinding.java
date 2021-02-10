// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities.add;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddOrdonnanceActivity_ViewBinding implements Unbinder {
  private AddOrdonnanceActivity target;

  private View view7f080090;

  private View view7f080091;

  private View view7f0800b9;

  private View view7f0800c6;

  @UiThread
  public AddOrdonnanceActivity_ViewBinding(AddOrdonnanceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddOrdonnanceActivity_ViewBinding(final AddOrdonnanceActivity target, View source) {
    this.target = target;

    View view;
    target.selectedContact = Utils.findRequiredViewAsType(source, R.id.selectContact, "field 'selectedContact'", AutoCompleteTextView.class);
    target.listContact = Utils.findRequiredViewAsType(source, R.id.listContact, "field 'listContact'", TextInputLayout.class);
    target.selectedRdvContact = Utils.findRequiredViewAsType(source, R.id.selectRdvContact, "field 'selectedRdvContact'", AutoCompleteTextView.class);
    target.listRdvContact = Utils.findRequiredViewAsType(source, R.id.listRdvContact, "field 'listRdvContact'", TextInputLayout.class);
    target.layoutDate = Utils.findRequiredViewAsType(source, R.id.layoutDate, "field 'layoutDate'", TextInputLayout.class);
    target.textDate = Utils.findRequiredViewAsType(source, R.id.textDate, "field 'textDate'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.chipWithRdv, "field 'chipWithRdv' and method 'chipWithRdvClick'");
    target.chipWithRdv = Utils.castView(view, R.id.chipWithRdv, "field 'chipWithRdv'", Chip.class);
    view7f080090 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipWithRdvClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.chipWithoutRdv, "field 'chipWithoutRdv' and method 'chipWithoutRdvClick'");
    target.chipWithoutRdv = Utils.castView(view, R.id.chipWithoutRdv, "field 'chipWithoutRdv'", Chip.class);
    view7f080091 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipWithoutRdvClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabAddPrescription, "field 'fabAddPrescription' and method 'fabAddPrescriptionClick'");
    target.fabAddPrescription = Utils.castView(view, R.id.fabAddPrescription, "field 'fabAddPrescription'", FloatingActionButton.class);
    view7f0800b9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabAddPrescriptionClick();
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
    target.listPrescription = Utils.findRequiredViewAsType(source, R.id.listPrescription, "field 'listPrescription'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddOrdonnanceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedContact = null;
    target.listContact = null;
    target.selectedRdvContact = null;
    target.listRdvContact = null;
    target.layoutDate = null;
    target.textDate = null;
    target.chipWithRdv = null;
    target.chipWithoutRdv = null;
    target.fabAddPrescription = null;
    target.fabSave = null;
    target.listPrescription = null;
    target.progressBar = null;

    view7f080090.setOnClickListener(null);
    view7f080090 = null;
    view7f080091.setOnClickListener(null);
    view7f080091 = null;
    view7f0800b9.setOnClickListener(null);
    view7f0800b9 = null;
    view7f0800c6.setOnClickListener(null);
    view7f0800c6 = null;
  }
}
