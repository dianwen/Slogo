package commands.advancedCommands;

import backend.Interpreter;
import commands.AdvancedCommand;
import exceptions.EndOfStackException;
import exceptions.InvalidSyntaxException;
import exceptions.SlogoException;

public class AddVariableAdvancedCommand extends AdvancedCommand {

	public AddVariableAdvancedCommand() {
		super(0);
	}

	@Override
	public double execute(Object o) throws InvalidSyntaxException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException, EndOfStackException, SlogoException {
		Interpreter interpreter = (Interpreter) o;
		String variableName = interpreter.readNextCommand();
		if (variableName.charAt(0) != ':') {
			throw new InvalidSyntaxException();
		}
		return interpreter.addVariable(variableName.substring(1));
	}

}
