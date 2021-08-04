package com.example.studiosol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String URL_NUMERO_ALEATORIO = "https://us-central1-ss-devops.cloudfunctions.net/rand?min=1&max=300";
    private int numeroAleatorioRecebido, numeroInformadoUsuario, numeroValido;
    /*Variável "numeroValido" utilizada para evitar um erro na troca de cor do display
    * em que, caso o usuário digite um número maior que 300 (que é salvo na variável
    * "numeroInformadoUsuario") e tenta trocar de cor, o display atualiza com o número incorreto
     */
    private Display display1, display2, display3;
    private Button novaPartida, enviar;
    private TextView resposta;
    private EditText palpite;

    //Segmentos do display, criados como 'botões' para facilitar a implementação
    private ImageView a1, a2, a3, b1, b2, b3, c1, c2, c3, d1, d2, d3, e1, e2, e3, f1, f2, f3, g1, g2, g3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conectando o layout ao código
        enviar = findViewById(R.id.btEnviar);
        palpite = findViewById(R.id.edPalpite);
        resposta = findViewById(R.id.tvResposta);
        novaPartida = findViewById(R.id.btNovaPartida);

        //Conectando todos os segmentos para manipulação
        a1 = findViewById(R.id.a1);
        b1 = findViewById(R.id.b1);
        c1 = findViewById(R.id.c1);
        d1 = findViewById(R.id.d1);
        e1 = findViewById(R.id.e1);
        f1 = findViewById(R.id.f1);
        g1 = findViewById(R.id.g1);
        a2 = findViewById(R.id.a2);
        b2 = findViewById(R.id.b2);
        c2 = findViewById(R.id.c2);
        d2 = findViewById(R.id.d2);
        e2 = findViewById(R.id.e2);
        f2 = findViewById(R.id.f2);
        g2 = findViewById(R.id.g2);
        a3 = findViewById(R.id.a3);
        b3 = findViewById(R.id.b3);
        c3 = findViewById(R.id.c3);
        d3 = findViewById(R.id.d3);
        e3 = findViewById(R.id.e3);
        f3 = findViewById(R.id.f3);
        g3 = findViewById(R.id.g3);

        //Unindo os segmentos em 3 displays individuais
        display1 = new Display(a1,b1,c1,d1,e1,f1,g1);
        display2 = new Display(a2,b2,c2,d2,e2,f2,g2);
        display3 = new Display(a3,b3,c3,d3,e3,f3,g3);

        //Ocultando o botão de novo partida
        novaPartida.setVisibility(View.INVISIBLE);

        //Startando o jogo
        novoJogo();

        //Evento de clique no botão enviar
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Caso o valor aleatório recebido seja 502, significa erro,
                * ativando o botão de 'Nova Partida' e desabilitando o botao de 'Enviar'
                 */
                if (numeroAleatorioRecebido == 502) {
                    resposta.setText("Erro");
                    novaPartida.setVisibility(View.VISIBLE);
                    enviar.setEnabled(false);
                }
                //Caso o palpite informado não seja nulo, vazio ou igual a zeros, faça:
                else if (palpite.getText() != null &&
                        !palpite.getText().toString().equals("") &&
                        !palpite.getText().toString().equals("0") &&
                        !palpite.getText().toString().equals("00") &&
                        !palpite.getText().toString().equals("000")) {

                    //A variável numeroInformado recebe o valor do palpite
                    numeroInformadoUsuario = Integer.parseInt(palpite.getText().toString());

                    //Caso seja maior que 300 deve ser informado um novo número
                    if (numeroInformadoUsuario > 300) {

                        //Toast informando o usuário que o valor máximo é 300
                        Toast.makeText(getApplicationContext(),
                                "Insira um valor menor ou igual a 300.",
                                Toast.LENGTH_SHORT).show();
                    }

                    /*Caso o valor do palpite seja menor que 300,
                    * mas maior que o valor aleatório recebido,
                    * a mensagem e o valor são mostrados.
                     */
                    else if (numeroInformadoUsuario > numeroAleatorioRecebido) {
                        resposta.setText("É menor");
                        display(numeroInformadoUsuario);
                        numeroValido = numeroInformadoUsuario;
                    }

                    /*Caso sejam iguais, o botão de 'Nova Partida' é mostrado,
                    * o botão de 'Enviar' é desabilitado, a mensagem e o valor são mostrados.
                     */
                    else if (numeroInformadoUsuario == numeroAleatorioRecebido) {
                        novaPartida.setVisibility(View.VISIBLE);
                        enviar.setEnabled(false);
                        resposta.setText("Acertou!");
                        display(numeroInformadoUsuario);
                        numeroValido = numeroInformadoUsuario;
                    }

                    /*Caso o valor informado seja menor,
                    * a mensagem e o valor são mostrados.
                     */
                    else {
                        resposta.setText("É maior");
                        display(numeroInformadoUsuario);
                        numeroValido = numeroInformadoUsuario;
                    }
                }
                //O EditText é limpo a cada nova tentativa
                palpite.setText("");
            }
        });

        //Evento de clique do botão Nova Partida
        novaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaPartida.setVisibility(View.INVISIBLE);
                novoJogo();
            }
        });
    }

    //Função para criar o menu de seleção de cores
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cores, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*Função que identifica a cor selecionada pelo usuário
    * e passa para a classe "mudarCor" fazer a alteração
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.amarelo:
                mudarCor(Color.YELLOW);
                break;
            case R.id.azul:
                mudarCor(Color.BLUE);
                break;
            case R.id.azul_claro:
                mudarCor(Color.CYAN);
                break;
            case R.id.cinza_claro:
                mudarCor(Color.LTGRAY);
                break;
            case R.id.cinza_escuro:
                mudarCor(Color.DKGRAY);
                break;
            case R.id.preto:
                mudarCor(Color.BLACK);
                break;
            case R.id.rosa:
                mudarCor(Color.MAGENTA);
                break;
            case R.id.verde:
                mudarCor(Color.GREEN);
                break;
            case R.id.vermelho:
                mudarCor(Color.RED);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Classe específica para a requisição HTTP que recupera o número aleatório a ser adivinhado
    class RequisicaoHTTP extends AsyncTask<Void, Void, String> {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer buffer = null;

        //Função que realiza a conexão e recuperação dos dados em segundo plano
        @Override
        protected String doInBackground(Void... voids) {

            try {
                //Conexão ao site que informará o número aleatório para o jogo
                URL url = new URL(URL_NUMERO_ALEATORIO);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //Recuperação e decodificação dos dados para manipulação pelo buffer
                inputStream = conexao.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                //Montagem do buffer contendo a string a ser utilizada
                buffer = new StringBuffer();
                String linha;
                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                    buffer.append("\n");
                }

            } catch (MalformedURLException e) {
                //Tratamento de erro em caso de URL mal formada
                e.printStackTrace();
            } catch (IOException e) {
                //Tratamento de erro em caso de outra exceção
                e.printStackTrace();
            }

            //Retorno da String completa com os dados recuperados do site
            return buffer.toString();
        }

        //Função que recupera os dados da função doInBackground e os deixa disponível para manipulação
        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            /*Caso a string recuperada possua a palavra "value"
            * significa que o valor é valido, sendo inserido na variavel "numeroRecebido"
             */
            if (resultado.contains("value")) {
                int ultCaractere = resultado.indexOf("}");
                numeroAleatorioRecebido = Integer.parseInt(resultado.substring(9, ultCaractere));
            }

            /*Caso não tenha a palavra "value" significa erro,
            * então o valor 502 é inserido na variável "numeroRecebido"
             */
            else {
                numeroAleatorioRecebido = 502;
            }
        }
    }

    /*
    * Função para a criação de um novo jogo
    * - Cria uma nova requisição e a executa
    * - Habilita o botão de 'Enviar' e limpa o TextView de Resposta
    * - Reseta o display com a função "reset"
     */
    public void novoJogo() {
        RequisicaoHTTP requisicao = new RequisicaoHTTP();
        requisicao.execute();
        enviar.setEnabled(true);
        resposta.setText("");
        reset();
    }

    /*
    * Função para edição do display
    * - Reseta o painel
    * - Recupera o numero informado pelo usuário e o transforma em String
    * - Pega a string e a transforma em um array de char
    * - De acordo com o tamanho do array, oculta os paineis não utilizados e
    *   formata os demais com o numero informado
     */
    public void display(int n) {

        reset();

        String numero = new StringBuilder(Integer.toString(n)).toString();

        char arrayNumeral[] = numero.toCharArray();

        /*Cada display representa um algarismo:
        * - Quando o número é <100, o primeiro display é ocultado
        * - Quando o número é <10, os dois primeiros displays são ocultados
         */
        if (arrayNumeral.length == 3){
            display1.formatarDisplay(arrayNumeral[0]);
            display2.formatarDisplay(arrayNumeral[1]);
            display3.formatarDisplay(arrayNumeral[2]);
        } else if (arrayNumeral.length == 2){
            display1.esconderDisplay();
            display2.formatarDisplay(arrayNumeral[0]);
            display3.formatarDisplay(arrayNumeral[1]);
        } else {
            display1.esconderDisplay();
            display2.esconderDisplay();
            display3.formatarDisplay(arrayNumeral[0]);
        }
    }

    /*
    * Função para resetar o painel, zerando todos e alterando a visilibidade para Visível
     */
    public void reset(){
        display1.zerarDisplay();
        display2.zerarDisplay();
        display3.zerarDisplay();
        display1.mostrarDisplay();
        display2.mostrarDisplay();
        display3.mostrarDisplay();
    }

    /*
    * Função que recebe a cor selecionada, altera em cada um dos displays e
    * chama a função "display" para atualizar a cor dos numeros atuais
     */
    public void mudarCor(int cor){
        display1.setCor(cor);
        display2.setCor(cor);
        display3.setCor(cor);
        display(numeroValido);
    }

}