// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities.add;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mypilulier.R;
import com.shawnlin.numberpicker.NumberPicker;
import java.lang.CharSequence;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddPrescriptionActivity_ViewBinding implements Unbinder {
  private AddPrescriptionActivity target;

  private View view7f0801e5;

  private TextWatcher view7f0801e5TextWatcher;

  private View view7f0801df;

  private TextWatcher view7f0801dfTextWatcher;

  private View view7f0800c6;

  private View view7f0800ba;

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
    target.layoutFrequence = Utils.findRequiredViewAsType(source, R.id.layoutFrequence, "field 'layoutFrequence'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.textFrequence, "field 'textFrequence', method 'textFrequenceClick', and method 'textFrequenceChanged'");
    target.textFrequence = Utils.castView(view, R.id.textFrequence, "field 'textFrequence'", TextInputEditText.class);
    view7f0801e5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textFrequenceClick();
      }
    });
    view7f0801e5TextWatcher = new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence p0, int p1, int p2, int p3) {
        target.textFrequenceChanged();
      }

      @Override
      public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
      }

      @Override
      public void afterTextChanged(Editable p0) {
      }
    };
    ((TextView) view).addTextChangedListener(view7f0801e5TextWatcher);
    target.layoutDuree = Utils.findRequiredViewAsType(source, R.id.layoutDuree, "field 'layoutDuree'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.textDuree, "field 'textDuree', method 'textDureeClick', and method 'textDureeChanged'");
    target.textDuree = Utils.castView(view, R.id.textDuree, "field 'textDuree'", TextInputEditText.class);
    view7f0801df = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textDureeClick();
      }
    });
    view7f0801dfTextWatcher = new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence p0, int p1, int p2, int p3) {
        target.textDureeChanged();
      }

      @Override
      public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
      }

      @Override
      public void afterTextChanged(Editable p0) {
      }
    };
    ((TextView) view).addTextChangedListener(view7f0801dfTextWatcher);
    target.layoutDate = Utils.findRequiredViewAsType(source, R.id.layoutDate, "field 'layoutDate'", TextInputLayout.class);
    target.textDate = Utils.findRequiredViewAsType(source, R.id.textDate, "field 'textDate'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800c6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabAddRappel, "field 'fabAddRappel' and method 'fabAddRappelClick'");
    target.fabAddRappel = Utils.castView(view, R.id.fabAddRappel, "field 'fabAddRappel'", ExtendedFloatingActionButton.class);
    view7f0800ba = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabAddRappelClick();
      }
    });
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
    target.rbWhenNeeded = Utils.findRequiredViewAsType(source, R.id.rbWhenNeeded, "field 'rbWhenNeeded'", RadioButton.class);
    target.rbEveryDay = Utils.findRequiredViewAsType(source, R.id.rbEveryDay, "field 'rbEveryDay'", RadioButton.class);
    target.rbEveryDayByHour = Utils.findRequiredViewAsType(source, R.id.rbEveryDayByHour, "field 'rbEveryDayByHour'", RadioButton.class);
    target.rbEveryXDays = Utils.findRequiredViewAsType(source, R.id.rbEveryXDays, "field 'rbEveryXDays'", RadioButton.class);
    target.rbChosenDays = Utils.findRequiredViewAsType(source, R.id.rbChosenDays, "field 'rbChosenDays'", RadioButton.class);
    target.rbNoEnding = Utils.findRequiredViewAsType(source, R.id.rbNoEnding, "field 'rbNoEnding'", RadioButton.class);
    target.rbUntilDate = Utils.findRequiredViewAsType(source, R.id.rbUntilDate, "field 'rbUntilDate'", RadioButton.class);
    target.rbDuringDays = Utils.findRequiredViewAsType(source, R.id.rbDuringDays, "field 'rbDuringDays'", RadioButton.class);
    target.chipLundi = Utils.findRequiredViewAsType(source, R.id.chipLundi, "field 'chipLundi'", Chip.class);
    target.chipMardi = Utils.findRequiredViewAsType(source, R.id.chipMardi, "field 'chipMardi'", Chip.class);
    target.chipMercredi = Utils.findRequiredViewAsType(source, R.id.chipMercredi, "field 'chipMercredi'", Chip.class);
    target.chipJeudi = Utils.findRequiredViewAsType(source, R.id.chipJeudi, "field 'chipJeudi'", Chip.class);
    target.chipVendredi = Utils.findRequiredViewAsType(source, R.id.chipVendredi, "field 'chipVendredi'", Chip.class);
    target.chipSamedi = Utils.findRequiredViewAsType(source, R.id.chipSamedi, "field 'chipSamedi'", Chip.class);
    target.chipDimanche = Utils.findRequiredViewAsType(source, R.id.chipDimanche, "field 'chipDimanche'", Chip.class);
    target.chipGroupJour = Utils.findRequiredViewAsType(source, R.id.chipGroupJour, "field 'chipGroupJour'", ChipGroup.class);
    target.numberPickerFrequence = Utils.findRequiredViewAsType(source, R.id.numberPickerFrequence, "field 'numberPickerFrequence'", NumberPicker.class);
    target.numberPickerDuree = Utils.findRequiredViewAsType(source, R.id.numberPickerDuree, "field 'numberPickerDuree'", NumberPicker.class);
    target.preFreq = Utils.findRequiredViewAsType(source, R.id.preFreq, "field 'preFreq'", TextView.class);
    target.preDuree = Utils.findRequiredViewAsType(source, R.id.preDuree, "field 'preDuree'", TextView.class);
    target.postFreq = Utils.findRequiredViewAsType(source, R.id.postFreq, "field 'postFreq'", TextView.class);
    target.postDuree = Utils.findRequiredViewAsType(source, R.id.postDuree, "field 'postDuree'", TextView.class);
    target.layoutFrequenceOption = Utils.findRequiredViewAsType(source, R.id.layoutFrequenceOption, "field 'layoutFrequenceOption'", LinearLayout.class);
    target.layoutDureeOption = Utils.findRequiredViewAsType(source, R.id.layoutDureeOption, "field 'layoutDureeOption'", LinearLayout.class);
    target.listRappel = Utils.findRequiredViewAsType(source, R.id.listRappel, "field 'listRappel'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddPrescriptionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedMedicament = null;
    target.listMedicament = null;
    target.layoutFrequence = null;
    target.textFrequence = null;
    target.layoutDuree = null;
    target.textDuree = null;
    target.layoutDate = null;
    target.textDate = null;
    target.fabSave = null;
    target.fabAddRappel = null;
    target.progressBar = null;
    target.rbWhenNeeded = null;
    target.rbEveryDay = null;
    target.rbEveryDayByHour = null;
    target.rbEveryXDays = null;
    target.rbChosenDays = null;
    target.rbNoEnding = null;
    target.rbUntilDate = null;
    target.rbDuringDays = null;
    target.chipLundi = null;
    target.chipMardi = null;
    target.chipMercredi = null;
    target.chipJeudi = null;
    target.chipVendredi = null;
    target.chipSamedi = null;
    target.chipDimanche = null;
    target.chipGroupJour = null;
    target.numberPickerFrequence = null;
    target.numberPickerDuree = null;
    target.preFreq = null;
    target.preDuree = null;
    target.postFreq = null;
    target.postDuree = null;
    target.layoutFrequenceOption = null;
    target.layoutDureeOption = null;
    target.listRappel = null;

    view7f0801e5.setOnClickListener(null);
    ((TextView) view7f0801e5).removeTextChangedListener(view7f0801e5TextWatcher);
    view7f0801e5TextWatcher = null;
    view7f0801e5 = null;
    view7f0801df.setOnClickListener(null);
    ((TextView) view7f0801df).removeTextChangedListener(view7f0801dfTextWatcher);
    view7f0801dfTextWatcher = null;
    view7f0801df = null;
    view7f0800c6.setOnClickListener(null);
    view7f0800c6 = null;
    view7f0800ba.setOnClickListener(null);
    view7f0800ba = null;
  }
}
