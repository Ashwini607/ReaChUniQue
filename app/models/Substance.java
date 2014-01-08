package models;

import java.util.*;

import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Substance extends Model{
	
	public String molecularFormula; 
	public String name;
	public int molecularWeight;
	
    @OneToMany(mappedBy="substance", cascade=CascadeType.ALL)
	public List<Protein> targets;


	public Substance(String molecularFormula, int molecularWeight, String name ){
		this.molecularFormula = molecularFormula;
		this.molecularWeight = molecularWeight;
		this. name = name;
		this.targets = new ArrayList<Protein>();
	}

	public Substance addProtein(String cellularLocation, String associatedDisease, String proteinSequence, String name){
		Protein newProtein = new Protein (cellularLocation, associatedDisease, proteinSequence, name, this).save();
		this.targets.add(newProtein);
		this.save();
		return this;
	}
}
