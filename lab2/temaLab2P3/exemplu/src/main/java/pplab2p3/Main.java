package pplab2p3;
import org.graalvm.polyglot.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try (Context context = Context.newBuilder()
                .allowAllAccess(true)
                .build()) {

            // Citirea numerelor din Python
            String pythonScript = """
                import sys
                from io import StringIO

                # Capturam output-ul standard
                old_stdout = sys.stdout
                sys.stdout = StringIO()

                # Citire de la utilizator
                n = int(input())
                x = int(input())

                # Scriem rezultatul in sys.stdout
                print(f"{n} {x}")

                # Returnam rezultatul capturat
                output = sys.stdout.getvalue().strip()
                sys.stdout = old_stdout  # Restauram stdout
                output
                """;

            Value result = context.eval("python", pythonScript);
            String[] values = result.asString().split(" ");
            int n = Integer.parseInt(values[0]);
            int x = Integer.parseInt(values[1]);

            // Ruby pentru calcul probabilitate
            String rubyScript = String.format("""
                                                ENV['GEM_PATH'] = `gem environment gemdir`.strip
                                                require 'distribution'
                                                n = %d
                                                x = %d
                                                probability = Distribution::Binomial.cdf(x, n, 0.5)
                                                puts "Probabilitatea de a obtine cel mult %d ori pajura este: #{probability}"
                                              """, n, x, x);
            context.eval("ruby", rubyScript);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
