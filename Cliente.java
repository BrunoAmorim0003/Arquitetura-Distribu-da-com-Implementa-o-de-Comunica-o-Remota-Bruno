import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Cliente {
        public static void main(String[] args) throws IOException {
        System.out.println("Conectando ao chat " + "localhost" + ":" + 5000 + "...");
        try (Socket socket = new Socket("localhost", 5000);
            BufferedReader Entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); //-Retorna um fluxo de entrada para esse soquete.-//
            PrintWriter Saida = new PrintWriter(socket.getOutputStream(), true); 
            BufferedReader Teclado = new BufferedReader(new InputStreamReader(System.in))) {

            //Thread para ler mensagens do servidor//
            Thread msg_server = new Thread(() -> { //Thread(ThreadGroup group, Runnable target)//
                try {
                    String msg;
                    while ((msg = Entrada.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("Conexão encerrada.");
                }
            });
            msg_server.setDaemon(true);//Finalização total//
            msg_server.start();

            //Entrada do usuário e envio//
            String entrada_usuario;
            while ((entrada_usuario = Teclado.readLine()) != null) {
                Saida.println(entrada_usuario);
                if (entrada_usuario.equalsIgnoreCase("/sair")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        }
        System.out.println("Cliente finalizado.");
        
    }
}

    