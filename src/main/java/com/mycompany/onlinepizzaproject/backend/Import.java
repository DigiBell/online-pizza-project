package com.mycompany.onlinepizzaproject.backend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.bson.Document;

import com.mycompany.onlinepizzaproject.backend.Product.Category;



public class Import implements ActionListener {

	private JFrame frame;
	private JPanel mainPanel;
	private JPanel btnPanel;
	private JButton pizzaBtn;
	private JButton beverageBtn;
	private JPanel logPanel;
	private JTextArea logTextArea;
	private JScrollPane logScrollPane;

	public Import() {

		SwingUtilities.invokeLater(() -> {

			frame = new JFrame("NoSQL import");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			mainPanel = new JPanel();
			mainPanel.setPreferredSize(new Dimension(600, 800));

			btnPanel = new JPanel(new GridLayout(1, 2));

			pizzaBtn = new JButton("Pizza");
			pizzaBtn.addActionListener(this);
			btnPanel.add(pizzaBtn);

			beverageBtn = new JButton("Beverage");
			beverageBtn.addActionListener(this);
			btnPanel.add(beverageBtn);

			logPanel = new JPanel();
			logPanel.setPreferredSize(new Dimension(600, 600));

			logTextArea = new JTextArea();
			logTextArea.setLineWrap(true);
			logTextArea.setDisabledTextColor(Color.BLACK);

			logScrollPane = new JScrollPane(logTextArea);
			logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			logScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			logScrollPane.setPreferredSize(new Dimension(580, 580));

			logPanel.add(logScrollPane);

			mainPanel.add(btnPanel);
			mainPanel.add(logPanel);

			frame.setContentPane(mainPanel);
			frame.pack();
			frame.setVisible(true);		
		});
	}

	public static void main(String[] args) {
		new Import();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pizzaBtn) {
			importPizzas();
		} else if (e.getSource() == beverageBtn) {
			importBeverages();
		}
	}
	
	private void importPizzas() {
		String[] lines = logTextArea.getText().split("\\r?\\n");
		
		System.out.println("Lines: " + lines.length);
		
		int counter = 0;
		
		String name = "";
		String descriptionSv = "";
		String descriptionEn = "";
		int p20 = 0;
		int p30 = 0;
		int p40 = 0;
		
		List<Document> pizzas = new ArrayList<>();
		
		for (String line : lines) {
			System.out.println("counter: " + counter);
			switch (counter) {
			case 0:
				name = line;
				break;
			case 1:
				descriptionSv = line;
				break;
			case 2:
				descriptionEn = line.substring(1, line.length()-1);
				break;
			case 4:
				p20 = Integer.parseInt(line.substring(0, line.indexOf(':')));
				break;
			case 5:
				p30 = Integer.parseInt(line.substring(0, line.indexOf(':')));
				break;
			case 6:
				p40 = Integer.parseInt(line.substring(0, line.indexOf(':')));
				break;
			case 7:
				
				Pizza p = new Pizza(name, p20, p30, p40, descriptionSv, descriptionEn);
				
				System.out.println(p.toDocument().toJson());
				
				pizzas.add(p.toDocument());
				
				counter = -1;
				break;
			default:
				break;
			}
			
			counter++;
		}
		
		API.addPizzas(pizzas);
	}
	
	private void importBeverages() {
		String[] lines = logTextArea.getText().split("\\r?\\n");
		
		System.out.println("Lines: " + lines.length);
		
		int counter = 0;
		
		String name = "";
		int price = 0;
		
		List<Document> products = new ArrayList<>();
		
		Random random = new Random();
		
		for (String line : lines) {
			System.out.println("counter: " + counter);
			switch (counter) {
			case 0:
				name = line;
				break;
			case 2:
				price = Integer.parseInt(line.substring(0, line.indexOf(':')));
				
				Product p = new Product(name, Category.beverage, price, random.nextInt(90)+10);
				
				System.out.println(p.toDocument().toJson());
				
				products.add(p.toDocument());
				
				counter = -1;
				break;
			default:
				break;
			}
			
			counter++;
		}
		
		API.addProducts(products);
	}

}