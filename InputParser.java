package aco.sojol.neuralnet;

import java.io.FileReader;

import javax.print.attribute.standard.PrinterLocation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
/**
*
* @author Golam Sarowar
*/
public class InputParser {
BufferedReader file;
int numInput;
int numOutput;
int numCases;
double[][] trainingCases;
double[] output;
double[][] extrema;
InputParser(String fileName, int numIns, int numOuts, int numLines) {
this.numInput = numIns;
this.numOutput = numOuts;
this.numCases = numLines;
trainingCases = new double[numCases][numInput+numOutput];
extrema = new double[numIns+numOuts][2];
for (int i=0;i<(numIns+numOuts);i++) {
extrema[i][0] = 10000.0;
extrema[i][1] = -10000.0;
}
try {
file = new BufferedReader(new FileReader(fileName));
}
catch (FileNotFoundException ex) {
System.out.println("Invalid input file!");
}
}
public void readInput() {
try {
for (int i=0;i<numCases;i++) {
String[] line = (file.readLine().split("\\s+"));
//String[] line = (file.readLine().split(","));
for(int j=0;j<(numInput+numOutput);j++) {
trainingCases[i][j] = Double.parseDouble(line[j]);
//System.out.println(trainingCases[i][j]);

if (trainingCases[i][j] < extrema[j][0])
extrema[j][0] = trainingCases[i][j];

if (trainingCases[i][j] > extrema[j][1])
extrema[j][1] = trainingCases[i][j];

}

}

//test whether both extrema are equal
for (int i=0; i < (numInput+numOutput); i++)
if (extrema[i][0] == extrema[i][1])
extrema[i][1]=extrema[i][0]+1;
//System.out.println("*************Extrema values*************");
//for (int i=0; i<(numInput + numOutput); i++)
//System.out.println(extrema[i][0] + " " + extrema[i][1]);
//System.out.println("*************End*************");
} catch (Exception ex) {
ex.printStackTrace();
System.out.println("Error while reading file!");
System.exit(0);
}
}
public void printOutput() {
for (int i=0;i<numCases;i++) {
for (int j=0;j<(numInput+numOutput);j++)
//System.out.print(trainingCases[i][j] + " ");
System.out.println("");
}
}
/*******************************************
Scale Desired Output to 0..1
*******************************************/
double[][] scaleDown() {
double[][] scaledDownInput;
int i,j;
scaledDownInput = new double[numCases][numInput+numOutput];
for (i=0;i<numCases;i++)
for (j=0;j<numInput+numOutput;j++) {
scaledDownInput[i][j] =
.9*(trainingCases[i][j]-extrema[j][0])/
(extrema[j][1]-extrema[j][0])+.05;
}
return scaledDownInput;
}
/*******************************************
Scale actual output to original range
*******************************************/
double scaleOutput(double X, int which) {
double range = extrema[which][1] - extrema[which][0];
double scaleUp = ((X-.05)/.9) * range;
return (extrema[which][0] + scaleUp);
}
}