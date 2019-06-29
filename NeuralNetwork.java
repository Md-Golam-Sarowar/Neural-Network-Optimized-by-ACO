package aco.sojol.neuralnet;

/**
*
* @author Golam Sarowar
*/
public class NeuralNetwork {
/* 2-dimensional array of neurons that represent the neural network*/
Neuron[][] neuralNet;
int count, value = 0;
int columns; /*number of columns including input layer*/
int maxRows; /*maximum number of rows in any column*/
int[] nodesPerLayer; /*number of nodes in each layer*/
int numIns;
int numOuts;
int numWeights=0;
double[][] dataSet;
NeuralNetwork(int numLayers, int mRows, int[] numPerLayer) {
this.maxRows = mRows;
this.columns = numLayers;
this.nodesPerLayer = numPerLayer;
this.neuralNet = new Neuron[columns][maxRows];
numIns = nodesPerLayer[0];
numOuts = nodesPerLayer[numLayers-1];
for (int i=1;i<columns;i++)
numWeights = numWeights + (nodesPerLayer[i]*nodesPerLayer[i-1]);
initNeurons();
}
protected void initNeurons() {
int i,j;
for(i=0;i<numIns;i++)
neuralNet[0][i] = new Neuron(0);
for (i=1;i<columns;i++)
for (j=0;j<nodesPerLayer[i];j++)
neuralNet[i][j] = new Neuron(nodesPerLayer[i-1]);
}
public void setWeights(double[] weights) {
int x=0;
if(weights.length != numWeights) {
System.out.println("mismatch in number of weights");
System.exit(1);
}
for (int i=1;i<columns;i++)
for (int j=0;j<nodesPerLayer[i];j++)
for (int k=0;k<neuralNet[i][j].numWeights;k++)
neuralNet[i][j].weights[k] = weights[x++];
}
public void assignDataSet(double[][] data_set) {
this.dataSet = data_set;
}

protected double[] computeOutput(double[] inputs) {
double sum;
int i,j,k;
double[] output = new double[nodesPerLayer[columns-1]];
/*Input Layer*/
for (i=0;i<nodesPerLayer[0];i++)
neuralNet[0][i].output = inputs[i];
/*Hidden Layers*/
for(i=1;i<columns-1;i++)
for (j=0;j<nodesPerLayer[i];j++) {
sum = 0.0;
for(k=0;k<nodesPerLayer[i-1];k++)
sum += neuralNet[i][j].weights[k]*neuralNet[i-1][k].output;
neuralNet[i][j].output = 1.0/(1.0 + Math.exp(-sum));
}
/*output Layer*/
for (i=0;i<nodesPerLayer[columns-1];i++) {
sum=0.0;
for (j=0;j<nodesPerLayer[columns-2];j++)
sum += neuralNet[columns-1][i].weights[j]
*neuralNet[columns-2][j].output;
output[i] =
neuralNet[columns-1][i].output = 1.0/(1.0+Math.exp(-sum));
}
return output;
}
public double computeError(boolean training) {
int i,j;
double[] input = new double[nodesPerLayer[0]];
double[] output = new double[nodesPerLayer[columns-1]];
double desiredOutput[] = new double[nodesPerLayer[columns-1]];
double totalError = 0.0;
double temp;

for (i=0;i<dataSet.length;i++) {
for (j=0;j<numIns;j++)
input[j] = dataSet[i][j];
for (j=0;j<numOuts;j++)
desiredOutput[j] = dataSet[i][numIns+j];
output = computeOutput(input);
if (!training) {
temp = (((output[0]-0.05)/0.9)*2) + 1;
//System.out.println(Math.round(temp));
}

for (j=0;j<numOuts;j++) {
double error = output[j] - desiredOutput[j];
if(training==false) {
	System.out.println(output[j]+"     "+desiredOutput[j]);
	if(Math.abs(output[j]-desiredOutput[j])<=0.015)
	{
		count++;
		value = numOuts;
	}
}
totalError += error*error;
}
}

if(training==false)
{
	Double d = new Double(count);
System.out.println("Depicted Accuracy : "+" "+ (d/15)*100+"%");
}

return totalError;
}
}