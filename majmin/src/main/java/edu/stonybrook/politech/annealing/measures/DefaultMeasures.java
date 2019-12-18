package edu.stonybrook.politech.annealing.measures;

import edu.stonybrook.politech.annealing.algorithm.Measure;
import edu.stonybrook.politech.annealing.algorithm.MeasureFunction;
import edu.stonybrook.politech.annealing.models.concrete.District;
import edu.stonybrook.politech.annealing.models.concrete.Precinct;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class DefaultMeasures implements MeasureFunction<Precinct, District> {

    // a map that gives a function to generate the "weights" for a given measure, usually just a constant function f(District) = C.
    private Map<Measure, Function<District, Double>> weightGenerator;

    // an activation function that is run, piecewise on each measure cost, before the weight is applied.
    // this is typically x-> 1-(1-x)^2, in accordance with Matt's original code.
    private Function<Double,Double> defaultActivationFunction;


    // creates a DefaultMeasures object with the given set of Measure,Weight tuples and vanilla activation function.
    public static DefaultMeasures defaultMeasuresWithWeights(Map<Measure, Double> weights) {
        DefaultMeasures newMeasureSet = new DefaultMeasures(new HashMap<>());
        newMeasureSet.updateConstantWeights(weights);
        return newMeasureSet;
    }
    public void updateConstantWeights(Map<Measure,Double> weights) {
        this.weightGenerator = new HashMap<>();
        for (Measure measure : weights.keySet()) {
            this.weightGenerator.put(measure, (district) -> weights.get(measure));
        }
    }

    public DefaultMeasures(Map<Measure, Function<District, Double>> weightGenerator,
                           Function<Double, Double> defaultActivationFunction) {
        this.weightGenerator = weightGenerator;
        this.defaultActivationFunction = defaultActivationFunction;
    }
    public DefaultMeasures(Map<Measure, Function<District, Double>> weightedActivation) {
        this(weightedActivation, (x) -> 1 - Math.pow((1 - x), 2));
    }


    @Override
    public double calculateMeasure(District district) {
        double sum = 0;
        for (Map.Entry<Measure, Function<District,Double>> entry: weightGenerator.entrySet()) {
            Measure measure = entry.getKey();
            //the weight is applied as a seperate factor
            double weight = entry.getValue().apply(district);
            //calculate the measure, run it through the activation function, multiply it by the weight.
            sum += defaultActivationFunction.apply(measure.calculateMeasure(district)) *
                    weight;
        }
        return sum;
    }


    @Override
    public Set<MeasureFunction<Precinct, District>> subMeasures() {
        return (Set) weightGenerator.keySet();
    }
}
