require 'numo/narray'
require 'gnuplot'

# Retrieve variables from Java
dataset_path = datasetPath
output_image = outputImage
plot_color = plotColor

# Load dataset (assuming CSV with two columns: x, y)
data = File.read(dataset_path).split("\n").map { |line| line.split(',').map(&:to_f) }
x = Numo::DFloat[*data.map(&:first)]
y = Numo::DFloat[*data.map(&:last)]

# Compute linear regression coefficients
x_mean, y_mean = x.mean, y.mean
slope = (x * y).mean - x_mean * y_mean
slope /= (x**2).mean - x_mean**2
intercept = y_mean - slope * x_mean

# Predicted values
y_pred = slope * x + intercept

# Generate plot
Gnuplot.open do |gp|
  Gnuplot::Plot.new(gp) do |plot|
    plot.terminal 'png'
    plot.output output_image
    plot.title 'Linear Regression'
    plot.xlabel 'X'
    plot.ylabel 'Y'

    plot.data << Gnuplot::DataSet.new([x.to_a, y.to_a]) do |ds|
      ds.with = 'points'
      ds.title = 'Data'
      ds.linecolor = 'black'
    end

    plot.data << Gnuplot::DataSet.new([x.to_a, y_pred.to_a]) do |ds|
      ds.with = 'lines'
      ds.title = 'Regression Line'
      ds.linecolor = plot_color
    end
  end
end

puts "Plot saved as #{output_image}"