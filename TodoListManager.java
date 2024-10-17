import java.util.*;
import java.io.*;

public class TodoListManager {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		List<String> toDoList = new ArrayList<>();

		List<String> finishedList = new ArrayList<>();
		PrintStream output = new PrintStream(new File("finished.txt"));

		String userChoice = "";
		System.out.println("Welcome to your TODO List Manager!");
		while (!userChoice.equalsIgnoreCase("q")) {
			System.out.println("What would you like to do?");
			System.out.print("(A)dd TODO, (M)ark TODO as done, (L)oad TODOs, (S)ave TODOs,"
					+ " (F)inished items TODO list, (D)eadlines for TODO list," + " (Q)uit? ");
			userChoice = console.nextLine();

			if (userChoice.equalsIgnoreCase("a")) {
				addItem(console, toDoList);
				printTodos(toDoList);
			} else if (userChoice.equalsIgnoreCase("m")) {
				markItemAsDone(console, toDoList, output);
				printTodos(toDoList);
			} else if (userChoice.equalsIgnoreCase("L")) {
				loadItems(console, toDoList);
				printTodos(toDoList);
			} else if (userChoice.equalsIgnoreCase("s")) {
				saveItems(console, toDoList);
				printTodos(toDoList);
			} else if (userChoice.equalsIgnoreCase("d")) {
				deadlines(console, toDoList);
				printTodos(toDoList);
			} else if (userChoice.equalsIgnoreCase("f")) {
				finishedItems(finishedList);
				finishedPrint(finishedList);
			} else if (!userChoice.equalsIgnoreCase("q")) {
				System.out.println("Unknown input: " + userChoice);
				printTodos(toDoList);
			} // end of if
		} // end of while loop
	}// main

	/*
	This method prints out the current activities in the list.
	Parameters: 
		- todos: a list of activities that user has to do
	Returns: 
		- nothing
	*/
	public static void printTodos(List<String> todos) {
		System.out.println("Today's TODOs:");
		if (todos.size() == 0) {
			System.out.println("  You have nothing to do yet today! Relax!");
		} else {
			for (int i = 0; i < todos.size(); i++) {
				int numbering = i + 1;
				System.out.println("  " + numbering + ": " + todos.get(i));
			} // end of for loop
		} // end of if
	} // end of printTodos

	/*
	This method adds a new item to the todo list. Users can also specify
    where the item should be placed in the list.
    Parameters:
        - console: a Scanner object to read user input
        - todos: a list of activities that user has to do
	Returns:
		- nothing
	*/
	public static void addItem(Scanner console, List<String> todos) {
		System.out.print("What would you like to add? ");
		String addAnswer = console.nextLine();

		if (todos.size() == 0) {
			todos.add(addAnswer);
		} else {
			System.out.print("Where in the list should it be (1-" + (todos.size() + 1) + ")? (Enter for end): ");
			String nString = console.nextLine();

			if (nString.equalsIgnoreCase("")) {
				todos.add((todos.size()), addAnswer);
			} else {
				int location = Integer.parseInt(nString);
				todos.add(location - 1, addAnswer);
			} // end of else
		} // end of if
	} // end of addItem

	/*
	This method removes a completed activity from the todo list and writes it
    to the provided PrintStream (e.g., a file or console output).
    Throws a FileNotFoundException if the specified file for the output
    does not exist. If the user input for the item to mark as done is invalid,
    an error will occur.
	Parameters: 
		- console: reads in user input
		- todos: a list of activities that user has to do
		- output: a PrintStream to record completed tasks
	Return: 
		- nothing
	*/
	public static void markItemAsDone(Scanner console, List<String> todos, PrintStream output)
									  throws FileNotFoundException {
		if (todos.isEmpty()) {
        	System.out.println("All done! Nothing left to mark as done!");
    	} else {
			System.out.print("Which item did you complete (1-" + todos.size() + ")? ");
			String nString = console.nextLine();
			try {
				int location = Integer.parseInt(nString);
				if (location < 1 || location > todos.size()) {
					System.out.println("Invalid input! Please enter a number between 1 and " + todos.size() + ".");
				} else {
					String finished = todos.remove(location - 1);
					output.println(finished);
				} // end of else
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a valid number.");
			} // end of catch
    	} // end of else
	} // end of markItemAsDone

	/*
    This method clears the provided list of completed activities and then 
    repopulates it from a text file.
    Throws a FileNotFoundException if the file "finished.txt" does not exist.
    Parameters:
        - finishedList: a list that stores completed activities
    Returns:
        - nothing
	*/
	public static void finishedItems(List<String> finishedList) throws FileNotFoundException {
		while (finishedList.size() != 0) {
			finishedList.remove(0);
		} // end of while loop

		Scanner newConsole = new Scanner(new File("finished.txt"));
		while (newConsole.hasNextLine()) {
			finishedList.add(newConsole.nextLine());
		} // end of while loop
	} // end of finishedItems

	/*
	This method loads items from a file and adds them to the current todos list.
	Throws a FileNotFoundException if the file specified by the user does not exist.
	If no file name is input, an error is output.
	Parameters: 
		- console: reads in user input
		- todos: a list of activities that user has to do
	Returns: 
		- nothing 
	*/
	public static void loadItems(Scanner console, List<String> todos) throws FileNotFoundException {
		if (todos.size() > 0) {
			for (int i = 0; i < todos.size(); i++) {
				todos.remove(i);
			} // end of for
		} // end of if 

		System.out.print("File name? ");
		String outFile = console.nextLine();
		Scanner newConsole = new Scanner(new File(outFile));
		while (newConsole.hasNextLine()) {
			String nextTodo = newConsole.nextLine();
			todos.add(nextTodo);
		} // end of while
	} // end of loadItems

	// This method can save the current list to a different file
	// Throws a FileNotFoundException if the file specified by the given fileName
	// does not exist.
	// If you don't input a file name, it'll output an error
	// Parameters:
	// -todos: a list of activities that user has to do
	// -scanner: reads in user input
	// Returns:
	// -Nothing

	/*
	This method saves the current todos list to a specified file.
	Throws a FileNotFoundException if the file cannot be created.
	If no file name is input, an error is output.
	Parameters: 
		- console: reads in user input
		- todos: a list of activities that user has to do
	Returns: 
		- nothing 
	*/
	public static void saveItems(Scanner console, List<String> todos) throws FileNotFoundException {
		System.out.print("File name? ");
		String outFile = console.nextLine();

		PrintStream output = new PrintStream(new File(outFile));
		for (int i = 0; i < todos.size(); i++) {
			output.println(todos.get(i));
		} // for loop
	} // end of saveItems

	// This method can set deadlines to the items on the list
	// If you don't input a valid number to add a deadline to, an error will occur
	// Parameters:
	// -todos: a list of activities that user has to do
	// -scanner: reads in user input
	// Returns:
	// -Nothing

	/*
	This method sets deadlines for items on the todos list.
	If an invalid number is provided for selecting an item, an error will occur.
	Parameters: 
		- console: reads in user input
		- todos: a list of activities that user has to do
	Returns: 
		- nothing 
	*/
	public static void deadlines(Scanner console, List<String> todos) {
		if (todos.size() == 0) {
			System.out.println("You have nothing in your list!");
		} else {
			System.out.print("Which item did you want to add a deadline to (1-" + (todos.size()) + ")? ");
			String nString = console.nextLine();
			int location = Integer.parseInt(nString);
			int actualLocation = location - 1;
			System.out.print("When is the deadline (MM/DD)? ");
			String deadline = console.nextLine();

			String activity = todos.get(actualLocation);
			todos.set(actualLocation, activity + " - " + deadline);
		} // end of if
	} // end of deadlines

	/*
	This method prints out the finished/completed activities in the list
	Parameters: 
		- finishedList: a list of activities that the user has completed
	Returns: 
		- nothing
	*/
	public static void finishedPrint(List<String> finishedList) {
		System.out.println("Finished TODOs: ");
		for (int i = 0; i < finishedList.size(); i++) {
			int numbering = i + 1;
			System.out.println("  " + numbering + ": " + finishedList.get(i));
		} // end of for loop
	} // end of finishedPrint

} // end of class
