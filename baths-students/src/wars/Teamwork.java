package wars; 


/**
 * Details of your team
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Teamwork
{
    private String[] details = new String[13];
    
    public Teamwork()
    {   // in each line replace the contents of the String 
        // with the details of your team member
        // Please list the member details alphabetically by surname 
        // i.e. the surname of member1 should come alphabetically 
        // before the surname of member 2...etc
        details[0] = "85";
        
        details[1] = "Martires";
        details[2] = "Julia";
        details[3] = "22096618";

        details[4] = "Jordanbland";
        details[5] = "Fred";
        details[6] = "22059872";

        details[7] = "Pranoto";
        details[8] = "Yodhitomo";
        details[9] = "23013218";

        details[10] = "Hasan";
        details[11] = "Nouh";
        details[12] = "23021813";
	
	   


    }
    
    public String[] getTeamDetails()
    {
        return details;
    }
    
    public void displayDetails()
    {
        for(String temp:details)
        {
            System.out.println(temp.toString());
        }
    }
}
        
