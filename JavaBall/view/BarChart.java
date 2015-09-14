package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Class that draws the Bar Chart. 
 * @author Team C
 */
public final class BarChart extends JPanel{

	/**Width of every bar.*/
	private static final int BAR_WIDTH = 40;
	/**Integer that is used to calculate the size of the axis area.*/
	private static final int AXIS_TOTAL_PORTION = 6;
	/**Color of every bar.*/
	private static final Color BAR_COLOR = Color.BLUE;
	/**Text color*/
	private static final Color TEXT_COLOR = Color.BLACK;
	/**Axis Font*/
	private static final Font AXIS_FONT = new Font("Courier", Font.BOLD, 17);
	/**Bars' offset from Y-Axis*/
	private static final int Y_AXIS_OFFSET = 20;
	/**X Dimension Offset of the values displayed on the Y-Axis*/
	private static final int VALUES_X__OFFSET = 50;
	/**Y Dimension Offset of the values displayed on the Y-Axis*/
	private static final int VALUES_Y_OFFSET = 10;

	//Array of integers containing the number of matches for every referee.
	private int[] matchesAll;
	//Array of Strings containing the IDs of every referee.
	private String[] refIDs;

	//Total height and width of the component
	private int totalHeight, totalWidth;
	private double minValue = 0, maxValue = 0;
	//Height and width of the axis plot.
	private double  axisHeight, axisWidth;

	//Positions (x,y) of the topLeft, bottomLeft and bottomRight points of the axis plot.
	private int topLeftX, topLeftY, bottomLeftX, bottomLeftY, bottomRightX, bottomRightY;
	//Gap between bars.
	private double barGap;

	private Graphics graphic;
	//Number of referees.
	private int totalRefs;

	public BarChart(int[] matchesAll, String[] refIDs) {
		this.matchesAll = matchesAll;
		this.refIDs = refIDs;	
	}

	@Override
	protected void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		this.graphic = graphic;

		totalRefs = refIDs.length;
		totalWidth = this.getSize().width;
		totalHeight = this.getSize().height;

		//Dynamically calculates the topLeft, bottomLeft and bottomRight points of the axis plot based on the size of the component.
		topLeftX = totalWidth / AXIS_TOTAL_PORTION;
		bottomLeftX = topLeftX;
		bottomRightX = (( AXIS_TOTAL_PORTION - 1 ) * totalWidth ) / AXIS_TOTAL_PORTION;

		topLeftY = totalHeight / AXIS_TOTAL_PORTION;
		bottomLeftY = (( AXIS_TOTAL_PORTION - 1 ) * totalHeight ) / AXIS_TOTAL_PORTION;
		bottomRightY = bottomLeftY;

		//Width and height of the axis rectangle
		axisWidth = bottomRightX - topLeftX;
		axisHeight = bottomLeftY - topLeftY;

		//Formula to dynamically calculate the gap between bars based on the number of referees.
		barGap = ( axisWidth - ( totalRefs * BAR_WIDTH )) / totalRefs;		

		//Finds the minimum and maximum values.
		for (int i = 0; i < matchesAll.length; i++) {
			if (minValue > matchesAll[i])
				minValue = matchesAll[i];
			if (maxValue < matchesAll[i])
				maxValue = matchesAll[i];
		}

		paintAxis();
		paintBars();
	}

	/**
	 * Paints the two Axis. On the Y-Axis adds values from maxValue to minValue divided in 4 quarters.
	 */
	private void paintAxis(){		
		//Draws a white background for the axis plot.
		graphic.setColor(Color.WHITE);
		graphic.fillRect(topLeftX, topLeftY, (int) axisWidth, (int) axisHeight);
		graphic.setColor(Color.BLACK);
		graphic.setFont(AXIS_FONT);

		//Draws X-Axis and its title.
		graphic.drawLine(bottomLeftX, bottomLeftY, bottomRightX, bottomRightY);
		graphic.drawString("Referees IDs", (totalWidth/2) - 50, totalHeight - 40);
		//Draws Y-Axis and its title.
		graphic.drawLine(topLeftX, topLeftY, bottomLeftX, bottomLeftY);
		graphic.drawString("Matches", 20, totalHeight/2);

		//Draws the Y-Axis values from maxValue to minValue divided in 4 quarters.
		graphic.drawString("" + maxValue, topLeftX - VALUES_X__OFFSET, topLeftY + VALUES_Y_OFFSET);
		graphic.drawLine(topLeftX - 3, topLeftY, topLeftX + 3, topLeftY);

		graphic.drawString("" + 3 * maxValue/4, topLeftX - VALUES_X__OFFSET, topLeftY + VALUES_Y_OFFSET + ( (int) axisHeight/4 ));
		graphic.drawLine(topLeftX - 3, topLeftY + ( (int) axisHeight/4 ), topLeftX + 3, topLeftY + ( (int) axisHeight/4 ));

		graphic.drawString("" + maxValue/2, topLeftX - VALUES_X__OFFSET, topLeftY + VALUES_Y_OFFSET + ( (int) axisHeight/2 ));
		graphic.drawLine(topLeftX - 3, topLeftY + ( (int) axisHeight/2 ), topLeftX + 3, topLeftY + ( (int) axisHeight/2 ));

		graphic.drawString("" + maxValue/4, topLeftX - VALUES_X__OFFSET, topLeftY + VALUES_Y_OFFSET + ( (int) (3 * axisHeight/4 )));
		graphic.drawLine(topLeftX - 3,  topLeftY + ( (int) (3 * axisHeight/4 )), topLeftX + 3,  topLeftY + ( (int) (3 * axisHeight/4 )));
		graphic.drawString("" + minValue, topLeftX - VALUES_X__OFFSET, topLeftY + VALUES_Y_OFFSET + ( (int) axisHeight ));				
	}

	/**
	 * Paints the bars based on the following algorithm:
	 * 	  Loops all the referees. For every one of them:
	 *  	i)draws a String of his ID under the X-Axis, 
	 *  	ii)draws a bar with height equal to the number of matches allocated to him  and
	 *  	iii)draws a String on top of the bar that displays the number of matches allocated to him.
	 */
	private void paintBars(){

		for(int i = 0; i < totalRefs; i++){			
			//topEdge is the top edge of every bar. 
			double topEdge = (bottomLeftY - (axisHeight * ( matchesAll[i] / maxValue )));
			//Left edge of every bar.
			double leftEdge = bottomLeftX + (i * (barGap + BAR_WIDTH )) + Y_AXIS_OFFSET;

			//Draws IDs under X-Axis.
			graphic.setColor(TEXT_COLOR);
			graphic.drawString(refIDs[i], (int) leftEdge + 5, bottomLeftY + Y_AXIS_OFFSET);

			//Draws bars.
			graphic.setColor(BAR_COLOR);
			graphic.drawRect((int) leftEdge, (int) topEdge, BAR_WIDTH,  (int) (bottomLeftY - topEdge));	
			graphic.fillRect((int) leftEdge, (int) topEdge, BAR_WIDTH,  (int) (bottomLeftY - topEdge));

			//Draws number of matches on top of each bar.
			graphic.setColor(TEXT_COLOR);
			graphic.drawString("" + matchesAll[i] , (int) leftEdge + BAR_WIDTH/3, (int) topEdge - Y_AXIS_OFFSET);
		}
	}
}