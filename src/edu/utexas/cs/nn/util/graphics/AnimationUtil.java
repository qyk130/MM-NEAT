package edu.utexas.cs.nn.util.graphics;

import java.awt.Image;

import edu.utexas.cs.nn.networks.Network;

/**
 * Series of utility methods used to create and manipulate
 * animations. Mainly used by AnimationBreederTask
 * 
 * @author Isabel Tweraser
 *
 */
public class AnimationUtil {
	
	/**
	 * Utility method that generates an array of images based on an input CPPN.
	 * 
	 * @param n CPPN used to create image
	 * @param imageWidth width of created image
	 * @param imageHeight height of created image
	 * @param time How long the animation should be. This input determines size of array of images
	 * @param inputMultiples array with inputs determining whether checkboxes in interface should be turned on or off
	 * @return Array of images that can be animated in a JApplet
	 */
	public static Image[] imagesFromCPPN(Network n, int imageWidth, int imageHeight, int time, double[] inputMultiples) {
		Image[] images = new Image[time];
		for(int i = 0; i < time; i++) {
			images[i] = GraphicsUtil.imageFromCPPN(n, imageWidth, imageHeight, inputMultiples, i);
		}
		return images;
	}
	
	
}
