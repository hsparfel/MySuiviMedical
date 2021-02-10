// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities.recherche;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mypilulier.R;
import java.lang.CharSequence;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChercherEtablissementActivity_ViewBinding implements Unbinder {
  private ChercherEtablissementActivity target;

  private View view7f0801f9;

  private TextWatcher view7f0801f9TextWatcher;

  private View view7f08008f;

  private View view7f080078;

  private View view7f080087;

  private View view7f080077;

  @UiThread
  public ChercherEtablissementActivity_ViewBinding(ChercherEtablissementActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChercherEtablissementActivity_ViewBinding(final ChercherEtablissementActivity target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.textZip, "field 'textZip' and method 'textZipChange'");
    target.textZip = Utils.castView(view, R.id.textZip, "field 'textZip'", TextInputEditText.class);
    view7f0801f9 = view;
    view7f0801f9TextWatcher = new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence p0, int p1, int p2, int p3) {
        target.textZipChange();
      }

      @Override
      public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
      }

      @Override
      public void afterTextChanged(Editable p0) {
      }
    };
    ((TextView) view).addTextChangedListener(view7f0801f9TextWatcher);
    target.layoutZip = Utils.findRequiredViewAsType(source, R.id.layoutZip, "field 'layoutZip'", TextInputLayout.class);
    target.listeEtablissement = Utils.findRequiredViewAsType(source, R.id.list_etablissement, "field 'listeEtablissement'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.chipVille, "field 'chipVille' and method 'chipVilleClick'");
    target.chipVille = Utils.castView(view, R.id.chipVille, "field 'chipVille'", Chip.class);
    view7f08008f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipVilleClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.chipCp, "field 'chipCp' and method 'chipCpClick'");
    target.chipCp = Utils.castView(view, R.id.chipCp, "field 'chipCp'", Chip.class);
    view7f080078 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipCpClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.chipPharmacie, "field 'chipPharmacie' and method 'chipPharmacieClick'");
    target.chipPharmacie = Utils.castView(view, R.id.chipPharmacie, "field 'chipPharmacie'", Chip.class);
    view7f080087 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipPharmacieClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.chipAutreType, "field 'chipAutreType' and method 'chipAutreTypeClick'");
    target.chipAutreType = Utils.castView(view, R.id.chipAutreType, "field 'chipAutreType'", Chip.class);
    view7f080077 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chipAutreTypeClick();
      }
    });
    target.chipGroupRechercheType = Utils.findRequiredViewAsType(source, R.id.chipGroupRechercheType, "field 'chipGroupRechercheType'", ChipGroup.class);
    target.fabChercher = Utils.findRequiredViewAsType(source, R.id.fabChercher, "field 'fabChercher'", FloatingActionButton.class);
    target.fabRaz = Utils.findRequiredViewAsType(source, R.id.fabRaz, "field 'fabRaz'", FloatingActionButton.class);
    target.selectionVille = Utils.findRequiredViewAsType(source, R.id.selectionVille, "field 'selectionVille'", AutoCompleteTextView.class);
    target.listVille = Utils.findRequiredViewAsType(source, R.id.listVille, "field 'listVille'", TextInputLayout.class);
    target.selectionAutreType = Utils.findRequiredViewAsType(source, R.id.selectionAutreType, "field 'selectionAutreType'", AutoCompleteTextView.class);
    target.listAutreType = Utils.findRequiredViewAsType(source, R.id.listAutreType, "field 'listAutreType'", TextInputLayout.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChercherEtablissementActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textZip = null;
    target.layoutZip = null;
    target.listeEtablissement = null;
    target.chipVille = null;
    target.chipCp = null;
    target.chipPharmacie = null;
    target.chipAutreType = null;
    target.chipGroupRechercheType = null;
    target.fabChercher = null;
    target.fabRaz = null;
    target.selectionVille = null;
    target.listVille = null;
    target.selectionAutreType = null;
    target.listAutreType = null;
    target.progressBar = null;

    ((TextView) view7f0801f9).removeTextChangedListener(view7f0801f9TextWatcher);
    view7f0801f9TextWatcher = null;
    view7f0801f9 = null;
    view7f08008f.setOnClickListener(null);
    view7f08008f = null;
    view7f080078.setOnClickListener(null);
    view7f080078 = null;
    view7f080087.setOnClickListener(null);
    view7f080087 = null;
    view7f080077.setOnClickListener(null);
    view7f080077 = null;
  }
}
