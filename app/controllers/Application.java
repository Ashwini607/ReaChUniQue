package controllers;

import java.io.PrintStream;
import java.io.Writer;
import java.util.*;

import play.*;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.JPABase;
import play.mvc.*;

import java.util.*;

//import javax.media.j3d.View;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import models.*;

import com.hp.hpl.jena.query.*;
public class Application extends Controller {

	public static void index() {
		
		home();
	}


	public static void start(){
		render();
	}

	public static String addFirst = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
			"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
			"PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
			"PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>\n" +
			"PREFIX dc:<http://purl.org/dc/elements/1.1/>\n" +
			"PREFIX dcterms:<http://purl.org/dc/terms/>\n" +
			"PREFIX skos:<http://www.w3.org/2004/02/skos/core#>\n" +
			"PREFIX cco:<http://rdf.ebi.ac.uk/terms/chembl#>\n" +
			"PREFIX biopax3:<http://www.biopax.org/release/biopax-level3.owl#>\n" +
			"PREFIX up:<http://purl.uniprot.org/core/> \n";	


	public static String mixQuery;
 

	public static void ChEMBL(String details, String firstname, String unit, String value, String journal, String date, String phase, String type, String score, String uni, String dis){
		
		String organism = new String();
		String mixQuery = new String();

		if (firstname.length()>0){
			organism = "FILTER regex(?organism,'" + firstname + "', 'i')";
		}

		String units = new String();

		if (unit.length()>0){
			units = "FILTER regex(?stdUnit,'" +unit+ "', 'i')";
		}        

		String values = new String();		
		if (value.length()>0){
			values = "FILTER (?stdValue" +value+ ")";
		}

		String journals = new String();		
		if (journal.length()>0){
			journals = "FILTER regex(?journalName,'" +journal+ "', 'i')";
		} 

		String dates = new String();		
		if (date.length()>0){
			dates = "FILTER (?date" +date+ ")";
		}

		String phases = new String();		
		if (phase.length()>0){
			phases = "FILTER (?highestDevelopmentPhase" +phase+ ")";
		}

		String types = new String();		
		if (type.length()>0){
			types = "FILTER regex(?assayType,'" +type+ "', 'i')";
		}

		String scores = new String();		
		if (score.length()>0){
			scores = "FILTER (?targetConfScore" +score+ ")";
		}
		
		String diss = new String();		
		if (dis.length()>0){
			diss = "FILTER regex(?disease,'" +dis+ "', 'i'"
					+ ")";
		}

		List <String> myList = new ArrayList(); 
		List <String> myList1 = new ArrayList();
		List <String> myList2 = new ArrayList();
		List <String> myList3 = new ArrayList();
		List <String> myList4 = new ArrayList();
		List <String> myList5 = new ArrayList();
		List <String> myList6 = new ArrayList();
		List <String> myList7 = new ArrayList();
		List <String> myList8 = new ArrayList();
		List <String> myList9 = new ArrayList();
		List <String> myList10 = new ArrayList();
		List <String> myList11 = new ArrayList();
		List <String> myList12 = new ArrayList();
		List <String> myList13 = new ArrayList();
		List <String> myList14 = new ArrayList();
		List <String> myList15 = new ArrayList();
		List <String> myList16 = new ArrayList();
		List <String> myList17 = new ArrayList();
		List <String> myList18 = new ArrayList();
		List <String> myList19 = new ArrayList();
		List <String> myList20 = new ArrayList();
		List <String> myList21 = new ArrayList();
		List <String> myList22 = new ArrayList();
		List <String> myList23 = new ArrayList();
		List <String> myList24 = new ArrayList();
		List <String> myList25 = new ArrayList();
		List <String> myList26 = new ArrayList();
		List <String> myList27 = new ArrayList();
		List <String> myList28 = new ArrayList();
		List <String> myList29 = new ArrayList();
		List <String> myList30 = new ArrayList();

		String selection = "?protein ?recommended ?disease ?aa";
		String addSelection =  new String();
		String addSelection1 =  new String();
		
		if(details != null){
			String selection1 = "?journalName ?date ?stdType ?stdValue ?stdRelation ?stdUnit ?annotation ?pathwayname ?ChEMBL_id ?moleculeDesc ?altLabel ?highestDevelopmentPhase ?substanceType ?assayLabel ?assayDesc ?assayType ?targetConfDesc ?targetConfScore ?targetRelType ?targetRelDesc ?targetLabel ?targetTitle ?targetType ?organism ";
		String[] finalSelection = selection1.split(" ");
		String[] finalDetails = details.split(" ");

		for(int item1=0; item1<finalDetails.length; item1++) 
		{
			if(finalDetails[item1].contains("."))
			{finalDetails[item1]=(finalDetails[item1].replaceAll("\\.", ""));}
			if(finalDetails[item1].contains(".,"))
			{finalDetails[item1]=(finalDetails[item1].replaceAll("\\.,", ""));}
			if(finalDetails[item1].contains(","))
			{finalDetails[item1]=(finalDetails[item1].replaceAll(",", ""));}

		}		


		int item;
		//item2 = 0;
		for ( item=0; item<finalSelection.length; item++)
		{
			for(int item1=0; item1<finalDetails.length; item1++) 
			{
				if(finalSelection[item].equals(finalDetails[item1]))

				{ addSelection += " "+finalSelection[item];
				break;
				}

			}


		}

		details = details.replaceAll(",", "");
		}	
		
		if(uni != null){
		String[] finalSelection1 = selection.split(" ");
		String[] finalDetails1 = uni.split(" ");

		for(int item1=0; item1<finalDetails1.length; item1++) 
		{
			if(finalDetails1[item1].contains("."))
			{finalDetails1[item1]=(finalDetails1[item1].replaceAll("\\.", ""));}
			if(finalDetails1[item1].contains(".,"))
			{finalDetails1[item1]=(finalDetails1[item1].replaceAll("\\.,", ""));}
			if(finalDetails1[item1].contains(","))
			{finalDetails1[item1]=(finalDetails1[item1].replaceAll(",", ""));}

		}		


		int item;
		//item2 = 0;
		for ( item=0; item<finalSelection1.length; item++)
		{
			for(int item1=0; item1<finalDetails1.length; item1++) 
			{
				if(finalSelection1[item].equals(finalDetails1[item1]))

				{ addSelection1 += " "+finalSelection1[item];
				break;
				}

			}


		}

		uni = uni.replaceAll(",", "");
		}	
		
	String uniprotQuery = new String();
	uniprotQuery = "SERVICE <http://beta.sparql.uniprot.org/sparql> {" +
	uni +
	diss +
	"}\n"
	      ;
			
		
	if(details==null){	
		mixQuery = addFirst + "SELECT DISTINCT"+
				addSelection +
				addSelection1 +
				" WHERE { \n" +			    
				uniprotQuery +
				"}\n"+
				"LIMIT 11";}
	
	
	if(uni==null){	
		mixQuery = addFirst + "SELECT DISTINCT"+
				addSelection +
				addSelection1 +
				" WHERE { \n" +		
				details +
				organism +	
				units +
				values +
				journals +
				dates +
				phases +
				types +
				scores +
				
				"}\n"+
				"LIMIT 11";}
	
	
	if(uni!=null && details!=null){	
		mixQuery = addFirst + "SELECT DISTINCT"+
				addSelection +
				addSelection1 +
				" WHERE { \n" +		
				details +
				organism +	
				units +
				values +
				journals +
				dates +
				phases +
				types +
				scores +
				uniprotQuery+
				"}\n"+
				"LIMIT 11";}
		//mixQuery = mixQuery.replaceAll(",", "");

		System.out.println(mixQuery);
		QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", mixQuery);
		ResultSet results = qExe.execSelect();
		//String k = new String();

		for ( ; results.hasNext() ; )
		{

			QuerySolution soln = results.nextSolution() ;
			//soln.getLiteral("stdValue").getLexicalForm(); 
			RDFNode x = soln.get("molecule") ; 
			RDFNode y = soln.get("activity") ; 
			RDFNode z = soln.get("assay") ; 
			RDFNode a = soln.get("target") ; 
			RDFNode b = soln.get("protein") ; 
			RDFNode c1 = soln.get("pathwayname") ; 
			if(c1 != null){
				String c = soln.getLiteral("pathwayname").getLexicalForm();
				myList5.add(c.toString());}


			RDFNode d = soln.get("stdType") ; 
			RDFNode e1 = soln.get("stdValue") ;
			if(e1!=null){
				String e = soln.getLiteral("stdValue").getLexicalForm();
				myList7.add(e.toString());}
			RDFNode f = soln.get("stdRelation") ; 
			RDFNode g = soln.get("stdUnit") ;

			RDFNode h = soln.get("ChEMBL_id") ; 
			RDFNode i = soln.get("moleculeDesc") ; 
			RDFNode j = soln.get("prefLabel") ; 
			RDFNode k1 = soln.get("highestDevelopmentPhase") ;
			if(k1 != null){
				String k = soln.getLiteral("highestDevelopmentPhase").getLexicalForm();
				myList13.add(k.toString());}
			RDFNode l = soln.get("substanceType") ; 

			RDFNode m = soln.get("assayLabel") ; 
			RDFNode n = soln.get("assayDesc") ;
			RDFNode o = soln.get("assayType") ; 
			RDFNode p = soln.get("targetConfDesc") ; 
			RDFNode q1 = soln.get("targetConfScore") ; 
			if(q1 != null){
				String q = soln.getLiteral("targetConfScore").getLexicalForm();
				myList19.add(q.toString());}
			RDFNode r = soln.get("targetRelType") ; 
			RDFNode s = soln.get("targetRelDesc") ; 

			RDFNode t = soln.get("targetLabel") ; 
			RDFNode u = soln.get("targetTitle") ; 
			RDFNode v = soln.get("targetType") ; 
			RDFNode w = soln.get("organism") ; 

			RDFNode a1 = soln.get("disease") ; 
			RDFNode b1 = soln.get("aa") ;
			RDFNode aa = soln.get("document") ; 
			if(aa != null)
				myList27.add(aa.toString());

			RDFNode aa1 = soln.get("journalName") ; 
			if(aa1 != null)
				myList28.add(aa1.toString());

			RDFNode aa21 = soln.get("date") ; 
			if(aa21!=null){
				String aa2 = soln.getLiteral("date").getLexicalForm();
				myList29.add(aa2.toString());}



			if(x != null)
				myList.add(x.toString());
			if (y!= null)
				myList1.add(y.toString());
			if(z != null)
				myList2.add(z.toString());
			if(a != null)
				myList3.add(a.toString());
			if(b != null)
				myList4.add(b.toString());
			//	if(c != null)
			//		myList5.add(c.toString());
			if(d != null)
				myList6.add(d.toString());
			//	if(e != null)
			//		myList7.add(e.toString());
			if(f != null)
				myList8.add(f.toString());
			if(g != null)
				myList9.add(g.toString());
			if(h != null)
				myList10.add(h.toString());
			if(i != null)
				myList11.add(i.toString());
			if(j != null)
				myList12.add(j.toString());
			//	if(k != null)
			//		myList13.add(k.toString());
			if(l != null)
				myList14.add(l.toString());
			if(m != null)
				myList15.add(m.toString());
			if(n != null)
				myList16.add(n.toString());
			if(o != null)
				myList17.add(o.toString());
			if(p != null)
				myList18.add(p.toString());
			//	if(q != null)
			//		myList19.add(q.toString());
			if(r != null)
				myList20.add(r.toString());
			if(s != null)
				myList21.add(s.toString());
			if(t != null)
				myList22.add(t.toString());
			if(u != null)
				myList23.add(u.toString());
			if(v != null)
				myList24.add(v.toString());
			if(w != null)
				myList25.add(w.toString());		
			if(a1 != null)
				myList26.add(a1.toString());
			if(b1 != null)
				myList30.add(b1.toString());

		}
		qExe.close();

		render(myList,myList1,myList2,myList3,myList4,myList5,myList6,myList7,myList8,myList9,myList10,myList11,myList12,myList13,myList14,myList15,myList16,myList17,myList18,myList19,myList20,myList21,myList22,myList23,myList24,myList25, myList26, myList27, myList28, myList29, myList30);


	}
	public static void SPARQLQuery(){

		String temp = mixQuery;
		System.out.println(temp);
		//renderTemplate("Application/SPARQLQuery.html", temp);
		render(temp);
	}

	public static void contact() {
		render();
	}

	public static void documentation() {
		render();		
	}
	
	public static void visualisation() {
		render();		
	}

	public static void home() {
		render();		
	}
	
	public static void example() {
		render();		
	}
	
}


