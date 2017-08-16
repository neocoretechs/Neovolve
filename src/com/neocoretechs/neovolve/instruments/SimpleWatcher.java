package com.neocoretechs.neovolve.instruments;

import com.neocoretechs.neovolve.GPListener;
import javax.swing.*;
import java.awt.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class SimpleWatcher extends JFrame implements GPListener {
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JProgressBar generationBar = new JProgressBar();
  JProgressBar evaluationBar = new JProgressBar();
  JProgressBar evolutionBar = new JProgressBar();

	int populationSize;
	int maxGenerations;
	int evolution = 0;
	int evaluation = 0;
	int generation = 0;

  public SimpleWatcher(String title) {
		super(title);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
		setVisible(true);
		setSize(800,300);
  }

  public void setPopulationSize(int s) {
		populationSize = s;
		evaluationBar.setMaximum(s);
		evolutionBar.setMaximum(s);
		evaluationBar.setString("0/"+s);
		evolutionBar.setString("0/"+s);
  }

	public void setMaxGenerations(int g) {
		maxGenerations = g;
		generationBar.setMaximum(g);
		generationBar.setString("0/"+g);
	}


  public void resetEvolutionProgress() {
		evolutionBar.setValue(0);
		evolutionBar.setString("0/"+populationSize);
		evolution = 0;
  }

  public void resetEvaluationProgress() {
		evaluationBar.setValue(0);
		evaluationBar.setString("0/"+populationSize);
		evaluation = 0;
  }

  public void bumpEvolutionProgress() {
		evolution++;
		evolutionBar.setValue(evolution);
		evolutionBar.setString(evolution+"/"+populationSize);
  }

  public void bumpEvaluationProgress() {
		evaluation++;
		evaluationBar.setValue(evaluation);
		evaluationBar.setString(evaluation+"/"+populationSize);
  }

  public void setGeneration(int g) {
		generation = g;
		generationBar.setValue(g);
		generationBar.setString(g+"/"+maxGenerations);
  }

  public void setBestFitness(float fitness) {
  }

  public void setEvaluationTime(long msec) {
  }

  private void jbInit() throws Exception {
    jPanel1.setLayout(null);
    jPanel2.setBorder(BorderFactory.createEtchedBorder());
    jPanel2.setBounds(new Rectangle(7, 11, 692, 87));
    jPanel2.setLayout(null);
    jPanel3.setBorder(BorderFactory.createEtchedBorder());
    jPanel3.setBounds(new Rectangle(7, 102, 692, 274));
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Generation");
    jLabel1.setBounds(new Rectangle(20, 9, 72, 15));
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Evaluation");
    jLabel2.setBounds(new Rectangle(20, 33, 72, 15));
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Evolution");
    jLabel3.setBounds(new Rectangle(22, 57, 70, 15));
    generationBar.setString("0/0");
    generationBar.setStringPainted(true);
    generationBar.setBounds(new Rectangle(108, 9, 571, 15));
    evaluationBar.setString("0/0");
    evaluationBar.setStringPainted(true);
    evaluationBar.setBounds(new Rectangle(108, 33, 571, 15));
    evolutionBar.setString("0/0");
    evolutionBar.setStringPainted(true);
    evolutionBar.setBounds(new Rectangle(108, 57, 571, 15));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel2, null);
    jPanel2.add(jLabel1, null);
    jPanel2.add(jLabel2, null);
    jPanel2.add(jLabel3, null);
    jPanel2.add(generationBar, null);
    jPanel2.add(evaluationBar, null);
    jPanel2.add(evolutionBar, null);
    jPanel1.add(jPanel3, null);
  }
}