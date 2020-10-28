import ir.huri.jcal.JalaliCalendar;

import java.util.*;

public class Voting {
    private int type;
    private String question;
    private ArrayList<Person> voters;
    private HashMap <String,HashSet<Vote>> listOfVotesToChoices;

    /**
     *
     * @param type is the type of voting .0 for single and 1 for multi voting mode
     * @param question is the question to be voted about
     */
    public Voting(int type,String question) {
        this.type=type;
        this.question=question;
        listOfVotesToChoices=new HashMap<String,HashSet<Vote>>();
        voters=new ArrayList<Person>();
    }

    /**
     * is used when voting system wants to print all of voting qustions
     * @return returns the question of the voting
     */
    public String getQuestion(){
        return question;
    }

    /**
     *
     * @param person is the person who votes
     * @param choices are the choice(s) of the person to vote to
     */
    public void vote(Person person ,ArrayList <String> choices){
        // in single vote mode choice list cant have more than 1 element
        // it is handled in VotingSystem vote method
        if(type==0 && choices.size()>1){
            System.out.println("You can not have several choices for single vote mode");
        }
        //handles if one person wanted to vote several time
        else if(checkPersonEquality(person)){
            System.out.println("This person voted earlier. same person can not vote several times.");
        }
        else {
            for(String choice:choices){
                //use choice as key to add new vote to its value (HashSet)
                listOfVotesToChoices.get(choice).add(new Vote(person,dateOfVote()));
            }
            voters.add(person);
            System.out.println("vote submitted.");
        }
    }
    public ArrayList<Person> getVoters() {
        return voters;
    }

    /**
     * this method is used when a new vote gonna be submitted
     * @return it returns the present date in jalali format
     */
    private String dateOfVote(){
        GregorianCalendar presentDate=new GregorianCalendar();
        int year= presentDate.get(Calendar.YEAR);
        //because month in calendars(jalali and gregorian ) is 0 based
        int month=presentDate.get(Calendar.MONTH);
        int day=presentDate.get(Calendar.DAY_OF_MONTH);
        JalaliCalendar jalaliDate=new JalaliCalendar(new GregorianCalendar(year,month,day));
        return jalaliDate.toString();
    }

    /**
     * @param voter the person we want to look for in voters list
     * @return if person is already in the list it returns true,otherwise false
     */
    private boolean checkPersonEquality(Person voter){
        for(Person person:voters) {
            if (person.equals(voter)){
                return true;
            }

        }
        return false;
    }
    /**
     *
     * @param choice choice will be put as key in hashmap with a new hashset as  value
     */
    public void createChoice(String choice){
        listOfVotesToChoices.put(choice,new HashSet<Vote>());
    }

    /**
     * this method goes prints the votes to each choice and voters who submitted to
     *      this choice
     */
    public void printVotes(){
    //first we print the number of votes to each choice
        Iterator <String> it=listOfVotesToChoices.keySet().iterator();
        while(it.hasNext()){
            String key=it.next();
            System.out.printf("%s -> %d\n",key,listOfVotesToChoices.get(key).size());
            //persons who voted and the date will be printed by this part
            for(Vote vote:listOfVotesToChoices.get(key)){
                vote.printVote();
            }
        }
    }

    /**
     *
     * @return returns the type of voting
     */
    public int getType() {
        return type;
    }

    /**
     * @return returns the keys of the hashmap as an ArrayList
     */
    public ArrayList<String> getChoices(){
        return new ArrayList<>(listOfVotesToChoices.keySet());
    }
}
