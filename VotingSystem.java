import com.sun.org.apache.xalan.internal.xsltc.dom.ArrayNodeListIterator;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class VotingSystem {
    private ArrayList <Voting> votingList;
    public VotingSystem (){
        votingList=new ArrayList<Voting>();
    }
    /**
     *
     * @param question it is the question of newly voting created
     * @param type it is the type of voting . 0 for single voting and 1 for multi voting
     * @param choices is the array of choices for the question
     *
     */
    public void createVoting(String question, int type, ArrayList<String> choices){
        /*
        this method first create new Voting type and then adds elements in choices list
        one by one in voting HashMap
         */
        Voting newVote=new Voting(type,question);
        for(String choice:choices){
            newVote.createChoice(choice);
        }
        votingList.add(newVote);
        System.out.println("vote created successfully");
    }

    /**
     * one of menu options
     * this method prints all of voting questions
     */
    public void printVotingQuestions(){
        System.out.println("Here are the voting questions : ");
        for(Voting vote:votingList){
            System.out.println(vote.getQuestion());
        }
        System.out.println("------------------------");
    }

    /**
     * one of menu options
     * @param index if index is valid it will print voting details based on index of printed table
     */
    public void printVoting(int index){
        if(validIndex(index)){
            System.out.println("Question: "+ votingList.get(index).getQuestion());
            System.out.println("Choices: ");
            for(String choice:votingList.get(index).getChoices()){
                System.out.println(choice);
            }
        }

    }

    /**
     * vote in indexed-voting
     * @param index its the index of voting in the list
     * @param voter the person who wants to vote
     * @param choices the choices which person want to vote for
     */
    public void vote(int index,Person voter,ArrayList<String> choices){
        votingList.get(index).vote(voter,choices);
    }

    /**
     * this method takes first name and last name of new person
     * @return it returns newly created person with fields taken from user
     */
    private Person createPerson(){
        Scanner input=new Scanner(System.in);
        System.out.println("Please enter firstName: ");
        String firstName=input.nextLine();
        System.out.println("Please enter lastName: ");
        String lastName=input.nextLine();
        return new Person(firstName,lastName);
    }

    /**
     *
     * @param indexOfVoting is the index of voting in the list
     * @return it returns a list of choices
     *
     */
    private ArrayList<String> makeChoices(int indexOfVoting){
        /*
        A manual to this method ...it divides to 2 part based on type of voting
        one part is for multi vote mode
        and another part is for single vote mode
            which single vote mode divides into 2 parts as random choice and non random choice
         */
        Scanner input=new Scanner(System.in);
        ArrayList<String> tempList=new ArrayList<String>();
        if(votingList.get(indexOfVoting).getType()==0){
            //this is the part which handles single voting mode
            System.out.println("This is single vote voting . if you want to randomly choose one press 1 else enter 0 :");
            int chooseRandom=input.nextInt();
            input.nextLine();
            if(chooseRandom!=1){
                //non random choice
                this.printChoices(indexOfVoting);
                System.out.println("So pick one of choice(s) :");
                boolean exceptionOccured;
                //this loop ensures user enters correct index of choice they wanna make
                do{
                    try{
                        int choiceIndex=input.nextInt();
                        input.nextLine();
                        tempList.add(votingList.get(indexOfVoting).getChoices().get(choiceIndex));
                        exceptionOccured=false;
                    }catch(Exception e) {
                    /*
                    possible bug
                    if user enters wrong index more than 2 times
                     */
                        System.out.println("Please be careful with number of choice :");
                        //tempList.add(votingList.get(indexOfVoting).getChoices().get(input.nextInt()));
                        exceptionOccured=true;
                    }
                }while(exceptionOccured);
            }else{
                //random choice
                String randString=this.randomChoice(indexOfVoting);
                tempList.add(randString);
                System.out.println("\""+randString+"\""+" have been submitted");
            }
            //end of single vote mode
        }else{
            //This part handles MULTI vote mode
            this.printChoices(indexOfVoting);
            System.out.println("Choose whatever you want .press any character to finish");
            while(true){
                    //ill use try-catch to handle when will loop finish
                    //if user enters character while program wants integer value it throws and error
                try{
                    int choiceIndex=input.nextInt();
                    String chosenChoice=votingList.get(indexOfVoting).getChoices().get(choiceIndex);
                    if(tempList.contains(chosenChoice)){
                        System.out.println("you already choose this.");
                    }else{
                        tempList.add(chosenChoice);
                        System.out.println("\""+chosenChoice+"\""+" have been chosen");
                    }
                }catch(InputMismatchException e){
                    break;
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Please enter correct number when choosing .");
                }
            }
        }
        return tempList;
    }
    /**
     * @param index it is the index of vote in the list
     * @return it randomly choose a choice
     */
    private String randomChoice(int index){
        Random randomizer=new Random();
        //it will make a random in [0,number of choices in voting)
        int randomIndex=randomizer.nextInt(votingList.get(index).getChoices().size());
        return votingList.get(index).getChoices().get(randomIndex);
    }
    private void printChoices(int indexOfVoting){
        int index=0;
        for (String choice:votingList.get(indexOfVoting).getChoices()){
            System.out.printf("%d- %s\n",index,choice);
            index++;
        }
    }

    /**
     *
     * @param index is the index of the voting we want to print its results
     */
    public void printResults(int index){
        if(validIndex(index))
            votingList.get(index).printVotes();
    }
    /**
     * this method prints all votings
     * its used to select from votings in other methods
     */
    private void printVotings(){
        int index=0;
        for(Voting vote: votingList){
            System.out.printf("%d- Question: %s - type: %s\n",index,vote.getQuestion(),vote.getType()==0 ? "(Single-Voting)"
                    :"(Multi-Voting)");
            index++;
        }
    }

    /**
     * the menu of the program
     * @return returns 0 when user choose exit option and end the program
     */
    public int menu(){
        Scanner input=new Scanner(System.in);
        System.out.println("1- Create voting");
        System.out.println("2- Print voting questions");
        System.out.println("3- Print voting");
        System.out.println("4- Vote");
        System.out.println("5- Print results");
        System.out.println("6- Exit");
        int indexChosen;
        switch (input.nextInt()){
            case 1:
                System.out.println("1-Single vote mode\n2-Multi vote mode");
                System.out.println("which mode you prefer? : ");
                int type=input.nextInt();
                input.nextLine();
                System.out.println("Please enter the question : ");
                String quesion=input.nextLine();
                System.out.println("enter choices , enter 0 to stop.");
                ArrayList<String> choices=new ArrayList <String>();
                while(true){
                    String choice =input.nextLine();
                    /*
                    if converting string to integer throw an exception it means
                    its one of the choices and its not number & 0
                     */
                    try {
                        int test=Integer.parseInt(choice);
                        if(test==0)
                             break;
                    }catch (Exception e){
                        //System.out.println("error");
                    }
                    //System.out.println(choice);
                    choices.add(choice);
                }
                /*
                if they choose number 1 type than it should 0(according to dastorekar :) to the method)
                 */
                this.createVoting(quesion,type==1 ? 0 : 1,choices);
                break;
            case 2:
                this.printVotingQuestions();
                break;
            case 3:
                /*
                first program shows a list of voting and then user can choose from it
                 */
                this.printVotings();
                System.out.println("Please choose from available votings :");
                indexChosen=input.nextInt();
                input.nextLine();
                this.printVoting(indexChosen);
                break;
            case 4:
                 this.printVotings();
                System.out.println("Choose voting you wanna vote for: ");
                indexChosen=input.nextInt();
                input.nextLine();
                if(validIndex(indexChosen)){
                    this.vote(indexChosen,createPerson(),makeChoices(indexChosen));
                }else
                    System.out.println("index is not valid.");
                break;
            case 5:
                printVotings();
                System.out.println("choose a voting :");
                indexChosen=input.nextInt();
                input.nextLine();
                this.printResults(indexChosen);
                break;

            case 6:
                return 0;
        }
        return 1;
    }

    /**
     *
     * @param index index of voting on the list
     * @return whether index is valid or not
     */
    private boolean validIndex(int index){
        return (index>=0 && index<votingList.size());
    }
}
