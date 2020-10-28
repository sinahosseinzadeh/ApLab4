public class Main {
    public static void main (String[] args){
        VotingSystem votingSystem=new VotingSystem();
        /*this
            when entering 6 in the menu menu method returns 0 and loop will break
         */
        while (true){
            if(votingSystem.menu()==0){
                System.out.println("Thank you for using our system.");
                break;
            }
        }
    }
}
