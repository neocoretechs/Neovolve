package com.neocoretechs.neovolve;

import java.util.EventListener;

public interface GPListener extends EventListener {
  void setPopulationSize(int s);
	void setMaxGenerations(int g);
  void resetEvolutionProgress();
  void resetEvaluationProgress();
  void bumpEvolutionProgress();
  void bumpEvaluationProgress();
  void setGeneration(int g);
	void setBestFitness(float fitness);
	void setEvaluationTime(long msec);
}