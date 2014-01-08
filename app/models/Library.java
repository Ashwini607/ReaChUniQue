package models;
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Library extends Model {
	public String name;
	public ArrayList<Substance> molecules;
	
	public Library(String name, ArrayList<Substance> molecules){
		this.name = name;
		this.molecules = molecules;
	}
   
   public void addsubstance(Substance s1){
	   this.addsubstance(s1);
   }
}
