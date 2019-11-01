import java.util.stream.IntStream;

/*Layer class represents a layer in the network*/

public class Layer {

	static enum LayerType {I, H, O}; //three types of layers: Input, Hidden and Output
	private Neuron[] neurons = null;
	private LayerType layerType;
	
	//layer constuctor
	public Layer(NeuralNetwork neuralNetwork, LayerType layerType) {
		this.layerType = layerType;
		switch (layerType) {
		case I:
			neurons = new Neuron[NeuralNetwork.NUM_OF_INPUT_NEURONS];
			IntStream.range(0, NeuralNetwork.NUM_OF_INPUT_NEURONS).forEach(i -> neurons[i] = new Neuron(layerType,0));
			break;
		case H: 
			neurons = new Neuron[neuralNetwork.getNumOfHiddenNeurons()];
			IntStream.range(0, neuralNetwork.getNumOfHiddenNeurons()).forEach(i -> neurons[i] = new Neuron(layerType, NeuralNetwork.NUM_OF_INPUT_NEURONS));
			break;
		case O: 
			neurons = new Neuron[NeuralNetwork.NUM_OF_OUTPUT_NEURONS];
			neurons[0] = new Neuron(layerType, neuralNetwork.getNumOfHiddenNeurons());
			break;
		}
	}
	
	//get methods
	public Neuron[] getNeurons() {
		return neurons;
	}
	
	public LayerType getLayerType() {
		return layerType;
	}
	
	//toString
	public String toString() {
		StringBuffer returnValue = new StringBuffer();
		IntStream.range(0, neurons.length).forEach(x -> returnValue.append(neurons[x] + " "));
		return returnValue.toString();
	}
}
