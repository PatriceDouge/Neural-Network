import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class Driver {
	
	static int NUM_OF_EPOCHS = 1000;
	//XOR training data
	static double TRAINING_DATA[][][] = new double[][][]	{{{1,0,0},{0}},
															{{1,0,1},{1}},
															{{1,1,0},{1}},
															{{1,1,1},{0}}};

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the number of neurons in the hidden layer: ");
		NeuralNetwork neuralNetwork = new NeuralNetwork(Integer.parseInt(bf.readLine()));
		boolean flag = true;
		while(flag) {
			
			System.out.println("What would you like to do?(test, train, exit)");
			String ans = bf.readLine();
			switch(ans) {
			case "test":
				double[] result = new double[Driver.TRAINING_DATA.length];
				IntStream.range(0, Driver.TRAINING_DATA.length).forEach(i -> result[i] = neuralNetwork.fwdProp(Driver.TRAINING_DATA[i][0]).getLayers()[2].getNeurons()[0].getOutput());
				printResult(result);
				break;
			case "train":
				IntStream.range(0, NUM_OF_EPOCHS).forEach(i -> {
					System.out.println("Epoch: " + i);
					IntStream.range(0, TRAINING_DATA.length).forEach(j -> System.out.println(neuralNetwork.fwdProp(Driver.TRAINING_DATA[j][0]).bckPropError(Driver.TRAINING_DATA[j][1][0])));
				});
				System.out.println("Training Complete");
				break;
			case "exit":
				flag = false;
				break;
			}
		}
		System.exit(0);
		
	}
	
	public static void printResult(double[] result) {
		
		IntStream.range(0, TRAINING_DATA[0][0].length).forEach(x -> System.out.print(" Input " + x + " |"));
		System.out.println(" Target Result | Result ");
		IntStream.range(0, TRAINING_DATA[0][0].length).forEach(x -> System.out.print(" ------------------ "));
		System.out.println(" -------------------------- ");
		IntStream.range(0, TRAINING_DATA.length).forEach(i -> {
			IntStream.range(0, TRAINING_DATA[0][0].length).forEach(j -> System.out.print("  " + TRAINING_DATA[i][0][j] + "   |  "));
			System.out.print("  " + TRAINING_DATA[i][1][0] + "   |      " + String.format("%.5f", result[i]) + " \n");
		});
		
	}

}
