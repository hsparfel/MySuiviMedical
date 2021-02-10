// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.recycler.holder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecyclerViewHolderRappel_ViewBinding implements Unbinder {
  private RecyclerViewHolderRappel target;

  @UiThread
  public RecyclerViewHolderRappel_ViewBinding(RecyclerViewHolderRappel target, View source) {
    this.target = target;

    target.detail = Utils.findRequiredViewAsType(source, R.id.detail, "field 'detail'", TextView.class);
    target.buttonDelete = Utils.findRequiredViewAsType(source, R.id.buttonDelete, "field 'buttonDelete'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecyclerViewHolderRappel target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.detail = null;
    target.buttonDelete = null;
  }
}
