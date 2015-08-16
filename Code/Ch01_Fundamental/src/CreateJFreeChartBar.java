import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.DefaultXYDataset;

//JFreeChart Bar Chart����״ͼ��   
public class CreateJFreeChartBar {

	/**
	 * ����JFreeChart Bar Chart����״ͼ��
	 */
	public static void main(String[] args) {
		CreateJFreeChartBar cjf=new CreateJFreeChartBar();
		// ����1������CategoryDataset����׼�����ݣ�
		DefaultXYDataset dataset = cjf.createXYPointSet();
		// ����2������Dataset ����JFreeChart�����Լ�����Ӧ������


		JFreeChart jfreechart = ChartFactory.createScatterPlot("demo",  
                "x", "y", dataset, PlotOrientation.VERTICAL, true, false,  
                false);  
        
		
		// ����3����JFreeChart����������ļ���Servlet�������
		saveAsFile(jfreechart, "E:\\xyscatter.png", 500, 400);
	}

	public DefaultXYDataset createXYPointSet(){
		DefaultXYDataset dataset=new DefaultXYDataset();
		for(int j=1;j<3;j++){
			double[][] datas = new double[2][10];
			for(int i=0;i<10;i++){
				datas[0][i] = i*10+j*10;  
	            datas[1][i] = i*5+2*j*10; 
			}
			dataset.addSeries("xilie"+j, datas);
		}
		
		return dataset;
	}
	
	
	
	
	// ����Ϊ�ļ�
	public static void saveAsFile(JFreeChart chart, String outputPath, int weight, int height) {
		FileOutputStream out = null;
		try {
			File outFile = new File(outputPath);
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			out = new FileOutputStream(outputPath);
			// ����ΪPNG�ļ�
			ChartUtilities.writeChartAsPNG(out, chart, weight, height);
			// ����ΪJPEG�ļ�
			// ChartUtilities.writeChartAsJPEG(out, chart, 500, 400);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}

	// ����CategoryDataset����JFreeChart����
	public static JFreeChart createChart(CategoryDataset categoryDataset) {
		JFreeChart jfreechart = ChartFactory.createBarChart("ѧ��ͳ��ͼ", // ����
				"ѧ������", // categoryAxisLabel ��category�ᣬ���ᣬX��ı�ǩ��
				"����", // valueAxisLabel��value�ᣬ���ᣬY��ı�ǩ��
				categoryDataset, // dataset
				PlotOrientation.VERTICAL, false, // legend
				false, // tooltips
				false); // URLs

		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);

		jfreechart.setTextAntiAlias(false);
		jfreechart.setBackgroundPaint(Color.white);

		CategoryPlot plot = jfreechart.getCategoryPlot();// ���ͼ���������

		// ���ú����߿ɼ�
		plot.setRangeGridlinesVisible(true);
		// ����ɫ��
		plot.setRangeGridlinePaint(Color.gray);
		// �����ᾫ��
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		// vn.setAutoRangeIncludesZero(true);
		DecimalFormat df = new DecimalFormat("#0.0");
		vn.setNumberFormatOverride(df); // ���������ݱ�ǩ����ʾ��ʽ

		// x������
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// �����
		domainAxis.setTickLabelFont(labelFont);// ����ֵ
		// Lable��Math.PI/3.0������б
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions
		// .createUpRotationLabelPositions(Math.PI / 3.0));
		domainAxis.setMaximumCategoryLabelWidthRatio(6.00f);// �����ϵ� Lable
		// �Ƿ�������ʾ

		// ���þ���ͼƬ��˾���
		domainAxis.setLowerMargin(0.1);
		// ���þ���ͼƬ�Ҷ˾���
		domainAxis.setUpperMargin(0.1);
		// ���� columnKey �Ƿ�����ʾ
		// domainAxis.setSkipCategoryLabelsToFit(true);
		plot.setDomainAxis(domainAxis);
		// ������ͼ����ɫ��ע�⣬ϵͳȡɫ��ʱ��Ҫʹ��16λ��ģʽ���鿴��ɫ���룬�����Ƚ�׼ȷ��
		plot.setBackgroundPaint(new Color(255, 255, 204));

		// y������
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(labelFont);
		rangeAxis.setTickLabelFont(labelFont);
		// ������ߵ�һ�� Item ��ͼƬ���˵ľ���
		rangeAxis.setUpperMargin(0.15);
		// ������͵�һ�� Item ��ͼƬ�׶˵ľ���
		rangeAxis.setLowerMargin(0.15);
		plot.setRangeAxis(rangeAxis);

		// ���������������(�ؼ�)
		TextTitle textTitle = jfreechart.getTitle();
		textTitle.setFont(new Font("����", Font.PLAIN, 20));
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		domainAxis.setLabelFont(new Font("����", Font.PLAIN, 12));
		vn.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		vn.setLabelFont(new Font("����", Font.PLAIN, 12));
		// jfreechart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12));

		BarRenderer renderer = new BarRenderer();
		// �������ӿ��
		renderer.setMaximumBarWidth(0.2);
		// �������Ӹ߶�
		renderer.setMinimumBarLength(0.2);
		// �������ӱ߿���ɫ
		renderer.setBaseOutlinePaint(Color.BLACK);
		// �������ӱ߿�ɼ�
		renderer.setDrawBarOutline(true);
		// // ����������ɫ
		renderer.setSeriesPaint(0, Color.decode("#8BBA00"));
		// ����ÿ��������������ƽ������֮�����
		renderer.setItemMargin(0.5);
		jfreechart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		plot.setRenderer(renderer);
		// ��������͸����
		plot.setForegroundAlpha(1.0f);

		// ����ɫ ͸����
		plot.setBackgroundAlpha(0.5f);

		return jfreechart;
	}

	// ����CategoryDataset����
	public static CategoryDataset createDataset() {
		double[][] data = new double[][] { { 25, 24, 40, 12, 33, 33 } };
		String[] rowKeys = { "" };
		String[] columnKeys = { "����", "����", "����", "����", "����", "�԰�" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
		return dataset;
	}

}