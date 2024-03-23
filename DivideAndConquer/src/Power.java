package DivideAndConquer.src;

public class Power {
    static int pwr(int x, int y) {
        // Cas de base : y = 1 ou 0
        if(y<2) {
            return x;
        }
        
        int a = y / 2;
        int b = y - a;
        return (pwr(x,a) * pwr(x,b));

    }
}
