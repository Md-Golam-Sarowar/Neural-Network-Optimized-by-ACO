package aco.sojol.neuralnet;

import aco.sojol.neuralnet.*;
/**
*
* @author Golam Sarowar
*/
public class Main {
/**
* @param args the command line arguments
*/
public static void main(String[] args) {
double[][] trainingData, testData;
int numInputs = 83;
int numOutputs = 1;
int numTrainCases = 98;
int numTestCases = 15;
int repoSize = 20; //size of the repository
String trainingFilePath = "C:\\Users\\~SOJOL\\eclipse-workspace\\ACO_Optimized_NN\\src\\aco\\sojol\\neuralnet\\seeds_training.txt";
String testFilePath = "C:\\Users\\~SOJOL\\eclipse-workspace\\ACO_Optimized_NN\\src\\aco\\sojol\\neuralnet\\seeds_testing.txt";


InputParser training_cases =
new InputParser(trainingFilePath, numInputs, numOutputs,numTrainCases);
InputParser test_cases =
new InputParser(testFilePath, numInputs, numOutputs,numTestCases);
training_cases.readInput();
test_cases.readInput();


trainingData = training_cases.scaleDown();
testData = test_cases.scaleDown();
System.out.println("Scaled down test data:\n");
for (int i=0;i<numTrainCases;i++) {
for (int j=0;j<numInputs+numOutputs;j++)
System.out.print(trainingData[i][j] + " ");
System.out.println("");
}
System.out.println("");
System.out.println("End: Test data:");
int[] numPerLayer = new int[3];
numPerLayer[0] = numInputs;
numPerLayer[1] = numInputs;
numPerLayer[2] = numOutputs;
NeuralNetwork nn = new NeuralNetwork(3, numInputs, numPerLayer);
nn.assignDataSet(
trainingData);
AcoFramework AF = new AcoFramework(nn, repoSize);
if (AF.trainNeuralNet()) {
System.out.println("******Test Output*****");
nn.assignDataSet(testData);
AF.testNeuralNet();
}
else {
System.exit(0);
}
}
}