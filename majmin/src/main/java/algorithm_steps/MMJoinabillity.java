package algorithm_steps;


public class MMJoinabillity implements AlgorithmStep{
    State curState; //state contains best pairings
    int minThreshold;
    int maxThreshold;
    Set<Cluster> clusters;
    Joinabillity joiner;
    Race minority;


    public MMJoinabillity(){
	this.curState = algorithmProperties.getState();
	this.minThreshold = algorithmProperties.minorityVotingThreshold();
	this.maxThreshold = algorithmProperties.majorityVotingThreshold();
	this.clusters = curState.getClusters();
	this.joiner = new Joinabillity();
	this.minority = algorithmProperties.getMinority(); // this is just 1
    }

    public boolean run(){
	Iterator<Clusters> clusterIter = clusters.iterator();

	while(clusterIter.hasNext()){
	    Cluster curCluster = clusterIter.next();
	    float joinabillity = joiner.calculateMinMajJoinabillity(curCluster, minThreshold, maxThreshold, this.minority);
	    curCluster.setJoinabillity(joinabillity);
	}
	
      	return true;
    }

    public AlgorithmStepStatus getStatus(){
	//this is where we send status
	return null;
    }
    
    public void pause(){
	//will do later
    }

    public Result onCompletion(){
	return null;
    }
    
    
}
