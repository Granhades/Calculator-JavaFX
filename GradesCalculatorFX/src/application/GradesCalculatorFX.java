//HGP Assignment 01 - GradesCalculatorFX template

//Student Name: Fernando Granado Bermudo
//Student Number: 3145546


package application;

import java.io.File;
import java.io.PrintWriter;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GradesCalculatorFX extends Application {
	// Declare components that require class scope
	Label lblNumber;
	Label lblClear;
	TextField txtfNumber; //
	TextArea txtMain;
	Button btnChangePhoto, btnAddGrades, btnExport, btnClear;
	
	//Previous user student number input
	String previousNumber;
	
	//Profile Image
	ImageView imvProfilePhoto; //Img profile photo
	Image img; //Normal img
	
	//Your Details
	String StudentName = "Calculator JavaFX"; 
	String StudentNumber = ""; 
	
	//TXTFields to receive and manage the input data Sub-Layout
	TextField txtCaGrade,txtExamGrade,txtCaWeight,txtExamWeight;
	
	//PrimaryStage declaration to use in different methods
	Stage primaryStage;
	
	//Title and Grade for each class
	//Make it constant variables to use Switch(case)
	final String lectureOne = "HCI and GUI Programming";
	final String lectureTwo = "Data Communications and Networks";
	final String lectureThree = "Software Engineering for Web Applications";
	final String lectureFour = "Programming and Data Structures";
	final String lectureFive = "Object Oriented Development";
	
	//Lectures agrupation
	ComboBox<String> lectures;
	
		//Lectures grade
	String lectureOneResults = "";
	String lectureTwoResults = "";
	String lectureThreeResults = "";
	String lectureFourResults = "";
	String lectureFiveResults = "";
	
	
	//TXT MAIN <h1>
	Label txtMainTitle;
	
	
	// Constructor
	public GradesCalculatorFX() {
		
		lblNumber = new Label("Student Number");
		txtfNumber = new TextField();
		txtMainTitle = new Label("RESULTS");
		txtMain = new TextArea();
		btnChangePhoto = new Button("Change Photo");
		btnAddGrades = new Button("Check Grades");
		btnExport = new Button("Export Results");
		btnClear = new Button("Clear");
		
		
		//Profile IMG
		img = new Image(getClass().getResource("/Assets/profile.jpg").toExternalForm());
		
		imvProfilePhoto = new ImageView(img);

		//Inside of the Check Grades dialog
		
		txtCaGrade = new TextField(); // 1,1 position
		txtExamGrade = new TextField(); // 1,2 position
		txtCaWeight = new TextField(); // 3,1 position
		txtExamWeight = new TextField();// 3,2 position
		
		//comboBox for menu with lectures
		//ComboBox for all the Lectures that we have this semester 
		lectures = new ComboBox<>(); //Position 1,0, 2,0, 3,0
		lectures.getItems().addAll(lectureOne,lectureTwo,lectureThree,lectureFour,lectureFive);
		
	} 

	// Event handling
	@Override 
	public void init() {
		
		//Handle events on check grades button (dialog)
		btnAddGrades.setOnAction(event -> {
			//Check if the format for student number are correct 
			if(checkStudentFormatNumber()) 
			{
				//Check if the last number user is the same that current one
				if(!txtfNumber.getText().equals(previousNumber))
				{	
					resetResults(); //We reset the grades and update the txtfNumber for next cycle.
				}
				//Open checkGrades
				checkGrades();		
			}
		});
		
		//Handle events on the export button (save as txt)
		
		btnExport.setOnAction(event -> exportResults());
				
		//Handle events on the change photo button (system dialog)
		
		btnChangePhoto.setOnAction(event -> changePhoto());
		
		//Custom - Reset - clear all grade value and update last student number
		btnClear.setOnAction(event -> resetResults());
		
	}
	
	
	
	// Window setup and layouts
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Declarate primaryStage
		this.primaryStage = primaryStage;
		
		//Manage window title and size
		primaryStage.setTitle("Granhades - " + StudentName + " " + StudentNumber);
		int height = 800;
		int width = 1000;
		//default window width and height
		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
		//Icon declaration
		
	
		try {
			primaryStage.getIcons().add(new Image("Assets/calculator.png"));
			}
		catch (Exception e) {
			
			e.printStackTrace();
			System.err.println("Something went wrong with the icon!");
			}
		//Create main layout
		BorderPane bpMain = new BorderPane();
		//Create sub-layouts
		VBox leftSideMenu = new VBox(); //Left Menu
		VBox centerSideMenu = new VBox(); // Center
		VBox bottomSideMenu = new VBox(); //Bottom
		
		//Add components to sub-layouts 
		leftSideMenu.getChildren().addAll(imvProfilePhoto, btnChangePhoto, lblNumber, txtfNumber, btnAddGrades);
		centerSideMenu.getChildren().addAll(txtMainTitle,txtMain, btnClear);	
		bottomSideMenu.getChildren().add(btnExport);
		
		//Characteristics for components in sub-layouts
		txtMain.setEditable(false);
		txtfNumber.setPromptText("Enter at least 8 digits");
		
		//Add sub-layouts to main layout
		
		bpMain.setLeft(leftSideMenu);
		bpMain.setCenter(centerSideMenu);
		bpMain.setBottom(bottomSideMenu);
		
		//Manage Padding and Spacing for Layouts
		
		leftSideMenu.setPadding(new Insets(30));
		leftSideMenu.setSpacing(10);
		centerSideMenu.setPadding(new Insets(30));
		bottomSideMenu.setPadding(new Insets(20));
		
		//Manage Alignment for Layouts
		
		leftSideMenu.setAlignment(Pos.CENTER);
		centerSideMenu.setAlignment(Pos.CENTER);
		bottomSideMenu.setAlignment(Pos.CENTER);
		
		//Bind ImageView to 1/4th of the width of the main window and preserve its ratio
		imvProfilePhoto.fitWidthProperty().bind(primaryStage.widthProperty().divide(4)); //bind
		imvProfilePhoto.setPreserveRatio(true); //preserve
		
		//Bind width of changePhoto button to width of ImageView
		btnChangePhoto.prefWidthProperty().bind(imvProfilePhoto.fitWidthProperty());
		
		//Bind txtMain with LeftSide Part
		txtMain.prefHeightProperty().bind(leftSideMenu.heightProperty().divide(1.5));

		//Create Scene (main layout as argument)
		Scene s = new Scene(bpMain);
		
		//Apply a Stylesheet (global style of UI)
		s.getStylesheets().add(getClass().getResource("/Assets/application.css").toExternalForm());
		
		//Set ID for CSS
		bpMain.setId("mainWindows");
		btnClear.setId("btnClear");
		leftSideMenu.setId("leftMenu");
		centerSideMenu.setId("centerMenu");
		//Set scene
		primaryStage.setScene(s);
		// Show stage
		primaryStage.show();
	}
	
	
	//SubLayout for Check grades
	private void checkGrades()
	{
		//Create a subscene 
		Stage checkG = new Stage();
		
		//Get back the number that they choosed to show up in title
		String chosenNum = txtfNumber.getText();
		
		//title
		checkG.setTitle("Please input marks for student : " + chosenNum);
		//Set by default the size
		checkG.setWidth(500);
		checkG.setHeight(250);
		//Set Icon
		
		try {
			checkG.getIcons().add(new Image("Assets/calculator.png"));
			}
		catch (Exception e) {
			
			e.printStackTrace();
			System.err.println("Something went wrong with the icon!");
			}
		
		//GridPane
		GridPane gpDialogGrade = new GridPane();
		
		
		//Components declaration
		Label module = new Label("Module :"); //Module 0,0 position
		Label caGrade = new Label("CA Grade: "); // 0,1 position
		Label examGrade = new Label("Exam/Project Grade: "); // 0,2 position
		Label caWeight = new Label("Weight :"); //2,1 
		Label examWeight = new Label("Weight :"); // 2,2
		Button cancel = new Button("Cancel"); //3,2
		Button calculate = new Button("Calculate"); // 3,3
		//Add controls for layout (col, rows)
		
		gpDialogGrade.add(module, 0,0);
		gpDialogGrade.add(caGrade, 0,1);
		gpDialogGrade.add(examGrade, 0,2);
		gpDialogGrade.add(lectures, 1, 0); 
		gpDialogGrade.add(txtCaGrade, 1,1); //added from main
		gpDialogGrade.add(txtExamGrade, 1,2);//added from main
		gpDialogGrade.add(caWeight, 2,1);
		gpDialogGrade.add(examWeight, 2,2);
		gpDialogGrade.add(txtCaWeight, 3,1);//added from main
		gpDialogGrade.add(txtExamWeight, 3,2);//added from main
		gpDialogGrade.add(calculate, 1, 3);
		gpDialogGrade.add(cancel, 3,3);
		//expanse lectures into 1,0 2,0 and 3,0 position
		GridPane.setColumnSpan(lectures, 3);
		
		//Default characteristics
		
		//WEIGHT - CA
		txtCaWeight.setText("50"); //By default 50
		txtCaWeight.setMaxWidth(55);
		txtCaWeight.setAlignment(Pos.CENTER);//Alignment
		//WEIGHT - EXAM
		txtExamWeight.setText("50"); //By default 50
		txtExamWeight.setMaxWidth(55);
		txtExamWeight.setAlignment(Pos.CENTER);//Alignment
		//ComboBox default 1
		lectures.setValue(lectureOne); //Avoid error providing at least one option
		//GRADE - CA
		txtCaGrade.setPromptText("1 to 99"); //To show the user that should insert numbers
		txtCaGrade.setMaxWidth(55);
		txtCaGrade.setAlignment(Pos.CENTER);//Alignment
		//GRADE - EXAM
		txtExamGrade.setPromptText("1 to 99"); //To show the user that should insert numbers
		txtExamGrade.setMaxWidth(55);
		txtExamGrade.setAlignment(Pos.CENTER); //Alignment
		
		
		//Styles inside of checkG
		gpDialogGrade.setPadding(new Insets(50));
		gpDialogGrade.setHgap(15);
		gpDialogGrade.setVgap(20);
	
		//Padding
		gpDialogGrade.setPadding(new Insets(30, 30, 30, 30));
		
		//Center
		gpDialogGrade.setAlignment(Pos.CENTER);
		
		//TextField
		txtCaGrade.setPrefWidth(100);
		txtExamGrade.setPrefWidth(100);
		txtCaWeight.setPrefWidth(100);
		txtExamWeight.setPrefWidth(100);
		
		//Providing ID -> .css
		gpDialogGrade.setId("checkWindows");
		module.setId("moduleTitle");
		cancel.setId("cancelButton");
		
		
		//Events inside Calculate and Close
		//sub init()
		calculate.setOnAction(event -> Calculate());
		cancel.setOnAction(event -> checkG.close());
				
		//Set the scene 
		Scene s = new Scene(gpDialogGrade, 500, 250);
		checkG.setScene(s);
		
		//CSS Link
		s.getStylesheets().add(getClass().getResource("/Assets/application.css").toExternalForm());
		
		//Set the position from MainLayouy to show on the left part as menu
		//Arbitrary calculation to try to show on the left part depending which size have the screen
		checkG.setX(primaryStage.getX() + imvProfilePhoto.localToScene(0, 0).getX() - (txtMain.localToScene(0, 0).getX() / 3));
		checkG.setY(primaryStage.getY() + imvProfilePhoto.localToScene(0, 0).getY() + 100);
				
		checkG.show();
	}
	
	//take the inputs and calculate
	private void Calculate()
	{	
		//Empty string to catch the situation (PASS, FAIL ETC)
		String situation = "";
		
		//Name
		String currentLecture = lectures.getValue(); //To put the name of the lecture
		
		try {
		
			//Get the input from the user and convert into int
			int caGrade = Integer.parseInt(txtCaGrade.getText());
			int caWeight = Integer.parseInt(txtCaWeight.getText()) ; 
			int examGrade = Integer.parseInt(txtExamGrade.getText());
			int examWeight = Integer.parseInt(txtExamWeight.getText());
			
			//Do the calculation
			int caTotal = (int)(caGrade * (caWeight / 100.0));
			int examTotal = (int)(examGrade * (examWeight / 100.0));
			int result = caTotal + examTotal;
		
			//Possible error alert declaration
			//Check if there is number < 1 or number>99 for GRADE
			Alert gradeError = new Alert(AlertType.ERROR);
			if(examGrade < 1 || examGrade > 99 || caGrade < 1 || caGrade > 99)
			{	//Errors output
				gradeError.setHeaderText("INPUT Calculation Error");
				gradeError.setContentText("ERROR grades should be between 1 to 99!");
				gradeError.showAndWait();
		
			}
			//Then if the numbers are correct we try to calculate it
			else {
				
				//Possible error because weight always should be 100
				
				// caWeight + examWeight should be always 100
				if(caWeight + examWeight == 100) //If everything is ok we proceed to calculate it
				{	
					//Module PASS to show in console
					if(caGrade >= 40 && examGrade >= 40  )
					{
						situation += "**** Module PASS. ****\n";
					}
					//Fail both
					else if(caGrade < 40 && examGrade < 40)
					{
						situation += "** Module FAIL. Repeat both components at next sitting. **\n";
					}
					//Fail assignment
					else if(caGrade < 40)
					{
						situation += "** Module FAIL. Repeat Assignment at next sitting. **\n";
					}
					//Fail exam
					else if(examGrade < 40)
					{
						situation += "** Module FAIL. Repeat Exam at next sitting. **\n";
					}
					
					
					//General String about grades
					
					String currentResult = (currentLecture+"\n" + "CA: "+caGrade + "%      " + "Exam: "+examGrade + "%      \n"
							+"Overrall Grade : " +result+"\n"+situation+"\n");
					
					//Now we update the lecture grade to the last input. 
					switch(currentLecture)
					{
					//Each lecture can just have 1 grade so we check 
					case lectureOne:
						lectureOneResults = currentResult;
						break;
					case lectureTwo:
						lectureTwoResults = currentResult;
						break;
					case lectureThree:
						lectureThreeResults = currentResult;
						break;
					case lectureFour:
						lectureFourResults = currentResult;
						break;
					case lectureFive:
						lectureFiveResults = currentResult;
						break;
					}
					//We update the Main text. NO -> append() we don't want add we want update -> use .setText()
					txtMain.setText(lectureOneResults + lectureTwoResults + lectureThreeResults + lectureFourResults + lectureFiveResults);

				}
				//If not we output the error
				else
				{	//Error output
					Alert weightError = new Alert(AlertType.ERROR);
					weightError.setHeaderText("Weight Calculation Error");
					weightError.setContentText("ERROR. Both weight must have the sum of 100");
					weightError.showAndWait();
				}
			}
		} //We catch everything that is not integer including blank space
		catch(NumberFormatException e)
		{	//Alert output
			Alert numberFormat = new Alert(AlertType.ERROR);
			numberFormat.setHeaderText("FORMAT ERROR");
			numberFormat.setContentText("Please introduce integers");
			numberFormat.showAndWait();
		}
	}
	
	//Button functions
	private void changePhoto()
	{
		//Take the file
		FileChooser fileChooser = new FileChooser();
		//Filter by type on this case is image
		ExtensionFilter onlyImage = new ExtensionFilter("JPG and JPEG Images", "*.jpg","*.jpeg");
		//Add the filter to filechooser
		fileChooser.getExtensionFilters().add(onlyImage);

		//open a browser for find a file
		File chosenFile = fileChooser.showOpenDialog(primaryStage);
		if(chosenFile != null)
		{
			//get the path and store the location
			String fileLocation = chosenFile.getPath();
			
			//Store the img	
			try {	
				//Show the img in the UI
				img = new Image(chosenFile.toURI().toString());
				imvProfilePhoto.setImage(img);		
			}
			//Possibles errors
			catch (Exception e)
			{
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("Image File Error");
				error.setHeaderText("Please try again");
				error.setContentText("An error ocurred trying to choose the image");
				error.showAndWait();
			}
		}	
		
	}
	
	private void exportResults()
	{ 	//Get back the whole text from the txtMain
		String text = txtMain.getText();
		
		FileChooser fileChooser = new FileChooser();
		//File Name including Results<StudentNumber>.txt
		String nameFileTxt =  "Results"+txtfNumber.getText();
		//Options and format
		fileChooser.setTitle("Export"); //Title
		fileChooser.setInitialFileName(nameFileTxt); //Name defined before
		//Filter just .txt
		ExtensionFilter txtFilter = new ExtensionFilter("Just text (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(txtFilter); //Create with the filter
		//Save the localization for the user
		File file = fileChooser.showSaveDialog(null);
		//Then try to write the file
		try (PrintWriter writer = new PrintWriter(file);) //We create with the file that the path that the user selected
		{ 	//Write the contains in text
			writer.write(text); //We create some alerts to inform the user that was sucessfull
			Alert save = new Alert(Alert.AlertType.INFORMATION);
			save.setTitle("Congratulations");
			save.setHeaderText("The file was save sucessfully");
			save.showAndWait();
		}
		catch(Exception e)
		{	//If there is some error we show an alert with information
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("File Error");
			error.setHeaderText("Please try again");
			error.setContentText("An error ocurred trying to save the file");
			error.showAndWait();
		}
		
	}
	
	private boolean checkStudentFormatNumber()
	{
		//Error declaration
		Alert studentLength = new Alert(AlertType.ERROR);
		if(!txtfNumber.getText().matches("\\d{8,}"))
		{	//Error format
			studentLength.setHeaderText("Number length error");
			studentLength.setContentText("Please introduce a real number that should be 8 digits!");
			studentLength.showAndWait();
			return false;
			}
		else {
			return true;
		}
	}
	
	private void resetResults()
	{	//Usefull to check Grades and Clear
		txtMain.setText(""); // reset the whole text
		//Also we reset the variables that store the grades
		lectureOneResults = "";
		lectureTwoResults = "";
		lectureThreeResults ="";
		lectureFourResults ="";
		lectureFiveResults = "";
		//We update current number to previous to check if is different to reset();
		previousNumber = txtfNumber.getText(); 
	}
	
	// Launch application
	public static void main(String[] args) {
		launch(args);
	}
}