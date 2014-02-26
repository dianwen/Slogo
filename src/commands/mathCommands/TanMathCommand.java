package commands.mathCommands;

public class TanMathCommand extends MathCommand {
	public TanMathCommand() {
		
		super(1);
		
	}

	@Override
	public double execute(Object o) {
		double expr = parameters.get(0);
		
		return Math.tan(expr);
		
	}

}
