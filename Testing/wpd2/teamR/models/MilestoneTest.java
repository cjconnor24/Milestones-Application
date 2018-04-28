package wpd2.teamR.models;

import org.junit.Test;


import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class MilestoneTest {


    @Test
    public void getPrettyDateCreated() {
        Milestone milestone = new Milestone(1,"Test","Test",new Timestamp(946684799),new Timestamp(946684799),new Timestamp(946684799),new Timestamp(946684799),1);
        String evalute = milestone.getPrettyDateCreated();
        assertEquals(evalute,"5 decades ago");
    }

    @Test
    public void getPrettyDateModified() {
        Milestone milestone = new Milestone(1,"Test","Test",new Timestamp(946684799),new Timestamp(946684799),new Timestamp(946684799),new Timestamp(946684799),1);
        String evalute = milestone.getPrettyDateModified();
        assertEquals(evalute,"5 decades ago");
    }

    @Test
    public void getDueDay() {
        Milestone milestone = new Milestone(1,"Test","Test",new Timestamp(946684799),new Timestamp(946684799),new Timestamp(946684799),new Timestamp(946684799),1);
        String evalute = milestone.getDueDay();
        assertEquals(evalute,"11");
    }
    
}