package vizdoom.examples;

import vizdoom.*;

import java.util.*;

public class Seed {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SpecifyDLL.specifyDLLPath();

		System.out.println("\nSEED EXAMPLE\n");
		DoomGame game = new DoomGame();

		// Choose scenario config file you wish to be watched by agent.
		// Don't load two configs cause the second will overwrite the first one.
		// Multiple config files are ok but combining these ones doesn't make
		// much sense.

		// game.loadConfig("vizdoom/examples/config/deadly_corridor.cfg")
		// game.loadConfig("vizdoom/examples/config/defend_the_center.cfg")
		// game.loadConfig("vizdoom/examples/config/defend_the_line.cfg")
		// game.loadConfig("vizdoom/examples/config/health_gathering.cfg")
		// game.loadConfig("vizdoom/examples/config/my_way_home.cfg")
		// game.loadConfig("vizdoom/examples/config/predict_position.cfg")

		game.loadConfig("vizdoom/examples/config/basic.cfg");
		game.setDoomScenarioPath("vizdoom/scenarios/basic.wad");
		game.setViZDoomPath("vizdoom/bin/vizdoom");

		// Sets path to doom2 iwad resource file which contains the actual doom
		// game-> Default is "./doom2.wad".
		game.setDoomGamePath("vizdoom/scenarios/freedoom2.wad");
		// game.setDoomGamePath("vizdoom/scenarios/doom2.wad"); // Not provided
		// with environment due to licences.

		game.setScreenResolution(ScreenResolution.RES_640X480);

		int seed = 1234;
		// Sets the seed. It could be after init as well.
		game.setSeed(seed);
		game.init();

		List<int[]> actions = new ArrayList<int[]>();
		actions.add(new int[] { 1, 0, 1 });
		actions.add(new int[] { 0, 1, 1 });
		actions.add(new int[] { 0, 0, 1 });

		Random ran = new Random();

		int episodes = 10;

		for (int i = 0; i < episodes; i++) {

			System.out.println("Episode #" + (i + 1));
			game.newEpisode();

			while (!game.isEpisodeFinished()) {
                // Gets the state and possibly to something with it
                GameState state = game.getState();

                // Make random action and get reward
                double reward = game.makeAction(actions.get(ran.nextInt(3)));

                System.out.println("State #" + state.number);
                System.out.println("Action Reward: " + reward);
                System.out.println("Seed: " + game.getSeed());
                System.out.println("=====================");


			}
			System.out.println("Episode finished!");
			System.out.println("Total reward: " + game.getTotalReward());
			System.out.println("************************");
		}
		// It will be done automatically in destructor but after close You can
		// init it again with different settings.
		game.close();
	}

}
