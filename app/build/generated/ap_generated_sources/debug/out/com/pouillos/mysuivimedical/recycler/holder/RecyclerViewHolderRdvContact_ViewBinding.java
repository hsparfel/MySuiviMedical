// Generated code from Butter Knife. Do not modify!
package com.pouillos.mysuivimedical.recycler.holder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.pouillos.mysuivimedical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecyclerViewHolderRdvContact_ViewBinding implements Unbinder {
  private RecyclerViewHolderRdvContact target;

  @UiThread
  public RecyclerViewHolderRdvContact_ViewBinding(RecyclerViewHolderRdvContact target,
      View source) {
    this.target = target;

    target.detail = Utils.findRequiredViewAsType(source, R.id.detail, "field 'detail'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecyclerViewHolderRdvContact target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.detail = null;
  }
}
