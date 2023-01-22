/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Roke
 */
public class Member extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date memberEndingDate;
    private Date memberStartingDate;
    private MembershipPlan plan;

    public Date getMemberEndingDate() {
        return memberEndingDate;
    }

    public void setMemberEndingDate(Date memberEndingDate) {
        this.memberEndingDate = memberEndingDate;
    }

    public Date getMemberStartingDate() {
        return memberStartingDate;
    }

    public void setMemberStartingDate(Date memberStartingDate) {
        this.memberStartingDate = memberStartingDate;
    }

    public MembershipPlan getPlan() {
        return plan;
    }

    public void setPlan(MembershipPlan plan) {
        this.plan = plan;
    }
}
