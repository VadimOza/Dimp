package sample;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.core.converters.CSVLoader;
import weka.filters.unsupervised.attribute.Remove;

import java.util.*;
import java.io.*;

public class WekaService {

    private Map<String, Integer> desidion = new HashMap<>();


    public WekaService() {

    }

    public Optional<String> train(File toTrain, File toTest) throws Exception {
        desidion.clear();
        CSVLoader loader = new CSVLoader();
        loader.setSource(toTrain);
        Instances train = loader.getDataSet();
        train.setClassIndex(train.numAttributes() - 1);

        loader.setSource(toTest);
        Instances test = loader.getDataSet();
        test.setClassIndex(train.numAttributes() - 1);

        test.setClass(train.classAttribute());


        // create copy
        Instances labeled = new Instances(test);


        Classifier cls = new J48();
        cls.buildClassifier(train);

        // label instances
        for (int i = 0; i < test.numInstances(); i++) {
            double clsLabel = cls.classifyInstance(test.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
            String predClass = test.classAttribute().value((int) clsLabel);
            System.out.println(predClass);
            incDes(predClass);
        }

        return desidion.entrySet().stream()
                .sorted((o1, o2) -> -o1.getValue().compareTo(o2.getValue()))
                .map(stringIntegerEntry -> stringIntegerEntry.getKey()).findFirst();
    }

    private void incDes(String value) {
        Integer key = desidion.get(value);
        if (key == null)
            key = 0;
        else
            key += 1;
        desidion.put(value, key);
    }
}
