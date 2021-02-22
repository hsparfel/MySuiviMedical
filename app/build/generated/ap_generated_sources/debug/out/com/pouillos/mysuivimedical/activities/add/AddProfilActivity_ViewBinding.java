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
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddProfilActivity_ViewBinding implements Unbinder {
  private AddProfilActivity target;

  private View view7f0800c8;

  @UiThread
  public AddProfilActivity_ViewBinding(AddProfilActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddProfilActivity_ViewBinding(final AddProfilActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.floating_action_button, "field 'fab' and method 'fabClick'");
    target.fab = Utils.castView(view, R.id.floating_action_button, "field 'fab'", FloatingActionButton.class);
    view7f0800c8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabClick();
      }
    });
    target.textDate = Utils.findRequiredViewAsType(source, R.id.textDate, "field 'textDate'", TextInputEditText.class);
    target.layoutDate = Utils.findRequiredViewAsType(source, R.id.layoutDate, "field 'layoutDate'", TextInputLayout.class);
    target.textImc = Utils.findRequiredViewAsType(source, R.id.textImc, "field 'textImc'", TextInputEditText.class);
    target.layoutImc = Utils.findRequiredViewAsType(source, R.id.layoutImc, "field 'layoutImc'", TextInputLayout.class);
    target.textPoids = Utils.findRequiredViewAsType(source, R.id.textPoids, "field 'textPoids'", TextInputEditText.class);
    target.layoutPoids = Utils.findRequiredViewAsType(source, R.id.layoutPoids, "field 'layoutPoids'", TextInputLayout.class);
    target.textTaille = Utils.findRequiredViewAsType(source, R.id.textTaille, "field 'textTaille'", TextInputEditText.class);
    target.layoutTaille = Utils.findRequiredViewAsType(source, R.id.layoutTaille, "field 'layoutTaille'", TextInputLayout.class);
    target.sliderTaille = Utils.findRequiredViewAsType(source, R.id.sliderTaille, "field 'sliderTaille'", Slider.class);
    target.sliderPoids = Utils.findRequiredViewAsType(source, R.id.sliderPoids, "field 'sliderPoids'", Slider.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddProfilActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fab = null;
    target.textDate = null;
    target.layoutDate = null;
    target.textImc = null;
    target.layoutImc = null;
    target.textPoids = null;
    target.layoutPoids = null;
    target.textTaille = null;
    target.layoutTaille = null;
    target.sliderTaille = null;
    target.sliderPoids = null;
    target.progressBar = null;

    view7f0800c8.setOnClickListener(null);
    view7f0800c8 = null;
  }
}
