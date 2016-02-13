package br.com.ujr.client.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

public class GaugePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	DefaultValueDataset dataset;
	
	public GaugePanel(int width, int height) {
		dataset = new DefaultValueDataset(0D);
        JFreeChart jfreechart = createStandardDialChart("Monitora‹o", "Transa›es por Segundo", dataset, 0D, 1000D, 50D, 5);
        
        DialPlot dialplot = (DialPlot)jfreechart.getPlot();
        
        StandardDialRange standarddialrange = new StandardDialRange(700D, 900D, Color.red);
        standarddialrange.setInnerRadius(0.52000000000000002D);
        standarddialrange.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(standarddialrange);
        
        StandardDialRange standarddialrange1 = new StandardDialRange(500D, 700D, Color.orange);
        standarddialrange1.setInnerRadius(0.52000000000000002D);
        standarddialrange1.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(standarddialrange1);
        
        StandardDialRange standarddialrange2 = new StandardDialRange(0D, 500D, Color.green);
        standarddialrange2.setInnerRadius(0.52000000000000002D);
        standarddialrange2.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(standarddialrange2);
        
        StandardDialRange standarddialrange3 = new StandardDialRange(900D, 1000D, Color.black);
        standarddialrange3.setInnerRadius(0.52000000000000002D);
        standarddialrange3.setOuterRadius(0.55000000000000004D);
        dialplot.addLayer(standarddialrange3);
        
        GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
        DialBackground dialbackground = new DialBackground(gradientpaint);
        dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        dialplot.setBackground(dialbackground);
        
        dialplot.removePointer(0);
        
        org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer();
        pointer.setFillPaint(Color.yellow);
        dialplot.addPointer(pointer);
        
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartpanel.setPreferredSize(new Dimension(width, height));
        add(chartpanel);
	}
	
	 public static JFreeChart createStandardDialChart(String s, String s1, ValueDataset valuedataset, double d, double d1, double d2, int i) {
             DialPlot dialplot = new DialPlot();
             dialplot.setDataset(valuedataset);
             dialplot.setDialFrame(new StandardDialFrame());
             dialplot.setBackground(new DialBackground());
             
             DialTextAnnotation dialtextannotation = new DialTextAnnotation(s1);
             dialtextannotation.setFont(new Font("Dialog", 1, 15));
             dialtextannotation.setRadius(0.69999999999999996D);
             dialplot.addLayer(dialtextannotation);
             
             DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
             dialvalueindicator.setFont(new Font("Arial",1,14));
             dialvalueindicator.setOutlineStroke(new BasicStroke(1f));
             dialvalueindicator.setOutlinePaint(Color.gray);
             dialvalueindicator.setNumberFormat(new DecimalFormat("0000"));
             dialplot.addLayer(dialvalueindicator);
             
             StandardDialScale standarddialscale = new StandardDialScale(d, d1, -130D, -280D, 5D, 4);
             standarddialscale.setTickLabelFormatter(new DecimalFormat("#,##0"));
             standarddialscale.setMajorTickIncrement(d2);
             standarddialscale.setMinorTickCount(i);
             standarddialscale.setTickRadius(0.90D);
             standarddialscale.setTickLabelOffset(0.15D);
             standarddialscale.setTickLabelFont(new Font("Arial", 1, 16));             
             dialplot.addScale(0, standarddialscale);
             
             dialplot.addPointer(new org.jfree.chart.plot.dial.DialPointer.Pin());
             DialCap dialcap = new DialCap();
             dialplot.setCap(dialcap);
             
             return new JFreeChart(s, dialplot);
     }
	 
	 public void setValue(int value) {
		 this.dataset.setValue(value);
	 }
	 public int getCurrentValue() {
		 return this.dataset.getValue().intValue();
	 }
	 public void moveTo(int value) {
			int current = this.getCurrentValue();
			int i       = current;
			while (true) {
				if ( current > value  ) {
					if (i <= value )break;
					i--;
				} else {
					if (i >= value )break;
					i++;
				}
				this.pause(1);
				this.setValue(i);
			}
	}
	 
	 // Utilit‡rio para simular intervalo
	 private void pause(long time) {
		 try {
			 Thread.sleep(time);
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 }
	 }

}
