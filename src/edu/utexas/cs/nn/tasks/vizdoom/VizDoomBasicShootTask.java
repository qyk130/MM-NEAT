package edu.utexas.cs.nn.tasks.vizdoom;

import edu.utexas.cs.nn.MMNEAT.MMNEAT;
import edu.utexas.cs.nn.evolution.genotypes.TWEANNGenotype;
import edu.utexas.cs.nn.networks.Network;
import edu.utexas.cs.nn.networks.TWEANN;
import edu.utexas.cs.nn.parameters.CommonConstants;
import edu.utexas.cs.nn.parameters.Parameters;
import vizdoom.Button;
import vizdoom.GameState;
import vizdoom.GameVariable;

public class VizDoomBasicShootTask<T extends Network> extends VizDoomTask<T> {

    public void setDoomActions() {
        // Adds buttons that will be allowed.
        game.addAvailableButton(Button.MOVE_LEFT);
        game.addAvailableButton(Button.MOVE_RIGHT);
        game.addAvailableButton(Button.ATTACK);

        // Define some actions. Each list entry corresponds to declared buttons:
        // MOVE_LEFT, MOVE_RIGHT, ATTACK
        // more combinations are naturally possible but only 3 are included for transparency when watching.
        addAction(new int[]{1, 0, 1}, "Left and Shoot");
        addAction(new int[]{0, 1, 1}, "Right and Shoot");
        addAction(new int[]{0, 0, 1}, "Still and Shoot");
    }
	
    public void setDoomStateVariables() {
        // Adds game variables that will be included in state.
        game.addAvailableGameVariable(GameVariable.AMMO2);
    }
    
    
    public static void main(String[] args) {
    	Parameters.initializeParameterCollections(new String[]{"watch:false","io:false","netio:false","task:edu.utexas.cs.nn.tasks.vizdoom.VizDoomBasicShootTask", "trials:3", "printFitness:true"});
    	MMNEAT.loadClasses();
    	VizDoomBasicShootTask<TWEANN> vd = new VizDoomBasicShootTask<TWEANN>();
    	TWEANNGenotype individual = new TWEANNGenotype();
    	System.out.println(vd.evaluate(individual));
    	System.out.println(vd.evaluate(individual));
    	vd.finalCleanup();
    }

	@Override
	public int numInputs() {
		return game.getScreenWidth();
	}

	@Override
	public double[] getInputs(GameState s) {
		int width = game.getScreenWidth();
		int height = game.getScreenHeight();
		int row = 61; // MAGIC NUMBER, change when introducing ratio method in VizDoomTask
		int color = RED_INDEX;
    	double[] result = new double[width];
    	int index = row * width * 3;
		for(int y = 0; y < height; y++) {
        	int bufferPos = 0;
        	for(int x = 0; x < width; x++) {
        		int c = index + bufferPos + color;
                result[x] = (s.imageBuffer[c]) / 255.0;
        		bufferPos += 3;
        	}
        }
		return result;
	}

	/**
	 * This method takes the given game information to send back the appropriate row number to get the inputs from.
	 * This is done based on Screen Resolution ratios. The calculations are hard coded, but tested and gave reliable rows when displayed.
	 */
	@Override
	public int getRow() {
		float first;
    	int second = 0;
		if(game.getScreenWidth()/4 == game.getScreenHeight()/3){ // ratio is 4:3
			System.out.println("Ratio is 4:3");
			first = (float) (game.getScreenWidth() * 0.3825);
    		System.out.println(first);
    		second = Math.round(first);    		
    		System.out.println(second);
    	} else if(game.getScreenWidth()/16 == game.getScreenHeight()/10){ // ratio is 16:10
    		System.out.println("Ratio is 16:10");
			first = (float) (game.getScreenWidth() * 0.32); // CHANGE THIS
    		System.out.println(first);
    		second = Math.round(first);    		
    		System.out.println(second);
    	} else if(game.getScreenWidth()/16 == game.getScreenHeight()/9){ // ratio is 16:9
    		System.out.println("Ratio is 16:9");
			first = (float) (game.getScreenWidth() * 0.29); // CHANGE THIS
    		System.out.println(first);
    		second = Math.round(first);    		
    		System.out.println(second);
    	} else { // ratio is 5:4
    		System.out.println("Ratio is 5:4");
			first = (float) (game.getScreenWidth() * 0.41); // CHANGE THIS
    		System.out.println(first);
    		second = Math.round(first);    		
    		System.out.println(second);
    	} 
		return second;
	}
}
