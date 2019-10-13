import java.util.*;
public class Transportation{//LeastCostMethod
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
	int []mincolumn;
	int []minrow;
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
					tc[i][j] = 0;
				}
			}
			find_minimum();
			cal_vogels();
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
	int sumof_mincolumn(){
		mincolumn = new int[column];
		int i,j,k;
		int first,second;
		for(i=0;i<column;i++){
			first = second = Integer.MAX_VALUE;
			if(temp_demand[i] != 0){
			 	for(j=0;j<row;j++){
			 		if(temp_supply[j]!=0){
						if(temp[j][i]<first && temp[j][i]!=-1){
							second = first;
							first = temp[j][i];
						}else if(temp[j][i]<=second && temp[j][i]!=-1){
							second = temp[j][i];
						}
					}
				}
				if(first == Integer.MAX_VALUE && second == Integer.MAX_VALUE){
					first = 0;
					second = 0;
				}
				else if(second == Integer.MAX_VALUE){
					second = 0;
					mincolumn[i] = first;
				}else{
					mincolumn[i] = second-first;
				}
			}else{
				mincolumn[i] = 0;
			}
		}
		return 0;
	}
	int sumof_minrow(){
		minrow = new int[row];
		int i,j;
		int first,second;
		for(i=0;i<row;i++){
			first = second = Integer.MAX_VALUE;
			if(temp_supply[i]!=0){
			 	for(j=0;j<column;j++){
			 		if(temp_demand[j]!=0){
						if(temp[i][j]<first && temp[i][j]!=-1){
							second = first;
							first = temp[i][j];
						}else if(temp[i][j]<=second && temp[i][j]!=-1){ 
							second = temp[i][j];
						}
					}
				}
				if(first == Integer.MAX_VALUE && second == Integer.MAX_VALUE){
					first = 0;
					second = 0;
				}
				else if(second == Integer.MAX_VALUE){
					second = 0;
					minrow[i] = first;
				}else{
					minrow[i] = second-first;
				}
			}else{
				minrow[i] = 0;
			}
		}
		return 0;
	}
	void find_minimum(){
		sumof_minrow();
		sumof_mincolumn();
		display_vogels();
	}
	void display_vogels(){
		System.out.println("\n\n\tVOGELS PROBLEM\n\n");
		int i,j;
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				System.out.print("\t"+temp[i][j]);
			}
			System.out.print("\t"+temp_supply[i]);
			System.out.print("\t("+minrow[i]+")");
			System.out.println();
		}
		System.out.println();
		for(i=0;i<column;i++){
			System.out.print("\t"+temp_demand[i]);
		}
		System.out.println();
		System.out.println();
		for(i=0;i<column;i++){
			System.out.print("\t("+mincolumn[i]+")");
		}
		System.out.println();
	}
	int max_in_row(){
		int rmax = 0;
		int i;
		for(i=0;i<row;i++){
			if(minrow[i]>minrow[rmax]){
				rmax = i;
			}
		}
		System.out.println("Max in Row: "+rmax);
		return rmax;
	}
	int max_in_column(){
		int cmax = 0;
		int i;
		for(i=0;i<column;i++){
			if(mincolumn[i]>mincolumn[cmax]){
				cmax = i;
			}
		}
		System.out.println("Max in column: "+cmax);
		return cmax;
	}
	int min_in_element(){
		int rmax = max_in_row(); 
		int cmax = max_in_column();
		int min_i=0,min_j=0,i,j;
		int max = Integer.MAX_VALUE;
		if(minrow[rmax]>mincolumn[cmax]){
			for(i=0;i<column;i++){
				if(temp[rmax][i]<max && temp[rmax][i]!=-1){
					max = temp[rmax][i];
					min_i = rmax;
					min_j = i;
				}
			}
			if(temp_supply[min_i]<temp_demand[min_j]){
				temp_demand[min_j] = temp_demand[min_j] - temp_supply[min_i];
				tc[min_i][min_j] = temp[min_i][min_j] * temp_supply[min_i];
				temp_supply[min_i] = 0;
				for(i=0;i<column;i++){
					temp[min_i][i] = -1;
				}
			}else{
				temp_supply[min_i] = temp_supply[min_i] - temp_demand[min_j];
				tc[min_i][min_j] = temp[min_i][min_j] * temp_demand[min_j];
				temp_demand[min_j] = 0;
				for(i=0;i<row;i++){
					temp[i][min_j] = -1;
				}
			}
		}else{
			for(i=0;i<row;i++){
				if(temp[i][cmax]<max && temp[i][cmax]!=-1){
					max = temp[i][cmax];
					min_i = i;
					min_j = cmax;
				}
			}
			if(temp_supply[min_i]<temp_demand[min_j]){
				temp_demand[min_j] = temp_demand[min_j] - temp_supply[min_i];
				tc[min_i][min_j] = temp[min_i][min_j] * temp_supply[min_i];
				temp_supply[min_i] = 0;
				for(i=0;i<column;i++){
					temp[min_i][i] = -1;
				}
			}else{
				temp_supply[min_i] = temp_supply[min_i] - temp_demand[min_j];
				tc[min_i][min_j] = temp[min_i][min_j] * temp_demand[min_j];
				temp_demand[min_j] = 0;
				for(i=0;i<row;i++){
					temp[i][min_j] = -1;
				}
			}
		}
		return 0;
	}
	void cal_vogels(){
		boolean loop = true;
		int n,flag = 0,i,j;
		int tr_cost = 0;
		n = row + column - 1;
		for(i=0;i<n;i++){
			min_in_element();
			find_minimum();
		}
		System.out.println("Transportation Cost = ");
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				if(tc[i][j]!=0){
					System.out.print(tc[i][j] +" + ");
					tr_cost = tr_cost + tc[i][j];
				}
			}
		}
		System.out.println();
		System.out.println("Transportation Cost = "+ tr_cost);
	}
}
