package edu.utexas.cs.nn.tasks.microrts.evaluation;

import java.util.HashSet;

import edu.utexas.cs.nn.MMNEAT.MMNEAT;
import edu.utexas.cs.nn.evolution.genotypes.Genotype;
import edu.utexas.cs.nn.networks.Network;
import edu.utexas.cs.nn.parameters.Parameters;
import edu.utexas.cs.nn.util.PopulationUtil;
import micro.rts.GameState;
import micro.rts.PhysicalGameState;
import micro.rts.units.Unit;

/**
 * Evaluation Function for MicroRTS that puts different unit-classes
 * onto their own substrates, according to parameters
 * 
 * @author alicequint
 * 
 * unfinished, eventually different substrate for each unit-type maybe.
 */
public class NNComplexEvaluationFunction<T extends Network> extends NNEvaluationFunction<T> {
	
	private int numSubstrates;
	
	//Indexes within areSubsActive
	private final int mobile = 0;
	private final int buildings = 1;
	private final int myMobile = 2;
	private final int myBuildings = 3;
	private final int oppsMobile = 4;
	private final int oppsBuildings = 5;
	private final int myAll = 6;
	private final int oppsAll = 7;
	private final int all = 8;
	private final int neutral = 9;
	private final int terrain = 10;
	
	private boolean[] areSubsActive = new boolean[]{
			Parameters.parameters.booleanParameter("mRTSMobileUnits"),
			Parameters.parameters.booleanParameter("mRTSBuildings"),
			Parameters.parameters.booleanParameter("mRTSMyMobileUnits"),
			Parameters.parameters.booleanParameter("mRTSMyBuildings"),
			Parameters.parameters.booleanParameter("mRTSOpponentsMobileUnits"),
			Parameters.parameters.booleanParameter("mRTSOpponentsBuildings"),
			Parameters.parameters.booleanParameter("mRTSMyAll"),
			Parameters.parameters.booleanParameter("mRTSOpponentsAll"),
			Parameters.parameters.booleanParameter("mRTSAll"), //the only one that is true by default
			Parameters.parameters.booleanParameter("mRTSNeutral"), //terrain and resources
			Parameters.parameters.booleanParameter("mRTSTerrain"),
	};
	
	//from NN2DEvaluationFunction
	private static final double BASE_WEIGHT = 4; //hard to quantify because different amount of importance at different stages of the game
	private static final double BASE_RESOURCE_WEIGHT = .25;
	private static final double BARRACKS_WEIGHT = 2.5;
	private static final double WORKER_WEIGHT = 1;
	private static final double WORKER_RESOURCE_WEIGHT = .15;
	private static final double LIGHT_WEIGHT = 3; 
	private static final double HEAVY_WEIGHT = 3.25;
	private static final double RANGED_WEIGHT = 3.75;
	private static final double RAW_RESOURCE_WEIGHT = .01;
	private static final double TERRAIN_WEIGHT = -.01;

	/**
	 * constructor for FEStatePane and similar
	 * @param NNfile
	 * 				neural network .xml file 
	 */
	public NNComplexEvaluationFunction(String NNfile){
		// Parameter init can/should be removed when moving to stand-alone competition entry
		Parameters.initializeParameterCollections(new String[]{"task:edu.utexas.cs.nn.tasks.microrts.MicroRTSTask","hyperNEAT:true"
				,"microRTSEnemySequence:edu.utexas.cs.nn.tasks.microrts.iterativeevolution.HardeningEnemySequence",
				"microRTSMapSequence:edu.utexas.cs.nn.tasks.microrts.iterativeevolution.GrowingMapSequence","log:microRTS-temp","saveTo:temp"});
		MMNEAT.loadClasses();
		Genotype<T> g = PopulationUtil.extractGenotype(NNfile);
		nn = g.getPhenotype();
	}

	/**
	 * Default constructor used by MMNEAT's class creation methods.
	 * Must pass in the network via the setNetwork method of parent class.
	 */
	public NNComplexEvaluationFunction(){
		super();
		numSubstrates = 0;
		for(boolean b : areSubsActive){
			if(b) numSubstrates++;
		}
	}

	/**
	 * takes the gameState and separates into substrates that contain
	 * different information.
	 */
	@Override
	protected double[] gameStateToArray(GameState gs) {
		pgs = gs.getPhysicalGameState();
		int substrateSize = pgs.getHeight()*pgs.getWidth();
		double[] inputs = new double[substrateSize * numSubstrates];
		Unit current = null;
		int boardIndex;
		for(int j = 0; j < pgs.getHeight(); j++){
			for(int i = 0; i < pgs.getWidth(); i++){
				boolean isTerrain = pgs.getTerrain(i, j) == PhysicalGameState.TERRAIN_WALL;
				boardIndex =  i + j * pgs.getHeight(); 
				current = pgs.getUnitAt(i, j);
				assert (isTerrain == (current == null));
				inputs = populateSubstratesWith(current, isTerrain, inputs, substrateSize, boardIndex);
			}//end i : width
		}//end j : height
		return inputs;
	}

	/**
	 * puts the current unit into all substrates where it should go
	 * according to parameters.
	 * 
	 * @param u
	 * 			unit to be put into substrates
	 * @param substrates
	 * 				array containing all substrates. this array is modified and then returned
	 * @param substrateSize
	 * 				how big each substrate is
	 * @param location
	 * 				index within an individual substrate
	 * @return double[] input as substrates, but with the unit added at location for every appropriate substrate
	 * 
	 */
	private double[] populateSubstratesWith(Unit u, boolean isTerrain, double[] substrates, int substrateSize, int location){
		HashSet<Integer> appropriateSubstrates = new HashSet<>();
		int numCurrentSubs = 0;
		//for current, find which substrates it belongs to
		if(u != null){
			if(areSubsActive[mobile]){ //all mobile units   
				if(u.getType().canMove){
					appropriateSubstrates.add(numCurrentSubs);
				}
				numCurrentSubs++;
			}
			if(areSubsActive[buildings]){ //all buildings
				if(!u.getType().canMove && u.getPlayer() != -1){
					appropriateSubstrates.add(numCurrentSubs);
				}
				numCurrentSubs++;
			}
			if(areSubsActive[myMobile]){
				if(u.getType().canMove && u.getPlayer() == 0){
					appropriateSubstrates.add(numCurrentSubs);
				}
				numCurrentSubs++;
			}
			if(areSubsActive[myBuildings]){
				if(!u.getType().canMove && u.getPlayer() != 1){
					appropriateSubstrates.add(numCurrentSubs);
				}
				numCurrentSubs++;
			}
			if(areSubsActive[oppsMobile]){
				if(u.getType().canMove && u.getPlayer() == 1){
					appropriateSubstrates.add(numCurrentSubs);
				}
				numCurrentSubs++;
			}
			if(areSubsActive[oppsBuildings]){
				if(!u.getType().canMove && u.getPlayer() != 0){
					appropriateSubstrates.add(numCurrentSubs);
				}
				numCurrentSubs++;
			}
			if(areSubsActive[myAll]){
				if(u.getPlayer() == 0){
					appropriateSubstrates.add(numCurrentSubs);
				}
				numCurrentSubs++;
			}
			if(areSubsActive[oppsAll]){
				if(u.getPlayer() == 1){
					appropriateSubstrates.add(numCurrentSubs);
				}
				numCurrentSubs++;
			}
		} //end if (u != null) : following subs can be appropriate if we are considering terrain 
		if(areSubsActive[all]){ //everything
			appropriateSubstrates.add(numCurrentSubs);
			numCurrentSubs++;
		}
		if(areSubsActive[neutral]){ //neutral (terrain & resources)
			if(isTerrain || u.getPlayer() == -1){
				appropriateSubstrates.add(numCurrentSubs);
			}
			numCurrentSubs++;
		}
		if(areSubsActive[terrain]){
			if(isTerrain){
				appropriateSubstrates.add(numCurrentSubs);
			}
			numCurrentSubs++;
		}
		for(int appropriateSubstrate : appropriateSubstrates){
			int indexWithinAll = (substrateSize * appropriateSubstrate) + location;
			int subID = -1; //TODO make it equal to appropriate global
			substrates[indexWithinAll] = getWeightedValue(subID , u, isTerrain);
		} 
		return substrates;
	}
	
	/**
	 * returns a value to be used as an input that represents a specific entity in a substrate,
	 * allows us to differentiate between different things inside the same substrate.
	 * 
	 * @param sub
	 * 			which substrate the value will be put in
	 * @param u
	 * 			unit
	 * @param isTerrain
	 * 			true if entity in question is a wall, false if traversable
	 * @return
	 * 			value to be put into the NN
	 */
	private double getWeightedValue(int sub, Unit u, boolean isTerrain){
		if(sub == neutral){
			if(isTerrain) return .25;
		}
		if(sub == all){
			double value = 0;
			if(isTerrain) value = .25;
			switch(u.getType().name){
			case "Worker": value = WORKER_WEIGHT + (WORKER_RESOURCE_WEIGHT * u.getResources()); break; 
			case "Light": value = LIGHT_WEIGHT; break;
			case "Heavy": value = HEAVY_WEIGHT; break;
			case "Ranged": value = RANGED_WEIGHT; break;
			case "Base": value = BASE_WEIGHT + (BASE_RESOURCE_WEIGHT * u.getResources()); break;
			case "Barracks": value = BARRACKS_WEIGHT; break;
			case "Resource": value = RAW_RESOURCE_WEIGHT; break;
			default: break;
			}
			if(u.getPlayer() == 1) value *= -1;
			return value;
		}
		return 1.0;
	}

	/**
	 * returns labels describing what gameStateToArray will
	 * give for the inputs to a NN
	 */
	@Override
	public String[] sensorLabels() {
		assert pgs != null : "There must be a physical game state in order to extract height and width";
		String[] labels = new String[pgs.getWidth()*pgs.getHeight() * numSubstrates];
		for(int h = 0; h < numSubstrates; h++ ){
			for(int i = 0; i < pgs.getWidth(); i++){
				for(int j = 0; j < pgs.getHeight(); j++){
					labels[i*pgs.getWidth() + j ] = "Mobile unit:  (" + i + ", " + j + ")";
					labels[i*pgs.getWidth() + j + (pgs.getWidth()*pgs.getHeight()*h)] = "Immobile unit: (" + i + "," + j + ")";
				}
			}
		}
		return labels;
	}

	@Override
	public int getNumInputSubstrates() {
		return numSubstrates;
	}

}
