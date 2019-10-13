import java.util.*;
public class Nwcr{//NorthWest Corner Rule
	public static void main(String[] args){
		TransportationProblem tp = new TransportationProblem();
	}
}
class TransportationProblem{
	int []supply;
	int []demand;
	int [][]a;
	int [][]temp;
	int [][]tc;
	int []temp_supply;
	int []temp_demand;
	int row, column, ti, tj;
	TransportationProblem(){
		int i,j;
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter the No. of Supply: ");
		row = input.nextInt();
		supply = new int[10];
		System.out.print("\nEnter the No. of Demands: ");
		column = input.nextInt();
		demand = new int[10];
		a = new int[10][10];
		temp = new int[10][10];
		tc = new int[10][10];
		System.out.print("\nEnter the values of Supply: \n");
		for(i=0;i<row;i++){
			System.out.print("Supply"+(i+1)+" = ");
			supply[i] = input.nextInt();
		}
		System.out.print("\nEnter the values of Demands: \n");
		for(i=0;i<column;i++){
			System.out.print("Demand"+(i+1)+" = ");
			demand[i] = input.nextInt();
		}
		System.out.print("\nEnter the values in the Matrix: ");
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				System.out.print("\na["+i+"]["+j+"] = ");
				a[i][j] = input.nextInt();
			}
		}
		display();
	}
	void display(){
		int i,j;
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				System.out.print("\t"+a[i][j]);
			}
			System.out.print("\t"+supply[i]);
			System.out.println();
		}
		System.out.println();
		for(i=0;i<column;i++){
			System.out.print("\t"+demand[i]);
		}
		System.out.println();
		check_constraint();
	}
	void check_constraint(){
		int i,j;
		int tot_supply=0,tot_demand=0;
		for(i=0;i<row;i++){
			tot_supply = tot_supply+supply[i];
		}
		for(i=0;i<column;i++){
			tot_demand = tot_demand+demand[i];
		}
		System.out.println(tot_supply+"=="+tot_demand);
		if(tot_supply==tot_demand){
			System.out.println("Supply and Demand is equal");
			temp_supply = new int[row];
			temp_demand = new int[column];
			for(i=0;i<row;i++){
				temp_supply[i] = supply[i];
			}
			for(i=0;i<column;i++){
				temp_demand[i] = demand[i];
			}
			for(i=0;i<row;i++){
				for(j=0;j<column;j++){
					temp[i][j] = a[i][j];
					tc[i][j] = -1;
				}
			}
			System.out.println("here");
			cal_nwcr();
		}else{
			if(tot_supply<tot_demand){
				System.out.println("Supply is less than Demand");
				row++;
				supply[row-1] = tot_demand-tot_supply;
				for(i=row-1;i<row;i++){
					for(j=0;j<column;j++){
						a[i][j] = 0;
					}
				}
				display();
			}else{
				column++;
				System.out.println("Demand is less than Supply");
				demand[column-1] = tot_supply-tot_demand;
				for(i=0;i<row;i++){
					for(j=column-1;j<column;j++){
						a[i][j]=0;
					}
				}
				display();
			}
		}
	}
	void cal_nwcr(){
		int i = 0, j = 0, cost = 0;
		System.out.println("here");
		while(i<row){
			if(temp_supply[i]<=temp_demand[j]){
				temp_demand[j] -= temp_supply[i];
				tc[i][j] = temp[i][j] * temp_supply[i];
				temp_supply[i] -= temp_supply[i];
				i++;
				temp_display();
			}else if(temp_demand[j]<temp_supply[i]){
				temp_supply[i] -= temp_demand[j];
				tc[i][j] = temp[i][j] * temp_demand[j];
				temp_demand[j] = temp_demand[j];
				j++;
				temp_display();
			}
		}
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				if(tc[i][j]!=-1){
					System.out.println(""+tc[i][j]);
					cost += tc[i][j];
				}
			}
		}
		System.out.println("Cost = "+ cost);
	}
	void temp_display(){
		int i,j;
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				System.out.print("\t"+temp[i][j]);
			}
			System.out.print("\t"+temp_supply[i]);
			System.out.println();
		}
		System.out.println();
		for(i=0;i<column;i++){
			System.out.print("\t"+temp_demand[i]);
		}
		System.out.println();
	}
}
