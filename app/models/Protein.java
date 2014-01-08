package models;
import java.util.*;

import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Protein extends Model{
	//field
	public String cellularLocation;
	public String associatedDisease;
	public String proteinSequence;
	public String name;
	
	@ManyToOne
	public Substance substance;

	//constructor
	public Protein(String cellularLocation, String associatedDisease, String proteinSequence, String name, Substance substance ) {
		this.cellularLocation = cellularLocation;
		this.associatedDisease = associatedDisease;
		this.proteinSequence = proteinSequence;
		this.name = name;
		this.substance = substance;
		

	}

	public int getSimilarity(Protein proteinToCompare) {
		// TODO Auto-generated method stub
		String currentSequence = this.proteinSequence;
		String sequenceToCompare = proteinToCompare.proteinSequence;
		//Implement comparison
		int seq1 = currentSequence.length();
		int seq2 = sequenceToCompare.length();
		int c = seq1-seq2;
		System.out.println("diffrence in length of proteins is "+ c + " amino acids");
		return (c >0) ? c : -c;
	}
}
