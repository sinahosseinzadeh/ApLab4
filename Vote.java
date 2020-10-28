import java.util.Objects;

public class Vote {
    private Person person;
    private String date;

    /**
     *
     * @param person is the person who votes
     * @param date is the date of vote
     */
    public Vote(Person person,String date){
        this.person=person;
        this.date=date;
    }

    /**
     *
     * @return returns the person who voted
     */
    public Person getPerson(){
        return person;
    }

    /**
     *
     * @return returns the date vote was submitted
     */
    public String getDate() {
        return date;
    }

    /**
     * this method prints vote details in preferred format
     */
    public void printVote(){
        System.out.println(person.getFirstName()+" "+person.getLastName()+" "+
                date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return getPerson().equals(vote.getPerson()) &&
                getDate().equals(vote.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPerson(), getDate());
    }
}
