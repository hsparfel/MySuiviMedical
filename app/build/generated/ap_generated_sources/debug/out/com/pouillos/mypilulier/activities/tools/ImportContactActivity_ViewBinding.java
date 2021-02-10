// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities.tools;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImportContactActivity_ViewBinding implements Unbinder {
  private ImportContactActivity target;

  private View view7f0800c1;

  private View view7f0800c9;

  @UiThread
  public ImportContactActivity_ViewBinding(ImportContactActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ImportContactActivity_ViewBinding(final ImportContactActivity target, View source) {
    this.target = target;

    View view;
    target.nbContactImported = Utils.findRequiredViewAsType(source, R.id.nbContactImported, "field 'nbContactImported'", TextView.class);
    view = Utils.findRequiredView(source, R.id.fabImport, "field 'fabImport' and method 'fabImportClick'");
    target.fabImport = Utils.castView(view, R.id.fabImport, "field 'fabImport'", FloatingActionButton.class);
    view7f0800c1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabImportClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSupprDoublon, "field 'fabSupprDoublon' and method 'fabSupprDoublonClick'");
    target.fabSupprDoublon = Utils.castView(view, R.id.fabSupprDoublon, "field 'fabSupprDoublon'", FloatingActionButton.class);
    view7f0800c9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSupprDoublonClick();
      }
    });
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImportContactActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.nbContactImported = null;
    target.fabImport = null;
    target.fabSupprDoublon = null;
    target.progressBar = null;

    view7f0800c1.setOnClickListener(null);
    view7f0800c1 = null;
    view7f0800c9.setOnClickListener(null);
    view7f0800c9 = null;
  }
}
