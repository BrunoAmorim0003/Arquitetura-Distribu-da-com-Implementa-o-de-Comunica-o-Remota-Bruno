import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000); 

    BufferedReader Teclado = new BufferedReader(new InputStreamReader(System.in)); 
    PrintWriter Saida = new PrintWriter(socket.getOutputStream(), true); 
    BufferedReader Entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); //-Retorna um fluxo de entrada para esse soquete.-//

    System.out.print("Digite uma mensagem: "); 
    String msg = Teclado.readLine(); 
    Saida.println(msg); 

    String resposta = Entrada.readLine(); 
    System.out.println("Resposta do servidor: " + resposta); 
    socket.close();

    }
}
    