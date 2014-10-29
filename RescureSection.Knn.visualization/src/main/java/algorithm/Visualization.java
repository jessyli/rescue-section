package algorithm;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

import utils.io.CSVFileParser;
import algorithm.interfaces.CanComputeDistance;
import algorithm.interfaces.Visualizable;
import algorithm.models.KNNPoint;

public class Visualization {

	public static void main(String[] args) {
		Visualization v2 = new Visualization("/Users/jessyli/Documents/workspace/RescureSection.KnnVisualize-master/src/main/resources/IrisTrain.csv", //
				"/Users/jessyli/Documents/workspace/RescureSection.KnnVisualize-master/src/main/resources/IrisTest.csv");
		v2.paint(1, 2, 1);
		
//		System.exit(0);
	}

	KNNAlgorithm algorithm = null;
	List<Visualizable> sourceData = null;
	List<Visualizable> testData = null;
	
	public Visualization(String training_path, String testingPath) {
		sourceData = CSVFileParser.parseOutsource(training_path,
				KNNPoint.class);
		testData = CSVFileParser.parseOutsource(testingPath, KNNPoint.class);
		algorithm = new KNNAlgorithm(sourceData);
	}

	Map<String, Integer> label2IndexMap = new HashMap<String, Integer>();
	public void paint(int feature1, int feature2, int testpoint ) {
		List<Visualizable> points1 = sourceData;
		List<Visualizable> points2 = testData;
		 
		double [] plots1 = new double [points1.size()];
		
		for(int i=0;i<points1.size();i++){
			plots1[i] = points1.get(i).getPlots()[feature1];
			String label = points1.get(i).getLabel();
			
			if(!label2IndexMap.containsKey(label))
				label2IndexMap.put(label, label2IndexMap.size());
		}
		
		for(int i=0;i<points1.size();i++){
			System.out.print(plots1[i]+" ");
		}
		
		
		System.out.println();

		double [] plots2 = new double [points1.size()];
		
		for(int i=0;i<points1.size();i++){
			plots2[i] = points1.get(i).getPlots()[feature2];
		}
		
		for(int i=0;i<points1.size();i++){
			System.out.print(plots2[i]+" ");
		}
		
		
		
		 
		 System.out.println();

		Color color_red = new Color(255, 0, 0);
		Color color_green = new Color(0, 255, 0);
		Color color_blue = new Color(0, 0, 255);
		double [] testdata = new double[2];
		
		double [] plots3 = new double [points2.size()];
		plots3[testpoint] = points2.get(testpoint).getPlots()[feature1];
		double [] plots4 = new double [points2.size()];
		plots4[testpoint] = points2.get(testpoint).getPlots()[feature2];
		testdata[0] = plots3[testpoint];
		testdata[1] = plots4[testpoint];
		Plot2DPanel plot = new Plot2DPanel();
		plot.addScatterPlot("scatter point", color_red, plots1, plots2);
		plot.addScatterPlot("scatter point", color_green, plots3, plots4);
		
		double [][] traindata = new double [points1.size()][2];
		for(int i=0;i<points1.size();i++){
			traindata[i][0] = plots1[i];
			traindata[i][1] = plots2[i];
		}
//		for(int i=0;i<points1.size();i++){
//			plot.addLinePlot("line", color_blue, testdata, traindata[i]);
//		}
		List<List<KNNPoint>> lists = new ArrayList<List<KNNPoint>>();
		for(int i=0; i<3; i++) {
			lists.add(new ArrayList<KNNPoint>());
		}
		for (int i=0;i<points1.size();i++){
						String currentLabel = points1.get(i).getLabel();
			if(label2IndexMap.containsKey(currentLabel)) {
				Integer index = label2IndexMap.get(currentLabel);
				lists.get(index).add((KNNPoint) points1.get(i));
			}
			
		}
	    
		for(int i=0;i<lists.get(0).size();i++){
//			System.out.println(testdata.length + "\t" + lists.get(0).get(i).getPlots().length);
			plot.addLinePlot("line", color_blue, testdata,//
					new double[]{lists.get(0).get(i).getPlots()[feature1], lists.get(0).get(i).getPlots()[feature2]});		
			}
		for(int i=0;i<lists.get(1).size();i++){
			plot.addLinePlot("line", color_green, testdata,//
					new double[]{lists.get(1).get(i).getPlots()[feature1], lists.get(1).get(i).getPlots()[feature2]});
			}
		for(int i=0;i<lists.get(2).size();i++){
			plot.addLinePlot("line", color_red, testdata,//
					new double[]{lists.get(2).get(i).getPlots()[feature1], lists.get(2).get(i).getPlots()[feature2]});
			}

				
		

//	plot.addLinePlot("line", color_green, plots1, plots2);
		
		
		JFrame frame = new JFrame("jingwei");
		frame.setContentPane(plot);
		frame.setVisible(true);
	}
//	ArrayList<KNNAlgorithm> dist = new ArrayList<KNNAlgorithm>();
//
//		for (int i=0;i<points1.size();i++){
//			dists.add(i, CanComputeDistance.class); 
//		Collections.sort(dists);
//		for 
//		}
	
	public KNNAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(KNNAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

}
