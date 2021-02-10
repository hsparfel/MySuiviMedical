// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities;

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
import java.lang.IllegalStateException;
import java.lang.Override;

public class AuthentificationActivity_ViewBinding implements Unbinder {
  private AuthentificationActivity target;

  private View view7f080056;

  @UiThread
  public AuthentificationActivity_ViewBinding(AuthentificationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AuthentificationActivity_ViewBinding(final AuthentificationActivity target, View source) {
    this.target = target;

    View view;
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
    target.selectedUser = Utils.findRequiredViewAsType(source, R.id.selectUser, "field 'selectedUser'", AutoCompleteTextView.class);
    target.listUser = Utils.findRequiredViewAsType(source, R.id.listUser, "field 'listUser'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.authFab, "field 'authFab' and method 'fabClick'");
    target.authFab = Utils.castView(view, R.id.authFab, "field 'authFab'", FloatingActionButton.class);
    view7f080056 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AuthentificationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
    target.selectedUser = null;
    target.listUser = null;
    target.authFab = null;

    view7f080056.setOnClickListener(null);
    view7f080056 = null;
  }
}
