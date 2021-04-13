package application;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainGUI extends Application {
	private Stage window; // The main window
	private Scene main; // The main menu scene
	private UploadedFile newFile=new UploadedFile(); //The uploaded file

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Sets the current window to the main menu stage
		window = primaryStage;

		// Sets the title of the window to Milk Weight Organizer
		window.setTitle("Milk Weight Organizer");

		// Creates a new GridPlane and sets the padding for grid to be 80 top, 40 right,
		// 80 from the bottom, 40 from the left
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(80, 40, 80, 40));

		// Sets the vertical gap to 35 pixels and horizontal gap to 20 pixels
		grid.setVgap(35);
		grid.setHgap(20);

		// Creates a new uploadGrid for a potential new window
		GridPane uploadGrid = new GridPane();
		uploadGrid.setPadding(new Insets(80, 40, 80, 40));
		uploadGrid.setVgap(25);
		uploadGrid.setHgap(30);

		// Sets the background color of the grid to the color
		uploadGrid.setStyle("-fx-background-color: #F0F8FF");

		// Creates a new scene if the upload button is pushed
		Scene uploadScene = new Scene(uploadGrid, 600, 500);

		// Creates a new grid for a potential new window when edit button is clicked
		GridPane editGrid = new GridPane();
		editGrid.setPadding(new Insets(80, 40, 80, 40));
		editGrid.setVgap(25);
		editGrid.setHgap(30);
		editGrid.setStyle("-fx-background-color: #F0F8FF");

		// sets a new scene if the edit button is pushed
		Scene scene2 = new Scene(editGrid, 600, 500);

		// Sets a new grid for a potential new window when display data is clicked
		GridPane displayGrid = new GridPane();
		displayGrid.setPadding(new Insets(80, 40, 80, 40));
		displayGrid.setVgap(25);
		displayGrid.setHgap(30);
		displayGrid.setStyle("-fx-background-color: #F0F8FF");

		// Sets a new scene if the display button is pushed
		Scene displayScene = new Scene(displayGrid, 600, 500);

		// Common styles for the buttons and labels with bold arial font and blue color
		String styles = "-fx-background-color: #00BFFF;" + "-fx-border-color: #008B8B;" + "-fx-font-size: 2em;"
				+ "-fx-font: bold italic 15pt \"Arial\";";
		String labelStyle = "-fx-font: bold italic 15pt \"Arial\";" + "-fx-font-size: 2em;";

		// Creates a new title label at the top of the main menu
		Label label = new Label("Milk Organizer Dashboard");
		label.setMaxSize(600, 300);
		label.setStyle(labelStyle);

		// Sets it to the 6th column and 1st row of the grid
		GridPane.setConstraints(label, 6, 1);

		// Creates a button that is used to upload files
		Button uploadData = new Button("Upload Data File");
		uploadData.setMaxSize(500, 200);
		uploadData.setStyle(styles);
		uploadData.setEffect(new DropShadow());

		// Button is in the 6th column and 2nd row
		GridPane.setConstraints(uploadData, 6, 2);

		// When the button is pressed, the uploadData method will be initiated
		uploadData.setOnAction(e -> uploadData(uploadGrid, uploadScene));

		// Creates a new button that is used to edit data
		Button editData = new Button("Edit Data");
		editData.setStyle(styles);
		editData.setEffect(new DropShadow());
		editData.setMaxWidth(500);
		editData.setMaxHeight(200);

		// Button is in the 6th column, 3rd row
		GridPane.setConstraints(editData, 6, 3);

		// When button is pressed, the editData method is called with the previous
		// editGrid and the scene
		// incorporating editGrid
		editData.setOnAction(e -> editData(editGrid, scene2));

		// Creates a button that displays the data
		Button displayData = new Button("Display Data");
		displayData.setStyle(styles);
		displayData.setEffect(new DropShadow());
		displayData.setMaxSize(500, 200);

		GridPane.setConstraints(displayData, 6, 4);

		try {
			// When display button is pressed, the displayData method is called
			displayData.setOnAction(e -> displayData(displayGrid, displayScene));
		} catch (NullPointerException incorrect) {
			// Creates a new label for wrong input
			Label wrong = new Label("Upload File");
			wrong.setMaxSize(100, 100);
			String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
			wrong.setStyle(wrongLabelStyle);

			// Places the label at 5th colum and 1st row
			GridPane.setConstraints(wrong, 6, 5);
			grid.getChildren().add(wrong);
			return;
		}

		// Adds all the buttons and labels to the grid
		grid.getChildren().addAll(label, uploadData, editData, displayData);

		// Sets the background of the grid to light blue
		grid.setStyle("-fx-background-color: #F0F8FF");

		// Gives the grid shadow effect
		grid.setEffect(new DropShadow());

		// Creates the main scene with the main grid and a 600 / 500 window
		main = new Scene(grid, 600, 500);

		// Sets the scene of the stage to the main scene
		window.setScene(main);

		// Displays the window
		window.show();

	}

	/**
	 * This method is used when the upload button is pressed and a file needs to be
	 * uploaded
	 * 
	 * @param grid  the grid for the uploadData method (uploadGrid)
	 * @param scene the scene for the uploadData method (uploadScene)
	 */
	private void uploadData(GridPane grid, Scene scene) {

		// Common styles for the buttons and labels
		String styles = "-fx-background-color: #00BFFF;" + "-fx-border-color: #008B8B;"
				+ "-fx-font: bold italic 15pt \"Arial\";";

		String labelStyle = "-fx-font: bold italic 15pt \"Arial\";";

		// Creates a new label at the top of the screen
		Label label = new Label("Upload a Data File:");
		label.setMaxSize(500, 200);
		label.setStyle(labelStyle);

		// Places the label at 5th colum and 1st row
		GridPane.setConstraints(label, 5, 1);

		// Creates the window with the given scene
		window.setScene(scene);
		window.show();

		// File chooser used to allow user to choose a file
		FileChooser fileChooser = new FileChooser();

		// The file chooser only takes in CSV files so extension filters allows only csv
		// files
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

		// Creates a new button to select a file
		Button fileSelect = new Button("Select A File");
		fileSelect.setMaxSize(500, 200);
		fileSelect.setStyle(styles);
		fileSelect.setEffect(new DropShadow());

		// Places button underneath label
		GridPane.setConstraints(fileSelect, 5, 2);

		// Creates a return home button that redirects to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setEffect(new DropShadow());
		returnHome.setMaxSize(500, 200);

		// Button is placed underneath select file button
		GridPane.setConstraints(returnHome, 5, 3);

		// When the returnHome button is pressed, the user is redirected to the main
		// scene
		returnHome.setOnAction(e -> window.setScene(main));

		// When button is pressed user must be able to select file
		fileSelect.setOnAction(e -> {

			// Allows user to select a file
			File selectedFile = fileChooser.showOpenDialog(window);

			// Checks if the user selected a file
			if (selectedFile != null) {

				// If a user selected a file then allow the user to upload it
				String entryStyles = "-fx-background-color: #7CFC00;" + "-fx-font: bold italic 15pt \"Arial\";";

				// Creates a new button that allows the user to upload their file
				Button fileHolder = new Button("Click to Upload " + selectedFile.getName());
				fileHolder.setMaxSize(500, 200);
				fileHolder.setStyle(entryStyles);
				fileHolder.setEffect(new DropShadow());
				GridPane.setConstraints(fileHolder, 5, 3);

				// Sets the button at column 5, row 4
				GridPane.setConstraints(returnHome, 5, 4);

				// Creates a new confirmation window
				GridPane confirm = new GridPane();
				confirm.setPadding(new Insets(80, 40, 80, 40));
				confirm.setVgap(25);
				confirm.setHgap(30);

				// Creates a new confirmation scene
				Scene confirmScene = new Scene(confirm, 600, 500);

				// When the upload button is pressed, the confirmation method is called
				fileHolder.setOnAction(newEvent -> {
					try {
						
						//Calls UploadedFile with the name of the user's file
						this.newFile = new UploadedFile(selectedFile.getName());
						
						//Sends the user a confirmation screen
						confirmation(confirm, confirmScene, styles, "upload");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				});

				// adds the fileHolder button to the grid
				grid.getChildren().addAll(fileHolder);

			}
		});

		// Adds all the buttons and labels to the grid
		grid.getChildren().addAll(fileSelect, label, returnHome);
	}

	/**
	 * This method is used to edit data and call the add data, remove data, and
	 * change data methods
	 * 
	 * @param grid  the current grid
	 * @param scene the current scene
	 */
	private void editData(GridPane grid, Scene scene) {

		// Creates a new addGrid if user selects to add Data
		GridPane addGrid = new GridPane();
		addGrid.setPadding(new Insets(80, 40, 80, 40));
		addGrid.setVgap(10);
		addGrid.setHgap(30);
		addGrid.setStyle("-fx-background-color: #F0F8FF");

		// Creates a new addScene
		Scene addScene = new Scene(addGrid, 600, 500);

		// Creates a new removeGrid if user selects to remove Data
		GridPane removeGrid = new GridPane();
		removeGrid.setPadding(new Insets(80, 40, 80, 40));
		removeGrid.setVgap(10);
		removeGrid.setHgap(30);
		removeGrid.setStyle("-fx-background-color: #F0F8FF");

		// Create a new removeScene
		Scene removeScene = new Scene(removeGrid, 600, 500);

		// Creates a new changeGrid if user selects to changeData
		GridPane changeGrid = new GridPane();
		changeGrid.setPadding(new Insets(80, 40, 80, 40));
		changeGrid.setVgap(10);
		changeGrid.setHgap(30);
		changeGrid.setStyle("-fx-background-color: #F0F8FF");

		// Create a new changeScene
		Scene changeScene = new Scene(changeGrid, 600, 500);

		String styles = "-fx-background-color: #00BFFF;" + "-fx-border-color: #008B8B;"
				+ "-fx-font: bold italic 15pt \"Arial\";";

		String labelStyle = "-fx-font: bold italic 15pt \"Arial\";";

		// Label for the edit window
		Label label = new Label("Choose what you would like to do:");
		label.setMaxSize(500, 200);
		label.setStyle(labelStyle);
		GridPane.setConstraints(label, 3, 1);

		// Button that allows user to add data
		Button addData = new Button("Add Data");
		addData.setStyle(styles);
		addData.setEffect(new DropShadow());
		addData.setMaxWidth(400);
		addData.setMaxHeight(200);
		GridPane.setConstraints(addData, 3, 2);

		// Button that allows user to remove data
		Button removeData = new Button("Remove Data");
		removeData.setStyle(styles);
		removeData.setEffect(new DropShadow());
		removeData.setMaxWidth(400);
		removeData.setMaxHeight(200);
		GridPane.setConstraints(removeData, 3, 3);

		// Button that allows user to change data
		Button changeData = new Button("Change Data");
		changeData.setStyle(styles);
		changeData.setEffect(new DropShadow());
		changeData.setMaxWidth(400);
		changeData.setMaxHeight(200);
		GridPane.setConstraints(changeData, 3, 4);

		// Button that allows user to return back to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setEffect(new DropShadow());
		returnHome.setMaxWidth(400);
		returnHome.setMaxHeight(200);
		GridPane.setConstraints(returnHome, 3, 5);

		// When pressed, the button sets the scene back to the main scene
		returnHome.setOnAction(e -> window.setScene(main));

		// Adds all the buttons and labels to the grid
		grid.getChildren().addAll(label, addData, removeData, changeData, returnHome);
		window.setScene(scene);
		window.show();

		// When add data is selected, the addData method is called
		addData.setOnAction(e -> addData(addGrid, addScene, styles, scene));

		// When remove data is selected, the removeData method is called
		removeData.setOnAction(e -> removeData(removeGrid, removeScene, styles, scene));

		// When change data is selected, the changeData method is called
		changeData.setOnAction(e -> changeData(changeGrid, changeScene, styles, scene));
	}

	/**
	 * This method is used to add data to the list of current data and takes using
	 * farm id, year, month, day, and milk weight
	 * 
	 * @param grid        the current grid
	 * @param scene       the current scene
	 * @param styles      the commons styles used for buttons
	 * @param returnScene the scene used in the edit menu
	 */
	private void addData(GridPane grid, Scene scene, String styles, Scene returnScene) {

		// Label for the addData window
		String labelStyle = "-fx-font: bold italic 10pt \"Arial\";";

		// Creates a title for the window
		Label label = new Label("Add A New Milk Entry");
		label.setMaxSize(500, 200);
		label.setStyle(labelStyle);
		GridPane.setConstraints(label, 2, 1);

		// Creates a label to show where to enter farm id
		Label labelFarm = new Label("Enter the Farm ID:");
		labelFarm.setMaxSize(300, 200);
		labelFarm.setStyle(labelStyle);
		GridPane.setConstraints(labelFarm, 1, 2);

		// Creates a text box for the user to enter a farm id
		TextField farmID = new TextField();

		// Text inside the box already and disappears when box is selected
		farmID.setPromptText("Enter Farm ID");
		farmID.setMaxSize(300, 100);
		;
		GridPane.setConstraints(farmID, 2, 2);

		// Creates a label to show where to enter year
		Label labelYear = new Label("Enter the Year:");
		labelYear.setMaxSize(300, 200);
		labelYear.setStyle(labelStyle);
		GridPane.setConstraints(labelYear, 1, 3);

		// Creates a text box for the user to enter a year
		TextField year = new TextField();
		year.setPromptText("Enter Year");
		year.setMaxSize(300, 100);
		;
		GridPane.setConstraints(year, 2, 3);

		// Label used to show user where to enter month
		Label labelMonth = new Label("Enter the Month:");
		labelMonth.setMaxSize(300, 200);
		labelMonth.setStyle(labelStyle);
		GridPane.setConstraints(labelMonth, 1, 4);

		// Creates a text box for the user to enter a month
		TextField month = new TextField();
		month.setPromptText("Enter Month as Number");
		month.setMaxSize(300, 100);
		;
		GridPane.setConstraints(month, 2, 4);

		// Label used to show user where to enter day
		Label labelDay = new Label("Enter the Day:");
		labelDay.setMaxSize(300, 200);
		labelDay.setStyle(labelStyle);
		GridPane.setConstraints(labelDay, 1, 5);

		// Creates a text box for the user to enter a day
		TextField day = new TextField();
		day.setPromptText("Enter Day");
		day.setMaxSize(300, 100);
		;
		GridPane.setConstraints(day, 2, 5);

		// Label used to show where to enter milk weight
		Label labelWeight = new Label("Enter the Milk Weight:");
		labelWeight.setMaxSize(300, 200);
		labelWeight.setStyle(labelStyle);
		GridPane.setConstraints(labelWeight, 1, 6);

		// Creates a text box for the user to enter a weight
		TextField weight = new TextField();
		weight.setPromptText("Enter Milk Weight");
		weight.setMaxSize(300, 100);
		GridPane.setConstraints(weight, 2, 6);

		// Creates a new addEntry button that is used to allow the user to add their
		// entry
		Button addEntry = new Button("Click To Add Entry");
		String entryStyles = "-fx-background-color: #D3D3D3;" + "-fx-font: bold italic 15pt \"Arial\";";
		addEntry.setStyle(entryStyles);
		addEntry.setMaxWidth(300);
		addEntry.setMaxHeight(200);

		// Sets the button at column 2, row 7
		GridPane.setConstraints(addEntry, 2, 7);

		// When the button is pressed
		addEntry.setOnAction(e -> {

			try {
				
				//Calls the addFarm method with the user inputs
				newFile.addFarm(farmID.getText(), year.getText(), month.getText(), day.getText(), weight.getText());

				// Create a new add grid to show confirmation
				GridPane newAddGrid = new GridPane();
				newAddGrid.setPadding(new Insets(80, 40, 80, 40));
				newAddGrid.setVgap(10);
				newAddGrid.setHgap(30);
				newAddGrid.setStyle("-fx-background-color: #F0F8FF");

				// Create a new confirmation scene
				Scene confirmationScene = new Scene(newAddGrid, 600, 500);

				// Call the confirmation method with newAddGrid and confirmation scene
				// with the add action
				confirmation(newAddGrid, confirmationScene, styles, "add");
			} catch (NumberFormatException incorrect) {
				//If arguments were not numbers
				
				// Creates a new label for wrong input
				Label wrong = new Label("Wrong input");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 5th column and 1st row
				GridPane.setConstraints(wrong, 2, 10);
				grid.getChildren().add(wrong);
				return;
			}

		});

		// Button used to return to the edit menu
		Button returnEdit = new Button("Return to Edit Menu");
		returnEdit.setStyle(styles);
		returnEdit.setMaxWidth(300);
		returnEdit.setMaxHeight(200);
		GridPane.setConstraints(returnEdit, 2, 8);

		// When pressed, sends the user back to the edit scene
		returnEdit.setOnAction(e -> window.setScene(returnScene));

		// Button used to return to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setMaxWidth(300);
		returnHome.setMaxHeight(200);
		GridPane.setConstraints(returnHome, 2, 9);
		returnHome.setOnAction(e -> window.setScene(main));

		// Adds all the labels, textboxes, and buttons to the grid
		grid.getChildren().addAll(farmID, year, month, day, weight, addEntry, returnEdit, returnHome, labelFarm,
				labelYear, labelMonth, labelDay, labelWeight, label);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This method is used as a confirmation screen to show an action has been done
	 * 
	 * @param grid   the current grid
	 * @param scene  the current scene
	 * @param styles the set of common styes used throughout the program
	 * @param action the action that was done
	 */
	private void confirmation(GridPane grid, Scene scene, String styles, String action) {
		String labelStyle = "-fx-font: bold italic 10pt \"Arial\";";

		// If the action was to add data
		if (action.equals("add")) {

			// Show the specific message for added data
			Label label = new Label("Your entry has been added, confirm in Display Data");
			label.setMaxSize(500, 200);
			label.setStyle(labelStyle);
			GridPane.setConstraints(label, 4, 1);
			grid.getChildren().add(label);
		}

		// If the action was to remove data
		if (action.equals("remove")) {

			// Show the specific message for remove data
			Label label = new Label("Your entry has been removed, confirm in Display Data");
			label.setMaxSize(500, 200);
			label.setStyle(labelStyle);
			GridPane.setConstraints(label, 4, 1);
			grid.getChildren().add(label);
		}

		//If action was change
		if (action.equals("change")) {
			// Show the specific message for change data
			Label label = new Label("Your entry has been changed, confirm in Display Data");
			label.setMaxSize(500, 200);
			label.setStyle(labelStyle);
			GridPane.setConstraints(label, 4, 1);
			grid.getChildren().add(label);
		}

		// If the action was to upload a file
		if (action.equals("upload")) {

			// Show the specific message for added file
			Label label = new Label("Your file has been uploaded, confirm in Display Data");
			label.setMaxSize(500, 200);
			label.setStyle(labelStyle);
			GridPane.setConstraints(label, 4, 1);
			grid.getChildren().add(label);
		}

		//If action was to get farm report
		if (action.equals("FarmReport")) {
			// Show the specific message for farm report
			Label label = new Label("Your farm report is ready, find farmReport.txt");
			label.setMaxSize(500, 200);
			label.setStyle(labelStyle);
			GridPane.setConstraints(label, 4, 1);
			grid.getChildren().add(label);
		}

		//If action was to get annual report
		if (action.equals("AnnualReport")) {
			// Show the specific message for annual report
			Label label = new Label("Your annual report is ready, find annualReport.txt");
			label.setMaxSize(500, 200);
			label.setStyle(labelStyle);
			GridPane.setConstraints(label, 4, 1);
			grid.getChildren().add(label);
		}

		//If action was to get a monthly report
		if (action.equals("MonthlyReport")) {
			// Show the specific message for monthly report
			Label label = new Label("Your monthly report is ready, find monthlyReport.txt");
			label.setMaxSize(500, 200);
			label.setStyle(labelStyle);
			GridPane.setConstraints(label, 4, 1);
			grid.getChildren().add(label);
		}

		// Button used to return back to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setMaxWidth(300);
		returnHome.setMaxHeight(200);
		GridPane.setConstraints(returnHome, 4, 2);
		returnHome.setOnAction(e -> window.setScene(main));

		// Adds the button to the grid
		grid.getChildren().addAll(returnHome);

		window.setScene(scene);
		window.show();
	}

	/**
	 * This method is used to remove data from the list of data with input from the
	 * user for what farm id, year, month, and day of the entry to remove
	 * 
	 * @param grid        the current grid
	 * @param scene       the current scene
	 * @param styles      the common styles throughout the program
	 * @param returnScene the edit scene menu
	 */
	private void removeData(GridPane grid, Scene scene, String styles, Scene returnScene) {
		String labelStyle = "-fx-font: bold italic 10pt \"Arial\";";

		// Creates a title for the window
		Label label = new Label("Remove An Existing Milk Entry");
		label.setMaxSize(500, 200);
		label.setStyle(labelStyle);
		GridPane.setConstraints(label, 2, 1);

		// Creates a label to show user where to enter farm id
		Label labelFarm = new Label("Enter the Farm ID:");
		labelFarm.setMaxSize(300, 200);
		labelFarm.setStyle(labelStyle);
		GridPane.setConstraints(labelFarm, 1, 2);

		// Text box used for user to input farm id
		TextField farmID = new TextField();
		farmID.setPromptText("Enter Farm ID");
		farmID.setMaxSize(300, 100);
		;
		GridPane.setConstraints(farmID, 2, 2);

		// Label used to show user where to enter year
		Label labelYear = new Label("Enter the Year:");
		labelYear.setMaxSize(300, 200);
		labelYear.setStyle(labelStyle);
		GridPane.setConstraints(labelYear, 1, 3);

		// Text box used for user to enter year
		TextField year = new TextField();
		year.setPromptText("Enter Year");
		year.setMaxSize(300, 100);
		;
		GridPane.setConstraints(year, 2, 3);

		// Label used to show user where to enter month
		Label labelMonth = new Label("Enter the Month:");
		labelMonth.setMaxSize(300, 200);
		labelMonth.setStyle(labelStyle);
		GridPane.setConstraints(labelMonth, 1, 4);

		// Text box used for user to enter year
		TextField month = new TextField();
		month.setPromptText("Enter Month");
		month.setMaxSize(300, 100);
		;
		GridPane.setConstraints(month, 2, 4);

		// Label used to show user where to enter day
		Label labelDay = new Label("Enter the Day:");
		labelDay.setMaxSize(300, 200);
		labelDay.setStyle(labelStyle);
		GridPane.setConstraints(labelDay, 1, 5);

		// Text box used for user to enter day
		TextField day = new TextField();
		day.setPromptText("Enter Day");
		day.setMaxSize(300, 100);
		;
		GridPane.setConstraints(day, 2, 5);

		// Button that allows the user to remove the entry given the users inputs
		Button removeEntry = new Button("Click To Remove Entry");
		String entryStyles = "-fx-background-color: #D3D3D3;" + "-fx-font: bold italic 15pt \"Arial\";";
		removeEntry.setStyle(entryStyles);
		removeEntry.setMaxWidth(300);
		removeEntry.setMaxHeight(200);
		GridPane.setConstraints(removeEntry, 2, 7);

		// If the button is clicked
		removeEntry.setOnAction(e -> {

			try {
				
				//Calls the remove farm method with user inputs
				newFile.removeFarm(farmID.getText(), year.getText(), month.getText(), day.getText());

				// Creates a new grid for the confirmation method
				GridPane newRemoveGrid = new GridPane();
				newRemoveGrid.setPadding(new Insets(80, 40, 80, 40));
				newRemoveGrid.setVgap(10);
				newRemoveGrid.setHgap(30);
				newRemoveGrid.setStyle("-fx-background-color: #F0F8FF");

				// Creates a new confirmation scene
				Scene confirmationScene = new Scene(newRemoveGrid, 600, 500);

				// Calls the confirmation method with the action remove
				confirmation(newRemoveGrid, confirmationScene, styles, "remove");
			} catch (NullPointerException incorrect) {
				//If the farm was not found
				
				// Creates a new label for wrong input
				Label wrong = new Label("Farm not found");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 5th colum and 1st row
				GridPane.setConstraints(wrong, 2, 10);
				grid.getChildren().add(wrong);
				return;
			} catch (NumberFormatException fail) {
				//If the inputs were not numbers
				
				// Creates a new label for wrong input
				Label wrong = new Label("Wrong input");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 5th colum and 1st row
				GridPane.setConstraints(wrong, 2, 11);
				grid.getChildren().add(wrong);
				return;
			}

		});

		// Button used to return back to the edit menu
		Button returnEdit = new Button("Return to Edit Menu");
		returnEdit.setStyle(styles);
		returnEdit.setMaxWidth(300);
		returnEdit.setMaxHeight(200);
		GridPane.setConstraints(returnEdit, 2, 8);

		// Returns when clicked
		returnEdit.setOnAction(e -> window.setScene(returnScene));

		// Button used to return back to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setMaxWidth(300);
		returnHome.setMaxHeight(200);
		GridPane.setConstraints(returnHome, 2, 9);

		// Returns when clicked
		returnHome.setOnAction(e -> window.setScene(main));

		// Adds the text boxes, buttons, and labels to the grid
		grid.getChildren().addAll(farmID, year, month, day, removeEntry, returnEdit, returnHome, label, labelFarm,
				labelYear, labelMonth, labelDay);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This method is used to change data within the given list given the farm id
	 * year, month, day, and new weight of the changed entry
	 * 
	 * @param grid        the current grid
	 * @param scene       the current scene
	 * @param styles      the common styles throughout the program
	 * @param returnScene the edit scene menu
	 */
	private void changeData(GridPane grid, Scene scene, String styles, Scene returnScene) {

		String labelStyle = "-fx-font: bold italic 10pt \"Arial\";";

		// Label for the title of the window
		Label label = new Label("Change An Existing Milk Entry");
		label.setMaxSize(500, 200);
		label.setStyle(labelStyle);
		GridPane.setConstraints(label, 2, 1);

		// The label used to show user where to enter farm id
		Label labelFarm = new Label("Enter the Farm ID:");
		labelFarm.setMaxSize(300, 200);
		labelFarm.setStyle(labelStyle);
		GridPane.setConstraints(labelFarm, 1, 2);

		// The text box for user to enter farm id
		TextField farmID = new TextField();
		farmID.setPromptText("Enter Farm ID");
		farmID.setMaxSize(300, 100);
		;
		GridPane.setConstraints(farmID, 2, 2);

		// The label used to show user where to enter year
		Label labelYear = new Label("Enter the Year:");
		labelYear.setMaxSize(300, 200);
		labelYear.setStyle(labelStyle);
		GridPane.setConstraints(labelYear, 1, 3);

		// The text box for user to enter year
		TextField year = new TextField();
		year.setPromptText("Enter Year");
		year.setMaxSize(300, 100);
		;
		GridPane.setConstraints(year, 2, 3);

		// The label used to show user where to enter the month
		Label labelMonth = new Label("Enter the Month:");
		labelMonth.setMaxSize(300, 200);
		labelMonth.setStyle(labelStyle);
		GridPane.setConstraints(labelMonth, 1, 4);

		// The text box used for user to enter month
		TextField month = new TextField();
		month.setPromptText("Enter Month");
		month.setMaxSize(300, 100);
		;
		GridPane.setConstraints(month, 2, 4);

		// The label to show the user where to enter the day
		Label labelDay = new Label("Enter the Day:");
		labelDay.setMaxSize(300, 200);
		labelDay.setStyle(labelStyle);
		GridPane.setConstraints(labelDay, 1, 5);

		// The text box for the user to enter the day
		TextField day = new TextField();
		day.setPromptText("Enter Day");
		day.setMaxSize(300, 100);
		;
		GridPane.setConstraints(day, 2, 5);

		// Label used for weight
		Label labelWeight = new Label("Enter A New Milk Weight:");
		labelWeight.setMaxSize(300, 200);
		labelWeight.setStyle(labelStyle);
		GridPane.setConstraints(labelWeight, 1, 6);

		// Text box for user to enter the new milk weight
		TextField weight = new TextField();
		weight.setPromptText("Enter Milk Weight");
		weight.setMaxSize(300, 100);
		GridPane.setConstraints(weight, 2, 6);

		// Button to allow user to change an entry when all inputs are put in
		Button changeEntry = new Button("Click To Change An Entry");
		String entryStyles = "-fx-background-color: #D3D3D3;" + "-fx-font: bold italic 15pt \"Arial\";";
		changeEntry.setStyle(entryStyles);
		changeEntry.setMaxWidth(300);
		changeEntry.setMaxHeight(200);
		GridPane.setConstraints(changeEntry, 2, 7);
		
		//When clicked
		changeEntry.setOnAction(e -> {
			try {
				
				//Changes a farm with user inputs
				newFile.changeFarm(farmID.getText(), year.getText(), month.getText(), day.getText(), weight.getText());

				// Creates a new grid for the confirmation method
				GridPane newChangeGrid = new GridPane();
				newChangeGrid.setPadding(new Insets(80, 40, 80, 40));
				newChangeGrid.setVgap(10);
				newChangeGrid.setHgap(30);
				newChangeGrid.setStyle("-fx-background-color: #F0F8FF");

				// Creates a new confirmation scene
				Scene confirmationScene = new Scene(newChangeGrid, 600, 500);

				// Calls the confirmation method with the action change
				confirmation(newChangeGrid, confirmationScene, styles, "change");
			} catch (NullPointerException incorrect) {
				//If the farm does not exist
				
				// Creates a new label for wrong input
				Label wrong = new Label("Farm not found");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 5th colum and 1st row
				GridPane.setConstraints(wrong, 2, 10);
				grid.getChildren().add(wrong);
				return;
			} catch (NumberFormatException fail) {
				//If arguments were not numbers
				
				// Creates a new label for wrong input
				Label wrong = new Label("Wrong input");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 5th colum and 1st row
				GridPane.setConstraints(wrong, 2, 11);
				grid.getChildren().add(wrong);
				return;
			}
		});

		// Button to return to the edit menu
		Button returnEdit = new Button("Return to Edit Menu");
		returnEdit.setStyle(styles);
		returnEdit.setMaxWidth(300);
		returnEdit.setMaxHeight(200);
		GridPane.setConstraints(returnEdit, 2, 8);
		returnEdit.setOnAction(e -> window.setScene(returnScene));

		// BUtton to return to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setMaxWidth(300);
		returnHome.setMaxHeight(200);
		GridPane.setConstraints(returnHome, 2, 9);
		returnHome.setOnAction(e -> window.setScene(main));

		// Adds all the text boxes, labels, and buttons to the grid
		grid.getChildren().addAll(farmID, year, month, day, weight, changeEntry, returnEdit, returnHome, label,
				labelFarm, labelYear, labelMonth, labelDay, labelWeight);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This method is used to display the list of data that was uploaded, added,
	 * removed, and changed
	 * 
	 * @param grid  the current grid
	 * @param scene the current scene
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void displayData(GridPane grid, Scene scene) {
		// Common styles for the buttons and labels
		String styles = "-fx-background-color: #00BFFF;" + "-fx-border-color: #008B8B;"
				+ "-fx-font: bold italic 15pt \"Arial\";";

		String labelStyle = "-fx-font: bold italic 15pt \"Arial\";";

		if (newFile.confirmUpload() == false) {
			// Creates a new label for wrong input
			Label wrong = new Label("Farm ID wrong");
			wrong.setMaxSize(100, 100);
			String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
			wrong.setStyle(wrongLabelStyle);

			// Places the label at 5th colum and 7th row
			GridPane.setConstraints(wrong, 5, 7);
			grid.getChildren().add(wrong);
			return;
		}

		// Creates a new label at the top of the screen
		Label label = new Label("Available Data:");
		label.setMaxSize(500, 200);
		label.setStyle(labelStyle);

		// Places the label at 5th colum and 1st row
		GridPane.setConstraints(label, 5, 1);

		// Creates a new addGrid if user selects to add Data
		GridPane farmReportGrid = new GridPane();
		farmReportGrid.setPadding(new Insets(80, 40, 80, 40));
		farmReportGrid.setVgap(10);
		farmReportGrid.setHgap(30);
		farmReportGrid.setStyle("-fx-background-color: #F0F8FF");

		// Creates a new addScene
		Scene farmScene = new Scene(farmReportGrid, 600, 500);

		// Button to get a farm Report
		Button farmReport = new Button("Farm Report");
		farmReport.setStyle(styles);
		farmReport.setMaxWidth(300);
		farmReport.setMaxHeight(200);
		GridPane.setConstraints(farmReport, 5, 2);
		farmReport.setOnAction(e -> {
			if (newFile.confirmUpload() == true) {
				farmReport(farmReportGrid, farmScene, scene);
			}
		});

		// Creates a new addGrid if user selects to add Data
		GridPane annualReportGrid = new GridPane();
		annualReportGrid.setPadding(new Insets(80, 40, 80, 40));
		annualReportGrid.setVgap(10);
		annualReportGrid.setHgap(30);
		annualReportGrid.setStyle("-fx-background-color: #F0F8FF");

		// Creates a new addScene
		Scene annualScene = new Scene(annualReportGrid, 600, 500);

		// Button to get a farm Report
		Button annualReport = new Button("Annual Report");
		annualReport.setStyle(styles);
		annualReport.setMaxWidth(300);
		annualReport.setMaxHeight(200);
		GridPane.setConstraints(annualReport, 5, 3);
		annualReport.setOnAction(e -> {
			if (newFile.confirmUpload() == true) {
				annualReport(annualReportGrid, annualScene, scene);
			}
		});

		// Creates a new addGrid if user selects to add Data
		GridPane monthlyReportGrid = new GridPane();
		monthlyReportGrid.setPadding(new Insets(80, 40, 80, 40));
		monthlyReportGrid.setVgap(10);
		monthlyReportGrid.setHgap(30);
		monthlyReportGrid.setStyle("-fx-background-color: #F0F8FF");

		// Creates a new addScene
		Scene monthlyScene = new Scene(monthlyReportGrid, 600, 500);

		// Button to get a farm Report
		Button monthlyReport = new Button("Monthly Report");
		monthlyReport.setStyle(styles);
		monthlyReport.setMaxWidth(300);
		monthlyReport.setMaxHeight(200);
		GridPane.setConstraints(monthlyReport, 5, 4);
		monthlyReport.setOnAction(e -> {
			if (newFile.confirmUpload() == true) {
				monthlyReport(monthlyReportGrid, monthlyScene, scene);
			}
		});

		// Button used to return to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setMaxWidth(300);
		returnHome.setMaxHeight(200);
		returnHome.setStyle(styles);
		returnHome.setOnAction(e -> window.setScene(main));
		GridPane.setConstraints(returnHome, 5, 5);

		// Adds the weights, the buttons, and labels to the grid
		grid.getChildren().addAll(returnHome, label, farmReport, annualReport, monthlyReport);

		window.setScene(scene);
		window.show();
	}

	/**
	 * This method is used to give the user a farmReport which gives a report of
	 * every farm, the total weight per month of all farms, and the percentage of
	 * the total weight of the month with a particular farm
	 * 
	 * @param grid        GridPane to hold buttons etc.
	 * @param scene       Scene for the farmReport
	 * @param returnScene the display Scene
	 */
	private void farmReport(GridPane grid, Scene scene, Scene returnScene) {
		// Common styles for the buttons and labels
		String styles = "-fx-background-color: #00BFFF;" + "-fx-border-color: #008B8B;"
				+ "-fx-font: bold italic 15pt \"Arial\";";

		String labelStyle = "-fx-font: bold italic 15pt \"Arial\";";

		// Creates a new label at the top of the screen
		Label label = new Label("Farm Report:");
		label.setMaxSize(500, 200);
		label.setStyle(labelStyle);

		// Places the label at 3rd colum and 1st row
		GridPane.setConstraints(label, 3, 1);

		// The label to show the user where to enter the FarmID
		Label farmID = new Label("Enter a Farm ID:");
		farmID.setMaxSize(300, 200);
		farmID.setStyle(labelStyle);
		GridPane.setConstraints(farmID, 2, 2);

		// The text box for the user to enter the FarmID
		TextField farm = new TextField();
		farm.setPromptText("Enter Farm ID");
		farm.setMaxSize(300, 100);
		;
		GridPane.setConstraints(farm, 3, 2);

		// Label used for year
		Label labelYear = new Label("Enter a Year:");
		labelYear.setMaxSize(300, 200);
		labelYear.setStyle(labelStyle);
		GridPane.setConstraints(labelYear, 2, 3);

		// Text box for user to enter the year
		TextField year = new TextField();
		year.setPromptText("Enter Year");
		year.setMaxSize(300, 100);
		GridPane.setConstraints(year, 3, 3);

		// Button for file output if user wants the farmReport in a file
		Button fileOutput = new Button("Click for a file");
		fileOutput.setStyle(styles);
		fileOutput.setMaxWidth(300);
		fileOutput.setMaxHeight(200);
		GridPane.setConstraints(fileOutput, 3, 4);

		// When clicked
		fileOutput.setOnAction(e -> {

			// Tries to call the farmReport method in UploadedFile
			try {

				// Calls farmReport with true, the user's farmID and year
				newFile.farmReport(true, farm.getText(), year.getText());
			} catch (NullPointerException incorrect) {
				// When FarmID is not found

				// Creates a new label for wrong input
				Label wrong = new Label("Farm ID wrong");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 3rd colum and 6th row
				GridPane.setConstraints(wrong, 3, 6);
				grid.getChildren().add(wrong);
				return;
			} catch (NumberFormatException fail) {
				// When the input is not a number

				// Creates a new label for wrong input
				Label wrong = new Label("Wrong input");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 3rd colum and 7th row
				GridPane.setConstraints(wrong, 3, 7);
				grid.getChildren().add(wrong);
				return;
			}

			// Creates a new grid for the confirmation method
			GridPane newFarmGrid = new GridPane();
			newFarmGrid.setPadding(new Insets(80, 40, 80, 40));
			newFarmGrid.setVgap(10);
			newFarmGrid.setHgap(30);
			newFarmGrid.setStyle("-fx-background-color: #F0F8FF");

			// Creates a new confirmation scene
			Scene confirmationScene = new Scene(newFarmGrid, 600, 500);

			// Calls the confirmation method with the action FarmReport
			confirmation(newFarmGrid, confirmationScene, styles, "FarmReport");
		});

		// Button for display if user wants the data to be displayed
		Button display = new Button("Click to Display");
		display.setStyle(styles);
		display.setMaxWidth(300);
		display.setMaxHeight(200);
		GridPane.setConstraints(display, 3, 5);

		// When clicked
		display.setOnAction(e -> {

			try {

				/*
				 * Calls the farmReport method with false, user farmID and year and stores it in
				 * a string
				 */
				String s = newFile.farmReport(false, farm.getText(), year.getText());

				// Creates a new BorderPane for the display window
				BorderPane root = new BorderPane();

				// Creates a new ScrollPane for the display window
				ScrollPane sp = new ScrollPane();

				// Sets the content of the scrollpane as the borderpane
				sp.setContent(root);

				// Creates a new scene for the display window
				Scene displayText = new Scene(sp, 600, 500);

				// Calls the displayText method with the particular String
				displayText(s, displayText, root, styles);
			} catch (NullPointerException incorrect) {
				// If the user types in a non existant FarmID

				// Creates a new label for wrong input
				Label wrong = new Label("Farm ID wrong");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				GridPane.setConstraints(wrong, 3, 6);
				grid.getChildren().add(wrong);
				return;
			} catch (NumberFormatException fail) {
				// If input are not numbers

				// Creates a new label for wrong input
				Label wrong = new Label("Wrong input");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				GridPane.setConstraints(wrong, 3, 7);
				grid.getChildren().add(wrong);
				return;
			}

		});

		// BUtton to return to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setMaxWidth(300);
		returnHome.setMaxHeight(200);
		GridPane.setConstraints(returnHome, 3, 6);
		returnHome.setOnAction(e -> window.setScene(main));

		// Adds all the text boxes, labels, and buttons to the grid
		grid.getChildren().addAll(label, fileOutput, labelYear, year, farm, farmID, returnHome, display);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This method is used to give the user an annual report of farms. This gives a
	 * total weight for the year and each farms total weight over the year and each
	 * farm's percent of year's total weight
	 * 
	 * @param grid        the grid for the report
	 * @param scene       the scene for the report
	 * @param returnScene the display scene
	 */
	private void annualReport(GridPane grid, Scene scene, Scene returnScene) {
		// Common styles for the buttons and labels
		String styles = "-fx-background-color: #00BFFF;" + "-fx-border-color: #008B8B;"
				+ "-fx-font: bold italic 15pt \"Arial\";";

		String labelStyle = "-fx-font: bold italic 15pt \"Arial\";";

		// Creates a new label at the top of the screen
		Label label = new Label("Annual Report:");
		label.setMaxSize(500, 200);
		label.setStyle(labelStyle);
		GridPane.setConstraints(label, 3, 1);

		// Label used for year
		Label labelYear = new Label("Enter a Year:");
		labelYear.setMaxSize(300, 200);
		labelYear.setStyle(labelStyle);
		GridPane.setConstraints(labelYear, 2, 2);

		// Text box for user to enter the year
		TextField year = new TextField();
		year.setPromptText("Enter Year");
		year.setMaxSize(300, 100);
		GridPane.setConstraints(year, 3, 2);

		// Button for file output if user wants the report in a file
		Button fileOutput = new Button("Click for a file");
		fileOutput.setStyle(styles);
		fileOutput.setMaxWidth(300);
		fileOutput.setMaxHeight(200);
		GridPane.setConstraints(fileOutput, 3, 3);

		// When clicked
		fileOutput.setOnAction(e -> {

			try {

				// Calls the annualReport method in UploadedFile with true and user year
				newFile.annualReport(true, year.getText());
			} catch (NullPointerException incorrect) {
				// If year is not in the data

				// Creates a new label for wrong input
				Label wrong = new Label("Year wrong");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				GridPane.setConstraints(wrong, 3, 5);
				grid.getChildren().add(wrong);
				return;
			} catch (NumberFormatException fail) {
				// If input is not a number

				// Creates a new label for wrong input
				Label wrong = new Label("Wrong input");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				GridPane.setConstraints(wrong, 3, 5);
				grid.getChildren().add(wrong);
				return;
			}

			// Creates a new grid for the confirmation method
			GridPane newAnnualGrid = new GridPane();
			newAnnualGrid.setPadding(new Insets(80, 40, 80, 40));
			newAnnualGrid.setVgap(10);
			newAnnualGrid.setHgap(30);
			newAnnualGrid.setStyle("-fx-background-color: #F0F8FF");

			// Creates a new confirmation scene
			Scene confirmationScene = new Scene(newAnnualGrid, 600, 500);

			// Calls the confirmation method with the action AnnualReport
			confirmation(newAnnualGrid, confirmationScene, styles, "AnnualReport");
		});

		// Button for display if user wants to display the data instead
		Button display = new Button("Click to Display");
		display.setStyle(styles);
		display.setMaxWidth(300);
		display.setMaxHeight(200);
		GridPane.setConstraints(display, 3, 4);

		// When clicked
		display.setOnAction(e -> {

			try {

				// Call annualReport with writeToFile as false and user year
				String s = newFile.annualReport(false, year.getText());

				// Create new BorderPane and ScrollPane for new window
				BorderPane root = new BorderPane();
				ScrollPane sp = new ScrollPane();

				// Set content of sp as root
				sp.setContent(root);

				// Create a new display scene
				Scene displayText = new Scene(sp, 600, 500);

				// Call displayText with the particular string
				displayText(s, displayText, root, styles);

			} catch (NumberFormatException incorrect) {
				// If input is not a number

				// Creates a new label for wrong input
				Label wrong = new Label("Year wrong");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 3th colum and 7th row
				GridPane.setConstraints(wrong, 3, 7);
				grid.getChildren().add(wrong);
				return;
			} catch (NullPointerException fail) {
				// If user types in a year that does not exist

				// Creates a new label for wrong input
				Label wrong = new Label("Year not found");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 3th colum and 8th row
				GridPane.setConstraints(wrong, 3, 8);
				grid.getChildren().add(wrong);
				return;
			}
		});

		// BUtton to return to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setMaxWidth(300);
		returnHome.setMaxHeight(200);
		GridPane.setConstraints(returnHome, 3, 5);
		returnHome.setOnAction(e -> window.setScene(main));

		// Adds all the text boxes, labels, and buttons to the grid
		grid.getChildren().addAll(label, fileOutput, display, labelYear, year, returnHome);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This method returns a monthlyReport of the farm. It gives the Farms in a
	 * particular month of a particular year and their total weight and percent of
	 * the month's total weight
	 * 
	 * @param grid        grid to be displayed
	 * @param scene       scene to be displayed
	 * @param returnScene display scene
	 */
	private void monthlyReport(GridPane grid, Scene scene, Scene returnScene) {
		// Common styles for the buttons and labels
		String styles = "-fx-background-color: #00BFFF;" + "-fx-border-color: #008B8B;"
				+ "-fx-font: bold italic 15pt \"Arial\";";

		String labelStyle = "-fx-font: bold italic 15pt \"Arial\";";

		// Creates a new label at the top of the screen
		Label label = new Label("Monthly Report:");
		label.setMaxSize(500, 200);
		label.setStyle(labelStyle);

		// Places the label at 3rd colum and 1st row
		GridPane.setConstraints(label, 3, 1);

		// The label to show the user where to enter the year
		Label year = new Label("Enter a year:");
		year.setMaxSize(300, 200);
		year.setStyle(labelStyle);
		GridPane.setConstraints(year, 2, 2);

		// The text box for the user to enter the year
		TextField yearText = new TextField();
		yearText.setPromptText("Enter Year");
		yearText.setMaxSize(300, 100);
		;
		GridPane.setConstraints(yearText, 3, 2);

		// Label used for month
		Label labelMonth = new Label("Enter Month:");
		labelMonth.setMaxSize(300, 200);
		labelMonth.setStyle(labelStyle);
		GridPane.setConstraints(labelMonth, 2, 3);

		// Text box for user to enter the month
		TextField month = new TextField();
		month.setPromptText("Enter Number of Month");
		month.setMaxSize(300, 100);
		GridPane.setConstraints(month, 3, 3);

		// Button for file output if user wants a file output
		Button fileOutput = new Button("Click for a file");
		fileOutput.setStyle(styles);
		fileOutput.setMaxWidth(300);
		fileOutput.setMaxHeight(200);
		GridPane.setConstraints(fileOutput, 3, 4);

		// When clicked
		fileOutput.setOnAction(e -> {

			try {

				// Call monthlyReport with true and user inputs
				newFile.monthlyReport(true, yearText.getText(), month.getText());
			} catch (IllegalArgumentException incorrect) {
				// If the month is not a valid month

				// Creates a new label for wrong input
				Label wrong = new Label("Month wrong");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 3th colum and 7th row
				GridPane.setConstraints(wrong, 3, 7);
				grid.getChildren().add(wrong);
				return;
			} catch (NullPointerException fail) {
				// If the year does not exist in the data

				// Creates a new label for wrong input
				Label wrong = new Label("Year wrong");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 3th colum and 8th row
				GridPane.setConstraints(wrong, 3, 8);
				grid.getChildren().add(wrong);
				return;
			}

			// Creates a new grid for the confirmation method
			GridPane newMonthGrid = new GridPane();
			newMonthGrid.setPadding(new Insets(80, 40, 80, 40));
			newMonthGrid.setVgap(10);
			newMonthGrid.setHgap(30);
			newMonthGrid.setStyle("-fx-background-color: #F0F8FF");

			// Creates a new confirmation scene
			Scene confirmationScene = new Scene(newMonthGrid, 600, 500);

			// Calls the confirmation method with the action MonthlyReport
			confirmation(newMonthGrid, confirmationScene, styles, "MonthlyReport");
		});

		// Button to display data if user wants to display instead of a file
		Button display = new Button("Click to Display");
		display.setStyle(styles);
		display.setMaxWidth(300);
		display.setMaxHeight(200);
		GridPane.setConstraints(display, 3, 5);

		// When clicked
		display.setOnAction(e -> {

			try {

				// Calls monthlyReport with false and user inputs
				String s = newFile.monthlyReport(false, yearText.getText(), month.getText());

				// Creates the new panes for the new window
				BorderPane root = new BorderPane();
				ScrollPane sp = new ScrollPane();
				sp.setContent(root);

				// Creates a new scene
				Scene displayText = new Scene(sp, 600, 500);

				// Calls displayText with the particular string
				displayText(s, displayText, root, styles);
			} catch (IllegalArgumentException incorrect) {
				// If the month is not valid

				// Creates a new label for wrong input
				Label wrong = new Label("Month wrong");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 3th colum and 7th row
				GridPane.setConstraints(wrong, 3, 7);
				grid.getChildren().add(wrong);
				return;
			} catch (NullPointerException fail) {
				// If the year does not exist

				// Creates a new label for wrong input
				Label wrong = new Label("Year wrong");
				wrong.setMaxSize(100, 100);
				String wrongLabelStyle = "-fx-font: bold italic 10pt \"Arial\";";
				wrong.setStyle(wrongLabelStyle);

				// Places the label at 3th colum and 7th row
				GridPane.setConstraints(wrong, 3, 7);
				grid.getChildren().add(wrong);
				return;
			}
		});

		// BUtton to return to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setMaxWidth(300);
		returnHome.setMaxHeight(200);
		GridPane.setConstraints(returnHome, 3, 6);
		returnHome.setOnAction(e -> window.setScene(main));

		// Adds all the text boxes, labels, and buttons to the grid
		grid.getChildren().addAll(label, fileOutput, yearText, display, year, month, labelMonth, returnHome);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This method is used to display text
	 * 
	 * @param text   the text to be displayed
	 * @param scene  the scene to be displayed in
	 * @param root   the pane to be displayed in
	 * @param styles the styles used commonly
	 */
	private void displayText(String text, Scene scene, BorderPane root, String styles) {

		// Creates a new text
		Text t = new Text();

		// Sets the text to the passed in text
		t.setText(text);

		// Sets the text to the left side of the screen
		root.setLeft(t);

		// BUtton to return to the main menu
		Button returnHome = new Button("Return to Main Menu");
		returnHome.setStyle(styles);
		returnHome.setMaxWidth(300);
		returnHome.setMaxHeight(200);
		returnHome.setLayoutX(t.getX() + 1);
		returnHome.setLayoutY(t.getY() + 1);
		returnHome.setOnAction(e -> window.setScene(main));

		// Sets the button to the bottom of the screen
		root.setBottom(returnHome);

		window.setScene(scene);
		window.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
