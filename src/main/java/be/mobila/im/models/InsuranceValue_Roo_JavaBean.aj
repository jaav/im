// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.Insurance;
import java.lang.Double;

privileged aspect InsuranceValue_Roo_JavaBean {
    
    public Double InsuranceValue.getProposal() {
        return this.proposal;
    }
    
    public void InsuranceValue.setProposal(Double proposal) {
        this.proposal = proposal;
    }
    
    public Insurance InsuranceValue.getInsurance() {
        return this.insurance;
    }
    
    public void InsuranceValue.setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
    
}
