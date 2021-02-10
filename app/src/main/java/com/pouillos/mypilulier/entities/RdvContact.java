package com.pouillos.mypilulier.entities;

import com.pouillos.mypilulier.activities.utils.DateUtils;

public class RdvContact extends Rdv {

    private Contact contact;

    public RdvContact() {
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return DateUtils.ecrireDateHeure(date) + " - " + contact.getNom();
    }
}
