package pplab2p2;

//import org.graalvm.polyglot.Context;
import java.io.*;
import org.graalvm.polyglot.*;
import java.util.Set;

public class Main{
    public static void main(String[] args) {

                try (Context context = Context.newBuilder()
                        .allowAllAccess(true)
                        .build()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Path si Nume imagine(e.g /home/student/p.png): ");
                    String outputImgName = reader.readLine();
                    System.out.println("Culoare plot(e.g blue): ");
                    String color = reader.readLine();

                    // Ruby script for linear regression
                    String rubyScript = """
                    ENV['GEM_PATH'] = `gem environment gemdir`.strip
                    require 'gnuplot'

                    # Citesc datele din fisier
                    x_values = []
                    y_values = []
                    File.open('date.txt', 'r') do |file|
                      file.each_line do |line|
                        x, y = line.split.map(&:to_f)
                        x_values << x
                        y_values << y
                      end
                    end

                    # regresie liniara
                    n = x_values.size
                    sum_x = x_values.sum
                    sum_y = y_values.sum
                    sum_xx = x_values.map { |x| x**2 }.sum
                    sum_xy = x_values.zip(y_values).map { |x, y| x * y }.sum

                    slope = (n * sum_xy - sum_x * sum_y) / (n * sum_xx - sum_x**2)
                    intercept = (sum_y - slope * sum_x) / n

                    # prezicere valori
                    y_predicted = x_values.map { |x| slope * x + intercept }

                    # plot
                    Gnuplot.open do |gp|
                      Gnuplot::Plot.new(gp) do |plot|
                        plot.terminal 'png'
                        plot.output '%s'
                        plot.title 'Regresie Liniara'
                        plot.xlabel 'X'
                        plot.ylabel 'Y'

                        data_color = '%s'
                        line_color = '%s'

                        plot.data << Gnuplot::DataSet.new([x_values, y_values]) do |ds|
                          ds.with = "points pointtype 7 linecolor rgb '#{data_color}'"
                          ds.title = 'Data Points'
                        end

                        plot.data << Gnuplot::DataSet.new([x_values, y_predicted]) do |ds|
                          ds.with = "lines linewidth 2 linecolor rgb '#{line_color}'"
                          ds.title = 'Regression Line'
                        end
                      end
                    end
                    puts 'Regression plot saved as %s'
                    """;
                    // inlocuire %s cu variabilele string
                    rubyScript = String.format(rubyScript, outputImgName, color, color, outputImgName);

                    // rulare cod
                    context.eval("ruby", rubyScript);
        } catch (IOException e) {
                    e.printStackTrace();
        }
    }
}