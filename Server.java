import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Server{
    
    private static final Set<Lista_Clientes> Clientes = ConcurrentHashMap.newKeySet();
    public static void main(String[] args) throws IOException{
        System.out.println("Conexão com Servidor estabelecida na porta " + 5000 + "...");
        
        try(ServerSocket SocketServer = new ServerSocket(5000);) {
                while (true) {
                    Socket socket = SocketServer.accept();
                    Lista_Clientes Cliente = new Lista_Clientes(socket);
                    Clientes.add(Cliente);
                    new Thread(Cliente).start();
                }
            
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
}

    public static void Broadcast(String mensagem, Lista_Clientes sender) {
        for (Lista_Clientes Cliente : Clientes) {
            if (Cliente != sender) {
                Cliente.enviar(mensagem);
            }
        }
    }

    public static void remover_cliente(Lista_Clientes Cliente) {
        Clientes.remove(Cliente);
        }


    static class Lista_Clientes implements Runnable{
        private final Socket socket;
        private PrintWriter saida;
        private String nickname = "Anônimo";

        Lista_Clientes(Socket socket) {
            this.socket = socket;
        }


        public void enviar(String mensagem) {
            if (saida != null) {
                saida.println(mensagem);
            }
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
             System.out.println("Cliente conectado: " + socket.getRemoteSocketAddress());

             try(BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));){

                saida = new PrintWriter(socket.getOutputStream(), true);

                saida.println("Bem-vindo! Digite seu nickname:");
                String nick = entrada.readLine();
                if (nick != null && !nick.isBlank()) {
                    nickname = nick.trim();
                }
                saida.println("Olá, " + nickname + "! Você entrou no chat.");
                Broadcast("[Sistema] " + nickname + " entrou no chat.", this);

                String entrada_usuario;
                while ((entrada_usuario = entrada.readLine()) != null) {
                    if (entrada_usuario.equalsIgnoreCase("/sair")) {
                        saida.println("Você saiu do chat. Até mais!");
                        break;
                    }
                    String msg = nickname + ": " + entrada_usuario;
                    System.out.println("Mensagem recebida: " + msg);
                    saida.println("(Você) " + entrada_usuario);
                    Server.Broadcast(msg, this);
                }
             } catch (IOException e) {
                System.err.println("Erro com cliente " + nickname + ": " + e.getMessage());
             } finally {
                try { socket.close(); } catch (IOException ignored) {}
                Server.remover_cliente(this);
                Server.Broadcast("[Sistema] " + nickname + " saiu do chat.", this);
                System.out.println("Cliente desconectado: " + nickname);
            }
        

        /*(while(true){

            /*System.out.println("Cliente conectado: " + Cliente.getInetAddress());  //-Retorna o endereço ao qual o soquete está conectado-//
            BufferedReader Entrada = new BufferedReader(new InputStreamReader(Cliente.getInputStream())); 
            PrintWriter Saida = new PrintWriter(Cliente.getOutputStream(), true); 

            String Mensagem = Entrada.readLine(); 
            System.out.println("Recebido: " + Mensagem); 
            
            Saida.println("Servidor recebeu: " + Mensagem); 
            Cliente.close();
        }*/

    
        }
    }
}