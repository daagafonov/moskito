package net.java.dev.moskito.core.helper;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.treshold.Threshold;

/**
 * Wrapper to allow tieables to be tied to on demand producers. The problem with OnDemandProducers is that at the moment 
 * of the registration the stats you later may want to tie to doesn't exists. Therefore the AutoTieWrapper
 * listens to interval update events, and checks whether the appropriate stat object has been created. If so, it will tie the target
 * to the stats object and do nothing from than on.
 * @author lrosenberg
 *
 */
public class AutoTieWrapper implements IntervalUpdateable{
	
	private Tieable tieable;
	private IStatsProducer producer;
	
	public AutoTieWrapper(Tieable aTieable, IStatsProducer aProducer){
		tieable = aTieable;
		producer  = aProducer;
	}
	
	public void update(){
		if (tieable.isActivated()){
			return;
		}
		
		for (IStats s : producer.getStats()){
			if (s.getName().equals(tieable.getTargetStatName())){
				tieable.tieToStats(s);
				tieable.update();
				return;
			}
		}
	}
}