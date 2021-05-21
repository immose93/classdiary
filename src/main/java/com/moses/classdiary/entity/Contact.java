package com.moses.classdiary.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private String contact;
    private String emergency1;
    private String emergency2;

    public Contact(Contact contact) {
        if(contact.contact != null) this.contact = contact.contact;
        else this.contact = "";
        if(contact.emergency1 != null) this.emergency1 = contact.emergency1;
        else this.emergency1 = "";
        if(contact.emergency2 != null) this.emergency2 = contact.emergency2;
        else this.emergency2 = "";
    }
}
