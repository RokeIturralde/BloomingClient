package logic;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import logic.objects.Member;
import logic.objects.MembershipPlan;
import logic.objects.Privilege;
import logic.objects.Status;
import logic.objects.User;

public abstract class UserList {

    public static final List<User> everyUser = 
        Arrays.asList(
                new User("opticks", "danielbarrios2002@gmail.com",
                        "Daniel Barrios Abad", "trapedinsadness",
                        Privilege.ADMIN, Status.ENABLE, randomDate()),

                new User("zappa", "zappa@gmail.com",
                        "Frank Vincent Zappa", "suzy-creamcheese",
                        Privilege.ADMIN, Status.DISABLE, randomDate()),

                new User("beefheart", "captainbeefeart@gmail.com",
                        "Don Van Vliet", "laserbeans",
                        Privilege.CLIENT, Status.DISABLE, randomDate()),

                new User("kaukonen", "jormakaukonen@gmail.com",
                        "Jorma Kaukonen", "jeffersonairplane",
                        Privilege.MEMBER, Status.ENABLE, randomDate()),

                new User("iancarr", "nucleus@gmail.com",
                        "Ian Carr", "roots",
                        Privilege.CLIENT, Status.DISABLE, randomDate()),

                new User("tull", "ianderson47@gmail.com",
                        "Ian Anderson", "aqualung",
                        Privilege.MEMBER, Status.ENABLE, randomDate()),

                new User("page", "jimmypage44@gmail.com",
                        "Jimmy Page", "zepp",
                        Privilege.CLIENT, Status.ENABLE, randomDate()),

                new User("hendrix", "jimihendrix@gmail.com",
                        "Jimmy 'James' Hendrix", "haveyoueverbeenexperienced",
                        Privilege.CLIENT, Status.DISABLE, randomDate()));


        public static final List <Member> everyUserAsMember = castToMember();
        

        private static List<Member> castToMember() {
                List<Member> l = new ArrayList<Member>();

                for (User u : everyUser)
                l.add(
                        new Member(u.getLogin(), u.getEmail(),
                                u.getFullName(), u.getPassword(), u.getPrivilege(),
                                u.getStatus(), u.getLastPasswordChange(),
                                randomDate(), randomDate(), 
                                new MembershipPlan((int) Math.random() * 10)));

                return l;
        }

    /**
     * @return a randomly generated exact moment in
     *         history between New Years Day 1899 and now.
     */

    public static Date randomDate() {
        long now = (ZonedDateTime.of(
                LocalDateTime.of(1979, 6, 15, 0, 0),
                ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()),

                yearZero = (ZonedDateTime.of(
                        LocalDateTime.of(1966, 1, 1, 0, 0),
                        ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli());
        return new Date(
                (long) (Math.random() * (now - yearZero)) + yearZero);
    }
}