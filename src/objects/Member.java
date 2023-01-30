package objects;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dani
 */
@XmlRootElement(name = "member")
public class Member extends User {

    private static final long serialVersionUID = 1L;
    private Date memberEndingDate;
    private Date memberStartingDate;
    private MembershipPlan plan;

    public Member(
            String login, String email, String fullName, String password,
            Privilege privilege, Status status, Date lastPasswordChange,
            Date memberEndingDate, Date memberStartingDate, MembershipPlan plan) {
        super(login, email, fullName, password, privilege, status, lastPasswordChange);
        this.memberEndingDate = memberEndingDate;
        this.memberStartingDate = memberStartingDate;
        this.plan = plan;
    }

    public Member(Date memberEndingDate, Date memberStartingDate, MembershipPlan plan) {
        this.memberEndingDate = memberEndingDate;
        this.memberStartingDate = memberStartingDate;
        this.plan = plan;
    }

    public Member() {
    }

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
