

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class KMeans
{
    //the number of clusters
	private static final int NUM_CLUSTERS = 5;    // Total clusters.
    //the number of the spots
	private static final int TOTAL_DATA = 100;      // Total data points.
    //the sample of the spots
/*    private static final double SAMPLES[][] = new double[][] {{1.0, 1.0}, 
                                                                {1.5, 2.0}, 
                                                                {3.0, 4.0}, 
                                                                {5.0, 7.0}, 
                                                                {3.5, 5.0}, 
                                                                {4.5, 5.0}, 
                                                                {3.5, 4.5}};*/

	private static double SAMPLES[][] = new double[TOTAL_DATA][TOTAL_DATA];
	
	
	private static void getData(){
		for(int i=0;i<TOTAL_DATA;i++){
			for(int j=0;j<TOTAL_DATA;j++){
				SAMPLES[i][j]=Math.random()*100;
			}
		}
	}
	
    //dataset the spots
    private static ArrayList<Data> dataSet = new ArrayList<Data>();
    //dataset the centroid
    private static ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    //��ʼ���ص����ĵ�
    private static void getCentroid(){
    	centroids.add(new Centroid(10.0, 10.0)); 
        centroids.add(new Centroid(20.0, 20.0)); 
        centroids.add(new Centroid(40.0, 40.0)); 
        centroids.add(new Centroid(60.0, 60.0)); 
        centroids.add(new Centroid(90.0, 90.0)); 
	}
    private static void initialize()
    {
//        System.out.println("Centroids initialized at:");
        getData();
        getCentroid();
//        System.out.println("     (" + centroids.get(0).X() + ", " + centroids.get(0).Y() + ")");
//        System.out.println("     (" + centroids.get(1).X() + ", " + centroids.get(1).Y() + ")");
//        System.out.print("\n");
        return;
    }
    //����KMeanCluster����
    private static void kMeanCluster()
    {
        final double bigNumber = Math.pow(10, 10);    // some big number that's sure to be larger than our data range.
        double minimum = bigNumber;                   // The minimum value to beat. 
        double distance = 0.0;                        // The current minimum value.
        int sampleNumber = 0;
        int cluster = 0;
        //�ж�һ���ص����ĵ��Ƿ����仯
        boolean isStillMoving = true;
        Data newData = null;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(dataSet.size() < TOTAL_DATA)
        {
            //��SAMPLES��������ݷŵ�dataSet���棬ÿ�η�һ����ȥ
        	newData = new Data(SAMPLES[sampleNumber][0], SAMPLES[sampleNumber][1]);
            dataSet.add(newData);
            
            minimum = bigNumber;
            
            //����ոռӽ����ĵ���ÿһ�����ĵ�ľ��룬ͨ��ѭ���ҵ���С�ľ���minimum������¼��������ĵ㣬�ŵ�Data.cluster����
            //������ȷ��ÿ�������newdata���ڵĴ�
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                distance = dist(newData, centroids.get(i));
                if(distance < minimum){
                    minimum = distance;
                    cluster = i;
                }
            }
            newData.cluster(cluster);
            
            // calculate new centroids.ʵ���Ͼ�����ÿһ�������ƽ��ֵ
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }
            //sampleNumber����ָ�룬һ��һ���İ����ӽ�����
            sampleNumber++;
        }
        
        // Now, keep shifting centroids until equilibrium occurs.
        while(isStillMoving)
        {
            // calculate new centroids.
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }
            
            // Assign all data to the new centroids
            isStillMoving = false;
            
            //�ж�ÿ��������ĵ��Ƿ������ƶ�
            for(int i = 0; i < dataSet.size(); i++)
            {
                Data tempData = dataSet.get(i);
                minimum = bigNumber;
                for(int j = 0; j < NUM_CLUSTERS; j++)
                {
                    distance = dist(tempData, centroids.get(j));
                    if(distance < minimum){
                        minimum = distance;
                        cluster = j;
                    }
                }
                tempData.cluster(cluster);
                if(tempData.cluster() != cluster){
                    tempData.cluster(cluster);
                    isStillMoving = true;
                }
            }
        }
        return;
    }
    
    /**
     * // Calculate Euclidean distance.
     * @param d - Data object.
     * @param c - Centroid object.
     * @return - double value.
     */
    private static double dist(Data d, Centroid c)
    {
        return Math.sqrt(Math.pow((c.Y() - d.Y()), 2) + Math.pow((c.X() - d.X()), 2));
    }
    
    //����Data�࣬��ʾһ������㣨mX,mY��,�Լ����ڴصı��
    private static class Data
    {
        private double mX = 0;
        private double mY = 0;
        private int mCluster = 0;
        
        public Data()
        {
            return;
        }
        
        public Data(double x, double y)
        {
            this.X(x);
            this.Y(y);
            return;
        }
        
        public void X(double x)
        {
            this.mX = x;
            return;
        }
        
        public double X()
        {
            return this.mX;
        }
        
        public void Y(double y)
        {
            this.mY = y;
            return;
        }
        
        public double Y()
        {
            return this.mY;
        }
        
        public void cluster(int clusterNumber)
        {
            this.mCluster = clusterNumber;
            return;
        }
        
        public int cluster()
        {
            return this.mCluster;
        }
    }
    
    //����Centroid�࣬������ţ�mX,mY��
    private static class Centroid
    {
        private double mX = 0.0;
        private double mY = 0.0;
        
        public Centroid()
        {
            return;
        }
        
        public Centroid(double newX, double newY)
        {
            this.mX = newX;
            this.mY = newY;
            return;
        }
        
        public void X(double newX)
        {
            this.mX = newX;
            return;
        }
        
        public double X()
        {
            return this.mX;
        }
        
        public void Y(double newY)
        {
            this.mY = newY;
            return;
        }
        
        public double Y()
        {
            return this.mY;
        }
    }
    
    public static void main(String[] args)
    {
        initialize();
        kMeanCluster();
        
        // Print out clustering results.
 /*     for(int i = 0; i < NUM_CLUSTERS; i++)
        {
            System.out.println("Cluster " + i + " includes:");
            for(int j = 0; j < TOTAL_DATA; j++)
            {
                if(dataSet.get(j).cluster() == i){
                    System.out.println("     (" + dataSet.get(j).X() + ", " + dataSet.get(j).Y() + ")");
                }
            } // j
            System.out.println();
        } // i
*/        
        //XYSeriesCollection�������XYSeries
        XYSeriesCollection xyseriescollection = new XYSeriesCollection(); //����XYSeriesCollection�����XYSeries ����
        for(int i = 0; i < NUM_CLUSTERS; i++)
        {
        	XYSeries xyseries = new XYSeries("cluster"+Integer.toString(i)); 
            for(int j = 0; j < TOTAL_DATA; j++)
            {
                if(dataSet.get(j).cluster() == i){
                	 xyseries.add(dataSet.get(j).X(),dataSet.get(j).Y());
                }
            }
            xyseriescollection.addSeries(xyseries);
           /* XYSeries xyseriesCentroid = new XYSeries("cluster"+Integer.toString(i)); 
            xyseriesCentroid.add(centroids.get(i).X(),centroids.get(i).Y());
            xyseriescollection.addSeries(xyseriesCentroid);*/
        } 
        
      //����������ʽ         
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");        
        //���ñ�������         
        standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));        
        //����ͼ��������        
        standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15));        
        //�������������       
        standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15));        
        //Ӧ��������ʽ      
        ChartFactory.setChartTheme(standardChartTheme);  
        //    JFreeChart chart=ChartFactory.createXYAreaChart("xyPoit", "����", "����", xyseriescollection, PlotOrientation.VERTICAL, true, false, false);
        JFreeChart chart=ChartFactory.createScatterPlot("�������", "����", "����", xyseriescollection, PlotOrientation.VERTICAL, true, false, false);
        
                try {
                    ChartUtilities.saveChartAsPNG(new File("E:/ScatterPlot.png"), chart, 500, 500);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        ChartFrame ChartFrame = new ChartFrame("ScatterPlot",chart);
        ChartFrame.pack();
        //ChartFrame.setFont(new Font("����",Font.BOLD,20));
        ChartFrame.setVisible(true);
        
    }
}
