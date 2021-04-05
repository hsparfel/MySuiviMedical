// Generated code from Butter Knife. Do not modify!
package com.pouillos.mysuivimedical.activities.photo;

import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pouillos.mysuivimedical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MakePhotoActivity_ViewBinding implements Unbinder {
  private MakePhotoActivity target;

  private View view7f0800c1;

  private View view7f0800be;

  private View view7f0800b7;

  @UiThread
  public MakePhotoActivity_ViewBinding(MakePhotoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MakePhotoActivity_ViewBinding(final MakePhotoActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.fabTakePhoto, "field 'fabTakePhoto' and method 'fabTakePhotoClick'");
    target.fabTakePhoto = Utils.castView(view, R.id.fabTakePhoto, "field 'fabTakePhoto'", FloatingActionButton.class);
    view7f0800c1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabTakePhotoClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabSavePhoto, "field 'fabSavePhoto' and method 'fabSavePhotoClick'");
    target.fabSavePhoto = Utils.castView(view, R.id.fabSavePhoto, "field 'fabSavePhoto'", FloatingActionButton.class);
    view7f0800be = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSavePhotoClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabCancelPhoto, "field 'fabCancelPhoto' and method 'fabCancelPhotoClick'");
    target.fabCancelPhoto = Utils.castView(view, R.id.fabCancelPhoto, "field 'fabCancelPhoto'", FloatingActionButton.class);
    view7f0800b7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabCancelPhotoClick();
      }
    });
    target.previewFL = Utils.findRequiredViewAsType(source, R.id.preview_layout, "field 'previewFL'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MakePhotoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fabTakePhoto = null;
    target.fabSavePhoto = null;
    target.fabCancelPhoto = null;
    target.previewFL = null;

    view7f0800c1.setOnClickListener(null);
    view7f0800c1 = null;
    view7f0800be.setOnClickListener(null);
    view7f0800be = null;
    view7f0800b7.setOnClickListener(null);
    view7f0800b7 = null;
  }
}
