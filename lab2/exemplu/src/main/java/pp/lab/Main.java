package pp.lab;

import java.io.*;
import org.graalvm.polyglot.*;

public class Main{
    public static void main(String[] args) {
        try {
            // Read dataset file path
            String datasetPath = "dataset.txt";  // Example file path

            // Read user input for output file
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter output image name (e.g., plot.png): ");
            String outputImage = reader.readLine();
            System.out.print("Enter plot color (e.g., 'blue'): ");
            String plotColor = reader.readLine();

            // Run R for linear regression and plotting
            try (Context rContext = Context.create("ruby")) {
                rContext.getBindings("ruby").putMember("datasetPath", datasetPath);
                rContext.getBindings("ruby").putMember("outputImage", outputImage);
                rContext.getBindings("ruby").putMember("plotColor", plotColor);

                //String script = "require './regression.rb'";  // Assuming regression.rb is in the same directory
                rContext.eval("ruby",
                        "require 'numo/narray'\n" +
                                "require 'gruff'\n" +
                                "\n" +
                                "# Retrieve Java variables\n" +
                                "dataset_path = datasetPath\n" +
                                "output_image = outputImage\n" +
                                "plot_color = plotColor\n" +
                                "\n" +
                                "# Load dataset (CSV with x, y)\n" +
                                "data = File.read(dataset_path).split(\"\\n\").map { |line| line.split(',').map(&:to_f) }\n" +
                                "x = Numo::DFloat[*data.map(&:first)]\n" +
                                "y = Numo::DFloat[*data.map(&:last)]\n" +
                                "\n" +
                                "# Compute linear regression\n" +
                                "x_mean, y_mean = x.mean, y.mean\n" +
                                "slope = (x * y).mean - x_mean * y_mean\n" +
                                "slope /= (x**2).mean - x_mean**2\n" +
                                "intercept = y_mean - slope * x_mean\n" +
                                "\n" +
                                "# Predicted values\n" +
                                "y_pred = slope * x + intercept\n" +
                                "\n" +
                                "# Create plot\n" +
                                "g = Gruff::Scatter.new\n" +
                                "g.title = 'Linear Regression'\n" +
                                "g.data(:Data, x.to_a, y.to_a)\n" +
                                "g.data(:Regression, x.to_a, y_pred.to_a, plot_color.to_sym)\n" +
                                "\n" +
                                "g.write(output_image)\n" +
                                "puts \"Plot saved as #{output_image}\"");

                System.out.println("Plot saved as " + outputImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}