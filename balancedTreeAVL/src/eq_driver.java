import bridges.connect.Bridges;
import bridges.data_src_dependent.EarthquakeUSGS;
import bridges.data_src_dependent.USGSaccount;
import java.util.*;


public class eq_driver {
	public static final int maxElements = 100; //number of earthquake records

	public static void main(String[] args) throws Exception{
	
    	// Instantiate a Bridges object */
		Bridges<Double, EarthquakeUSGS> bridges = new Bridges<Double, 
				EarthquakeUSGS>(50,
						"929945842955", "choney18");

		// Set up the earthquake user 
		USGSaccount name = new USGSaccount("earthquake"); 
		
		// Retrieve a list of (maxElements) Tweets */	
		List <EarthquakeUSGS> eqList=Bridges.getAssociations(name, maxElements);

		// To do: create a BST object to hold the earthquake data

		BST<Double, EarthquakeUSGS> bst = new BST<Double, EarthquakeUSGS>();

		Scanner input = new Scanner(System.in);
		System.out.println("Type 1 for a sorted list and type 2 for a reverse list and type 3 for regulare list: ");
		int choice = input.nextInt();
		//ascending 
		if (choice == 1){			
			Collections.sort(eqList);
			for (int k = 0; k < eqList.size(); k++) {
				   EarthquakeUSGS quakeList = eqList.get(k);
				   quakeList.setLabel(quakeList.getLocation());
				   bst.insert(eqList.get(k).getMagnitude(), eqList.get(k));        
				}
		}
		//descending 
		if (choice == 2){
			Collections.sort(eqList);
			Collections.reverse(eqList);
			for (int k = 0; k < eqList.size(); k++) {
				   EarthquakeUSGS quakeList = eqList.get(k);
				   quakeList.setLabel(quakeList.getLocation());
				   bst.insert(eqList.get(k).getMagnitude(), eqList.get(k));        
				}
		}
		
		if (choice == 3){
			for (int k = 0; k < eqList.size(); k++) {
				   EarthquakeUSGS quakeList = eqList.get(k);
				   quakeList.setLabel(quakeList.getLocation());
				   bst.insert(eqList.get(k).getMagnitude(), eqList.get(k));        
				}
		}
		
		
		
		 bridges.setDataStructure(bst.getTreeRoot());

		// if the tree is properly created, then visualize
		 bridges.visualize();
    }
}
