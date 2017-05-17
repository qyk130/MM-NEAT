package edu.utexas.cs.nn.util.sound;


import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.utexas.cs.nn.MMNEAT.MMNEAT;
import edu.utexas.cs.nn.evolution.genotypes.HyperNEATCPPNGenotype;
import edu.utexas.cs.nn.networks.ActivationFunctions;
import edu.utexas.cs.nn.networks.Network;
import edu.utexas.cs.nn.parameters.Parameters;
import edu.utexas.cs.nn.util.MiscUtil;
import edu.utexas.cs.nn.util.datastructures.ArrayUtil;
import edu.utexas.cs.nn.util.graphics.DrawingPanel;
import edu.utexas.cs.nn.util.graphics.GraphicsUtil;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * Class containing various testing examples for sound utility methods in sound package.
 * Pretty disorganized, but just storing the various examples here so we don't lose them.
 * 
 * @author Isabel Tweraser
 *
 */
public class SoundUtilExamples {

	private static final String BEARGROWL_WAV = "data/sounds/bear_growl_y.wav";
	private static final String APPLAUSE_WAV = "data/sounds/applause_y.wav";
	private static final String HARP_WAV = "data/sounds/harp.wav";
	private static final String HAPPY_MP3 = "data/sounds/25733.mp3";
	private static final String PIRATES = "data/sounds/pirates.mid";

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException, JavaLayerException {
		Parameters.initializeParameterCollections(new String[]{"io:false","netio:false"});
		MMNEAT.loadClasses();
		HyperNEATCPPNGenotype test = new HyperNEATCPPNGenotype(3, 1, 0);
		for(int i = 0; i < 30; i++) {
			test.mutate();
		}
		Network cppn = test.getCPPN();
		double[] testArray = SoundAmplitudeArrayManipulator.amplitudeGenerator(cppn, 60000, 440);
		MiscSoundUtil.play(testArray);
		GraphicsUtil.wavePlotFromDoubleArray(testArray);
		
		//		ArrayList<Double> fileArrayList2 = ArrayUtil.doubleVectorFromArray(testArray2); //convert array into array list
		//		DrawingPanel panel2 = new DrawingPanel(500,500, "2"); //create panel where line will be plotted 
		//		GraphicsUtil.linePlot(panel2, -1.0, 1.0, fileArrayList2, Color.black); //call linePlot with ArrayList to draw graph
		//		MiscUtil.waitForReadStringAndEnterKeyPress();

		//playWAVFile(BEARGROWL_WAV);
		//mp3Conversion(HAPPY_MP3).playMP3File();
		byte[] bearNumbers = WAVUtil.WAVToByte(BEARGROWL_WAV);
		//System.out.println(Arrays.toString(bearNumbers));
		AudioInputStream bearAIS = WAVUtil.byteToAIS(bearNumbers);
		int[] bearData = SoundAmplitudeArrayManipulator.extractAmplitudeDataFromAudioInputStream(bearAIS);
		//System.out.println(Arrays.toString(bearData));

		byte[] applauseNumbers =WAVUtil.WAVToByte(APPLAUSE_WAV);	
		//System.out.println(Arrays.toString(applauseNumbers));

		byte[] harpNumbers = WAVUtil.WAVToByte(HARP_WAV);
		//System.out.println(Arrays.toString(harpNumbers));
		AudioInputStream harpAIS = WAVUtil.byteToAIS(harpNumbers);
		int[] harpData = SoundAmplitudeArrayManipulator.extractAmplitudeDataFromAudioInputStream(harpAIS);
		System.out.println(Arrays.toString(harpData));

		//System.out.println(stream);

		//		for(int i = 0; i < Math.max(bearNumbers.length, applauseNumbers.length); i++) {
		//			if(i < bearNumbers.length) System.out.print(bearNumbers[i]);
		//			System.out.print("\t");
		//			if(i < applauseNumbers.length) System.out.print(applauseNumbers[i]);
		//			System.out.println();
		//			
		//			new Scanner(System.in).nextLine();
		//		}	

		//FileOutputStream bearReturns = byteToWAV(BEARGROWL_WAV, bearNumbers);
		//playWAVFile(bearReturns);

		//playByteAIS(applauseNumbers);

		AudioInputStream applauseAIS = WAVUtil.byteToAIS(applauseNumbers);
		int[] applauseNumbers2 = SoundAmplitudeArrayManipulator.extractAmplitudeDataFromAudioInputStream(applauseAIS);
		//System.out.println(Arrays.toString(applauseNumbers2));



		//		for(int i = bearNumbers.length-11; i <= bearNumbers.length-1; i++) {
		//			System.out.print(bearNumbers[i] + " ");
		//		}
		//		System.out.println();

		double[] splice = new double[applauseNumbers.length];
		//		for(int i = 0; i < splice.length; i++) {
		//			if(i < 46)
		//				splice[i] = bearNumbers[i];
		//			else 
		//				splice[i] = applauseNumbers[i];
		//		}
		//		StdAudio.play(splice);

		double[] bear = new double[bearNumbers.length];
		for(int i = 0; i < bear.length; i++) {
			if(i < 50120 || i >= bearNumbers.length-11) 
				bear[i] = bearNumbers[i];
			else 
				bear[i] = 20;
		}
		bear[bear.length-1] = 0;
		MiscSoundUtil.play(bear);

		//		double[] applause = new double[bearNumbers.length];
		//		for(int i = 0; i < original.length; i++) {
		//			if(i < 50120 || i >= bearNumbers.length-11) 
		//				original[i] = bearNumbers[i];
		//			else 
		//				original[i] = 20;
		//		}
		//		original[original.length-1] = 0;
		//		StdAudio.play(original);
		//		
		//		double[] original = new double[bearNumbers.length];
		//		for(int i = 0; i < original.length; i++) {
		//			if(i < 50120 || i >= bearNumbers.length-11) 
		//				original[i] = bearNumbers[i];
		//			else 
		//				original[i] = 20;
		//		}
		//		original[original.length-1] = 0;
		//		StdAudio.play(original);

		//		StdAudio.wavePlot(BEARGROWL_WAV);
		//		StdAudio.wavePlot(APPLAUSE_WAV);
		//		StdAudio.wavePlot(HARP_WAV);

		System.out.println("bear growl: " + bearNumbers.length);
		System.out.println("applause: " + applauseNumbers.length);
		System.out.println("harp: " + harpNumbers.length);
		double[] applauseAndHarp = ArrayUtil.overlap(APPLAUSE_WAV, HARP_WAV);
		System.out.println("applauseAndHarp length: " + applauseAndHarp.length);
		double[] bearGrowlAndHarp = ArrayUtil.overlap(BEARGROWL_WAV, HARP_WAV);
		System.out.println("bearGrowlAndHarp length: " + bearGrowlAndHarp.length);
		double[] applauseAndBearGrowl = ArrayUtil.overlap(APPLAUSE_WAV, BEARGROWL_WAV);
		System.out.println("applauseAndBearGrowl length: " + applauseAndBearGrowl.length);

		//		StdAudio.play(applauseAndHarp);
		//		StdAudio.play(bearGrowlAndHarp);
		//		StdAudio.play(applauseAndBearGrowl);

		//MiscSoundUtil.play(PIRATES);

		// 440 Hz for 1 sec
		double freq1 = 440.0;
		for (int i = 0; i <= MiscSoundUtil.SAMPLE_RATE; i++) {
			//StdAudio.play(0.5 * Math.sin(2*Math.PI * freq1 * i / StdAudio.SAMPLE_RATE));
		}

		for (int i = 0; i <= MiscSoundUtil.SAMPLE_RATE; i++) {
			//StdAudio.play(0.5 * ActivationFunctions.squareWave(2*Math.PI * freq1 * i / StdAudio.SAMPLE_RATE));
		}

		for (int i = 0; i <= MiscSoundUtil.SAMPLE_RATE; i++) {
			//StdAudio.play(0.5 * ActivationFunctions.triangleWave(2*Math.PI * freq1 * i / StdAudio.SAMPLE_RATE));
		}


		//			// scale increments
		//			int[] steps = { 0, 2, 4, 5, 7, 9, 11, 12 };
		//			for (int i = 0; i < steps.length; i++) {
		//				double hz = 440.0 * Math.pow(2, steps[i] / 12.0);
		//				StdAudio.play(note(hz, 1.0, 0.5));
		//			}

		double[] exampleSound = new double[MiscSoundUtil.SAMPLE_RATE+1];
		double freq2 = 440.0;
		for (int i = 0; i <= MiscSoundUtil.SAMPLE_RATE; i++) {
			exampleSound[i] = (0.5 * Math.sin(2*Math.PI * freq2 * i / MiscSoundUtil.SAMPLE_RATE));
		}
		//StdAudio.play(exampleSound);

		for (int i = 0; i <= MiscSoundUtil.SAMPLE_RATE; i++) {
			exampleSound[i] = (0.5 * ActivationFunctions.fullSawtooth(2*Math.PI * freq2 * i / MiscSoundUtil.SAMPLE_RATE));
		}
		//StdAudio.play(exampleSound);

		double[] thing = new double[500];
		for(int i = 0; i < thing.length; i++) {
			thing[i] = i;
		}
		//StdAudio.play(thing);


		for (double i = 0; i <= MiscSoundUtil.SAMPLE_RATE; i++) {
			exampleSound[(int)i] = (ActivationFunctions.tanh( 2*(i/MiscSoundUtil.SAMPLE_RATE) - 1 ));
		}
		//StdAudio.play(exampleSound);

		//String pirates= "data/sounds/pirates.mid";
		//play(pirates);
		//playApplet(pirates);
		String classical = "data/sounds/CLASSICA.MID";
		//play(classical);
		//StdAudio.playApplet(classical); //TODO: failure here?


		// need to call this in non-interactive stuff so the program doesn't terminate
		// until all the sound leaves the speaker.
		
		
		//SAVING FILES FROM ARRAYS
		SoundAmplitudeArrayManipulator.saveFileFromArray(bearNumbers, "data/sounds/bearGrowlCopy.wav");
//		byte[] happyNumbers = MiscSoundUtil.readByte(HAPPY_MP3);
//		SoundAmplitudeArrayManipulator.saveFileFromArray(happyNumbers, "data/sounds/happyCopy.mp3");
		byte[] classicalNumbers = MiscSoundUtil.readByte(classical);
		SoundAmplitudeArrayManipulator.saveFileFromArray(classicalNumbers, "data/sounds/classicalCopy.mid");
		MiscSoundUtil.close(); 
	}


}
