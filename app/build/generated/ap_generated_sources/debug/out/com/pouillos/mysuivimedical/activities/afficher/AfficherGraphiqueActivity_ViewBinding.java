// Generated code from Butter Knife. Do not modify!
package com.pouillos.mysuivimedical.activities.afficher;

import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.chip.Chip;
import com.pouillos.mysuivimedical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AfficherGraphiqueActivity_ViewBinding implements Unbinder {
  private AfficherGraphiqueActivity target;

  private View view7f080077;

  private View view7f080078;

  private View view7f080075;

  @UiThread
  public AfficherGraphiqueActivity_ViewBinding(AfficherGraphiqueActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AfficherGraphiqueActivity_ViewBinding(final AfficherGraphiqueActivity target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.chipPoids, "field 'chipPoids' and method 'chipPoidsClick'");
    target.chipPoids = Utils.castView(view, R.id.chipPoids, "field 'chipPoids'", Chip.class);
    view7f080077 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipPoidsClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.chipTaille, "field 'chipTaille' and method 'chipTailleClick'");
    target.chipTaille = Utils.castView(view, R.id.chipTaille, "field 'chipTaille'", Chip.class);
    view7f080078 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipTailleClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.chipImc, "field 'chipImc' and method 'chipImcClick'");
    target.chipImc = Utils.castView(view, R.id.chipImc, "field 'chipImc'", Chip.class);
    view7f080075 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipImcClick();
      }
    });
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
    target.chart = Utils.findRequiredViewAsType(source, R.id.chart1, "field 'chart'", LineChart.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AfficherGraphiqueActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.chipPoids = null;
    target.chipTaille = null;
    target.chipImc = null;
    target.progressBar = null;
    target.chart = null;

    view7f080077.setOnClickListener(null);
    view7f080077 = null;
    view7f080078.setOnClickListener(null);
    view7f080078 = null;
    view7f080075.setOnClickListener(null);
    view7f080075 = null;
  }
}
