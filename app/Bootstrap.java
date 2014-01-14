                                                                                                                                              import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {
      public void doJob() {
    	  //check if database is empty
    	  if (Substance.count() == 0){
    		  Fixtures.loadModels("data.yml");
    	  }
      }
}
