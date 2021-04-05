// Generated code from Butter Knife. Do not modify!
package com.pouillos.mysuivimedical.activities.afficher;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ortiz.touchview.TouchImageView;
import com.pouillos.mysuivimedical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AfficherPhotoActivity_ViewBinding implements Unbinder {
  private AfficherPhotoActivity target;

  private View view7f0800bf;

  private View view7f0800b8;

  private View view7f080076;

  private View view7f080071;

  private View view7f080073;

  @UiThread
  public AfficherPhotoActivity_ViewBinding(AfficherPhotoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AfficherPhotoActivity_ViewBinding(final AfficherPhotoActivity target, View source) {
    this.target = target;

    View view;
    target.selectedItem = Utils.findRequiredViewAsType(source, R.id.selectItem, "field 'selectedItem'", AutoCompleteTextView.class);
    target.listItem = Utils.findRequiredViewAsType(source, R.id.listItem, "field 'listItem'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.fabShare, "field 'fabShare' and method 'fabShareClick'");
    target.fabShare = Utils.castView(view, R.id.fabShare, "field 'fabShare'", FloatingActionButton.class);
    view7f0800bf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabShareClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabDelete, "field 'fabDelete' and method 'fabDeleteClick'");
    target.fabDelete = Utils.castView(view, R.id.fabDelete, "field 'fabDelete'", FloatingActionButton.class);
    view7f0800b8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabDeleteClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.chipOrdonnance, "field 'chipOrdonnance' and method 'chipOrdonnanceClick'");
    target.chipOrdonnance = Utils.castView(view, R.id.chipOrdonnance, "field 'chipOrdonnance'", Chip.class);
    view7f080076 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipOrdonnanceClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.chipAnalyse, "field 'chipAnalyse' and method 'chipAnalyseClick'");
    target.chipAnalyse = Utils.castView(view, R.id.chipAnalyse, "field 'chipAnalyse'", Chip.class);
    view7f080071 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipAnalyseClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.chipExamen, "field 'chipExamen' and method 'chipExamenClick'");
    target.chipExamen = Utils.castView(view, R.id.chipExamen, "field 'chipExamen'", Chip.class);
    view7f080073 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipExamenClick();
      }
    });
    target.chipGroupType = Utils.findRequiredViewAsType(source, R.id.chipGroupType, "field 'chipGroupType'", ChipGroup.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.imageView, "field 'imageView'", TouchImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AfficherPhotoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedItem = null;
    target.listItem = null;
    target.fabShare = null;
    target.fabDelete = null;
    target.chipOrdonnance = null;
    target.chipAnalyse = null;
    target.chipExamen = null;
    target.chipGroupType = null;
    target.progressBar = null;
    target.imageView = null;

    view7f0800bf.setOnClickListener(null);
    view7f0800bf = null;
    view7f0800b8.setOnClickListener(null);
    view7f0800b8 = null;
    view7f080076.setOnClickListener(null);
    view7f080076 = null;
    view7f080071.setOnClickListener(null);
    view7f080071 = null;
    view7f080073.setOnClickListener(null);
    view7f080073 = null;
  }
}
