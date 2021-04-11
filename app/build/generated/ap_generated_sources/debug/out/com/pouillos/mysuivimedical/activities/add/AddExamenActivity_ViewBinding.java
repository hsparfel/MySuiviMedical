// Generated code from Butter Knife. Do not modify!
package com.pouillos.mysuivimedical.activities.add;

import android.view.View;
import android.widget.ProgressBar;
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

public class AddExamenActivity_ViewBinding implements Unbinder {
  private AddExamenActivity target;

  private View view7f0800bb;

  @UiThread
  public AddExamenActivity_ViewBinding(AddExamenActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddExamenActivity_ViewBinding(final AddExamenActivity target, View source) {
    this.target = target;

    View view;
    target.layoutName = Utils.findRequiredViewAsType(source, R.id.layoutName, "field 'layoutName'", TextInputLayout.class);
    target.textName = Utils.findRequiredViewAsType(source, R.id.textName, "field 'textName'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800bb = view;
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
    AddExamenActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layoutName = null;
    target.textName = null;
    target.fabSave = null;
    target.progressBar = null;

    view7f0800bb.setOnClickListener(null);
    view7f0800bb = null;
  }
}
