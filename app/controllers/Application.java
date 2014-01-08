package controllers;

import java.io.PrintStream;
import java.io.Writer;
import java.util.*;

import play.*;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.JPABase;
import play.mvc.*;

import java.util.*;

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
		List<Protein> proteins = Protein.findAll();
		List<Substance> compounds = Substance.findAll();
		Substance s1 = new Substance("C2H6",30, "Ethane");
		Protein p1 = new Protein("Cytoplasm", "Cancer", "RRRRRR", "PGB", s1);
		Protein p2 = new Protein("Cytoplasm", "Cancer", "RRRRR", "PGV", s1);
	    int y = p1.getSimilarity(p2);
		render(proteins, compounds,y);
	}
	//Pseudo-code:
	//Print a message saying the query is being executed
	//TODO do a SPARQL query
	//Need a way to run a SPARQL query (jena library)
	//Set the Jena library deps
	//See the class for SPARQL query
	//Print the results in console
	//Try to think how it can be integrated with the view
	//Which end-point are we going to use?
	//Need to have a "debug" SPARQL query
	//Print a message saying the query is done

	public static void sparql() {
		List <String> myList = new ArrayList();
		String queryString = 
				        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n " +
						"PREFIX skos: <http://www.w3.org/2004/02/skos/core#> \n" +
						"SELECT ?tradeName\n" +
						"	WHERE { \n" +
						"<http://rdf.ebi.ac.uk/resource/chembl/molecule/CHEMBL192> skos:altLabel ?tradeName.\n" + 
						"}\n";

		QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", queryString);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("tradeName") ;       // Get a result variable by name.
			System.out.println(x);
			Resource r = soln.getResource("VarR") ; // Get a result variable - must be a resource
			Literal l = soln.getLiteral("VarL") ;   // Get a result variable - must be a literal
			myList.add(x.toString() + "\n");			
		}
		//ResultSetFormatter.out(System.out, results) ;
		qExe.close();
		render(myList);
	}

	public static void test(String id){
		String ChEMBLQuery = 
				        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
						"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
						"SELECT ?molfor\n" +
						//"CONSTRUCT {<http://rdf.ebi.ac.uk/resource/chembl/molecule/" + id + "#full_molformula> rdfs:label ?molfor.} \n" +
						"WHERE {\n" +
						"<http://rdf.ebi.ac.uk/resource/chembl/molecule/" + id + "#full_molformula> rdfs:label ?molfor.\n" +
						"}\n";
		QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", ChEMBLQuery);

		ResultSet results = qExe.execSelect();

		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("molfor") ;       // Get a result variable by name.
			System.out.println(x + "\n");
			Resource r = soln.getResource("VarR") ; // Get a result variable - must be a resource
			Literal l = soln.getLiteral("VarL") ;   // Get a result variable - must be a literal
			render(x);

		}
		//ResultSetFormatter.out(System.out, results) ;	 
		qExe.close() ;
		//Logger.info(results.toString());
		//render(results);
	} 

	public static void disease(String id){
		List <String> myList = new ArrayList();
		List <String> myList1 = new ArrayList();
		List <String> myList2 = new ArrayList();
		String diseaseSearch = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				               "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
				               "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
				               "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
				               "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> \n" +
				               "PREFIX up:<http://purl.uniprot.org/core/>\n " + 

			"SELECT ?protein  ?disease ?aa \n" +
			"WHERE { \n" +
			"  SERVICE <http://beta.sparql.uniprot.org/sparql> { \n" +
			"  ?protein a up:Protein.\n " +
			"  ?protein up:sequence ?seq. \n" +
			"  ?seq rdf:value ?aa.\n " +
			"  ?protein up:annotation ?annotation. \n" +
			"  ?annotation up:disease ?dis.\n" +
			"  ?dis skos:prefLabel ?disease . \n" + 
			"  FILTER regex(?disease,'" + id + "', 'i')" +
			"}\n" +
			"}\n"+
			"LIMIT 5";
		Query query = QueryFactory.create(diseaseSearch);
		QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", query);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("protein") ;       // Get a result variable by name.
			RDFNode y = soln.get("disease") ; 
			RDFNode z = soln.get("aa") ; 
			myList.add(x.toString() +"\t"+ y.toString() +"\t"+ z.toString());
		}
		qExe.close();
		render(myList, myList1, myList2 );

	}
	
	public static void start(){
		render();
	}

 public static String addFirst = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
"PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
"PREFIX dcterms: <http://purl.org/dc/terms/>\n" +
"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
"PREFIX cco: <http://rdf.ebi.ac.uk/terms/chembl#>\n" +
"PREFIX biopax3:<http://www.biopax.org/release/biopax-level3.owl#>\n" +
"PREFIX up:<http://purl.uniprot.org/core/> \n";	

 
  public static String Substance(){
	  List <String> myList = new ArrayList(); 
	  String substance = addFirst + 
			  "SELECT ?drugName \n" +
				"WHERE { \n" +
			  "?drug rdfs:subClassOf cco:Substance. \n"+
			  "?drug rdfs:label ?drugName.\n" +
			  "}\n"+
			  "LIMIT 5";
	  Query query = QueryFactory.create(substance);
	  QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", query);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("drugName") ;     
			myList.add(x.toString());
		}
		//ResultSetFormatter.out(System.out, results) ;
		qExe.close();
		render(myList);
		return(substance);
	}
  
  public static void Document(){
	  List <String> myList = new ArrayList(); 
	  String query = addFirst + 
			  "SELECT ?document \n" +
				"WHERE { \n" +
			  "?document  rdf:type cco:Document. \n"+
			  "}\n"+
			  "LIMIT 5";
	  
	  QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", query);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("document") ;     
			myList.add(x.toString());
		}
		qExe.close();
		render(myList);
	}	
  public static String Activity(){
	  List <String> myList = new ArrayList(); 
	  List <String> myList1 = new ArrayList();
	  List <String> myList2 = new ArrayList();
	  List <String> myList3 = new ArrayList();
	  List <String> myList4 = new ArrayList();
	  String query = addFirst + 
			  "SELECT ?activity ?stdType ?stdValue ?stdRelation ?stdUnit\n" +
				"WHERE { \n" +
			  "?activity  rdf:type cco:Activity. \n"+
		      "?activity cco:standardType ?stdType.\n"+
			  "?activity cco:standardValue ?stdValue.\n"+
		      "?activity cco:standardRelation ?stdRelation.\n"+
			  "?activity cco:standardUnits ?stdUnit.\n"+
			  "}\n"+
			  "LIMIT 5";
	  
	  QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", query);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("activity") ; 
			RDFNode y = soln.get("stdType") ; 
			RDFNode z = soln.get("stdValue") ; 
			RDFNode a = soln.get("stdRelation") ; 
			RDFNode b = soln.get("stdUnit") ; 
			myList.add(x.toString());
			myList1.add(y.toString());
			myList2.add(z.toString());
			myList3.add(a.toString());
			myList4.add(b.toString());
		}
		qExe.close();
		render(myList,myList1,myList2,myList3,myList4 );
		return(query);
	}			
  
  public static void Assay(){
	  List <String> myList = new ArrayList(); 
	  String query = addFirst + 
			  "SELECT ?assay \n" +
				"WHERE { \n" +
			  "?assay  rdf:type cco:Assay. \n"+
			  "}\n"+
			  "LIMIT 5";
	  
	  QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", query);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("assay") ;     
			myList.add(x.toString());
		}
		qExe.close();
		render(myList);
	}			
  
  public static void Target(){
	  List <String> myList = new ArrayList(); 
	  String query = addFirst + 
			  "SELECT ?target \n" +
				"WHERE { \n" +
			  "?target  rdfs:subClassOf cco:Target. \n"+
			  "}\n"+
			  "LIMIT 5";
	  
	  QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", query);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("target") ;     
			myList.add(x.toString());
		}
		qExe.close();
		render(myList);
	}			
  
  public static void Protein(){
	  List <String> myList = new ArrayList(); 
	  String query = addFirst + 
			  "SELECT ?protein \n" +
				"WHERE { \n" +
			  "SERVICE <http://beta.sparql.uniprot.org/sparql> { \n" +
                  "?protein a up:Protein.\n" +
              "}\n"+
              "}\n"+
			  "LIMIT 5";
	  
	  QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", query);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("protein") ;     
			myList.add(x.toString());
		}
		qExe.close();
		render(myList);
	}			

  public static void Pathway(){
	  List <String> myList = new ArrayList(); 
	  String query = addFirst + 
			  "SELECT ?pathwayname \n" +
				"WHERE { \n" +
			  "SERVICE <http://www.ebi.ac.uk/rdf/services/reactome/sparql> { \n" +
                    "?protein rdf:type biopax3:Protein .\n" +
                    "?protein biopax3:memberPhysicalEntity [biopax3:entityReference ?dbXref ] .\n" +
                    "?pathway biopax3:displayName ?pathwayname .\n" +
                    "?pathway biopax3:pathwayComponent ?reaction .\n" +
                    "?reaction ?rel ?protein\n" +
                    "}\n"+
                    "}\n"+
			        "LIMIT 5";
	  
	  QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", query);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("pathwayname") ;     
			myList.add(x.toString());
		}
		qExe.close();
		render(myList);
	}			
  public static void ActivityDetails(List<String> ActivityDetails ){
	  
	  render(ActivityDetails);
  }

  public static void ChEMBL(String details){
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
	  
	  System.out.println(details);
	  String mixQuery = addFirst + 
			  "SELECT ?molecule ?activity ?stdType ?stdValue ?stdRelation ?stdUnit ?assay ?target ?protein ?annotation ?pathwayname \n" +
			  "WHERE { \n" +
			  details +
              "}\n"+
		        "LIMIT 5";
	  mixQuery = mixQuery.replaceAll(",", "");
	  System.out.println(mixQuery);
	  QueryExecution qExe = QueryExecutionFactory.sparqlService("http://www.ebi.ac.uk/rdf/services/chembl/sparql", mixQuery);
		ResultSet results = qExe.execSelect();
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			RDFNode x = soln.get("molecule") ; 
			RDFNode y = soln.get("activity") ; 
			RDFNode z = soln.get("assay") ; 
			RDFNode a = soln.get("target") ; 
			RDFNode b = soln.get("protein") ; 
			RDFNode c = soln.get("pathwayname") ; 

			RDFNode d = soln.get("stdType ") ; 
			RDFNode e = soln.get("stdValue") ; 
			RDFNode f = soln.get("stdRelation") ; 
			RDFNode g = soln.get("?stdUnit") ;
			
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
			if(c != null)
			myList5.add(c.toString());
			if(d != null)
			myList6.add(d.toString());
			if(e != null)
			myList7.add(e.toString());
			if(f != null)
			myList8.add(f.toString());
			if(g != null)
			myList9.add(g.toString());
		}
		qExe.close();
		render(myList,myList1,myList2,myList3,myList4,myList5,myList6,myList7,myList8,myList9);
	  
  }
}


