
package it.uniroma3.gta.actions;

//Abstract Action Class: extended by other action classes
public abstract class Action {
	
	//Parameter execute method to return action result
	public abstract String execute(String string) throws InstantiationException, IllegalAccessException, ClassNotFoundException;

}
