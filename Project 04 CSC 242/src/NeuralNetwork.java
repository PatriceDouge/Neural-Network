import java.util.stream.IntStream;

/*NeuralNetwork class represents backpropagation and runs the network*/

public class NeuralNetwork {
	
	final static int NUM_OF_INPUT_NEURONS = Driver.TRAINING_DATA[0][0].length;
	final static int NUM_OF_OUTPUT_NEURONS = 1;
	private int numOfHiddenNeurons;
	private Layer[] layers = new Layer[Layer.LayerType.values().length];
	static final double LEARNING_RATE = 0.8;
	
	//constructor
	public NeuralNetwork(int numOfHiddenNeurons) {
		this.numOfHiddenNeurons = numOfHiddenNeurons;
		layers[0] = new Layer(this, Layer.LayerType.I);
		layers[1] = new Layer(this, Layer.LayerType.H);
		layers[2] = new Layer(this, Layer.LayerType.O);
	}
	
	//forward propagate algorithm
	public NeuralNetwork fwdProp(double input[]) {
		
		for(int i = 0; i < layers.length; i++) {
			switch(layers[i].getLayerType()) {
			 case I:
				 for(int j = 0; j < layers[i].getNeurons().length; j++) {
					layers[i].getNeurons()[j].setOutput(input[j]);
				 }
				 break;
			 case H: 
				 for(int j = 0; j < layers[i].getNeurons().length; j++) {
					 double weightedSum = 0;
					 for(int a = 0; a < layers[i].getNeurons()[0].getWeights().length; a++) {
						 weightedSum += layers[i].getNeurons()[j].getWeights()[a] * layers[i - 1].getNeurons()[a].getOutput();
					 }
					 layers[i].getNeurons()[j].applyActivationFunction(weightedSum);
				 }
				 break;
			 case O: 
				 double weightedSum = 0;
				 for(int a = 0; a < layers[i].getNeurons()[0].getWeights().length; a++) {
					 weightedSum += layers[i].getNeurons()[0].getWeights()[a] * layers[i - 1].getNeurons()[a].getOutput();
				 }
				 layers[i].getNeurons()[0].applyActivationFunction(weightedSum);
				 break;
			}
		}
		return this;
	}
	
	//backpropagate algorithm
	public NeuralNetwork bckPropError(double targetResult) {
		
		Neuron[] inputNeuron = layers[0].getNeurons();
		Neuron[] hiddenNeuron = layers[1].getNeurons();
		Neuron outputNeuron = layers[layers.length - 1].getNeurons()[0];
		
		outputNeuron.setError((targetResult - outputNeuron.getOutput()) * outputNeuron.derivative());
		for(int i = 0; i < outputNeuron.getWeights().length; i++) {
			outputNeuron.getWeights()[i] = outputNeuron.getWeights()[i] + LEARNING_RATE * outputNeuron.getError() * hiddenNeuron[i].getOutput();
		}
		for(int j = 0; j < hiddenNeuron.length; j++) {
			hiddenNeuron[j].setError((outputNeuron.getWeights()[j] * outputNeuron.getError()) * hiddenNeuron[j].derivative());
			for(int i = 0; i < hiddenNeuron[0].getWeights().length; i++) {
				hiddenNeuron[j].getWeights()[i] = hiddenNeuron[j].getWeights()[i] + LEARNING_RATE * hiddenNeuron[j].getError() * inputNeuron[i].getOutput();
			}
		}
		return this;
	}
	
	//get methods
	public int getNumOfHiddenNeurons() {
		return numOfHiddenNeurons;
	}
	
	public Layer[] getLayers() {
		return layers;
	}
	
	//toString
	public String toString() {
		StringBuffer returnValue = new StringBuffer();
		IntStream.range(0, layers.length).forEach(x -> returnValue.append(layers[x] + " "));
		return returnValue.toString();
	}

}
