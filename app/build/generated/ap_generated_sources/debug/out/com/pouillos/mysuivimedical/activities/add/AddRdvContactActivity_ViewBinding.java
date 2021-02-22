// Generated code from Butter Knife. Do not modify!
package com.pouillos.mysuivimedical.activities.add;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddRdvContactActivity_ViewBinding implements Unbinder {
  private AddRdvContactActivity target;

  private View view7f0800ba;

  @UiThread
  public AddRdvContactActivity_ViewBinding(AddRdvContactActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddRdvContactActivity_ViewBinding(final AddRdvContactActivity target, View source) {
    this.target = target;

    View view;
    target.layoutContact = Utils.findRequiredViewAsType(source, R.id.layoutContact, "field 'layoutContact'", TextInputLayout.class);
    target.textContact = Utils.findRequiredViewAsType(source, R.id.textContact, "field 'textContact'", TextInputEditText.class);
    target.layoutDate = Utils.findRequiredViewAsType(source, R.id.layoutDate, "field 'layoutDate'", TextInputLayout.class);
    target.textDate = Utils.findRequiredViewAsType(source, R.id.textDate, "field 'textDate'", TextInputEditText.class);
    target.layoutHeure = Utils.findRequiredViewAsType(source, R.id.layoutHeure, "field 'layoutHeure'", TextInputLayout.class);
    target.textHeure = Utils.findRequiredViewAsType(source, R.id.textHeure, "field 'textHeure'", TextInputEditText.class);
    target.layoutNote = Utils.findRequiredViewAsType(source, R.id.layoutNote, "field 'layoutNote'", TextInputLayout.class);
    target.textNote = Utils.findRequiredViewAsType(source, R.id.textNote, "field 'textNote'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800ba = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSaveClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AddRdvContactActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layoutContact = null;
    target.textContact = null;
    target.layoutDate = null;
    target.textDate = null;
    target.layoutHeure = null;
    target.textHeure = null;
    target.layoutNote = null;
    target.textNote = null;
    target.fabSave = null;

    view7f0800ba.setOnClickListener(null);
    view7f0800ba = null;
  }
}
