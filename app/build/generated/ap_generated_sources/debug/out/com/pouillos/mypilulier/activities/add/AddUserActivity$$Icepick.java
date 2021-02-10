// Generated code from Icepick. Do not modify!
package com.pouillos.mypilulier.activities.add;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class AddUserActivity$$Icepick<T extends AddUserActivity> extends com.pouillos.mypilulier.activities.NavDrawerActivity$$Icepick<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
            
  }

  private final static Helper H = new Helper("com.pouillos.mypilulier.activities.add.AddUserActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.userToModify = H.getSerializable(state, "userToModify");
    target.dateOfBirth = H.getSerializable(state, "dateOfBirth");
    target.departement = H.getSerializable(state, "departement");
    target.userToCreate = H.getSerializable(state, "userToCreate");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putSerializable(state, "userToModify", target.userToModify);
    H.putSerializable(state, "dateOfBirth", target.dateOfBirth);
    H.putSerializable(state, "departement", target.departement);
    H.putSerializable(state, "userToCreate", target.userToCreate);
  }
}