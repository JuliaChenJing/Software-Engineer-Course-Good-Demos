# Software-Engineer-Course-Good-Demos

## 1. JdbcReview	:

* Check the screenshot. 
* Using user first name to get the ssn nunmber  
* Need to check the build path and turn on mysql.   
* A good JDBC demo with JavaFX. 
---

## 2. Exercise_LoginAndAuthorization_Soln 

* Check the PDF. 
* A good demo about all you need to know on Login and Authorization using Java
* userid = 1   password = 1  private
* userid = 2   password = 2  admin
---

## 2.5 Exercise_Callback_Solution	

* Check the pdf.
* Check Alice's and Joe's teacher report and grade by logging in. 
* Their user name and password are their names.
* Callbacks are most easily described in terms of the telephone system. A function call is analogous to calling someone on a telephone, asking her a question, getting an answer, and hanging up; adding a callback changes the analogy so that after asking her a question, you also give her your name and number so she can call you back with the answer.

---
## 3. Exercise_RefactorControl_Soln	
* Check the comments from professor in UI classes.
* There are 3 solutions on the discussion of UI, controller and printer. The later demo is loosely coupled and controller does not even know the existance of ui using call back function.
* The design is made easier by requiring Control to be a singleton. Since a controller
 will typically be responsible for several
 windows, and it may have to manage relationships between data among many of these, it is
 often a good idea for a controller to be a singleton.

---
## 4. ArchAnFXRules
* Need to check the build path.
* A small working E-Bazaar demo about rules engine.
* The rules file is .clp. It is JEZZ-the rule engine for the Java Platform.

---
## 5. SampleDataAccessSubsystem		
* Need to check the build path.
* doInsert();
* doUpdate();
* doDelete();
* doRead();


---
## 5.5 Exercise_DataAccessSubsystem

* Requirements: Use the data access subsystem to 
interact with the database -- use the project SampleDataAccessSubsystem for reference.

* Implement the three methods listed in business.DbClassCatalog
  List<Catalog> getAllCatalogs() 
  int insertNewCatalog(String catalogName) 
  int deleteCatalog(int id) 
  
* Data for catalogs is stored in productsdb.catalogtype.

* The main method in the Main class provides test code
for your methods. Be careful in the way you modify
the catalogtype table in the database, since it will be used in the
real project later. The main method will ask you
to add a new catalog, grab the new catalog id,
and then delete that same catalog, so the table
will remain unchanged.

---

