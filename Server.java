import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Server{
    public static void main(String[] args) throws IOException{

        ServerSocket SocketServer = new ServerSocket(5000);
        System.out.println("Conexão com Servidor estabelecida...");

        while(true){

            Socket Cliente = SocketServer.accept();
            System.out.println("Cliente conectado: " + Cliente.getInetAddress());  //-Retorna o endereço ao qual o soquete está conectado-//
            BufferedReader Entrada = new BufferedReader(new InputStreamReader(Cliente.getInputStream())); 
            PrintWriter Saida = new PrintWriter(Cliente.getOutputStream(), true); 

            String Mensagem = Entrada.readLine(); 
            System.out.println("Recebido: " + Mensagem); 
            
            Saida.println("Servidor recebeu: " + Mensagem); 
            Cliente.close();
        }

    }
    
}