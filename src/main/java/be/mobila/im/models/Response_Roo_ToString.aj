// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import java.lang.String;

privileged aspect Response_Roo_ToString {
    
    public String Response.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("ResponseDate: ").append(getResponseDate()).append(", ");
        sb.append("ResponseMode: ").append(getResponseMode()).append(", ");
        sb.append("ImRequest: ").append(getImRequest()).append(", ");
        sb.append("Status: ").append(getStatus()).append(", ");
        sb.append("Imresult: ").append(getImresult()).append(", ");
        sb.append("Address: ").append(getAddress());
        return sb.toString();
    }
    
}
