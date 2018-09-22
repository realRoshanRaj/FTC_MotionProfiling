import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
	
/**
 * To close the graph window press 'esc'
 * 
 * @author ankith, Roshan
 *
 */
public class Graph extends Thread {
	java.awt.Color txtColor = new java.awt.Color(102, 190, 107);
	java.awt.Color pointColor = new java.awt.Color(255, 0, 0);
	boolean isElevator, isPreview;
	double[] x, y;
	String xName, yName;

	public void run() {
		try {
			displayProfile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Graph(String xName, String yName, double[] x, double[] y) {
		this.x = x;
		this.y = y;
		this.xName = xName;
		this.yName = yName;
	}

	public void displayProfile() throws Exception {

		// Create Chart
		XYChart positionChart = new XYChartBuilder().width(800).height(600).title("").xAxisTitle(xName)
				.yAxisTitle(yName).build();
		// Customize Chart
		positionChart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.GREY));
		positionChart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
		positionChart.getStyler().setChartBackgroundColor(Color.BLACK);
		positionChart.getStyler().setLegendBackgroundColor(Color.DARK_GRAY);
		positionChart.getStyler().setChartFontColor(txtColor);
		positionChart.getStyler().setChartTitleBoxBackgroundColor(Color.BLACK);
		positionChart.getStyler().setChartTitleBoxVisible(true);
		positionChart.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
		positionChart.getStyler().setPlotGridLinesVisible(false);
		positionChart.getStyler().setChartTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		positionChart.getStyler().setAxisTickPadding(20);

		positionChart.getStyler().setAxisTickMarkLength(15);
		
		positionChart.getStyler().setMarkerSize(5);

		positionChart.getStyler().setPlotMargin(20);
		positionChart.getStyler().setXAxisTickMarkSpacingHint(30);
		positionChart.getStyler().setYAxisTickMarkSpacingHint(30);

		positionChart.getStyler().setAxisTickLabelsColor(txtColor);
		positionChart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		positionChart.getStyler().setLegendFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		positionChart.getStyler().setLegendPosition(LegendPosition.InsideSE);

		// Series
		XYSeries posCenter = positionChart.addSeries(yName + " vs. " + xName, x, y);
		posCenter.setLineColor(XChartSeriesColors.GREEN);
		posCenter.setMarkerColor(txtColor);
		posCenter.setMarker(SeriesMarkers.CIRCLE);
		posCenter.setLineStyle(SeriesLines.SOLID);

		JFrame positionJFrame = (new SwingWrapper<XYChart>(positionChart)).displayChart("profile");

		java.awt.event.ActionListener posEscListener = new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				positionJFrame.dispose();
			}
		};

		positionJFrame.getRootPane().registerKeyboardAction(posEscListener,
				KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

		positionJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}

}